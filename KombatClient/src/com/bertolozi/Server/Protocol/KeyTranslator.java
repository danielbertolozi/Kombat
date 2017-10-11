package com.bertolozi.Server.Protocol;

import java.util.HashMap;

public abstract class KeyTranslator {
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

    public static String getDirectionForPress(String command) {
        return pressCmdToDirection.get(command);
    }

    public static String getDirectionForRelease(String command) {
        return releaseCmdToDirection.get(command);
    }
}
