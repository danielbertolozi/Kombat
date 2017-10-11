package com.bertolozi.Player;

import com.bertolozi.Control.KeyListener;
import com.bertolozi.Message.MessageTranslator;
import com.bertolozi.Server.ClientConnectionHandler;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ServerPlayer {
    private KeyListener keyListener;
    private ClientConnectionHandler clientConnectionHandler;
    private MessageTranslator message = new MessageTranslator();
    private Thread playerActionListener;
    private int x = 0;
    private int y = 0;
    private int w = 90;
    private int h = 127;
    private int id;
    private static final int SPEED = 8;

    public ServerPlayer() {
        this.id = this.hashCode();
        this.clientConnectionHandler = ClientConnectionHandler.getInstance();
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
        if (playerActionListener == null) {
            playerActionListener = new Thread(() -> {
                keyListener = new KeyListener(in, this);
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
            });
        }
        return this.playerActionListener;
    }

    private void syncMovement() {
        String msg = message.movement(id, x, y);
        clientConnectionHandler.broadcast(msg);
    }

    public int getId() {
        return id;
    }

    public void disconnect() {
        playerActionListener.interrupt();
        keyListener.interrupt();
        clientConnectionHandler.removePlayer(this);
    }
}
