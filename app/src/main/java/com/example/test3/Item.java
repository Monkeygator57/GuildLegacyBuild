package com.example.test3;

public abstract class Item {
    protected String name;
    protected String description;
    protected int value;
    protected int weight;

    public Item(String name, String description, int value, int weight) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }
}