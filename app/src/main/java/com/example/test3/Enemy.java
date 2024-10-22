package com.example.test3;

public class Enemy extends Character {
    private int lootValue;
    private boolean isElite;

    public Enemy(String name, int health, int attackPower, int defence, int lootValue, boolean isElite) {
        super (name, health, attackPower, defence);
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
        System.out.println(name + " attacks " + target.getName() + " for " + attackPower + " damage!");
        target.takeDamage(attackPower);
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
