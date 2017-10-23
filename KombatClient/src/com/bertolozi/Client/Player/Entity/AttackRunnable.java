package com.bertolozi.Client.Player.Entity;

public class AttackRunnable implements Runnable {
    private final Player player;

    AttackRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.setAttack(true);
        player.character.setAttackMode(true);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.setAttack(false);
        player.character.setAttackMode(false);
    }
}
