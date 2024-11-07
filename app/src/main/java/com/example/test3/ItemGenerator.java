package com.example.test3;

import java.util.Random;

public class ItemGenerator {
    private static final String[] weaponNames = {"Rusty Sword", "Wooden Club", "Iron Dagger", "Enchanted Bow"};
    private static final String[] armorNames = {"Leather Tunic", "Chain Mail", "Plate Armor", "Dragonscale Breastplate"};
    private static final int[] weaponAttackBonus = {2, 3, 5, 8};
    private static final int[] armorDefenseBonus = {1, 3, 6, 10};
    private static final Armor.ArmorWeight[] armorWeights = {
            Armor.ArmorWeight.LIGHT, Armor.ArmorWeight.MEDIUM, Armor.ArmorWeight.HEAVY
    };

    private static final Random random = new Random();

    public static Weapon generateWeapon() {
        int index = random.nextInt(weaponNames.length);
        return new Weapon(
                weaponNames[index],
                "A basic weapon.",
                random.nextInt(50) + 50, // Value
                random.nextInt(5) + 1,  // Weight
                weaponAttackBonus[index]
        );
    }

    public static Armor generateArmor() {
        int index = random.nextInt(armorNames.length);
        return new Armor(
                armorNames[index],
                "Protective gear.",
                random.nextInt(100) + 100, // Value
                random.nextInt(10) + 5,   // Weight
                armorDefenseBonus[index],
                armorWeights[random.nextInt(armorWeights.length)]
        );
    }
}