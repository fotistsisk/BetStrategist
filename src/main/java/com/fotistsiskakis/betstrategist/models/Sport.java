package com.fotistsiskakis.betstrategist.models;

public enum Sport {
    FOOTBALL(1),
    BASKETBALL(2);

    private final int id;

    Sport(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
