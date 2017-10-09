package com.bertolozi.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerPlayer {
    public int x = 0;
    public int y = 0;
    public int w = 90;
    public int h = 127;
    public boolean moveRight = false;
    public boolean moveLeft = false;
    public boolean moveUp = false;
    public boolean moveDown = false;
    public static final int SPEED = 8;

    public void move() {
        return;
    }

    public Runnable getPlayerHandle(ServerPlayer player, BufferedReader in, PrintWriter out) {
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
}
