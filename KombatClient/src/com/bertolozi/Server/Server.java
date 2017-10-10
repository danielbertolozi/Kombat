package com.bertolozi.Server;

import com.bertolozi.Player.ServerPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ClientConnectionHandler clientConnectionHandler = ClientConnectionHandler.getInstance();
    private int[] ports = new int[] {8880, 8881};
    public static void main(String[] args) {
        System.out.println("Starting...");
        Server server = new Server();
        server.waitForPlayer();
    }

    public void waitForPlayer() {
        int playerCount = 0;
        while (playerCount < 2) {
            try {
                ServerPlayer player = new ServerPlayer();
                ServerSocket ss = new ServerSocket(ports[playerCount]);
                Socket s = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                out.println(player.hashCode());
                clientConnectionHandler.addPlayer(player, out);
                Runnable runnable = player.getPlayerActionsHandler(player, in, out);
                Thread th = new Thread(runnable);
                th.start();
                playerCount++;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
