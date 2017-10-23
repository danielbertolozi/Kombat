package com.bertolozi.Client.Player.Entity;

public class Player {
    public ScreenElement character = new ScreenElement();
    private int id;
    private int health;

    public Player() {
        this.character.setup();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void move(int x, int y) {
        this.character.move(x, y);
    }
}
