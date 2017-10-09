package com.bertolozi.Server;

import com.bertolozi.Player.ServerAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int SPEED = 8;

    public static void main(String[] args) {
        System.out.println("Starting...");
        Server server = new Server();
        server.waitForPlayer();
    }

    private Runnable getPlayerHandle(ServerAgent player, BufferedReader in, PrintWriter out) {
        Thread keymapLoop = new Thread(() -> {
            String command = "";
            try {
                while (!(command = in.readLine()).equals("exit")) {
                    if (command.equals("PR_R")) {
                        player.moveRight = true;
                    }
                    if (command.equals("RE_R")) {
                        player.moveRight = false;
                    }
                    if (command.equals("PR_L")) {
                        player.moveLeft = true;
                    }
                    if (command.equals("RE_L")) {
                        player.moveLeft = false;
                    }
                    if (command.equals("PR_U")) {
                        player.moveUp = true;
                    }
                    if (command.equals("RE_U")) {
                        player.moveUp = false;
                    }
                    if (command.equals("PR_D")) {
                        player.moveDown = true;
                    }
                    if (command.equals("RE_D")) {
                        player.moveDown = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return () -> {
            keymapLoop.start();
            try {
                while (true) {
                    Thread.sleep(30);
                    player.move();
                    if (player.moveRight) {
                        player.x += SPEED;
                        out.println(player.x + "_" + player.y + "_"
                                + player.w + "" + player.h);
                    }
                    if (player.moveLeft) {
                        player.x -= SPEED;
                        out.println(player.x + "_" + player.y + "_"
                                + player.w + "" + player.h);
                    }
                    if (player.moveDown) {
                        player.y += SPEED;
                        out.println(player.x + "_" + player.y + "_"
                                + player.w + "" + player.h);
                    }
                    if (player.moveUp) {
                        player.y -= SPEED;
                        out.println(player.x + "_" + player.y + "_"
                                + player.w + "" + player.h);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public void waitForPlayer() {
        try {
            ServerAgent player = new ServerAgent();
            ServerSocket ss = new ServerSocket(8880);
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            Runnable runnable = getPlayerHandle(player, in, out);
            Thread th = new Thread(runnable);
            th.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
