package com.example.test3;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BattleManager {
    private final List<Hero> heroes = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private final List<SpriteSheetImageView> heroViews = new ArrayList<>();
    private List<SpriteSheetImageView> enemyViews = new ArrayList<>();
    private final Handler handler = new Handler();
    private final int delayBetweenActions = 1000;
    private boolean isTurnInProgress = false;
    private int currentFloor = 1;

    // Queue for actions to be processed
    private final Queue<Runnable> actionQueue = new LinkedList<>();

    private GridManager gridManager;  // Add GridManager reference

    // Constructor to accept GridManager
    public BattleManager(GridManager gridManager) {
        this.gridManager = gridManager;
    }

    // Method to gather characters from the grid and set up heroes and enemies
    public void gatherCharactersFromGrid() {
        heroes.clear();
        heroViews.clear();
        enemies.clear();
        enemyViews.clear();

        for (Map.Entry<Pair<Integer, Integer>, Object> entry : gridManager.getCharacterObjects().entrySet()) {
            Pair<Integer, Integer> position = entry.getKey();
            Object character = entry.getValue();

            if (character instanceof Hero) {
                heroes.add((Hero) character);
                SpriteSheetImageView heroView = gridManager.getCharacterViewAtPosition(position);
                if (heroView != null) {
                    heroViews.add(heroView);
                }
            } else if (character instanceof Enemy) {
                enemies.add((Enemy) character);
                SpriteSheetImageView enemyView = gridManager.getCharacterViewAtPosition(position);
                if (enemyView != null) {
                    enemyViews.add(enemyView);
                }
            }
        }
    }

    public void startNewFloor(Floor floor) {
        // Clear the grid to remove old characters and reset
        //gridManager.clearGrid();

        // Add heroes to the grid
        for (int i = 0; i < floor.getHeroPositions().size(); i++) {
            Pair<Integer, Integer> position = floor.getHeroPositions().get(i);
            Hero hero = floor.getHeroes().get(i);
            SpriteSheetImageView heroView = floor.getHeroViews().get(i);

            // Extract row and column from the Pair and call addCharacterToGrid()
            int row = position.first;
            int col = position.second;
            gridManager.addCharacterToGrid(row, col, hero);
        }

        // Add enemies to the grid
        for (int i = 0; i < floor.getEnemyPositions().size(); i++) {
            Pair<Integer, Integer> position = floor.getEnemyPositions().get(i);
            Enemy enemy = floor.getEnemies().get(i);
            SpriteSheetImageView enemyView = floor.getEnemyViews().get(i);

            // Extract row and column from the Pair and call addCharacterToGrid()
            int row = position.first;
            int col = position.second;
            gridManager.addCharacterToGrid(row, col, enemy);
        }

        // Gather characters from the grid for the battle manager
        //this.gatherCharactersFromGrid(gridManager);

        // Display the grid again to ensure everything is rendered correctly
        //gridManager.displayCharacterGrid();

        // Start the battle
        this.startBattle();
    }

    private void startBattle() {
        Log.v("BattleManager", "Battle Start");
        System.out.println("Battle starting with " + heroes.size() + " heroes and " + enemies.size() + " enemies.");
        executeNextAction();
    }

    private void resetHeroHealth() {
        for (Hero hero : heroes) {
            hero.resetHealth();  // Ensure each hero has a `resetHealth` method to restore initial health
        }
    }

    private void executeNextAction() {
        if (isTurnInProgress) return;  // Prevent overlapping actions
        isTurnInProgress = true;

        // Clear any pending tasks to prevent overlap
        handler.removeCallbacksAndMessages(null);

        // Process actions in the queue
        if (!actionQueue.isEmpty()) {
            Runnable action = actionQueue.poll();
            action.run();  // Run the action
        } else {
            // Determine next action if queue is empty
            if (!heroes.isEmpty() && !enemies.isEmpty()) {
                Hero hero = heroes.get(0);  // Example: pick the first hero
                Enemy enemy = enemies.get(0);  // Example: pick the first enemy
                queueAction(() -> heroAttack(hero, enemy));
            } else {
                displayWinner();
                isTurnInProgress = false;
                return;
            }
        }

        // Set up for the next action in the queue
        handler.postDelayed(() -> {
            isTurnInProgress = false;
            executeNextAction();
        }, delayBetweenActions);
    }

    private void queueAction(Runnable action) {
        actionQueue.add(action);
    }

    private void heroAttack(Hero hero, Enemy enemy) {
        SpriteSheetImageView heroView = heroViews.get(heroes.indexOf(hero));
        hero.setSpriteState(Character.SpriteState.ATTACK);
        heroView.setCharacter(hero);  // Show attack animation
        hero.attack(enemy);

        handler.postDelayed(() -> {
            hero.setSpriteState(Character.SpriteState.IDLE);
            heroView.setCharacter(hero);  // Return to idle

            if (!enemy.isAlive()) {
                queueAction(() -> enemyDeath(enemy));
            } else {
                queueAction(() -> enemyHit(enemy));
            }
        }, delayBetweenActions);
    }

    private void enemyHit(Enemy enemy) {
        SpriteSheetImageView enemyView = enemyViews.get(enemies.indexOf(enemy));
        enemy.setSpriteState(Character.SpriteState.HIT);
        enemyView.setCharacter(enemy);  // Show hit animation
    }

    private void enemyAttack(Enemy enemy, Hero hero) {
        SpriteSheetImageView enemyView = enemyViews.get(enemies.indexOf(enemy));
        enemy.setSpriteState(Character.SpriteState.ATTACK);
        enemyView.setCharacter(enemy);  // Show attack animation
        enemy.attack(hero);

        handler.postDelayed(() -> {
            enemy.setSpriteState(Character.SpriteState.IDLE);
            enemyView.setCharacter(enemy);  // Return to idle

            if (!hero.isAlive()) {
                queueAction(() -> heroDeath(hero));
            } else {
                queueAction(() -> heroHit(hero));
            }
        }, delayBetweenActions);
    }

    private void heroHit(Hero hero) {
        SpriteSheetImageView heroView = heroViews.get(heroes.indexOf(hero));
        hero.setSpriteState(Character.SpriteState.HIT);
        heroView.setCharacter(hero);  // Show hit animation
    }

    private void heroDeath(Hero hero) {
        SpriteSheetImageView heroView = heroViews.get(heroes.indexOf(hero));
        hero.setSpriteState(Character.SpriteState.DEATH);
        heroView.setCharacter(hero);  // Show death animation
        System.out.println(hero.getName() + " has been defeated!");
        heroes.remove(hero);
    }

    private void enemyDeath(Enemy enemy) {
        SpriteSheetImageView enemyView = enemyViews.get(enemies.indexOf(enemy));
        enemy.setSpriteState(Character.SpriteState.DEATH);
        enemyView.setCharacter(enemy);  // Show death animation
        System.out.println(enemy.getName() + " has been defeated!");
        enemies.remove(enemy);
    }

    private void displayWinner() {
        if (!heroes.isEmpty()) {
            System.out.println("Heroes win the floor!");
            currentFloor++;  // Increment floor for the next setup
        } else if (!enemies.isEmpty()) {
            System.out.println("Enemies win the floor!");
        } else {
            System.out.println("It's a draw!");
        }
        handler.removeCallbacksAndMessages(null);  // Clear all tasks
    }
}
