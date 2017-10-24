package com.bertolozi.Server.Player.Actions;

import com.bertolozi.Server.Player.Entity.Player;
import com.bertolozi.Server.Player.Service.PlayerService;

import java.io.BufferedReader;

public class PlayerActionListener extends Thread {
    private final BufferedReader in;
    private PlayerMovementHandler movementHandler;
    private Player player;
    private static final int SPEED = 8;

    public PlayerActionListener(Player player, BufferedReader in) {
        this.player = player;
        this.in = in;
    }

    @Override
    public void run() {
        movementHandler = new PlayerMovementHandler(in, player);
        movementHandler.start();
        while (true) {
            try {
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
            move(player);
            PlayerService.syncMovement(player.getId(), player.x, player.y, player.isAttack());
        }
    }

    private void move(Player player) {
        if (movementHandler.get("RIGHT")) {
            player.x += SPEED;
        }
        if (movementHandler.get("LEFT")) {
            player.x -= SPEED;
        }
        if (movementHandler.get("DOWN")) {
            player.y += SPEED;
        }
        if (movementHandler.get("UP")) {
            player.y -= SPEED;
        }
        if (movementHandler.get("ATTACK")) {
            player.attack();
        }
    }

    public void kill() {
        movementHandler.interrupt();
        this.interrupt();
    }
}
