package com.example.test3;

public class DamageOverTimeTrinket extends Trinket {
    private static final int DOT_DAMAGE = 5;
    private static final int DOT_DURATION = 3;

    public DamageOverTimeTrinket(String name, String description, int value, int weight) {
        super(name, description, value, weight, 0);
    }

    public int getDotDamage() {
        return DOT_DAMAGE;
    }

    public int getDotDuration() {
        return DOT_DURATION;
    }
}
