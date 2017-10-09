package com.bertolozi.Player;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ClientPlayer extends JLabel {
    private int x = 0;
    private int y = 0;
    ImageIcon walkLeft;
    ImageIcon walkRight;
    private final String RIGHT_SPRITE = "/Players/walk_r.gif";
    private final String LEFT_SPRITE = "/Players/walk_l.gif";
    private final int PLAYER_WIDTH = 88;
    private final int PLAYER_HEIGHT = 127;

    public void setup() {
        setText("12");
        walkRight = getPlayerImageFrom(RIGHT_SPRITE);
        walkLeft = getPlayerImageFrom(LEFT_SPRITE);
        move();
        setIconRight();
    }

    public void move() {
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

    public void move(int x, int y) {
        setPlayerDirection(x);
        this.x = x;
        this.y = y;
        this.move();
    }

    public void setIconRight() {
        setIcon(walkRight);
    }

    public void setIconLeft() {
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
