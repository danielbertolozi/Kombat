package com.bertolozi.Client;

import com.bertolozi.Player.ClientPlayer;

import java.util.Collection;
import java.util.HashMap;

public class AttachedPlayerHandler {
    private HashMap<Integer, ClientPlayer> playerMap = new HashMap<>();

    public void addPlayer(int id) {
        ClientPlayer newPlayer = new ClientPlayer();
        newPlayer.setId(id);
        this.addPlayer(newPlayer);
    }

    public void addPlayer(ClientPlayer player) {
        playerMap.put(player.getId(), player);
    }

    public Collection<ClientPlayer> getAllPlayers() {
        return playerMap.values();
    }

    public ClientPlayer get(int id) {
        return playerMap.get(id);
    }

}
