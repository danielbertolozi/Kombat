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

    private HashMap<PlayerActions, Boolean> movementMap = new HashMap<PlayerActions, Boolean>() {{
        put(PlayerActions.RIGHT, false);
        put(PlayerActions.LEFT, false);
        put(PlayerActions.UP, false);
        put(PlayerActions.DOWN, false);
        put(PlayerActions.ATTACK, false);
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
                movementMap.put(PlayerActions.valueOf(direction), false);
            }
            return;
        }
        movementMap.put(PlayerActions.valueOf(direction), true);
    }

    boolean get(PlayerActions direction) {
        return this.movementMap.get(direction);
    }
}
