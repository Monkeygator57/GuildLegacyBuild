package com.example.test3;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FloorFactory {

    public static Floor createFloor1(Context context) {
        // Define enemies for Floor 1
        Enemy enemy1 = CharacterFactory.createGoblin();
        Enemy enemy2 = CharacterFactory.createGoblin();
        Enemy enemy3 = CharacterFactory.createGoblin();

        List<Enemy> floor1Enemies = new ArrayList<>();
        floor1Enemies.add(enemy1);
        floor1Enemies.add(enemy2);
        floor1Enemies.add(enemy3);

        // Create SpriteSheetImageView for each enemy
        SpriteSheetImageView enemyView1 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView enemyView2 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView enemyView3 = new SpriteSheetImageView(context, null);
        List<SpriteSheetImageView> floor1EnemyViews = new ArrayList<>();
        floor1EnemyViews.add(enemyView1);
        floor1EnemyViews.add(enemyView2);
        floor1EnemyViews.add(enemyView3);

        // Define positions for each enemy
        List<Pair<Integer, Integer>> floor1EnemyPositions = new ArrayList<>();
        floor1EnemyPositions.add(new Pair<>(2, 6));  // Position for enemy1
        floor1EnemyPositions.add(new Pair<>(4, 6));  // Position for enemy2
        floor1EnemyPositions.add(new Pair<>(6, 6));

        // Define heroes for Floor 1
        Hero hero1 = CharacterFactory.createWarrior();
        Hero hero2 = CharacterFactory.createWarrior();
        Hero hero3 = CharacterFactory.createMage();
        Hero hero4 = CharacterFactory.createRanger();
        Hero hero5 = CharacterFactory.createRanger();

        List<Hero> floor1Heroes = new ArrayList<>();
        floor1Heroes.add(hero1);
        floor1Heroes.add(hero2);
        floor1Heroes.add(hero3);
        floor1Heroes.add(hero4);
        floor1Heroes.add(hero5);

        // Create SpriteSheetImageView for each hero
        SpriteSheetImageView heroView1 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView2 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView3 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView4 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView5 = new SpriteSheetImageView(context, null);
        List<SpriteSheetImageView> floor1HeroViews = new ArrayList<>();
        floor1HeroViews.add(heroView1);
        floor1HeroViews.add(heroView2);
        floor1HeroViews.add(heroView3);
        floor1HeroViews.add(heroView4);
        floor1HeroViews.add(heroView5);

        // Define positions for each hero
        List<Pair<Integer, Integer>> floor1HeroPositions = new ArrayList<>();
        floor1HeroPositions.add(new Pair<>(3, 2));  // Position for hero1
        floor1HeroPositions.add(new Pair<>(5, 2));  // Position for hero2
        floor1HeroPositions.add(new Pair<>(4, 1));
        floor1HeroPositions.add(new Pair<>(2, 1));
        floor1HeroPositions.add(new Pair<>(6, 1));

        // Set the floor number
        Floor.setFloorNumber(1);

        // Create Floor 1 with heroes, enemies, views, and positions
        return new Floor(
                1,
                floor1Heroes,
                floor1HeroViews,
                floor1HeroPositions,
                floor1Enemies,
                floor1EnemyViews,
                floor1EnemyPositions
        );
    }

    public Floor createFloor(Context context, int floorNumber){

        List<Enemy> enemies = new ArrayList<>();
        List<SpriteSheetImageView> enemyViews = new ArrayList<>();
        List<Pair<Integer, Integer>> enemyPositions = new ArrayList<>();


        // Set the maximum number of enemies to 5
        int maxEnemies = 5;

        // If it's a boss floor (every 10th floor)
        if (floorNumber % 10 == 0) {
            // Create a boss enemy
            Enemy boss = CharacterFactory.createGoblinRiderBoss(); // Create a boss enemy
            enemies.add(boss);

            // Create the view for the boss
            SpriteSheetImageView bossView = new SpriteSheetImageView(context, null);
            enemyViews.add(bossView);

            // Define a position for the boss (e.g., center of the battlefield)
            enemyPositions.add(new Pair<>(4, 6)); // Position the boss centrally or at a challenging spot
        } else {
            // Regular floor: Define enemies based on the floor number
            for (int i = 0; i < maxEnemies; i++) {
                Enemy enemy;

                // Create a harder enemy if the floor number is high enough
                if (floorNumber >= 3 && i >= 2) {
                    // Example: On floors 3 and above, the last 3 enemies are harder (like Orcs)
                    enemy = CharacterFactory.createGoblinElite(); // Harder enemy type (like an Orc)
                } else {
                    enemy = CharacterFactory.createGoblin(); // Default enemy (Goblin)
                }

                enemies.add(enemy);

                // Create the view for the enemy
                SpriteSheetImageView enemyView = new SpriteSheetImageView(context, null);
                enemyViews.add(enemyView);

                // Define a position for the enemy (positioning logic can be made more complex as needed)
                enemyPositions.add(new Pair<>(2 + i, 6)); // Example: Enemies positioned in a line
            }
        }

        // Define heroes for Floor 1
        Hero hero1 = CharacterFactory.createWarrior();
        Hero hero2 = CharacterFactory.createWarrior();
        Hero hero3 = CharacterFactory.createMage();
        Hero hero4 = CharacterFactory.createRanger();
        Hero hero5 = CharacterFactory.createRanger();

        List<Hero> floor1Heroes = new ArrayList<>();
        floor1Heroes.add(hero1);
        floor1Heroes.add(hero2);
        floor1Heroes.add(hero3);
        floor1Heroes.add(hero4);
        floor1Heroes.add(hero5);

        // Create SpriteSheetImageView for each hero
        SpriteSheetImageView heroView1 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView2 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView3 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView4 = new SpriteSheetImageView(context, null);
        SpriteSheetImageView heroView5 = new SpriteSheetImageView(context, null);
        List<SpriteSheetImageView> floor1HeroViews = new ArrayList<>();
        floor1HeroViews.add(heroView1);
        floor1HeroViews.add(heroView2);
        floor1HeroViews.add(heroView3);
        floor1HeroViews.add(heroView4);
        floor1HeroViews.add(heroView5);

        // Define positions for each hero
        List<Pair<Integer, Integer>> heroStartingPositions = new ArrayList<>();
        heroStartingPositions.add(new Pair<>(3, 2));  // Position for hero1
        heroStartingPositions.add(new Pair<>(5, 2));  // Position for hero2
        heroStartingPositions.add(new Pair<>(4, 1));
        heroStartingPositions.add(new Pair<>(2, 1));
        heroStartingPositions.add(new Pair<>(6, 1));

        return new Floor(
                floorNumber,
                floor1Heroes,
                floor1HeroViews,
                heroStartingPositions,
                enemies,
                enemyViews,
                enemyPositions
        );
    }
}
