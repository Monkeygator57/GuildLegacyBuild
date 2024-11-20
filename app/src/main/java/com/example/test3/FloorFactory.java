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

        // Define positions for each hero
        List<Pair<Integer, Integer>> floor1HeroPositions = new ArrayList<>();
        floor1HeroPositions.add(new Pair<>(3, 2));  // Position for hero1 warrior
        floor1HeroPositions.add(new Pair<>(5, 2));  // Position for hero2 warrior2
        floor1HeroPositions.add(new Pair<>(4, 0));  // mage
        floor1HeroPositions.add(new Pair<>(2, 1));  // ranger
        floor1HeroPositions.add(new Pair<>(6, 1));  // ranger2

        //MODIFIED above and below

        // Create Floor 1 with heroes, enemies, views, and positions
        return new Floor(
                1,
                floor1Heroes,
                floor1HeroPositions,
                floor1Enemies,
                floor1EnemyPositions
        );
    }
}
