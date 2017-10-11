package com.bertolozi.Client.Player.Entity;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ScreenElement extends JLabel {
    private int x = 0;
    private int y = 0;
    private ImageIcon walkLeft;
    private ImageIcon walkRight;
    private final int PLAYER_WIDTH = 88;
    private final int PLAYER_HEIGHT = 127;

    void setup() {
        setText("12");
        walkRight = getPlayerImageFrom("/Players/walk_r.gif");
        walkLeft = getPlayerImageFrom("/Players/walk_l.gif");
        move();
        setIconRight();
    }

    private void move() {
        setBounds(this.x, this.y, 90, 127);
    }

    private void setPlayerDirection(int x) {
        if (this.x == x) {
            return;
        }
        if (this.x > x) {
            setIconLeft();
        }
        else {
            setIconRight();
        }
    }

    @Override
    public void move(int x, int y) {
        setPlayerDirection(x);
        this.x = x;
        this.y = y;
        this.move();
    }

    private void setIconRight() {
        setIcon(walkRight);
    }

    private void setIconLeft() {
        setIcon(walkLeft);
    }

    private ImageIcon getPlayerImageFrom(String resource) {
        ImageIcon source = new ImageIcon(getClass().getResource(resource));
        Image scaledImage = getScaledImageFrom(source, PLAYER_WIDTH, PLAYER_HEIGHT);
        return new ImageIcon(scaledImage);
    }

    private Image getScaledImageFrom(ImageIcon source, int width, int height) {
        return source.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }
}
