package com.example.test3;

import java.util.HashMap;
import java.util.Map;


public class CharacterFactory {
    // Static method to create a knight hero with predefined attributes
    public static Hero createWarrior() {
        // Define sprite sheets for the knight character
        Map<Character.SpriteState, String> knightSpriteSheets = new HashMap<>();
        knightSpriteSheets.put(Character.SpriteState.IDLE, "knight_idle");
        knightSpriteSheets.put(Character.SpriteState.ATTACK, "knight_attack01");
        knightSpriteSheets.put(Character.SpriteState.HIT, "knight_hurt");
        knightSpriteSheets.put(Character.SpriteState.DEATH, "knight_death");
        knightSpriteSheets.put(Character.SpriteState.MOVING, "knight_moving");

        // Define sprite sheet frames for states
        Map<Character.SpriteState, Integer> knightFrameCounts = new HashMap<>();
        knightFrameCounts.put(Character.SpriteState.IDLE, 6);     // 6 frames for IDLE
        knightFrameCounts.put(Character.SpriteState.ATTACK, 7);   // 7 frames for ATTACK
        knightFrameCounts.put(Character.SpriteState.HIT, 4);      // 4 frames for HIT
        knightFrameCounts.put(Character.SpriteState.DEATH, 4);    // 4 frames for Death
        knightFrameCounts.put(Character.SpriteState.MOVING, 8);

        // Create and return the Warrior hero
        Job WarriorJob = createWarriorJob();
        return new Hero("Steve", WarriorJob, knightSpriteSheets, knightFrameCounts, false);
    }

    public static Job createWarriorJob(){
        return new Job("Warrior", 200, 10, 30, 4, 10, 1, 15, 2, 1);
    }
    public static Hero createMage(){

        Map<Character.SpriteState, String> mageSpriteSheets = new HashMap<>();
        mageSpriteSheets.put(Character.SpriteState.IDLE, "wizard_idle");
        mageSpriteSheets.put(Character.SpriteState.HIT, "wizard_hurt");
        mageSpriteSheets.put(Character.SpriteState.DEATH, "wizard_death");
        mageSpriteSheets.put(Character.SpriteState.ATTACK, "wizard_attack01");
        mageSpriteSheets.put(Character.SpriteState.MOVING, "wizard_walk");

        Map<Character.SpriteState, Integer> mageFrameCounts = new HashMap<>();
        mageFrameCounts.put(Character.SpriteState.IDLE, 6);
        mageFrameCounts.put(Character.SpriteState.HIT, 4);
        mageFrameCounts.put(Character.SpriteState.DEATH, 4);
        mageFrameCounts.put(Character.SpriteState.ATTACK, 6);
        mageFrameCounts.put(Character.SpriteState.MOVING, 8);

        Job MageJob = createMageJob();
        return new Hero("Balbus", MageJob, mageSpriteSheets, mageFrameCounts, false);
    }

    public static Job createMageJob(){
        return new Job("Mage", 100, 10, 10, 1, 30, 5, 10, 2, 5);
    }

    public static Hero createRanger(){

        Map<Character.SpriteState, String> rangerSpriteSheets = new HashMap<>();
        rangerSpriteSheets.put(Character.SpriteState.IDLE, "archer_idle");
        rangerSpriteSheets.put(Character.SpriteState.HIT, "archer_hurt");
        rangerSpriteSheets.put(Character.SpriteState.DEATH, "archer_death");
        rangerSpriteSheets.put(Character.SpriteState.ATTACK, "archer_attack01");
        rangerSpriteSheets.put(Character.SpriteState.MOVING, "archer_walk");

        Map<Character.SpriteState, Integer> rangerFrameCounts = new HashMap<>();
        rangerFrameCounts.put(Character.SpriteState.IDLE, 6);
        rangerFrameCounts.put(Character.SpriteState.HIT, 4);
        rangerFrameCounts.put(Character.SpriteState.DEATH, 4);
        rangerFrameCounts.put(Character.SpriteState.ATTACK, 9);
        rangerFrameCounts.put(Character.SpriteState.MOVING, 8);

        Job RangerJob = createRangerJob();
        return new Hero("Ranger", RangerJob, rangerSpriteSheets,rangerFrameCounts, false);
    }

    public static Job createRangerJob(){
        return new Job("Ranger", 150, 10, 15, 2, 10, 1, 30, 5, 3);
    }

    public static Enemy createGoblin(){
        // Define sprite sheets for the goblin character
        Map<Character.SpriteState, String> goblinSpriteSheets = new HashMap<>();
        goblinSpriteSheets.put(Character.SpriteState.IDLE, "orc_idle");
        goblinSpriteSheets.put(Character.SpriteState.ATTACK, "orc_attack01");
        goblinSpriteSheets.put(Character.SpriteState.HIT, "orc_hurt");
        goblinSpriteSheets.put(Character.SpriteState.DEATH, "orc_death");
        goblinSpriteSheets.put(Character.SpriteState.MOVING, "orc_walk");

        Map<Character.SpriteState, Integer> goblinFrameCounts = new HashMap<>();
        goblinFrameCounts.put(Character.SpriteState.IDLE, 6);
        goblinFrameCounts.put(Character.SpriteState.ATTACK, 6);
        goblinFrameCounts.put(Character.SpriteState.HIT, 4);
        goblinFrameCounts.put(Character.SpriteState.DEATH, 4);
        goblinFrameCounts.put(Character.SpriteState.MOVING, 8);

        return new Enemy("Goblin", 100, 20, 1, 5, 1, 3,10, 5, 5, 0, false, goblinSpriteSheets, goblinFrameCounts, true);
    }

    public static Enemy createGoblinElite(){
        // Define sprite sheets for the elite goblin character
        Map<Character.SpriteState, String> goblinEliteSpriteSheets = new HashMap<>();
        goblinEliteSpriteSheets.put(Character.SpriteState.IDLE, "elite_orc_idle");
        goblinEliteSpriteSheets.put(Character.SpriteState.ATTACK, "elite_orc_attack01");
        goblinEliteSpriteSheets.put(Character.SpriteState.HIT, "elite_orc_hurt");
        goblinEliteSpriteSheets.put(Character.SpriteState.DEATH, "elite_orc_death");
        goblinEliteSpriteSheets.put(Character.SpriteState.MOVING, "elite_orc_walk");

        Map<Character.SpriteState, Integer> goblinEliteFrameCounts = new HashMap<>();
        goblinEliteFrameCounts.put(Character.SpriteState.IDLE, 6);
        goblinEliteFrameCounts.put(Character.SpriteState.ATTACK, 7);
        goblinEliteFrameCounts.put(Character.SpriteState.HIT, 4);
        goblinEliteFrameCounts.put(Character.SpriteState.DEATH, 4);
        goblinEliteFrameCounts.put(Character.SpriteState.MOVING, 8);

        return new Enemy("Goblin Warlord", 150, 25, 2, 10, 1, 3,15, 6, 7, 10, true, goblinEliteSpriteSheets, goblinEliteFrameCounts, true);
    }
}
