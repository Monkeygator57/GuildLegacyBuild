package com.example.test3;

import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.util.*;

public abstract class Character {
    protected String name;
    protected int health;
    protected int attackPower;
    protected int defense;
    protected int speed;
    protected int moveSpeed;
    protected int strength;
    protected int agility;
    protected int intelligence;
    protected int attackRange;
    protected boolean facingLeft;
    protected SpriteState currentState;
    protected Map<SpriteState, String> spriteSheetResources;
    protected Map<SpriteState, Integer> stateFrameCounts;
    private Pair<Integer, Integer> position; // Character's current position

    private View associatedView; // HERERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR

    public enum SpriteState {
        IDLE(5), ATTACK(3), HIT(2), DEATH(4), MOVING(8);

        private final int frameCount;

        SpriteState(int frameCount) {
            this.frameCount = frameCount;
        }

        public int getFrameCount(){
            return frameCount;
        }
    }

    public Character(String name, int health, int attackPower, int attackRange, int defense, int speed, int moveSpeed, int strength, int agility, int intelligence, Map<SpriteState, String> spriteSheetResources, Map<SpriteState, Integer> stateFrameCounts, boolean facingLeft) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.speed = speed;
        this.moveSpeed = moveSpeed;
        this.facingLeft = facingLeft;
        this.attackRange = attackRange;
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


    // HERERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
    public View getAssociatedView(){
        return associatedView;
    }

    public void setAssociatedView(View associatedView){
        this.associatedView = associatedView;
    }
    // HERERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR


    // Pathfinding method to move towards a target
    public void moveTowards(Pair<Integer, Integer> targetPosition, Set<Pair<Integer, Integer>> occupiedCells, CharacterController characterController) {
        AStarPathfinder pathfinder = new AStarPathfinder();

        // Get the grid size from GridManager
        int numRows = characterController.getNumRows();
        int numCols = characterController.getNumCols();

        // Get path from current position to target, avoiding occupied cells
        List<Pair<Integer, Integer>> fullPath = pathfinder.findPath(
                position.first, position.second,
                targetPosition.first, targetPosition.second,
                occupiedCells, numRows, numCols);

        if (fullPath == null || fullPath.isEmpty()) {
            // No path found or already at destination
            return;
        }

        // Limit path based on move speed
        int steps = Math.min(moveSpeed, fullPath.size() - 1); // Exclude starting position
        List<Pair<Integer, Integer>> limitedPath = fullPath.subList(1, steps + 1);

        // Move along the limited path by updating the character's position on each step
        if (!limitedPath.isEmpty()) {
            Pair<Integer, Integer> nextPosition = limitedPath.get(limitedPath.size() - 1); // The furthest position within moveSpeed
            characterController.moveCharacter(position.first, position.second, nextPosition.first, nextPosition.second, this);
            position = nextPosition;  // Update the character's current position
        }
    }

    // Method for attack
    public abstract void attack(Character target);

    // Method to take damage
    public void takeDamage(int damage) {
        int damageTaken = Math.max(damage - defense, 0); // Adjust for defense
        health -= damageTaken;
        Log.d("Character", name + " takes " + damageTaken + " damage! Health now: " + health);
    }

    // Check if character is alive
    public boolean isAlive() {
        return health > 0;
    }

    // Getters and Setters
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }

    public boolean getFacingLeft() {
        return facingLeft;
    }

    public int getHealth() {
        return health;
    }

    public int getMoveSpeed(){ return moveSpeed; }

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

    public int getAttackRange() { return attackRange; }
}
