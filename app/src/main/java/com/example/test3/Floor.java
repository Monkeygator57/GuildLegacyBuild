package com.example.test3;

import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Floor {
    public static int floorNumber;
    private final List<Hero> heroes;
    private final List<SpriteSheetImageView> heroViews;
    private final List<Pair<Integer, Integer>> heroPositions;
    private final List<Enemy> enemies;
    private final List<SpriteSheetImageView> enemyViews;
    private final List<Pair<Integer, Integer>> enemyPositions;

    public Floor(
            int floorNumber,
            List<Hero> heroes,
            List<SpriteSheetImageView> heroViews,
            List<Pair<Integer, Integer>> heroPositions,
            List<Enemy> enemies,
            List<SpriteSheetImageView> enemyViews,
            List<Pair<Integer, Integer>> enemyPositions
    ) {
        floorNumber = floorNumber;
        this.heroes = heroes;
        this.heroViews = heroViews;
        this.heroPositions = heroPositions;
        this.enemies = enemies;
        this.enemyViews = enemyViews;
        this.enemyPositions = enemyPositions;
    }

    public static int getFloorNumber() {
        return floorNumber;
    }

    public static int setFloorNumber(int currentFloorNumber) {
        return floorNumber = currentFloorNumber;
    };

    public static int nextFloorNumber(){
        setFloorNumber(floorNumber + 1);
        return getFloorNumber();
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public List<SpriteSheetImageView> getHeroViews() {
        return heroViews;
    }

    public List<Pair<Integer, Integer>> getHeroPositions() {
        return heroPositions;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<SpriteSheetImageView> getEnemyViews() {
        return enemyViews;
    }

    public List<Pair<Integer, Integer>> getEnemyPositions() {
        return enemyPositions;
    }
}
