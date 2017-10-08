package com.bertolozi.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static final int SPEED = 8;
    public Player player;
    boolean btR = false;
    boolean btL = false;
    boolean btU = false;
    boolean btD = false;
    PrintWriter out;

    public static void main(String[] args) {
        System.out.println("Starting...");
        Server server = new Server();
        server.waitForPlayer();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(30);
                if (btR) {
                    player.x += SPEED;
                    out.println(player.x + "_" + player.y + "_"
                            + player.w + "" + player.h);
                }
                if (btL) {
                    player.x -= SPEED;
                    out.println(player.x + "_" + player.y + "_"
                            + player.w + "" + player.h);
                }
                if (btL) {
                    player.x -= SPEED;
                    out.println(player.x + "_" + player.y + "_"
                            + player.w + "" + player.h);
                }
                if (btD) {
                    player.y += SPEED;
                    out.println(player.x + "_" + player.y + "_"
                            + player.w + "" + player.h);
                }
                if (btU) {
                    player.y -= SPEED;
                    out.println(player.x + "_" + player.y + "_"
                            + player.w + "" + player.h);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForPlayer() {
        try {
            player = new Player();
            ServerSocket ss = new ServerSocket(8880);
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);

            Thread th = new Thread(this);
            th.start();

            String command = "";
            while (!(command = in.readLine()).equals("exit")) {
                if (command.equals("PR_R")) {
                    btR = true;
                }
                if (command.equals("RE_R")) {
                    btR = false;
                }
                if (command.equals("PR_L")) {
                    btL = true;
                }
                if (command.equals("RE_L")) {
                    btL = false;
                }
                if (command.equals("PR_U")) {
                    btU = true;
                }
                if (command.equals("RE_U")) {
                    btU = false;
                }
                if (command.equals("PR_D")) {
                    btD = true;
                }
                if (command.equals("RE_D")) {
                    btD = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
