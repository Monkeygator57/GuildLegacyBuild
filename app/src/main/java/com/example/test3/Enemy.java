package com.example.test3;

import android.util.Log;

import java.util.Map;

public class Enemy extends Character {
    private int lootValue;
    private boolean isElite;

    public Enemy(String name, int health, int attackPower, int attackRange, int defence, int speed, int moveSpeed, int strength, int intelligence, int agility, int levelValue, boolean isElite, Map<SpriteState, String> spriteSheetResources, Map<SpriteState, Integer> stateFrameCounts, boolean facingLeft) {
        super (name, health, attackPower, attackRange, defence, speed, moveSpeed, strength, intelligence, agility, spriteSheetResources, stateFrameCounts, facingLeft);
        this.lootValue = levelValue;
        this.isElite = isElite;

        // Boost states if the enemy is elite
        if (isElite) {
            this.health += 50;
            this.attackPower += 10;
            this.defense += 5;
        }
    }

    @Override
    public void attack(Character target) {
        // Implement attack logic
        int damage = this.attackPower; // Adjust as needed
        target.takeDamage(damage);
        Log.d("Enemy", this.name + " attacked " + target.getName() + " for " + damage + " damage.");
    }

    // Loot System mechanics goes here
    public int getLootValue() {
        return lootValue;
    }

    // Check if enemy is elite
    public boolean isElite() {
        return isElite;
    }

}
