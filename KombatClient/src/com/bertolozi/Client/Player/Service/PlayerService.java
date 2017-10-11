package com.bertolozi.Client.Player.Service;

import com.bertolozi.Client.Exceptions.PlayerNotFoundException;
import com.bertolozi.Client.Player.Entity.Player;
import com.bertolozi.Client.Protocol.MessageTranslator;

import java.util.Collection;

public class PlayerService {
    private PlayerTracker players = new PlayerTracker();

    public void add(String message) {
        int id = MessageTranslator.getIdForNewPlayer(message);
        players.addPlayer(id);
    }

    public void add(Player player) {
        players.addPlayer(player);
    }

    public void remove(String message) {
        int id = MessageTranslator.delete(message);
        players.remove(id);
    }

    public Collection<Player> get() {
        return players.getAllPlayers();
    }

    public Player get(int id) throws PlayerNotFoundException {
        Player player = players.get(id);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return player;
    }
}
