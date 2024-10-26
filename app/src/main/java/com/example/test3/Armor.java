public class Armor extends Item {
    private int defenseBonus;
    private String armorType; //i figure we can use this to add a bonus (for light armor) and a penalty (for heavy armor) to speed

    public Armor(String name, String description, int value, int weight, int defenseBonus, String armorType) {
        super(name, description, value);
        this.defenseBonus = defenseBonus;
        this.armorType = armorType;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public String getArmorType() {
        return armorType;
    }
}