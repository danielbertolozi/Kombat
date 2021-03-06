package com.bertolozi.Server.Application;

import com.bertolozi.Server.Connection.ClientConnector;
import com.bertolozi.Server.Connection.PortDispatcher;
import com.bertolozi.Server.Player.Entity.Player;
import com.bertolozi.Server.Player.Service.CollisionDetectionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT = 8880;
    private ClientConnector clientConnector = ClientConnector.getInstance();
    private CollisionDetectionService collisionDetection = new CollisionDetectionService();
    private PortDispatcher portDispatcher = new PortDispatcher(PORT);

    private Server() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting...");
        Server server = new Server();
        server.waitForPlayer();
    }

    private void waitForPlayer() {
        collisionDetection.start();
        while (true) {
            try {
                Socket connection = portDispatcher.dispatchIncomingConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                Player player = new Player();
                out.println(player.hashCode());
                clientConnector.addPlayer(player, out);
                Runnable runnable = player.getActionHandler(in);
                Thread th = new Thread(runnable);
                th.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
