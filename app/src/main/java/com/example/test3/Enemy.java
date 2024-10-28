package com.example.test3;

import java.util.Map;

public class Enemy extends Character {
    private int lootValue;
    private boolean isElite;

    public Enemy(String name, int health, int attackPower, int defence,int speed, int strength, int intelligence, int agility, int lootValue, boolean isElite, Map<SpriteState, String> spriteSheetResources, Map<SpriteState, Integer> stateFrameCounts) {
        super (name, health, attackPower, defence, speed, strength, intelligence, agility, spriteSheetResources, stateFrameCounts);
        this.lootValue = lootValue;
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
        setSpriteState(SpriteState.ATTACK);
        System.out.println(name + " attacks " + target.getName() + " for " + attackPower + " damage!");
        target.takeDamage(attackPower);

        setSpriteState(SpriteState.IDLE);
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
