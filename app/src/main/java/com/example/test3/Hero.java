package com.example.test3;

import android.util.Log;

import java.util.Map;

public class Hero extends Character{
    private Job job;
    private int level;
    private int currentXP;
    private int xpToNextLevel;
    private static final double XP_GROWTH_RATE = 1.5; // Increase XP requirement by 50% per level
    private int experiencePoints;
    private String heroClass;
    int maxHealth;

    public Hero(String name,Job job, Map<SpriteState, String> spriteSheetResources, Map<SpriteState, Integer> stateFrameCounts, boolean facingLeft) {
        super(name,job.getBaseHealth(), 0, 0, 0,1,3, job.getBaseStrength(),job.getBaseAgility(), job.getBaseIntelligence(), spriteSheetResources, stateFrameCounts, facingLeft);
        this.experiencePoints = experiencePoints;
        this.job = job;
        this.level = 1;
        this.currentXP = 0;
        this.xpToNextLevel = 100; // Initial XP required for level 2
        applyJobStats();
        this.maxHealth = health;
    }

    private void applyJobStats() {
        this.health = job.getBaseHealth() + level * job.getHealthGrowth();  // Increase health based on level
        this.strength = job.getBaseStrength() + level * job.getStrengthGrowth();  // Increase strength based on level
        this.intelligence = job.getBaseIntelligence() + level * job.getIntelligenceGrowth();  // Increase intelligence based on level
        this.agility = job.getBaseAgility() + level * job.getAgilityGrowth();  // Increase agility based on level
        this.attackRange = job.getAttackRange(); // Keep attack range as per job
        this.attackPower = job.getBaseAttackPower() * job.getMainStatValue();
    }

    @Override
    public void attack(Character target) {
        // Implement attack logic
        int damage = this.attackPower; // Adjust as needed
        target.takeDamage(damage);
        Log.d("Hero", this.name + " attacked " + target.getName() + " for " + damage + " damage.");
    }

    public void resetHealth(){
        this.health = maxHealth;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setJob(Job newJob) {
        this.job = newJob;
        applyJobStats();  // Re-apply stats based on the new job
    }

    public String getJobName() {
        return job.getName();
    }

    public int getAttackRange() {
        return job.getAttackRange();
    }

    // Method to gain experience and handle leveling up
    public void gainXP(int xp) {
        currentXP += xp;

        // Check if enough XP to level up
        while (currentXP >= xpToNextLevel) {
            levelUp();
        }
    }

    // Handle leveling up and applying stat increases
    private void levelUp() {
        currentXP -= xpToNextLevel;  // Deduct XP for the level
        level++;  // Increase level
        xpToNextLevel *= XP_GROWTH_RATE;  // Increase XP requirement for next level

        System.out.println(name + " has leveled up to level " + level + "!");

        applyJobStats();  // Recalculate stats based on new level
    }
}
