package com.bertolozi.Client.Application;

import com.bertolozi.Client.Connection.ServerConnector;
import com.bertolozi.Client.Exceptions.PlayerNotFoundException;
import com.bertolozi.Client.Player.Entity.Player;
import com.bertolozi.Client.Player.Service.PlayerService;
import com.bertolozi.Client.Protocol.MessageTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
            parent.clearPlayersFromScreen(playerService.get());
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

        int[] actions = MessageTranslator.getCoordinates(message);

        currentPlayer.move(actions[0], actions[1]);
        if (actions[2] == 1) { // determine where current player is attacking; consider as a binary
            currentPlayer.attack();
        }
    }

    int getConnectionPort(int defaultPort) {
        int port = 0;
        try {
            Socket socket = new Socket("localhost", defaultPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            port = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }
}
