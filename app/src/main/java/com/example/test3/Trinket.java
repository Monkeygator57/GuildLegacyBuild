package com.example.test3;

public class Trinket extends Item {
    private int statBonus;

    public Trinket(String name, String description, int value, int weight, int statBonus) {
        super(name, description, value, weight);
        this.statBonus = statBonus;
    }

    /*eventually, i'm going to implement the ability for items to have unique status effects
    for now though trinkets will just give a flat bonus to all of your stats just because i don't want to
    get too in the weeds without going too far out of scope
    gotta figure out a good way of incorporating it with easily modifiable external files. maybe
    by having a list of unique effects that the .xml file can refer to? then the code can just
    call to a specific method or something, probably baked into a separate class. open to ideas
    on this.*/

    public int getStatBonus() {
        return statBonus;
    }
}

