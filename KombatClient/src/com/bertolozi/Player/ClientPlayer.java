package com.bertolozi.Player;

public class ClientPlayer {
    public PlayerCharacter character;
    private int id;

    public ClientPlayer() {
        this.character = new PlayerCharacter();
        this.character.setup();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void move(int x, int y) {
        this.character.move(x, y);
    }
}
