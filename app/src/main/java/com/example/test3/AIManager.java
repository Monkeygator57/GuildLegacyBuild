package com.example.test3;

import android.util.Pair;
import java.util.List;
import java.util.Set;

public class AIManager {
    private GridManager gridManager;

    public AIManager(GridManager gridManager) {
        this.gridManager = gridManager;
    }

    // Process a turn for a single character
    public void processTurn(Character character, List<Character> enemies) {
        // Step 1: Identify the closest enemy
        Character closestEnemy = findClosestEnemy(character, enemies);
        if (closestEnemy == null) return;  // No enemies to target

        // Step 2: Check if the character is in range to attack
        int distanceToEnemy = calculateDistance(character.getPosition(), closestEnemy.getPosition());
        if (distanceToEnemy <= character.getAttackRange()) {
            // If in range, attack
            attack(character, closestEnemy);
        } else {
            // If not in range, move closer to the enemy
            moveCloser(character, closestEnemy.getPosition());
        }
    }

    // Finds the closest enemy to the character
    private Character findClosestEnemy(Character character, List<Character> enemies) {
        Character closestEnemy = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Character enemy : enemies) {
            int distance = calculateDistance(character.getPosition(), enemy.getPosition());
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closestEnemy = enemy;
            }
        }

        return closestEnemy;
    }

    // Calculates Manhattan distance between two positions
    private int calculateDistance(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2) {
        return Math.abs(pos1.first - pos2.first) + Math.abs(pos1.second - pos2.second);
    }

    // Moves the character closer to the target position
    private void moveCloser(Character character, Pair<Integer, Integer> targetPosition) {
        Set<Pair<Integer, Integer>> occupiedCells = gridManager.getOccupiedPositions();  // Retrieve occupied cells
        occupiedCells.remove(character.getPosition()); // Exclude current character position

        // Move towards the target
        character.moveTowards(targetPosition, occupiedCells, gridManager);
    }

    // Performs an attack action
    private void attack(Character attacker, Character target) {
        System.out.println(attacker.getName() + " attacks " + target.getName());
        target.takeDamage(attacker.getAttackPower());

        // Check if target is defeated
        if (!target.isAlive()) {
            System.out.println(target.getName() + " has been defeated!");
            //gridManager.removeCharacter(target.getPosition());  // Optional method to remove dead characters from the grid
        }
    }
}
