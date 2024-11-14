package com.example.test3;

public class ExtraLifeTrinket extends Trinket {
    private boolean active = true;

    public ExtraLifeTrinket(String name, String description, int value, int weight) {
        super(name, description, value, weight, 0);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
