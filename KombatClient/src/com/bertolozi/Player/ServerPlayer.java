package com.bertolozi.Player;

import com.bertolozi.Control.KeyTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ServerPlayer {
    private int x = 0;
    private int y = 0;
    private int w = 90;
    private int h = 127;
    private int id;
    private static final int SPEED = 8;
    private HashMap<String, Boolean> movementMap = new HashMap<String, Boolean>() {{
        put("RIGHT", false);
        put("LEFT", false);
        put("UP", false);
        put("DOWN", false);
    }};

    public ServerPlayer() {
        this.id = this.hashCode();
    }

    private void move() {
        if (movementMap.get("RIGHT")) {
            x += SPEED;
        }
        if (movementMap.get("LEFT")) {
            x -= SPEED;
        }
        if (movementMap.get("DOWN")) {
            y += SPEED;
        }
        if (movementMap.get("UP")) {
            y -= SPEED;
        }
    }

    public Runnable getPlayerActionsHandler(ServerPlayer player, BufferedReader in, PrintWriter out) {
        return () -> {
            Thread keymapLoop = getKeymapListener(in);
            keymapLoop.start();
            try {
                while (true) {
                    Thread.sleep(30);
                    player.move();
                    syncMovement(out);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private Thread getKeymapListener(BufferedReader in) {
        return new Thread(() -> {
            String command;
            try {
                while (!(command = in.readLine()).equals("exit")) {
                    movePlayerByCommand(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void movePlayerByCommand(String command) {
        String direction = KeyTranslator.getDirectionForPress(command);
        if (direction == null) {
            direction = KeyTranslator.getDirectionForRelease(command);
            if (direction != null) {
                movementMap.put(direction, false);
            }
            return;
        }
        movementMap.put(direction, true);
    }

    private void syncMovement(PrintWriter out) {
        // add player id here
        // TODO broadcast this wiht all positions and to all players
        out.println(x + "_" + y + "_"
                + w + "" + h);
    }
}
