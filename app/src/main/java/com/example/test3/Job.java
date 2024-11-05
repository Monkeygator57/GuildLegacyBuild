package com.example.test3;

enum MainStatType {
    STRENGTH,
    INTELLIGENCE,
    AGILITY
}

public class Job {
    private String name;
    private int baseHealth;
    private int baseStrength;
    private int baseIntelligence;
    private int baseAgility;
    private int attackRange;
    private int healthGrowth;
    private int strengthGrowth;
    private int intelligenceGrowth;
    private int agilityGrowth;
    private int attackPower;
    private MainStatType mainStat;

    public Job(String name, int baseHealth, int healthGrowth, int baseStrength, int strengthGrowth, int baseIntelligence, int intelligenceGrowth, int baseAgility, int agilityGrowth, int attackRange, MainStatType mainStat, int attackPower) {
        this.name = name;
        this.baseHealth = baseHealth;
        this.healthGrowth = healthGrowth;
        this.baseStrength = baseStrength;
        this.strengthGrowth = strengthGrowth;
        this.baseIntelligence = baseIntelligence;
        this.intelligenceGrowth = intelligenceGrowth;
        this.baseAgility = baseAgility;
        this.agilityGrowth = agilityGrowth;
        this.attackRange = attackRange;
        this.attackPower = attackPower;
        this.mainStat = mainStat;
    }

    // Getters for job-specific attributes
    public int getBaseHealth() { return baseHealth; }

    public int getBaseStrength() { return baseStrength; }

    public int getBaseIntelligence() { return baseIntelligence; }

    public int getBaseAgility() { return baseAgility; }

    public int getAttackRange() { return attackRange; }

    public int getHealthGrowth() { return healthGrowth; }

    public int getStrengthGrowth() { return strengthGrowth; }

    public int getIntelligenceGrowth() { return intelligenceGrowth; }

    public int getAgilityGrowth() { return agilityGrowth; }

    public String getName() { return name; }

    // Method to get the value of the main stat
    public int getMainStatValue() {
        switch (mainStat) {
            case STRENGTH:
                return baseStrength;
            case INTELLIGENCE:
                return baseIntelligence;
            case AGILITY:
                return baseAgility;
            default:
                return 0; // Or throw an exception if appropriate
        }
    }

    public int getBaseAttackPower() { return attackPower; }

}
