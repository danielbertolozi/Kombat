package com.bertolozi.Player;

import com.bertolozi.Control.KeyListener;
import com.bertolozi.Server.ClientConnector;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ServerPlayer {
    private int x = 0;
    private int y = 0;
    private int w = 90;
    private int h = 127;
    private ClientConnector clientConnector;
    private KeyListener keyListener;
    private int id;
    private static final int SPEED = 8;

    public ServerPlayer() {
        this.id = this.hashCode();
        this.clientConnector = ClientConnector.getInstance();
    }

    private void move() {
        if (keyListener.get("RIGHT")) {
            x += SPEED;
        }
        if (keyListener.get("LEFT")) {
            x -= SPEED;
        }
        if (keyListener.get("DOWN")) {
            y += SPEED;
        }
        if (keyListener.get("UP")) {
            y -= SPEED;
        }
    }

    public Runnable getPlayerActionsHandler(ServerPlayer player, BufferedReader in, PrintWriter out) {
        // TODO see if I can use ClientConnector for something here
        return () -> {
            keyListener = new KeyListener(in);
            keyListener.start();
            try {
                while (true) {
                    Thread.sleep(30);
                    player.move();
                    syncMovement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private void syncMovement() {
        clientConnector.broadcast(this.id + "-" + x + "_" + y);
    }

    public int getId() {
        return id;
    }
}
