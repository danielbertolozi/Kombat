package com.bertolozi.Server.Application;

import com.bertolozi.Server.Connection.ClientConnector;
import com.bertolozi.Server.Player.Entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ClientConnector clientConnector = ClientConnector.getInstance();
    private int[] ports = new int[] {8880, 8881};
    public static void main(String[] args) {
        System.out.println("Starting...");
        Server server = new Server();
        server.waitForPlayer();
    }
// TODO clean this mess
    private void waitForPlayer() {
        int playerCount = 0;
        while (playerCount < 2) {
            try {
                ServerSocket ss = new ServerSocket(ports[playerCount]);
                Socket s = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                Player player = new Player();
                out.println(player.hashCode());
                clientConnector.addPlayer(player, out);
                Runnable runnable = player.getActionHandler(in);
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
