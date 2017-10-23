package com.bertolozi.Client.Application;

import com.bertolozi.Client.Exceptions.SetLookAndFeelException;
import com.bertolozi.Client.Protocol.MessageTranslator;
import com.bertolozi.Client.Player.Entity.Player;
import com.bertolozi.Client.Protocol.KeyTranslator;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.Collection;

import static java.awt.EventQueue.invokeLater;
import static javax.swing.UIManager.getInstalledLookAndFeels;

public class KombatClient extends JFrame implements Runnable {
    private ClientService service;
    private Player player;
    private int port = 8880;
    Thread mainThread;

    public static void main(String args[]) throws SetLookAndFeelException {
        setLookAndFeel();
        invokeLater(() -> new KombatClient().setVisible(true));
    }

    private static void setLookAndFeel() throws SetLookAndFeelException {
        for (UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    throw new SetLookAndFeelException();
                }
                break;
            }
        }
    }

    private KombatClient() {
        service = new ClientService(this);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (player != null) {
                    disconnect(player.getId());
                }
                mainThread.interrupt();
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

    private void disconnect(int id) {
        String message = MessageTranslator.delete(id);
        service.send(message);
    }

    private void formWindowOpened() {
        player = new Player();
        getContentPane().add(player.character);
        int port = service.getConnectionPort(this.port);
        int id = service.connectAndGetId(port);
        initialSetupForSelf(id);
        repaint();
        mainThread = new Thread(this);
        mainThread.start();
    }

    private void initialSetupForSelf(int id) {
        player.setId(id);
        service.add(player);
    }

    @Override
    public void run() {
        String message = "";
        while (true) {
            try {
                message = service.read();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            service.decodeCommand(message);
        }
    }

    void addPlayersToScreen(Collection<Player> players) {
        for (Player player : players) {
            getContentPane().add(player.character);
        }
        getContentPane().validate();
        repaint();
    }

    private void formKeyPressed(KeyEvent evt) {
        String code = KeyTranslator.translatePressEvent(evt.getKeyCode());
        if (code != null) {
            service.send(code);
        }
    }

    private void formKeyReleased(KeyEvent evt) {
        String code = KeyTranslator.translateReleaseEvent(evt.getKeyCode());
        if (code != null) {
            service.send(code);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    void clearPlayersFromScreen(Collection<Player> players) {
        for (Player player : players) {
            getContentPane().remove(player.character);
        }
        getContentPane().validate();
        repaint();
    }
}
