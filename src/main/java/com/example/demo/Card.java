package com.example.demo;

import java.util.List;

public class Card {

    private String id;
    private String name;
    private String type;
    private int hp;
    private List<Attack> attacks;
    private List<Ability> abilities;
    private List<Weakness> weaknesses;
    private List<Resistance> resistances;
    private int retreatCost;
    private String raritySymbol;
    private String evolutionStage;
    private double marketValue;
    private String imageUrl;

    public Card() {}

    public Card(String name, String type, int hp, List<Attack> attacks, List<Ability> abilities,
                List<Weakness> weaknesses, List<Resistance> resistances, int retreatCost,
                String raritySymbol, String evolutionStage, double marketValue, String imageUrl) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.attacks = attacks;
        this.abilities = abilities;
        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.retreatCost = retreatCost;
        this.raritySymbol = raritySymbol;
        this.evolutionStage = evolutionStage;
        this.marketValue = marketValue;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public List<Attack> getAttacks() { return attacks; }
    public void setAttacks(List<Attack> attacks) { this.attacks = attacks; }

    public List<Ability> getAbilities() { return abilities; }
    public void setAbilities(List<Ability> abilities) { this.abilities = abilities; }

    public List<Weakness> getWeaknesses() { return weaknesses; }
    public void setWeaknesses(List<Weakness> weaknesses) { this.weaknesses = weaknesses; }

    public List<Resistance> getResistances() { return resistances; }
    public void setResistances(List<Resistance> resistances) { this.resistances = resistances; }

    public int getRetreatCost() { return retreatCost; }
    public void setRetreatCost(int retreatCost) { this.retreatCost = retreatCost; }

    public String getRaritySymbol() { return raritySymbol; }
    public void setRaritySymbol(String raritySymbol) { this.raritySymbol = raritySymbol; }

    public String getEvolutionStage() { return evolutionStage; }
    public void setEvolutionStage(String evolutionStage) { this.evolutionStage = evolutionStage; }

    public double getMarketValue() { return marketValue; }
    public void setMarketValue(double marketValue) { this.marketValue = marketValue; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
