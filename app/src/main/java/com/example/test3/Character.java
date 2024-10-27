package com.example.test3;


// Parent Class
public abstract class Character {
    protected String name;
    protected int health;
    protected int attackPower;
    protected int defense;

    public Character(String name, int health, int attackPower, int defense) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
    }

    // Method for attack
    public abstract void attack(Character target);

    // Method to take damage
    public void takeDamage(int damage) {
        int damageTaken = Math.max(damage - defense, 0); // Damage Can't be Negative
        health -= damageTaken;
        System.out.println(name + " takes " + damageTaken + " damage! Health now: " + health);
    }

    // Check if character is alive
    public boolean isAlive() {
        return health > 0;
    }

    // Gathers and Setters
    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
