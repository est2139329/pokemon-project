package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CardService {

    private static final Logger log = Logger.getLogger(CardService.class.getName());
    private final WebClient webClient;

    public CardService(@Value("${pokemontcg.api.key:}") String apiKey) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(30));

        WebClient.Builder builder = WebClient.builder()
                .baseUrl("https://api.pokemontcg.io/v2")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(strategies);

        if (apiKey != null && !apiKey.isBlank()) {
            builder.defaultHeader("X-Api-Key", apiKey);
        }

        this.webClient = builder.build();
    }

    public List<Card> searchByName(String name) {
        return fetchCards("name:" + name);
    }

    public List<Card> filterByType(String type) {
        return fetchCards("types:" + type);
    }

    public List<Card> filterBySet(String setName) {
        return fetchCards("set.name:" + setName);
    }

    public List<Card> filterByRarity(String rarity) {
        return fetchCards("rarity:" + rarity);
    }

    public Card getById(String id) {
        try {
            JsonNode response = webClient.get()
                    .uri("/cards/{id}", id)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (response == null || !response.has("data")) return null;
            return mapCard(response.get("data"));
        } catch (Exception e) {
            log.warning("Failed to fetch card by id: " + id + " — " + e.getMessage());
            return null;
        }
    }

    private List<Card> fetchCards(String query) {
        try {
            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/cards")
                            .queryParam("q", query)
                            .queryParam("pageSize", 20)
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            List<Card> cards = new ArrayList<>();
            if (response == null || !response.has("data")) return cards;

            for (JsonNode node : response.get("data")) {
                cards.add(mapCard(node));
            }
            return cards;
        } catch (Exception e) {
            log.warning("Failed to fetch cards for query: " + query + " — " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private Card mapCard(JsonNode node) {
        Card card = new Card();

        card.setId(getText(node, "id"));
        card.setName(getText(node, "name"));
        card.setHp(node.has("hp") ? parseIntSafe(node.get("hp").asText()) : 0);
        card.setRaritySymbol(getText(node, "rarity"));
        card.setEvolutionStage(getText(node, "supertype"));
        card.setImageUrl(node.has("images") ? node.get("images").path("large").asText() : null);

        // Type (first entry in types array)
        if (node.has("types") && node.get("types").isArray() && node.get("types").size() > 0) {
            card.setType(node.get("types").get(0).asText());
        }

        // Retreat cost (count of energy symbols)
        if (node.has("retreatCost") && node.get("retreatCost").isArray()) {
            card.setRetreatCost(node.get("retreatCost").size());
        }

        // Market value
        if (node.has("cardmarket")) {
            JsonNode prices = node.get("cardmarket").path("prices");
            if (prices.has("averageSellPrice")) {
                card.setMarketValue(prices.get("averageSellPrice").asDouble());
            }
        }

        // Attacks
        List<Attack> attacks = new ArrayList<>();
        if (node.has("attacks") && node.get("attacks").isArray()) {
            for (JsonNode a : node.get("attacks")) {
                Attack attack = new Attack();
                attack.setName(getText(a, "name"));
                attack.setDamage(getText(a, "damage"));
                attack.setDescription(getText(a, "text"));
                List<String> cost = new ArrayList<>();
                if (a.has("cost") && a.get("cost").isArray()) {
                    for (JsonNode c : a.get("cost")) cost.add(c.asText());
                }
                attack.setCost(cost);
                attacks.add(attack);
            }
        }
        card.setAttacks(attacks);

        // Abilities
        List<Ability> abilities = new ArrayList<>();
        if (node.has("abilities") && node.get("abilities").isArray()) {
            for (JsonNode a : node.get("abilities")) {
                Ability ability = new Ability();
                ability.setName(getText(a, "name"));
                ability.setType(getText(a, "type"));
                ability.setDescription(getText(a, "text"));
                abilities.add(ability);
            }
        }
        card.setAbilities(abilities);

        // Weaknesses
        List<Weakness> weaknesses = new ArrayList<>();
        if (node.has("weaknesses") && node.get("weaknesses").isArray()) {
            for (JsonNode w : node.get("weaknesses")) {
                weaknesses.add(new Weakness(getText(w, "type"), getText(w, "value")));
            }
        }
        card.setWeaknesses(weaknesses);

        // Resistances
        List<Resistance> resistances = new ArrayList<>();
        if (node.has("resistances") && node.get("resistances").isArray()) {
            for (JsonNode r : node.get("resistances")) {
                resistances.add(new Resistance(getText(r, "type"), getText(r, "value")));
            }
        }
        card.setResistances(resistances);

        return card;
    }

    private String getText(JsonNode node, String field) {
        return node.has(field) ? node.get(field).asText() : null;
    }

    private int parseIntSafe(String value) {
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { return 0; }
    }
}
