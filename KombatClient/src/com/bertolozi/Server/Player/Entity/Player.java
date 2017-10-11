package com.bertolozi.Server.Player.Entity;

import com.bertolozi.Server.Connection.ClientConnector;
import com.bertolozi.Server.Player.Actions.PlayerActionListener;

import java.io.BufferedReader;

public class Player {
    private ClientConnector connector;
    private PlayerActionListener actionListener;
    public int x = 0;
    public int y = 0;
    private int w = 90;
    private int h = 127;
    private int id;

    public Player() {
        this.id = this.hashCode();
        this.connector = ClientConnector.getInstance();
    }

    public Runnable getActionHandler(BufferedReader in) {
        if (actionListener == null) {
            actionListener = new PlayerActionListener(this, in);
        }
        return this.actionListener;
    }

    public int getId() {
        return id;
    }

    public void disconnect() {
        actionListener.kill();
        connector.removePlayer(this);
    }
}
