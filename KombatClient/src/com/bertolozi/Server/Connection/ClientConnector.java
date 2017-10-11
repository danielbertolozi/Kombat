package com.bertolozi.Server.Connection;

import com.bertolozi.Server.Protocol.MessageTranslator;
import com.bertolozi.Server.Player.Entity.Player;

import java.io.PrintWriter;
import java.util.HashMap;

public class ClientConnector {
    private HashMap<Integer, Player> playerHashMap = new HashMap<>();
    private HashMap<Integer, PrintWriter> writerHashMap = new HashMap<>();
    private static ClientConnector instance = new ClientConnector();

    public static ClientConnector getInstance() {
        return instance;
    }

    public void broadcast(String command) {
        for (PrintWriter out : instance.writerHashMap.values()) {
            out.println(command);
        }
    }

    // TODO in the future, implement a thread that keep checking on a queue

    public void removePlayer(Player player) {
        int id = player.getId();
        instance.playerHashMap.remove(id);
        instance.writerHashMap.remove(id).close();
        String deletion = MessageTranslator.delete(id);
        instance.broadcast(deletion);
    }

    public void addPlayer(Player player, PrintWriter out) {
        int key = player.getId();
        instance.playerHashMap.put(key, player);
        instance.writerHashMap.put(key, out);
        synchronizePlayers(player, out);
    }

    private void synchronizePlayers(Player player, PrintWriter out) {
        syncExistingPlayersWithNewPlayer(player, out);
        syncNewPlayerWithExistingPlayers(player.getId());
    }

    private void syncExistingPlayersWithNewPlayer(Player newPlayer, PrintWriter newPlayerOut) {
        int actualId;
        for (Player player : playerHashMap.values()) {
            actualId = player.getId();
            if (newPlayer.getId() != actualId) {
                sendIdToPlayer(newPlayerOut, actualId);
            }
        }
    }

    private void syncNewPlayerWithExistingPlayers(int newPlayerId) {
        // TODO change X and Y from 0 0 to something else (maybe rand?)
        for (Player player : playerHashMap.values()) {
            if (player.getId() != newPlayerId) {
                PrintWriter out = writerHashMap.get(player.getId());
                sendIdToPlayer(out, newPlayerId);
            }
        }
    }

    private void sendIdToPlayer(PrintWriter out, int id) {
        String newPlayer = MessageTranslator.newPlayer(id);
        out.println(newPlayer);
    }
}
