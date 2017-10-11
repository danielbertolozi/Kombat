package com.bertolozi.Client.Player.Service;

import com.bertolozi.Client.Player.Entity.Player;

import java.util.Collection;
import java.util.HashMap;

class PlayerTracker {
    private HashMap<Integer, Player> playerMap = new HashMap<>();

    void addPlayer(int id) {
        Player newPlayer = new Player();
        newPlayer.setId(id);
        this.addPlayer(newPlayer);
    }

    void addPlayer(Player player) {
        playerMap.put(player.getId(), player);
    }

    Collection<Player> getAllPlayers() {
        return playerMap.values();
    }

    Player get(int id) {
        return playerMap.get(id);
    }

    void remove(int id) {
        playerMap.remove(id);
    }
}
