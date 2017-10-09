package com.bertolozi.Control;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public abstract class KeyTranslator {
    private static HashMap<Integer, String> pressKeyToCmd = new HashMap<Integer, String>() {{
        put(KeyEvent.VK_RIGHT, "PR_R");
        put(KeyEvent.VK_LEFT, "PR_L");
        put(KeyEvent.VK_UP, "PR_U");
        put(KeyEvent.VK_DOWN, "PR_D");
    }};

    private static HashMap<Integer, String> releaseKeyToCmd = new HashMap<Integer, String>() {{
        put(KeyEvent.VK_RIGHT, "RE_R");
        put(KeyEvent.VK_LEFT, "RE_L");
        put(KeyEvent.VK_UP, "RE_U");
        put(KeyEvent.VK_DOWN, "RE_D");
    }};

    private static HashMap<String, String> pressCmdToDirection = new HashMap<String, String>() {{
        put("PR_R", "RIGHT");
        put("PR_L", "LEFT");
        put("PR_U", "UP");
        put("PR_D", "DOWN");
    }};

    private static HashMap<String, String> releaseCmdToDirection = new HashMap<String, String>() {{
        put("RE_R", "RIGHT");
        put("RE_L", "LEFT");
        put("RE_U", "UP");
        put("RE_D", "DOWN");
    }};

    public static String translatePressEvent(Integer event) {
        return pressKeyToCmd.get(event);
    }

    public static String translateReleaseEvent(Integer event) {
        return releaseKeyToCmd.get(event);
    }

    public static String getDirectionForPress(String command) {
        return pressCmdToDirection.get(command);
    }

    public static String getDirectionForRelease(String command) {
        return releaseCmdToDirection.get(command);
    }
}
