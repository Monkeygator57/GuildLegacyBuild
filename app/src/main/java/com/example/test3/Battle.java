package com.example.test3;

import android.util.Log;

public class Battle {
    private Hero hero;
    private Enemy enemy;
    private SpriteSheetImageView heroImageView;
    private SpriteSheetImageView enemyImageView;

    // Updated constructor to accept SpriteSheetImageView references
    public Battle(Hero hero, Enemy enemy, SpriteSheetImageView heroImageView, SpriteSheetImageView enemyImageView) {
        this.hero = hero;
        this.enemy = enemy;
        this.heroImageView = heroImageView;
        this.enemyImageView = enemyImageView;
    }

    // Run the battle with animation
    public void start() {
        Log.v("Battle", "Battle Start");
        System.out.println("Battle starting: " + hero.getName() + " vs " + enemy.getName());

        // Turn-based battle loop with animations
        while (hero.isAlive() && enemy.isAlive()) {
            // Hero attacks first
            hero.setSpriteState(Character.SpriteState.ATTACK);
            heroImageView.setCharacter(hero);  // Display the attack animation
            hero.attack(enemy);

            if (!enemy.isAlive()) {
                enemy.setSpriteState(Character.SpriteState.DEATH);
                enemyImageView.setCharacter(enemy);  // Display death animation
                System.out.println(enemy.getName() + " has been defeated!");
                break;
            } else {
                enemy.setSpriteState(Character.SpriteState.HIT);
                enemyImageView.setCharacter(enemy);  // Display hit animation
            }

            // Enemy's turn
            enemy.setSpriteState(Character.SpriteState.ATTACK);
            enemyImageView.setCharacter(enemy);  // Display attack animation
            enemy.attack(hero);

            if (!hero.isAlive()) {
                hero.setSpriteState(Character.SpriteState.DEATH);
                heroImageView.setCharacter(hero);  // Display death animation
                System.out.println(hero.getName() + " has been defeated!");
            } else {
                hero.setSpriteState(Character.SpriteState.HIT);
                heroImageView.setCharacter(hero);  // Display hit animation
            }
        }

        // Final winner display
        if (hero.isAlive()) {
            System.out.println(hero.getName() + " wins the battle!");
        } else {
            System.out.println(enemy.getName() + " wins the battle!");
        }
    }
}
