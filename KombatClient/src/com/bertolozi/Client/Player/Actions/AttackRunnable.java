package com.bertolozi.Client.Player.Actions;

import com.bertolozi.Client.Player.Entity.Player;

public class AttackRunnable implements Runnable {
    private final Player player;

    public AttackRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.setAttack(true);
        player.character.setAttackMode(true);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.setAttack(false);
        player.character.setAttackMode(false);
    }
}
