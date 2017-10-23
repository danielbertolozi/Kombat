package com.bertolozi.Client.Player.Entity;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ScreenElement extends JLabel {
    private int x = 0;
    private int y = 0;
    private ImageIcon walkLeft = getPlayerImageFrom("/Players/walk_l.gif");;
    private ImageIcon walkRight = getPlayerImageFrom("/Players/walk_r.gif");;
    private ImageIcon attackLeft = getPlayerImageFrom("/Players/attack_l.gif");
    private ImageIcon attackRight = getPlayerImageFrom("/Players/attack_r.gif");
    private final int PLAYER_WIDTH = 88;
    private final int PLAYER_HEIGHT = 127;
    private PlayerDirection direction;
    private boolean attacking;

    void setup() {
        setText("12");
        move();
        direction = PlayerDirection.RIGHT;
    }

    private void move() {
        setBounds(this.x, this.y, 90, 127);
    }

    private void setPlayerDirection(int x) {
        if (this.x == x) {
            return;
        }
        if (this.x > x) {
            direction = PlayerDirection.LEFT;
        }
        else {
            direction = PlayerDirection.RIGHT;
        }
        updateIcon();
    }

    void setAttackMode(boolean isAttack) {
        attacking = isAttack;
    }

    @Override
    public void move(int x, int y) {
        setPlayerDirection(x);
        this.x = x;
        this.y = y;
        this.move();
    }

    private void updateIcon() {
        if (attacking) {
            if (direction == PlayerDirection.LEFT) {
                setAttackingIconLeft();
                return;
            }
            setAttackingIconRight();
            return;
        }
        if (direction == PlayerDirection.LEFT) {
            setWalkingIconLeft();
            return;
        }
        setWalkingIconRight();
    }

    private void setWalkingIconRight() {
        setIcon(walkRight);
    }

    private void setWalkingIconLeft() {
        setIcon(walkLeft);
    }

    private void setAttackingIconRight() {
        setIcon(attackRight);
    }

    private void setAttackingIconLeft() {
        setIcon(attackLeft);
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
