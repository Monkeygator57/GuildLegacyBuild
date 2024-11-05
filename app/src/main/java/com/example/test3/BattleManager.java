package com.example.test3;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class BattleManager {
    private final List<Hero> heroes = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private final List<SpriteSheetImageView> heroViews = new ArrayList<>();
    private List<SpriteSheetImageView> enemyViews = new ArrayList<>();
    private final Handler handler = new Handler();
    private final int delayBetweenActions = 500;  // Reduced delay for testing purposes
    private boolean isTurnInProgress = false;
    private int currentFloor = 1;
    private final List<BattleCharacters> sortedCharacters = new ArrayList<>();

    // Queue for actions to be processed
    private final Queue<Runnable> actionQueue = new LinkedList<>();

    private GridManager gridManager;
    private FloorFactory floorFactory;

    // Constructor to accept GridManager and FloorFactory
    public BattleManager(GridManager gridManager, FloorFactory floorFactory) {
        this.gridManager = gridManager;
        this.floorFactory = floorFactory;
    }

    // Overloaded constructor to accept only GridManager
    public BattleManager(GridManager gridManager) {
        this.gridManager = gridManager;
        this.floorFactory = new FloorFactory(); // Provide a default or new instance
    }

    // Method to enqueue an action
    public void enqueueAction(Runnable action) {
        actionQueue.offer(action);
        Log.d("BattleManager", "Action enqueued. Queue size after enqueue: " + actionQueue.size());
        if (!isTurnInProgress) {
            processNextAction();  // Start processing if not already in progress
        }
    }

    // Method to process the next action in the queue
    private void processNextAction() {
        if (actionQueue.isEmpty()) {
            isTurnInProgress = false;  // No more actions to process
            Log.d("BattleManager", "No more actions to process.");
            return;
        }

        isTurnInProgress = true;
        Runnable nextAction = actionQueue.poll();  // Get the next action from the queue

        if (nextAction != null) {
            Log.d("BattleManager", "Processing action. Remaining queue size: " + actionQueue.size());
            handler.postDelayed(() -> {
                try {
                    nextAction.run();  // Execute the action
                    Log.d("BattleManager", "Action executed successfully.");
                } catch (Exception e) {
                    Log.e("BattleManager", "Error executing action: " + e.getMessage());
                } finally {
                    isTurnInProgress = false;  // Mark action as done
                    processNextAction();  // Process the next action in the queue
                }
            }, delayBetweenActions);
        } else {
            isTurnInProgress = false;  // If for some reason no action was retrieved, mark as done
        }
    }

    /// Gather and sort characters by speed
    private void gatherAndSortCharactersBySpeed() {
        sortedCharacters.clear();

        // Check for mismatches between views and characters
        if (heroViews.size() < heroes.size()) {
            Log.e("BattleManager", "Mismatch: heroViews size (" + heroViews.size() + ") is less than heroes size (" + heroes.size() + ")");
        }
        if (enemyViews.size() < enemies.size()) {
            Log.e("BattleManager", "Mismatch: enemyViews size (" + enemyViews.size() + ") is less than enemies size (" + enemies.size() + ")");
        }

        // Add heroes with matching views to sortedCharacters
        for (int i = 0; i < Math.min(heroes.size(), heroViews.size()); i++) {
            SpriteSheetImageView view = heroViews.get(i);
            if (view == null) {
                Log.e("BattleManager", "Hero view is null at index: " + i);
                continue;
            }
            sortedCharacters.add(new BattleCharacters(heroes.get(i), view));
        }

        // Add enemies with matching views to sortedCharacters
        for (int i = 0; i < Math.min(enemies.size(), enemyViews.size()); i++) {
            SpriteSheetImageView view = enemyViews.get(i);
            if (view == null) {
                Log.e("BattleManager", "Enemy view is null at index: " + i);
                continue;
            }
            sortedCharacters.add(new BattleCharacters(enemies.get(i), view));
        }

        // Sort characters by speed in descending order
        sortedCharacters.sort((a, b) -> b.character.getSpeed() - a.character.getSpeed());

        Log.d("BattleManager", "Characters sorted by speed for battle.");
        Log.d("BattleManager", "Total characters sorted by speed: " + sortedCharacters.size());
        Log.d("BattleManager", "Number of heroes: " + heroes.size());
        Log.d("BattleManager", "Number of enemies: " + enemies.size());
    }

    // Start battle sequence using sorted characters
    public void startBattle() {
        gatherAndSortCharactersBySpeed();
        Log.d("BattleManager", "Characters sorted by speed. Queue size before enqueuing: " + actionQueue.size());

        for (BattleCharacters character : sortedCharacters) {
            Log.d("BattleManager", "Enqueuing action for character: " + character.getName());

            // Explicitly create a Runnable to avoid the lambda type error
            enqueueAction(new Runnable() {
                @Override
                public void run() {
                    characterAction(character);
                }
            });
        }

        if (!isTurnInProgress) {
            Log.d("BattleManager", "Starting action processing.");
            processNextAction();
        }
    }

    // Start new floor
    public void startNewFloor(Floor floor) {
        // Clear existing lists to avoid residual data
        heroes.clear();
        enemies.clear();
        heroViews.clear();
        enemyViews.clear();

        // Populate heroes, enemies, and corresponding views from the Floor object
        heroes.addAll(floor.getHeroes());
        enemies.addAll(floor.getEnemies());
        heroViews.addAll(floor.getHeroViews());
        enemyViews.addAll(floor.getEnemyViews());

        // Logging to confirm the setup
        Log.d("BattleManager", "BattleManager setup - Number of heroes: " + heroes.size());
        Log.d("BattleManager", "BattleManager setup - Number of enemies: " + enemies.size());
        Log.d("BattleManager", "BattleManager setup - Number of hero views: " + heroViews.size());
        Log.d("BattleManager", "BattleManager setup - Number of enemy views: " + enemyViews.size());

        // Add heroes to the grid
        for (int i = 0; i < heroes.size(); i++) {
            Pair<Integer, Integer> position = floor.getHeroPositions().get(i);
            Hero hero = heroes.get(i);
            SpriteSheetImageView heroView = heroViews.get(i);

            // Extract row and column from the Pair and call addCharacterToGrid()
            int row = position.first;
            int col = position.second;
            gridManager.addCharacterToGrid(row, col, hero);

            // Optionally log each hero placement
            Log.d("BattleManager", "Placed hero at (" + row + ", " + col + ")");
        }

        // Add enemies to the grid
        for (int i = 0; i < enemies.size(); i++) {
            Pair<Integer, Integer> position = floor.getEnemyPositions().get(i);
            Enemy enemy = enemies.get(i);
            SpriteSheetImageView enemyView = enemyViews.get(i);

            // Extract row and column from the Pair and call addCharacterToGrid()
            int row = position.first;
            int col = position.second;
            gridManager.addCharacterToGrid(row, col, enemy);

            // Optionally log each enemy placement
            Log.d("BattleManager", "Placed enemy at (" + row + ", " + col + ")");
        }

        // Start the battle
        this.startBattle();
    }

    // Character action logic: move or attack
    private void characterAction(BattleCharacters character) {
        Log.d("BattleManager", "Character " + character.character.getName() + " is taking action.");
        BattleCharacters target = findClosestEnemy(character);

        if (target == null) {
            Log.d("BattleManager", character.character.getName() + " has no target.");
            return;  // No target available
        }

        if (isInRange(character, target)) {
            Log.d("BattleManager", character.character.getName() + " is in range to attack " + target.character.getName());
            enqueueAction(() -> attack(character, target));
        } else {
            Log.d("BattleManager", character.character.getName() + " is moving towards " + target.character.getName());
            moveCharacterTowardsTarget(character, target);
        }
    }

    // Find the closest enemy for the character
    private BattleCharacters findClosestEnemy(BattleCharacters character) {
        List<BattleCharacters> enemies = character.character instanceof Hero ? sortedEnemies() : sortedHeroes();
        BattleCharacters closestEnemy = null;
        int closestDistance = Integer.MAX_VALUE;

        Pair<Integer, Integer> currentPosition = getCharacterPosition(character.character);
        if (currentPosition == null) {
            Log.e("BattleManager", "Character position not found for: " + character.character.getName());
            return null;
        }

        for (BattleCharacters enemy : enemies) {
            Pair<Integer, Integer> enemyPosition = getCharacterPosition(enemy.character);
            if (enemyPosition != null) {
                int distance = Math.abs(currentPosition.first - enemyPosition.first) + Math.abs(currentPosition.second - enemyPosition.second);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEnemy = enemy;
                }
            }
        }
        if (closestEnemy != null) {
            Log.d("BattleManager", "Closest enemy to " + character.character.getName() + " is " + closestEnemy.character.getName());
        }
        return closestEnemy;
    }

    // Determine if character is in range to attack
    private boolean isInRange(BattleCharacters character, BattleCharacters target) {
        Pair<Integer, Integer> charPosition = getCharacterPosition(character.character);
        Pair<Integer, Integer> targetPosition = getCharacterPosition(target.character);

        if (charPosition == null || targetPosition == null) {
            Log.e("BattleManager", "Cannot determine range, positions are null.");
            return false;
        }

        int distance = Math.abs(charPosition.first - targetPosition.first) + Math.abs(charPosition.second - targetPosition.second);
        boolean inRange = distance <= character.character.getAttackRange();
        Log.d("BattleManager", character.character.getName() + " is " + (inRange ? "in" : "out of") + " range to attack " + target.character.getName());
        return inRange;
    }

    // Move character towards target using A* pathfinding
    private void moveCharacterTowardsTarget(BattleCharacters character, BattleCharacters target) {
        Pair<Integer, Integer> currentPosition = getCharacterPosition(character.character);
        Pair<Integer, Integer> targetPosition = getCharacterPosition(target.character);

        if (currentPosition == null || targetPosition == null) {
            Log.e("BattleManager", "Cannot move character, positions are null.");
            return;  // Ensure positions are available
        }

        AStarPathfinder pathfinder = new AStarPathfinder();
        Set<Pair<Integer, Integer>> occupiedCells = gridManager.getCharacterObjects().keySet();
        List<Pair<Integer, Integer>> path = pathfinder.findPath(currentPosition.first, currentPosition.second, targetPosition.first, targetPosition.second, occupiedCells);

        if (path != null && !path.isEmpty()) {
            int animationDurationPerStep = 200;  // Set the duration per step for animation
            gridManager.animatePath(character.characterView, currentPosition, path, animationDurationPerStep);
            character.character.setSpriteState(Character.SpriteState.MOVING);
            character.characterView.setCharacter(character.character);  // Update the view
            Log.d("BattleManager", character.character.getName() + " moves along the path.");
        } else {
            Log.d("BattleManager", "No valid path found for " + character.character.getName());
        }
    }

    // Attack method for a character
    private void attack(BattleCharacters attacker, BattleCharacters target) {
        Log.d("BattleManager", attacker.character.getName() + " is attacking " + target.character.getName());
        attacker.character.setSpriteState(Character.SpriteState.ATTACK);
        attacker.characterView.setCharacter(attacker.character);

        target.character.takeDamage(attacker.character.getAttackPower());
        Log.d("BattleManager", target.character.getName() + " took damage, remaining health: " + target.character.getHealth());

        // Handle target's death
        if (target.character.getHealth() <= 0) {
            target.character.setSpriteState(Character.SpriteState.DEATH);
            gridManager.removeCharacter(getCharacterPosition(target.character));
            Log.d("BattleManager", target.character.getName() + " has been defeated!");
        }
    }

    // Helper methods to get sorted enemies and heroes
    private List<BattleCharacters> sortedEnemies() {
        List<BattleCharacters> enemyList = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            enemyList.add(new BattleCharacters(enemies.get(i), enemyViews.get(i)));
        }
        return enemyList;
    }

    private List<BattleCharacters> sortedHeroes() {
        List<BattleCharacters> heroList = new ArrayList<>();
        for (int i = 0; i < heroes.size(); i++) {
            heroList.add(new BattleCharacters(heroes.get(i), heroViews.get(i)));
        }
        return heroList;
    }

    // Helper method to get character position
    private Pair<Integer, Integer> getCharacterPosition(Object character) {
        for (Map.Entry<Pair<Integer, Integer>, Object> entry : gridManager.getCharacterObjects().entrySet()) {
            if (entry.getValue().equals(character)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
