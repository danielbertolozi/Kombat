package com.bertolozi.Player;

public class ClientPlayer {
    public PlayerCharacter playerCharacter;
    private int id;

    public ClientPlayer() {
        this.playerCharacter = new PlayerCharacter();
        this.playerCharacter.setup();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void move(int x, int y) {
        this.playerCharacter.move(x, y);
    }
}
