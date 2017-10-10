package com.bertolozi.Client;

import com.bertolozi.Exceptions.SetLookAndFeelException;
import com.bertolozi.Player.ClientPlayer;
import com.bertolozi.Control.KeyTranslator;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import static java.awt.EventQueue.invokeLater;
import static javax.swing.UIManager.getInstalledLookAndFeels;

public class KombatMainForm extends JFrame implements Runnable {
    private HashMap<Integer, ClientPlayer> playerMap = new HashMap<>();
    private int port = 8880;
    private Client2ServerConnector connector = new Client2ServerConnector();

    public static void main(String args[]) throws SetLookAndFeelException {
        setLookAndFeel();
        int port = 0;
        if (args.length != 0 && args[0].equals("-p")) {
            port = Integer.parseInt(args[1]);
        }
        int finalPort = port;
        invokeLater(() -> new KombatMainForm(finalPort).setVisible(true));
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

    private KombatMainForm(int port) {
        this.port = port != 0 ? port : this.port;
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

    private void formWindowOpened(WindowEvent evt) {
        ClientPlayer player = new ClientPlayer();
        getContentPane().add(player.playerCharacter);
        connector.connect(port);
        getIdFor(player);
        repaint();
        Thread mainThread = new Thread(this);
        mainThread.start();
    }

    private void getIdFor(ClientPlayer player) {
        int id = connector.getIdForSelf();
        player.setId(id);
        playerMap.put(player.getId(), player);
    }

    @Override
    public void run() {
        String serverInput;
        try {
            while (true) {
                serverInput = connector.read();
                // TODO maybe throw in some validations?
                decodeCommand(serverInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void decodeCommand(String serverInput) {
        if (serverInput.startsWith("NEW")) {
            addNewPlayer(serverInput);
        } else {
            executeMovements(serverInput);
        }
    }

    private void addNewPlayer(String serverInput) {
        String[] data = serverInput.split("-");
        ClientPlayer newPlayer = new ClientPlayer();
        newPlayer.setId(Integer.parseInt(data[1]));
        playerMap.put(newPlayer.getId(), newPlayer);
        addPlayersToScreen();
    }

    private void addPlayersToScreen() {
        for (ClientPlayer player : playerMap.values()) {
            getContentPane().remove(player.playerCharacter);
            getContentPane().validate();
            getContentPane().add(player.playerCharacter);
        }
    }

    private void executeMovements(String serverInput) {
        String input[] = serverInput.split("-");
        int id = Integer.parseInt(input[0]);
        ClientPlayer currentPlayer = playerMap.get(id);

        String data[] = input[1].split("\\_");
        int x = Integer.parseInt(data[0]);
        int y = Integer.parseInt(data[1]);

        currentPlayer.move(x, y);
    }

    private void formKeyPressed(KeyEvent evt) {
        String code = KeyTranslator.translatePressEvent(evt.getKeyCode());
        if (code != null) {
            connector.send(code);
        }
    }

    private void formKeyReleased(KeyEvent evt) {
        String code = KeyTranslator.translateReleaseEvent(evt.getKeyCode());
        if (code != null) {
            connector.send(code);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
