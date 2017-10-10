package com.bertolozi.Control;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class KeyListener extends Thread {
    BufferedReader in;
    public HashMap<String, Boolean> movementMap = new HashMap<String, Boolean>() {{
        put("RIGHT", false);
        put("LEFT", false);
        put("UP", false);
        put("DOWN", false);
    }};

    public KeyListener(BufferedReader in) {
        this.in = in;
    }

    public boolean get(String direction) {
        return this.movementMap.get(direction);
    }

    @Override
    public void run() {
        String command;
        try {
            while (!(command = in.readLine()).equals("exit")) {
                movePlayerByCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
