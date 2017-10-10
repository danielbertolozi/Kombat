package com.bertolozi.Message;

public class MessageTranslator {
    public boolean isNewPlayer(String message) {
        return message.startsWith("NEW");
    }

    public int getIdForNewPlayer(String message) {
        String id = message.split("-")[1];
        return Integer.parseInt(id);
    }

    public int getIdForPlayer(String message) {
        String id = message.split("-")[0];
        return Integer.parseInt(id);
    }

    public int[] getCoordinates(String message) {
        String input[] = message.split("-");
        String data[] = input[1].split("\\_");
        int x = Integer.parseInt(data[0]);
        int y = Integer.parseInt(data[1]);
        int[] coordinates = new int[] {x, y};
        return coordinates;
    }

    public String newPlayerString(int id) {
        return "NEW-" + id + "-0_0";
    }

    public String newPlayerString(int id, int x, int y) {
        return "NEW-" + id + "-" + x + "_" + y;
    }
}
