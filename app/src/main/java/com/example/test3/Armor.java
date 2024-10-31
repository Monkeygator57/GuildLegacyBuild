package com.example.test3;


public class Armor extends Item {
    private int defenseBonus;
    private ArmorWeight armorWeight;
    private int speedModifier;

    public enum ArmorWeight {
        LIGHT(2),
        MEDIUM(0),
        HEAVY(-3);

        private final int speedModifier;

        ArmorWeight(int speedModifier) {
            this.speedModifier = speedModifier;
        }

        public int getSpeedModifier() {
            return speedModifier;
        }
    }

    public Armor(String name, String description, int value, int weight, int defenseBonus, ArmorWeight armorWeight) {
        super(name, description, value, weight);  // Now matches Item constructor
        this.defenseBonus = defenseBonus;
        this.armorWeight = armorWeight;
        this.speedModifier = armorWeight.getSpeedModifier();
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public ArmorWeight getArmorWeight() {
        return armorWeight;
    }

    public int getSpeedModifier() {
        return speedModifier;
    }
}