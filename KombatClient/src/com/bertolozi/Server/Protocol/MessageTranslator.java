package com.bertolozi.Server.Protocol;

public abstract class MessageTranslator {

    public static String newPlayer(int id) {
        return newPlayer(id, 0, 0);
    }

    public static String newPlayer(int id, int x, int y) {
        return "NEW_" + movement(id, x, y, false);
    }

    public static String delete(int id) {
        return "DEL_" + id;
    }

    public static String movement(int id, int x, int y, boolean attack) {
        return id + "_" + x + "_" + y + "_" + (attack ? 1 : 0);
    }

    public static boolean isDeletion(String message) {
        return message.startsWith("DEL_");
    }
}
