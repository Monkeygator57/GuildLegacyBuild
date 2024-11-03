package com.example.test3;

import java.util.Map;

public class Hero extends Character{
    private int experiencePoints;
    private String heroClass;

    public Hero(String name, int health, int attackPower, int defence, int speed, int moveSpeed, int strength, int intelligence, int agility, int experiencePoints, String heroClass, Map<SpriteState, String> spriteSheetResources, Map<SpriteState, Integer> stateFrameCounts, boolean facingLeft) {
        super(name, health, attackPower, defence, speed, moveSpeed, strength, intelligence, agility, spriteSheetResources, stateFrameCounts, facingLeft);
        this.experiencePoints = experiencePoints;
        this.heroClass = heroClass;
    }

    @Override
    public void attack(Character target) {
        setSpriteState(SpriteState.ATTACK);
        System.out.println(name + " attacks " + target.getName() + " for " + attackPower + " damage!");
        target.takeDamage(attackPower);

        setSpriteState(SpriteState.IDLE);
    }

    // Level up or experience machanics go here
    public void gainExperience(int xp){
        experiencePoints += xp;
        System.out.println(name + " gains " + experiencePoints + " experience!");
    }
}
