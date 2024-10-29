package com.example.test3;

import android.os.Handler;
import android.util.Log;

public class Battle {
    private final Hero hero;
    private final Enemy enemy;
    private final SpriteSheetImageView heroImageView;
    private final SpriteSheetImageView enemyImageView;
    private final Handler handler = new Handler();
    private final int delayBetweenActions = 1000;  // Consistent delay between actions in milliseconds
    private boolean isTurnInProgress = false;  // Flag to control turn sequence

    public Battle(Hero hero, Enemy enemy, SpriteSheetImageView heroImageView, SpriteSheetImageView enemyImageView) {
        this.hero = hero;
        this.enemy = enemy;
        this.heroImageView = heroImageView;
        this.enemyImageView = enemyImageView;
    }

    public void start() {
        Log.v("Battle", "Battle Start");
        System.out.println("Battle starting: " + hero.getName() + " vs " + enemy.getName());

        executeTurn();
    }

    private void executeTurn() {
        if (isTurnInProgress) return;  // Exit if a turn is already in progress
        isTurnInProgress = true;       // Mark turn as in progress

        // Clear any pending tasks to prevent overlap
        handler.removeCallbacksAndMessages(null);

        if (!hero.isAlive() || !enemy.isAlive()) {
            displayWinner();
            isTurnInProgress = false;
            return;
        }

        // Hero's turn to attack
        handler.postDelayed(() -> {
            heroAttack();
        }, delayBetweenActions);
    }

    private void heroAttack() {
        hero.setSpriteState(Character.SpriteState.ATTACK);
        heroImageView.setCharacter(hero);  // Display attack animation
        hero.attack(enemy);

        handler.postDelayed(() -> {
            hero.setSpriteState(Character.SpriteState.IDLE);
            heroImageView.setCharacter(hero);  // Return to idle after attack

            if (!enemy.isAlive()) {
                enemyDeath();
            } else {
                enemyHit();
            }
        }, delayBetweenActions);
    }

    private void enemyHit() {
        enemy.setSpriteState(Character.SpriteState.HIT);
        enemyImageView.setCharacter(enemy);  // Display hit animation

        handler.postDelayed(() -> {
            enemyAttack();
        }, delayBetweenActions);
    }

    private void enemyAttack() {
        enemy.setSpriteState(Character.SpriteState.ATTACK);
        enemyImageView.setCharacter(enemy);  // Display attack animation
        enemy.attack(hero);

        handler.postDelayed(() -> {
            enemy.setSpriteState(Character.SpriteState.IDLE);
            enemyImageView.setCharacter(enemy);  // Return to idle after attack

            if (!hero.isAlive()) {
                heroDeath();
            } else {
                heroHit();
            }
        }, delayBetweenActions);
    }

    private void heroHit() {
        hero.setSpriteState(Character.SpriteState.HIT);
        heroImageView.setCharacter(hero);  // Display hit animation

        handler.postDelayed(() -> {
            isTurnInProgress = false;  // Reset flag to allow the next turn
            executeTurn();             // Restart the turn sequence
        }, delayBetweenActions);
    }

    private void heroDeath() {
        hero.setSpriteState(Character.SpriteState.DEATH);
        heroImageView.setCharacter(hero);  // Display death animation
        System.out.println(hero.getName() + " has been defeated!");
        displayWinner();
    }

    private void enemyDeath() {
        enemy.setSpriteState(Character.SpriteState.DEATH);
        enemyImageView.setCharacter(enemy);  // Display death animation
        System.out.println(enemy.getName() + " has been defeated!");
        displayWinner();
    }

    private void displayWinner() {
        if (hero.isAlive()) {
            System.out.println(hero.getName() + " wins the battle!");
        } else {
            System.out.println(enemy.getName() + " wins the battle!");
        }
        handler.removeCallbacksAndMessages(null);  // Clear all tasks after the battle
    }
}
