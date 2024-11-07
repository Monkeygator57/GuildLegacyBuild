package com.example.test3;

import java.util.Random;

public class ItemGenerator {
    private static final String[] weaponNames = {"Rusty Sword", "Wooden Club", "Iron Dagger", "Enchanted Bow", "Radiant Greatsword", "Jagged Halberd"};
    private static final String[] armorNames = {"Leather Tunic", "Chain Mail", "Plate Armor", "Dragonscale Breastplate", "Shimmering Robes", "Spiked Pauldrons"};
    private static final int[] weaponAttackBonus = {2, 3, 5, 8, 12, 15};
    private static final int[] armorDefenseBonus = {1, 3, 6, 10, 14, 18};
    private static final Armor.ArmorWeight[] armorWeights = {
            Armor.ArmorWeight.LIGHT, Armor.ArmorWeight.MEDIUM, Armor.ArmorWeight.HEAVY
    };

    private static final Random random = new Random();

    public static Weapon generateWeapon() {
        ItemRarity rarity = getRandomRarity();
        int index = random.nextInt(weaponNames.length);
        int value = getItemValue(rarity);
        int weight = getItemWeight(rarity);
        int attackBonus = getItemAttackBonus(rarity, index);

        return new Weapon(
                weaponNames[index],
                "A " + rarity.toString().toLowerCase() + " weapon.",
                value,
                weight,
                attackBonus
        );
    }

    public static Armor generateArmor() {
        ItemRarity rarity = getRandomRarity();
        int index = random.nextInt(armorNames.length);
        int value = getItemValue(rarity);
        int weight = getItemWeight(rarity);
        int defenseBonus = getItemDefenseBonus(rarity, index);
        Armor.ArmorWeight armorWeight = armorWeights[random.nextInt(armorWeights.length)];

        return new Armor(
                armorNames[index],
                "A " + rarity.toString().toLowerCase() + " armor piece.",
                value,
                weight,
                defenseBonus,
                armorWeight
        );
    }

    private static ItemRarity getRandomRarity() {
        ItemRarity[] rarities = ItemRarity.values();
        return rarities[random.nextInt(rarities.length)];
    }

    private static int getItemValue(ItemRarity rarity) {
        int value;
        switch (rarity) {
            case COMMON:
                value = random.nextInt(25) + 25;
                break;
            case UNCOMMON:
                value = random.nextInt(50) + 50;
                break;
            case RARE:
                value = random.nextInt(100) + 100;
                break;
            case LEGENDARY:
                value = random.nextInt(200) + 200;
                break;
            default:
                value = 0;
        }
        return value;
    }

    private static int getItemWeight(ItemRarity rarity) {
        int weight;
        switch (rarity) {
            case COMMON:
                weight = random.nextInt(3) + 1;
                break;
            case UNCOMMON:
                weight = random.nextInt(5) + 3;
                break;
            case RARE:
                weight = random.nextInt(7) + 5;
                break;
            case LEGENDARY:
                weight = random.nextInt(10) + 8;
                break;
            default:
                weight = 0;
        }
        return weight;
    }

    private static int getItemAttackBonus(ItemRarity rarity, int index) {
        int attackBonus = weaponAttackBonus[index];
        switch (rarity) {
            case COMMON:
                attackBonus = (int) (attackBonus * 1.0);
                break;
            case UNCOMMON:
                attackBonus = (int) (attackBonus * 1.25);
                break;
            case RARE:
                attackBonus = (int) (attackBonus * 1.5);
                break;
            case LEGENDARY:
                attackBonus = (int) (attackBonus * 2.0);
                break;
        }
        return attackBonus;
    }

    private static int getItemDefenseBonus(ItemRarity rarity, int index) {
        int defenseBonus = armorDefenseBonus[index];
        switch (rarity) {
            case COMMON:
                defenseBonus = (int) (defenseBonus * 1.0);
                break;
            case UNCOMMON:
                defenseBonus = (int) (defenseBonus * 1.25);
                break;
            case RARE:
                defenseBonus = (int) (defenseBonus * 1.5);
                break;
            case LEGENDARY:
                defenseBonus = (int) (defenseBonus * 2.0);
                break;
        }
        return defenseBonus;
    }

    public enum ItemRarity {
        COMMON,
        UNCOMMON,
        RARE,
        LEGENDARY
    }
}