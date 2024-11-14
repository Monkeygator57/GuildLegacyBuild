package com.example.test3;

public class HealthRegenTrinket extends Trinket {
    private static final int HEALTH_REGEN = 3;

    public HealthRegenTrinket(String name, String description, int value, int weight) {
        super(name, description, value, weight, 0);
    }

    public int getHealthRegen() {
        return HEALTH_REGEN;
    }
}
