package com.bertolozi.Client.Protocol;

public abstract class MessageTranslator {
    public static boolean isNewPlayer(String message) {
        return message.startsWith("NEW_");
    }

    public static int getIdForNewPlayer(String message) {
        String id = message.split("_")[1];
        return Integer.parseInt(id);
    }

    public static int getIdForPlayer(String message) {
        String id = message.split("_")[0];
        return Integer.parseInt(id);
    }

    public static String delete(int id) {
        return "DEL_" + id;
    }

    public static int delete(String serverInput) {
        return Integer.parseInt(serverInput.split("_")[1]);
    }

    public static int[] getCoordinates(String message) {
        String data[] = message.split("_");
        int x = Integer.parseInt(data[1]);
        int y = Integer.parseInt(data[2]);
        return new int[] {x, y};
    }

    public static boolean isDeletion(String serverInput) {
        return serverInput.startsWith("DEL_");
    }
}
