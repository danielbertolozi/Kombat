package com.bertolozi.Message;

public class MessageTranslator {
    public boolean isNewPlayer(String message) {
        return message.startsWith("NEW_");
    }

    public int getIdForNewPlayer(String message) {
        String id = message.split("_")[1];
        return Integer.parseInt(id);
    }

    public int getIdForPlayer(String message) {
        String id = message.split("_")[0];
        return Integer.parseInt(id);
    }

    public int[] getCoordinates(String message) {
        String data[] = message.split("_");
        int x = Integer.parseInt(data[1]);
        int y = Integer.parseInt(data[2]);
        int[] coordinates = new int[] {x, y};
        return coordinates;
    }

    public String newPlayer(int id) {
        return newPlayer(id, 0, 0);
    }

    public String newPlayer(int id, int x, int y) {
        return "NEW_" + movement(id, x, y);
    }

    public int delete(String serverInput) {
        return Integer.parseInt(serverInput.split("_")[1]);
    }
    // TODO split method into server & client
    public String delete(int id) {
        return "DEL_" + id;
    }

    public String movement(int id, int x, int y) {
        return id + "_" + x + "_" + y;
    }

    public boolean isDeletion(String serverInput) {
        return serverInput.startsWith("DEL_");
    }
}
