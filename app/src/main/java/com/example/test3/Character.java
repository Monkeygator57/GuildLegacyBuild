package com.example.test3;


// Parent Class
public abstract class Character {
    protected String name;
    protected int health;
    protected int attackPower;
    protected int defense;
    protected int baseSpeed;
    protected Weapon equippedWeapon;
    protected Armor equippedArmor;
    protected Trinket equippedTrinket;


    public Character(String name, int health, int attackPower, int defense, int baseSpeed) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.baseSpeed = baseSpeed;
        this.equippedWeapon = null;
        this.equippedArmor = null;
        this.equippedTrinket = null;
    }

    // Method for attack
    public abstract void attack(Character target);


    // Modified takeDamage method to use total defense
    public void takeDamage(int damage) {
        int damageTaken = Math.max(damage - getTotalDefense(), 0);
        health -= damageTaken;
        System.out.println(name + " takes " + damageTaken + " damage! Health now: " + health);
    }


    // Equipment methods
    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
        System.out.println(name + " equipped " + weapon.getName());
    }

    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
        System.out.println(name + " equipped " + armor.getName());
    }

    public void equipTrinket(Trinket trinket) {
        this.equippedTrinket = trinket;
        System.out.println(name + " equipped " + trinket.getName());
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

    // Modified getters to include equipment bonuses
    public int getTotalAttackPower() {
        int totalAttack = attackPower;
        if (equippedWeapon != null) {
            totalAttack += equippedWeapon.getAttackBonus();
        }
        if (equippedTrinket != null) {
            totalAttack += equippedTrinket.getStatBonus();
        }
        return totalAttack;
    }

    public int getTotalDefense() {
        int totalDefense = defense;
        if (equippedArmor != null) {
            totalDefense += equippedArmor.getDefenseBonus();
        }
        if (equippedTrinket != null) {
            totalDefense += equippedTrinket.getStatBonus();
        }
        return totalDefense;
    }

    public int getTotalSpeed() {
        int totalSpeed = baseSpeed;
        if (equippedArmor != null) {
            totalSpeed += equippedArmor.getSpeedModifier();
        }
        if (equippedTrinket != null) {
            totalSpeed += equippedTrinket.getStatBonus();
        }
        return Math.max(1, totalSpeed); // Speed cannot go below 1
    }
}

