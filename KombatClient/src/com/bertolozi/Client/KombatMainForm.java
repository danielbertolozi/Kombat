package com.bertolozi.Client;

import com.bertolozi.Player.ClientPlayer;
import com.bertolozi.Control.KeyTranslator;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.awt.EventQueue.invokeLater;
import static javax.swing.UIManager.getInstalledLookAndFeels;

public class KombatMainForm extends javax.swing.JFrame implements Runnable {
    Thread gameFlowThread;
    ClientPlayer player;
    Socket s;
    BufferedReader in;
    PrintWriter out;

    public KombatMainForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                formKeyPressed(evt);
            }

            public void keyReleased(KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE)
        );
        pack();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        player = new ClientPlayer();
        player.setup();
        getContentPane().add(player);
        connect();
        repaint();
        gameFlowThread = new Thread(this);
        gameFlowThread.start();
    }

    public void connect() {
        try {
            s = new Socket("localhost", 8880);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gameFlow() {
        String command;
        try {
            while (true) {
                command = in.readLine();
                String data[] = command.split("\\_");
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                player.move(x, y);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void formKeyPressed(KeyEvent evt) {
        String code = KeyTranslator.translatePressEvent(evt.getKeyCode());
        if (code != null) {
            out.println(code);
        }
    }

    private void formKeyReleased(KeyEvent evt) {
        String code = KeyTranslator.translateReleaseEvent(evt.getKeyCode());
        if (code != null) {
            out.println(code);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void run() {
        gameFlow();
    }

    public static void main(String args[]) throws ClassNotFoundException, InstantiationException,
                                                  IllegalAccessException, UnsupportedLookAndFeelException {
        for (UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        invokeLater(() -> new KombatMainForm().setVisible(true));
    }
}
