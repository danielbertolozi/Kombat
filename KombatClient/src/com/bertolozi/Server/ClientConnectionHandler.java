package com.bertolozi.Server;

import com.bertolozi.Player.ServerPlayer;

import java.io.PrintWriter;
import java.util.HashMap;

public class ClientConnectionHandler {
    private HashMap<Integer, ServerPlayer> playerHashMap = new HashMap<Integer, ServerPlayer>();
    private HashMap<Integer, PrintWriter> writerHashMap = new HashMap<Integer, PrintWriter>();
    private static ClientConnectionHandler instance = new ClientConnectionHandler();

    public ClientConnectionHandler() {
        throw new ExceptionInInitializerError("This class should not be instantiated.");
    }

    public static ClientConnectionHandler getInstance() {
        return instance;
    }

    public void broadcast(String command) {
        for (PrintWriter out : instance.writerHashMap.values()) {
            out.println(command);
        }
    }

    // TODO in the future, implement a thread that keep checking on a queue

    public void addPlayer(ServerPlayer player, PrintWriter out) {
        int key = player.getId();
        instance.playerHashMap.put(key, player);
        instance.writerHashMap.put(key, out);
        synchronizePlayers(player, out);
    }

    private void synchronizePlayers(ServerPlayer player, PrintWriter out) {
        syncExistingPlayersWithNewPlayer(player, out);
        syncNewPlayerWithExistingPlayers(player.getId());
    }

    private void syncExistingPlayersWithNewPlayer(ServerPlayer newPlayer, PrintWriter newPlayerOut) {
        int actualId;
        for (ServerPlayer player : playerHashMap.values()) {
            actualId = player.getId();
            if (newPlayer.getId() != actualId) {
                sendIdToPlayer(newPlayerOut, actualId);
            }
        }
    }

    private void syncNewPlayerWithExistingPlayers(int newPlayerId) {
        // TODO change X and Y from 0 0 to something else (maybe rand?)
        for (ServerPlayer player : playerHashMap.values()) {
            if (player.getId() != newPlayerId) {
                PrintWriter out = writerHashMap.get(player.getId());
                sendIdToPlayer(out, newPlayerId);
            }
        }
    }

    private void sendIdToPlayer(PrintWriter out, int id) {
        out.println("NEW-" + id + "-0_0");
    }
}
