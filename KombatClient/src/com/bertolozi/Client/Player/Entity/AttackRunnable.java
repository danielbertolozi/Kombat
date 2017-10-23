package com.bertolozi.Client.Player.Entity;

public class AttackRunnable implements Runnable {
    private final Player player;

    public AttackRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        this.player.setAttack(true);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.player.setAttack(false);
    }
}
