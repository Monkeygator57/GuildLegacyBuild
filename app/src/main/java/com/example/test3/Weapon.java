package com.example.test3;

public class Weapon extends Item {
    private int attackBonus;

    public Weapon(String name, String description, int value, int weight, int attackBonus) {
        super(name, description, value, weight);  // Now matches Item constructor
        this.attackBonus = attackBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }
}