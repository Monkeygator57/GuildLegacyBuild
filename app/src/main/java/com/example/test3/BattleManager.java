package com.example.test3;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BattleManager {
    private final List<Hero> heroes = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<SpriteSheetImageView> heroViews = new ArrayList<>();
    private final List<SpriteSheetImageView> enemyViews = new ArrayList<>();
    public static final List<BattleCharacter> allCharacters = new ArrayList<>();
    private final CharacterController characterController;
    private FloorFactory floorFactory;

    private int currentFloorNumber = Floor.floorNumber;
    private final Handler handler = new Handler();
    private final int delayBetweenActions = 1000;  // Delay between actions in milliseconds
    private boolean isBattleInProgress = false;
    private final GridBattleActivity gridBattleActivity;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Constructor to accept CharacterController and FloorFactory
    public BattleManager(GridBattleActivity gridBattleActivity, CharacterController characterController, FloorFactory floorFactory) {
        //this.context = context;
        this.characterController = characterController;
        this.floorFactory = floorFactory;
        this.gridBattleActivity = gridBattleActivity;
    }

    // Public getter for charactercontroller
    public CharacterController getCharacterController() {
        return characterController;
    }


    // Helper method to clear previous floor data
    private void clearPreviousFloorData() {
        // Remove all characters from the grid
        for (BattleCharacter character : allCharacters) {
            characterController.removeCharacter(character.getRow(), character.getCol());
        }

        // Clear all character lists
        heroes.clear();
        enemies.clear();
        heroViews.clear();
        enemyViews.clear();
        allCharacters.clear();

        // Log the clearing of previous data
        Log.d("BattleManager", "Cleared previous floor data from grid and lists.");
    }

    // Start new floor
    public void startNewFloor(Floor floor) {
        // Clear existing lists to avoid residual data
        clearPreviousFloorData();

        // Populate heroes, enemies, and corresponding views from the Floor object
        heroes.addAll(floor.getHeroes());
        enemies.addAll(floor.getEnemies());


        // Logging to confirm the setup
        Log.d("BattleManager", "BattleManager setup - Number of heroes: " + heroes.size());
        Log.d("BattleManager", "BattleManager setup - Number of enemies: " + enemies.size());

        // Add heroes to the grid and allCharacters list
        for (int i = 0; i < heroes.size(); i++) {
            Pair<Integer, Integer> position = floor.getHeroPositions().get(i);
            Hero hero = heroes.get(i);

            int row = position.first;
            int col = position.second;
            characterController.addCharacterToGrid(row, col, hero);

            SpriteSheetImageView heroView = characterController.getCharacterViewAtPosition(row, col);

            // Add to allCharacters
            BattleCharacter battleCharacter = new BattleCharacter(hero, heroView, row, col);
            allCharacters.add(battleCharacter);

            Log.d("BattleManager", "Placed hero at (" + row + ", " + col + ")");
        }

        // Add enemies to the grid and allCharacters list
        for (int i = 0; i < enemies.size(); i++) {
            Pair<Integer, Integer> position = floor.getEnemyPositions().get(i);
            Enemy enemy = enemies.get(i);

            int row = position.first;
            int col = position.second;
            characterController.addCharacterToGrid(row, col, enemy);

            SpriteSheetImageView enemyView = characterController.getCharacterViewAtPosition(row, col);

            // Add to allCharacters
            BattleCharacter battleCharacter = new BattleCharacter(enemy, enemyView, row, col);
            allCharacters.add(battleCharacter);

            Log.d("BattleManager", "Placed enemy at (" + row + ", " + col + ")");
        }

        // Start the battle
        //startBattle();
    }

    // Start the battle logic
    public void startBattle() {
        if (isBattleInProgress) return;  // Battle is already in progress

        isBattleInProgress = true;
        Log.v("BattleManager", "Battle Start");
        System.out.println("Battle starting: Heroes vs Enemies");

        executeTurn();
    }

    private void executeTurn() {
        if (!isBattleInProgress) {
            Log.d("BattleManager", "Battle is not in progress.");
            return;  // Exit if battle is not in progress
        }

        Log.d("BattleManager", "Executing turn...");

        // Clear any pending tasks to prevent overlap
        handler.removeCallbacksAndMessages(null);

        if (heroes.isEmpty() || enemies.isEmpty()) {
            displayWinner();
            isBattleInProgress = false;
            return;
        }

        // Clone the list to avoid ConcurrentModificationException
        List<BattleCharacter> charactersToAct = new ArrayList<>(allCharacters);

        // Loop through all characters and schedule their actions
        for (int i = 0; i < charactersToAct.size(); i++) {
            BattleCharacter character = charactersToAct.get(i);
            if (!character.isAlive()) continue; // Skip defeated characters

            int delay = i * delayBetweenActions;

            handler.postDelayed(() -> {
                Log.d("BattleManager", "Character " + character.getCharacter().getName() + " is about to take action.");
                character.takeAction(this);
            }, delay);
        }

        // Schedule the next turn after all characters have acted
        handler.postDelayed(() -> {
            Log.d("BattleManager", "Turn complete. Scheduling next turn.");
            executeTurn();
        }, delayBetweenActions * charactersToAct.size());
    }

    // Method to find the next position towards the target using pathfinding
    public Pair<Integer, Integer> findNextPositionTowards(BattleCharacter character, BattleCharacter target) {
        int startRow = character.getRow();
        int startCol = character.getCol();
        int endRow = target.getRow();
        int endCol = target.getCol();

        Set<Pair<Integer, Integer>> occupiedCells = characterController.getOccupiedPositions();

        occupiedCells.remove(new Pair<>(startRow, startCol)); // Exclude the character's current position
        occupiedCells.remove(new Pair<>(endRow, endCol));     // Exclude the target's position

        // Get the grid size from GridManager
        int numRows = characterController.getNumRows();
        int numCols = characterController.getNumCols();

        AStarPathfinder pathfinder = new AStarPathfinder();
        List<Pair<Integer, Integer>> fullPath = pathfinder.findPath(
                startRow, startCol, endRow, endCol, occupiedCells, numRows, numCols
        );

        if (fullPath != null && fullPath.size() > 1) {
            int moveSpeed = character.getCharacter().getMoveSpeed();
            int steps = Math.min(moveSpeed, fullPath.size() - 1); // Exclude starting position
            Pair<Integer, Integer> nextPosition = fullPath.get(steps);
            Log.d("BattleManager", "Path found for " + character.getCharacter().getName() + ": " + fullPath.toString());
            Log.d("BattleManager", character.getCharacter().getName() + " will move " + steps + " steps to position " + nextPosition.toString());
            return nextPosition; // Return the position moveSpeed steps ahead
        } else {
            Log.d("BattleManager", "No path found for " + character.getCharacter().getName());
            return null;
        }
    }

    // Asynchronous version of findNextPositionTowards
    public void findNextPositionTowardsAsync(BattleCharacter character, BattleCharacter target, Consumer<Pair<Integer, Integer>> callback) {
        executorService.execute(() -> {
            Pair<Integer, Integer> nextPosition = findNextPositionTowards(character, target);
            handler.post(() -> {
                callback.accept(nextPosition);
            });
        });
    }

    // Method to move a character on the grid
    public void moveCharacter(BattleCharacter character, Pair<Integer, Integer> newPosition) {
        int oldRow = character.getRow();
        int oldCol = character.getCol();
        int newRow = newPosition.first;
        int newCol = newPosition.second;

        // Update the grid manager
        characterController.moveCharacter(oldRow, oldCol, newRow, newCol, character.getCharacter());

        // Update character's position
        character.setPosition(newRow, newCol);

        Log.d("BattleManager", character.getCharacter().getName() + " moved to (" + newRow + ", " + newCol + ")");
    }

    // Method to handle an attack between characters
    public void attackCharacter(BattleCharacter attacker, BattleCharacter defender) {
        Log.d("BattleManager", attacker.getCharacter().getName() + " is attacking " + defender.getCharacter().getName());

        // Display attack animation
        attacker.getCharacter().setSpriteState(Character.SpriteState.ATTACK);
        attacker.getView().setCharacter(attacker.getCharacter());

        handler.postDelayed(() -> {
            // Perform attack
            attacker.getCharacter().attack(defender.getCharacter());

            // Return attacker to idle
            attacker.getCharacter().setSpriteState(Character.SpriteState.IDLE);
            attacker.getView().setCharacter(attacker.getCharacter());

            if (!defender.getCharacter().isAlive()) {
                // Display defender's death animation
                defender.getCharacter().setSpriteState(Character.SpriteState.DEATH);
                defender.getView().setCharacter(defender.getCharacter());
                Log.d("BattleManager", defender.getCharacter().getName() + " has been defeated.");

                // Remove from grid and allCharacters list
                int defenderRow = defender.getRow();
                int defenderCol = defender.getCol();
                characterController.removeCharacter(defenderRow, defenderCol);
                allCharacters.remove(defender);

                // Remove from specific lists
                if (defender.getCharacter() instanceof Hero) {
                    heroes.remove(defender.getCharacter());  // Remove from heroes list
                } else {
                    enemies.remove(defender.getCharacter()); // Remove from enemies list
                }
            } else {
                // Display defender's hit animation
                defender.getCharacter().setSpriteState(Character.SpriteState.HIT);
                defender.getView().setCharacter(defender.getCharacter());

                handler.postDelayed(() -> {
                    // Return defender to idle
                    defender.getCharacter().setSpriteState(Character.SpriteState.IDLE);
                    defender.getView().setCharacter(defender.getCharacter());
                }, delayBetweenActions);
            }
        }, delayBetweenActions);
    }

    // Method to find the closest enemy
    public BattleCharacter findClosestEnemy(BattleCharacter character) {
        List<BattleCharacter> opponents = character.isHero() ? getEnemies() : getHeroes();
        BattleCharacter closestEnemy = null;
        int closestDistance = Integer.MAX_VALUE;

        for (BattleCharacter opponent : opponents) {
            if (!opponent.isAlive()) continue;

            int distance = Math.abs(character.getRow() - opponent.getRow())
                    + Math.abs(character.getCol() - opponent.getCol());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEnemy = opponent;
            }
        }
        return closestEnemy;
    }

    // Helper methods to get alive heroes and enemies
    public List<BattleCharacter> getHeroes() {
        List<BattleCharacter> heroList = new ArrayList<>();
        for (BattleCharacter character : allCharacters) {
            if (character.isHero() && character.isAlive()) {
                heroList.add(character);
            }
        }
        return heroList;
    }

    public List<BattleCharacter> getEnemies() {
        List<BattleCharacter> enemyList = new ArrayList<>();
        for (BattleCharacter character : allCharacters) {
            if (!character.isHero() && character.isAlive()) {
                enemyList.add(character);
            }
        }
        return enemyList;
    }
//
//    public void advanceToNextFloor(){
//
//        //set the new floor
//        Floor nextFloor = GridBattleActivity.currentFloor;
//
//        //start the ner floor
//        startNewFloor(nextFloor);
//    }

    private void displayWinner() {
        boolean allHeroesDead = true;
        boolean allEnemiesDead = true;

        for (BattleCharacter character : allCharacters) {
            if (character.isAlive()) {
                if (character.isHero()) {
                    allHeroesDead = false;  // If we find an alive hero, set `allHeroesDead` to false
                } else {
                    allEnemiesDead = false;  // If we find an alive enemy, set `allEnemiesDead` to false
                }
            }
            // If neither all heroes nor all enemies are dead, we can stop early
            if (!allHeroesDead && !allEnemiesDead) {
                break;
            }
        }

        if (allHeroesDead) {
            System.out.println("Enemies win the battle!");
        } else if (allEnemiesDead) {
            gridBattleActivity.advanceToNextFloor();
            try{
                MainActivity.warrior.UpdateData();
                MainActivity.mage.UpdateData();
                MainActivity.cleric.UpdateData();
                MainActivity.ranger.UpdateData();
            } catch (FileNotFoundException e){

            }


        }
        handler.removeCallbacks(() -> executeTurn());  // Clear all tasks after the battle
        isBattleInProgress = false;
    }

    public void saveHeroStates(List<Hero> heroes) {
        for (BattleCharacter character : allCharacters) { // save new hero positions after placed
            if (character.getCharacter() instanceof Hero) {
                Hero hero = (Hero) character.getCharacter(); // reset hero health
                hero.resetHealth();
                heroes.add(hero);
            }
        }
    }
}