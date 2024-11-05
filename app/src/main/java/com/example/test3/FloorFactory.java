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
}
