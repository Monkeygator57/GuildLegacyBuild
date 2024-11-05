package com.example.test3;

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

    public Job(String name, int baseHealth, int healthGrowth, int baseStrength, int strengthGrowth, int baseIntelligence, int intelligenceGrowth, int baseAgility, int agilityGrowth, int attackRange){
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

}
