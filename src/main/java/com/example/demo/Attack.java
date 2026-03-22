package com.example.demo;

import java.util.List;

public class Attack {

    private String name;
    private List<String> cost;
    private String damage;
    private String description;

    public Attack() {}

    public Attack(String name, List<String> cost, String damage, String description) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getCost() { return cost; }
    public void setCost(List<String> cost) { this.cost = cost; }

    public String getDamage() { return damage; }
    public void setDamage(String damage) { this.damage = damage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
