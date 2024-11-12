package com.example.test3;

import android.util.Log;
import android.util.Pair;

public class BattleCharacter {
    private Character character; // Can be Hero or Enemy
    private SpriteSheetImageView view;
    private int row;
    private int col;

    public BattleCharacter(Character character, SpriteSheetImageView view, int row, int col) {
        this.character = character;
        this.view = view;
        this.row = row;
        this.col = col;
    }

    public Character getCharacter() {
        return character;
    }

    public SpriteSheetImageView getView() {
        return view;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isHero() {
        return character instanceof Hero;
    }

    public boolean isAlive() {
        return character.isAlive();
    }

    // Method for the character to take action
    public void takeAction(BattleManager battleManager) {
        if (!isAlive()) return;

        Log.d("BattleCharacter", character.getName() + " is taking action.");
        Log.d("BattleCharacter", character.getName() + " is at position: (" + row + ", " + col + ")");

        // Find the closest enemy
        BattleCharacter target = battleManager.findClosestEnemy(this);

        if (target != null) {
            Log.d("BattleCharacter", character.getName() + " found target: " + target.getCharacter().getName());
            Log.d("BattleCharacter", target.getCharacter().getName() + " is at position: (" + target.getRow() + ", " + target.getCol() + ")");

            // Use Chebyshev Distance
            int distance = Math.max(Math.abs(row - target.getRow()), Math.abs(col - target.getCol()));
            int attackRange = character.getAttackRange();
            Log.d("BattleCharacter", character.getName() + " is at distance: " + distance + " with attack range: " + attackRange);

            if (distance <= attackRange) {
                // Attack the target
                Log.d("BattleCharacter", character.getName() + " is attacking " + target.getCharacter().getName());
                battleManager.attackCharacter(this, target);
            } else {
                // Try to move towards the target
                Log.d("BattleCharacter", character.getName() + " is moving towards " + target.getCharacter().getName());
                moveTowardsTarget(battleManager, target, () -> {
                    // After movement, check if within attack range
                    int newDistance = Math.max(Math.abs(row - target.getRow()), Math.abs(col - target.getCol()));
                    Log.d("BattleCharacter", character.getName() + " is at new distance: " + newDistance + " after moving.");
                    if (newDistance <= attackRange) {
                        Log.d("BattleCharacter", character.getName() + " is attacking " + target.getCharacter().getName() + " after moving.");
                        battleManager.attackCharacter(this, target);
                    } else {
                        Log.d("BattleCharacter", character.getName() + " could not get within attack range after moving.");
                    }
                });
            }
        } else {
            Log.d("BattleCharacter", character.getName() + " found no target.");
        }
    }

    // Method to move towards a target
    private void moveTowardsTarget(BattleManager battleManager, BattleCharacter target, Runnable afterMoveAction) {
        // Use asynchronous pathfinding to determine next position
        battleManager.findNextPositionTowardsAsync(this, target, nextPosition -> {
            if (nextPosition != null) {
                int nextRow = nextPosition.first;
                int nextCol = nextPosition.second;

                // Check if the new position is occupied
                if (!battleManager.getCharacterController().isPositionOccupied(nextRow, nextCol)) {
                    battleManager.moveCharacter(this, nextPosition);
                    Log.d("BattleCharacter", character.getName() + " moved to (" + nextRow + ", " + nextCol + ")");

                    // Update the character's position
                    setPosition(nextRow, nextCol);
                } else {
                    Log.d("BattleCharacter", character.getName() + " cannot move to (" + nextRow + ", " + nextCol + ") - position occupied.");
                }
            } else {
                Log.d("BattleCharacter", character.getName() + " cannot find path to target.");
            }

            // Execute the action after movement attempt
            afterMoveAction.run();
        });
    }
}
