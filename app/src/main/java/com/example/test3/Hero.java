package com.example.test3;

public class Hero extends Character{
    private int experiencePoints;
    private String heroClass;

    public Hero(String name, int health, int attackPower, int defence, int speed, int strength, int intelligence, int agility, int experiencePoints, String heroClass) {
        super(name, health, attackPower, defence, speed, strength, intelligence, agility);
        this.experiencePoints = experiencePoints;
        this.heroClass = heroClass;
    }

    @Override
    public void attack(Character target) {
        System.out.println(name + " attacks " + target.getName() + " for " + attackPower + " damage!");
        target.takeDamage(attackPower);
    }

    // Level up or experience machanics go here
    public void gainExperience(int xp){
        experiencePoints += xp;
        System.out.println(name + " gains " + experiencePoints + " experience!");
    }
}
