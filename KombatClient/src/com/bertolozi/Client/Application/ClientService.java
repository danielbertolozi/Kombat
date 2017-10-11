package com.bertolozi.Client.Application;

import com.bertolozi.Client.Connection.ServerConnector;
import com.bertolozi.Client.Exceptions.PlayerNotFoundException;
import com.bertolozi.Client.Player.Entity.Player;
import com.bertolozi.Client.Player.Service.PlayerService;
import com.bertolozi.Client.Protocol.MessageTranslator;

import java.io.IOException;

class ClientService {
    private PlayerService playerService = new PlayerService();
    private ServerConnector connector = new ServerConnector();
    private KombatClient parent;

    ClientService(KombatClient parent) {
        this.parent = parent;
    }

    void add(Player player) {
        playerService.add(player);
    }

    String read() throws IOException {
        return connector.read();
    }

    void send(String message) {
        connector.send(message);
    }

    int connectAndGetId(int port) {
        connector.connect(port);
        return connector.getIdForSelf();
    }

    void decodeCommand(String message) {
        if (MessageTranslator.isNewPlayer(message)) {
            playerService.add(message);
            parent.addPlayersToScreen(playerService.get());
        } else if (MessageTranslator.isDeletion(message)) {
            playerService.remove(message);
            parent.addPlayersToScreen(playerService.get());
        } else {
            move(message);
        }
    }

    private void move(String message) {
        int id = MessageTranslator.getIdForPlayer(message);
        Player currentPlayer;
        try {
            currentPlayer = playerService.get(id);
        } catch (PlayerNotFoundException e) {
            return;
        }

        int[] coord = MessageTranslator.getCoordinates(message);

        currentPlayer.move(coord[0], coord[1]);
    }
}
