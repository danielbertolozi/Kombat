package com.bertolozi.Client.Protocol;

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

    public static String translatePressEvent(Integer event) {
        return pressKeyToCmd.get(event);
    }

    public static String translateReleaseEvent(Integer event) {
        return releaseKeyToCmd.get(event);
    }
}
