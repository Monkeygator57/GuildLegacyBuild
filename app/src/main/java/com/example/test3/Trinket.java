package com.example.test3;

public class Trinket extends Item {
    private int statBonus;

    public Trinket(String name, String description, int value, int weight, int statBonus) {
        super(name, description, value, weight);
        this.statBonus = statBonus;
    }

    public int getStatBonus() {
        return statBonus;
    }
}