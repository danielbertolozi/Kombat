package com.bertolozi.Server;

import com.bertolozi.Player.ServerPlayer;

import java.io.PrintWriter;
import java.util.HashMap;

public class ConnectionHandler {
    private HashMap<Integer, ServerPlayer> playerHashMap = new HashMap<Integer, ServerPlayer>();
    private HashMap<Integer, PrintWriter> writerHashMap = new HashMap<Integer, PrintWriter>();
    // TODO perhaps use something else than a hashmap for broadcasting?
    private static ConnectionHandler instance = new ConnectionHandler();

    public static ConnectionHandler getInstance() {
        return instance;
    }

    public void broadcast(String command) {
        for (PrintWriter out : writerHashMap.values()) {
            out.println(command);
        }
    }

    // TODO in the future, implement a thread that keep checking on a queue

    public void addPlayer(ServerPlayer player, PrintWriter out) {
        int key = player.getId();
        instance.playerHashMap.put(key, player);
        instance.writerHashMap.put(key, out);
        synchronizeNewPlayer(player, out);
        synchronizeOtherPlayers(key);
    }

    private void synchronizeNewPlayer(ServerPlayer newPlayer, PrintWriter newPlayerOut) {
        // send everyone to new
        int oldId;
        for (ServerPlayer oldPlayer : playerHashMap.values()) {
            oldId = oldPlayer.getId();
            if (newPlayer.getId() != oldId) {
                newPlayerOut.println("NEW-" + oldId + "-0_0");
            }
        }
    }

    private void synchronizeOtherPlayers(int newPlayerId) {
        // send new to everyone
        // TODO change X and Y from 0 0 to something else (maybe rand?)
        for (ServerPlayer player : playerHashMap.values()) {
            if (player.getId() != newPlayerId) {
                PrintWriter out = writerHashMap.get(player.getId());
                out.println("NEW-" + newPlayerId + "-0_0");
            }
        }
    }
}
