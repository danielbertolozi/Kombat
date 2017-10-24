package com.bertolozi.Server.Player.Actions;

import com.bertolozi.Server.Player.Entity.Player;
import com.bertolozi.Server.Protocol.KeyTranslator;
import com.bertolozi.Server.Protocol.MessageTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class PlayerMovementHandler extends Thread {
    private final Player player;
    private BufferedReader in;

    private HashMap<String, Boolean> movementMap = new HashMap<String, Boolean>() {{
        put("RIGHT", false);
        put("LEFT", false);
        put("UP", false);
        put("DOWN", false);
        put("ATTACK", false);
    }};

    PlayerMovementHandler(BufferedReader in, Player player) {
        this.in = in;
        this.player = player;
    }

    @Override
    public void run() {
        String message;
        try {
            while (!(message = in.readLine()).equals("exit")) {
                if (MessageTranslator.isDeletion(message)) {
                    player.disconnect();
                }
                playerActionByCommand(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playerActionByCommand(String message) {
        String direction = KeyTranslator.getDirectionForPress(message);
        if (direction == null) {
            direction = KeyTranslator.getDirectionForRelease(message);
            if (direction != null) {
                movementMap.put(direction, false);
            }
            return;
        }
        movementMap.put(direction, true);
    }

    boolean get(String direction) {
        return this.movementMap.get(direction);
    }
}
