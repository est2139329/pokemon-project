package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/search")
    public List<Card> searchByName(@RequestParam String name) {
        return cardService.searchByName(name);
    }

    @GetMapping("/{id}")
    public Card getById(@PathVariable String id) {
        return cardService.getById(id);
    }

    @GetMapping("/filter/type")
    public List<Card> filterByType(@RequestParam String type) {
        return cardService.filterByType(type);
    }

    @GetMapping("/filter/set")
    public List<Card> filterBySet(@RequestParam String set) {
        return cardService.filterBySet(set);
    }

    @GetMapping("/filter/rarity")
    public List<Card> filterByRarity(@RequestParam String rarity) {
        return cardService.filterByRarity(rarity);
    }
}
