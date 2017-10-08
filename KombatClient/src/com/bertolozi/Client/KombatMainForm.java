package com.bertolozi.Client;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class KombatMainForm extends javax.swing.JFrame implements Runnable {
    Thread gameFlowThread;
    Player player;
    Socket s;
    BufferedReader in;
    PrintWriter out;
    private HashMap<Integer, String> pressKeymap = new HashMap<>();
    private HashMap<Integer, String> releaseKeymap = new HashMap<>();

    public KombatMainForm() {
        initComponents();
        initPressKeymap();
        initReleaseKeymap();
    }

    private void initPressKeymap() {
        pressKeymap.put(KeyEvent.VK_RIGHT, "PR_R");
        pressKeymap.put(KeyEvent.VK_LEFT, "PR_L");
        pressKeymap.put(KeyEvent.VK_UP, "PR_U");
        pressKeymap.put(KeyEvent.VK_DOWN, "PR_D");
    }

    private void initReleaseKeymap() {
        releaseKeymap.put(KeyEvent.VK_RIGHT, "RE_R");
        releaseKeymap.put(KeyEvent.VK_LEFT, "RE_L");
        releaseKeymap.put(KeyEvent.VK_UP, "RE_U");
        releaseKeymap.put(KeyEvent.VK_DOWN, "RE_D");
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
        player = new Player();
        player.setup();
        getContentPane().add(player);        
        connect();
        repaint();
        gameFlowThread = new Thread(this);
        gameFlowThread.start();
    }

    public void connect(){
        try {
            s   = new Socket("localhost",8880);
            in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(),true);
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
                player.x = Integer.parseInt(data[0]);
                player.y = Integer.parseInt(data[1]);
                player.move();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void formKeyPressed(KeyEvent evt) {
        String code = this.pressKeymap.get(evt.getKeyCode());
        if (code != null) {
            out.println(code);
        }
    }

    private void formKeyReleased(KeyEvent evt) {
        String code = this.releaseKeymap.get(evt.getKeyCode());
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

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KombatMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KombatMainForm().setVisible(true);
            }
        });
    }
}
