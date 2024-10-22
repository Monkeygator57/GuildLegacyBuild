package com.example.test3;

import android.util.Log;

public class Battle {
    private Hero hero;
    private Enemy enemy;

    public Battle(Hero hero, Enemy enemy){
        this.hero = hero;
        this.enemy = enemy;
    }

    // Run the battle
    public void start(){
        Log.v("Battle", "Battle Start");
        System.out.println("Battle starting: " + hero.getName() + " vs " + enemy.getName());


        // Turn-based battle loop
        while (hero.isAlive() && enemy.isAlive()){
            // Hero attacks first
            hero.attack(enemy);
            if (!enemy.isAlive()){
                System.out.println(enemy.getName() + " has been defeated!");
                break;
            }

            // Enemy's turn
            enemy.attack(hero);
            if (!hero.isAlive()){
                System.out.println(hero.getName() + " has been defeated!");
            }
        }

        // Check winner
        if (hero.isAlive()){
            System.out.println(hero.getName() + " wins the battle!");
        } else {
            System.out.println(enemy.getName() + " wins the battle!");
        }
    }
}
