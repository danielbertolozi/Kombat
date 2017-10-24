package com.bertolozi.Client.Player.Entity;

public class Player {
    public ScreenElement character = new ScreenElement();
    private int id;
    private int health;
    private boolean attack;
    private AttackRunnable attackRunnable;

    // TODO collision detection with https://stackoverflow.com/questions/335600/collision-detection-between-two-images-in-java
    public Player() {
        this.character.setup();
        this.attackRunnable = new AttackRunnable(this);
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

    public boolean isAttacking() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void move(int x, int y) {
        this.character.move(x, y);
    }

    public void attack() {
        Thread attackThread = new Thread(attackRunnable);
        attackThread.start();
    }
}
