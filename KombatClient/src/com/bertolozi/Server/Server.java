package com.bertolozi.Server;

import com.bertolozi.Player.ServerAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        System.out.println("Starting...");
        Server server = new Server();
        server.waitForPlayer();
    }

    public void waitForPlayer() {
        try {
            ServerAgent player = new ServerAgent();
            ServerSocket ss = new ServerSocket(8880);
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            Runnable runnable = player.getPlayerHandle(player, in, out);
            Thread th = new Thread(runnable);
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
