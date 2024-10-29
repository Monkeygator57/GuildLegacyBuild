package com.example.test3;

import java.util.HashMap;
import java.util.Map;

// Parent Class
public abstract class Character {
    protected String name;
    protected int health;
    protected int attackPower;
    protected int defense;
    protected int speed;
    protected int strength;
    protected int agility;
    protected int intelligence;
    protected SpriteState currentState;
    protected Map<SpriteState, String> spriteSheetResources;
    protected Map<SpriteState, Integer> stateFrameCounts;


    public enum SpriteState {
        IDLE(5), ATTACK(3), HIT(2), DEATH(4);

        private final int frameCount;

        SpriteState(int frameCount) {
            this.frameCount = frameCount;
        }

        public int getFrameCount(){
            return frameCount;
        }
    }



    public Character(String name, int health, int attackPower, int defense, int speed, int strength, int agility, int intelligence, Map<SpriteState, String> spriteSheetResources, Map<SpriteState, Integer> stateFrameCounts) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.speed = speed;
        this.spriteSheetResources = spriteSheetResources != null ? spriteSheetResources : new HashMap<>();
        this.stateFrameCounts = stateFrameCounts != null ? stateFrameCounts : new HashMap<>();
        this.currentState = SpriteState.IDLE;
    }

    public int getFrameCountsForCurrentState(){
        return stateFrameCounts.getOrDefault(currentState, 1);
    }

    public String getCurrentSpriteSheetResource() {
        return spriteSheetResources.getOrDefault(currentState, null);
    }

    public void setSpriteState(SpriteState state){
        this.currentState = state;
    }

    public SpriteState getSpriteState(){
        return currentState;
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

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getSpeed() {
        return speed;
    }
}
