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

        // Define sprite sheet frames for states
        Map<Character.SpriteState, Integer> knightFrameCounts = new HashMap<>();
        knightFrameCounts.put(Character.SpriteState.IDLE, 6);     // 6 frames for IDLE
        knightFrameCounts.put(Character.SpriteState.ATTACK, 7);   // 7 frames for ATTACK
        knightFrameCounts.put(Character.SpriteState.HIT, 4);      // 4 frames for HIT
        knightFrameCounts.put(Character.SpriteState.DEATH, 4);    // 4 frames for Death

        // Create and return the knight hero
        return new Hero("Knight", 200, 30, 10, 1, 3,10, 4, 5, 0, "Knight", knightSpriteSheets, knightFrameCounts, false);
    }

    public static Hero createMage(){

        Map<Character.SpriteState, String> mageSpriteSheets = new HashMap<>();
        mageSpriteSheets.put(Character.SpriteState.IDLE, "wizard_idle");
        mageSpriteSheets.put(Character.SpriteState.HIT, "wizard_hurt");
        mageSpriteSheets.put(Character.SpriteState.DEATH, "wizard_death");
        mageSpriteSheets.put(Character.SpriteState.ATTACK, "wizard_attack01");

        Map<Character.SpriteState, Integer> mageFrameCounts = new HashMap<>();
        mageFrameCounts.put(Character.SpriteState.IDLE, 6);
        mageFrameCounts.put(Character.SpriteState.HIT, 4);
        mageFrameCounts.put(Character.SpriteState.DEATH, 4);
        mageFrameCounts.put(Character.SpriteState.ATTACK, 6);

        return new Hero("Wizard", 100, 40, 10, 1, 3,10, 4, 5, 0, "Wizard", mageSpriteSheets, mageFrameCounts, false);
    }

    public static Hero createRanger(){

        Map<Character.SpriteState, String> rangerSpriteSheets = new HashMap<>();
        rangerSpriteSheets.put(Character.SpriteState.IDLE, "archer_idle");
        rangerSpriteSheets.put(Character.SpriteState.HIT, "archer_hurt");
        rangerSpriteSheets.put(Character.SpriteState.DEATH, "archer_death");
        rangerSpriteSheets.put(Character.SpriteState.ATTACK, "archer_attack01");

        Map<Character.SpriteState, Integer> rangerFrameCounts = new HashMap<>();
        rangerFrameCounts.put(Character.SpriteState.IDLE, 6);
        rangerFrameCounts.put(Character.SpriteState.HIT, 4);
        rangerFrameCounts.put(Character.SpriteState.DEATH, 4);
        rangerFrameCounts.put(Character.SpriteState.ATTACK, 9);

        return new Hero("Ranger", 100, 40,10, 1, 3,10, 4, 5, 0, "Ranger", rangerSpriteSheets, rangerFrameCounts, false);
    }

    public static Enemy createGoblin(){
        // Define sprite sheets for the goblin character
        Map<Character.SpriteState, String> goblinSpriteSheets = new HashMap<>();
        goblinSpriteSheets.put(Character.SpriteState.IDLE, "orc_idle");
        goblinSpriteSheets.put(Character.SpriteState.ATTACK, "orc_attack01");
        goblinSpriteSheets.put(Character.SpriteState.HIT, "orc_hurt");
        goblinSpriteSheets.put(Character.SpriteState.DEATH, "orc_death");

        Map<Character.SpriteState, Integer> goblinFrameCounts = new HashMap<>();
        goblinFrameCounts.put(Character.SpriteState.IDLE, 6);
        goblinFrameCounts.put(Character.SpriteState.ATTACK, 6);
        goblinFrameCounts.put(Character.SpriteState.HIT, 4);
        goblinFrameCounts.put(Character.SpriteState.DEATH, 4);

        return new Enemy("Goblin", 100, 20, 5, 1, 3,10, 5, 5, 0, false, goblinSpriteSheets, goblinFrameCounts, true);
    }

    public static Enemy createGoblinElite(){
        // Define sprite sheets for the elite goblin character
        Map<Character.SpriteState, String> goblinEliteSpriteSheets = new HashMap<>();
        goblinEliteSpriteSheets.put(Character.SpriteState.IDLE, "elite_orc_idle");
        goblinEliteSpriteSheets.put(Character.SpriteState.ATTACK, "elite_orc_attack01");
        goblinEliteSpriteSheets.put(Character.SpriteState.HIT, "elite_orc_hurt");
        goblinEliteSpriteSheets.put(Character.SpriteState.DEATH, "elite_orc_death");

        Map<Character.SpriteState, Integer> goblinEliteFrameCounts = new HashMap<>();
        goblinEliteFrameCounts.put(Character.SpriteState.IDLE, 6);
        goblinEliteFrameCounts.put(Character.SpriteState.ATTACK, 7);
        goblinEliteFrameCounts.put(Character.SpriteState.HIT, 4);
        goblinEliteFrameCounts.put(Character.SpriteState.DEATH, 4);

        return new Enemy("Goblin Warlord", 150, 25, 10, 1, 3,15, 6, 7, 10, true, goblinEliteSpriteSheets, goblinEliteFrameCounts, true);
    }
}
