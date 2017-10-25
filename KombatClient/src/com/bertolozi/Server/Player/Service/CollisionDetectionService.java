package com.bertolozi.Server.Player.Service;

import com.bertolozi.Client.Exceptions.PlayerNotFoundException;
import com.bertolozi.Server.Connection.ClientConnector;
import com.bertolozi.Server.Player.Entity.Player;

public class CollisionDetectionService extends Thread {
    private ClientConnector clientConnector;

    public CollisionDetectionService() {
        clientConnector = ClientConnector.getInstance();
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                detect();
            } catch (PlayerNotFoundException e) {
                // no need to do shit in here
            }
        }
    }

    public void detect() throws PlayerNotFoundException {
        Player[] players = (Player[]) clientConnector.getAllPlayers().toArray();
        if (players.length == 0) {
            throw new PlayerNotFoundException();
        }
        int i, j;
        for (i = 0; i < players.length / 2; i++) {
            for (j = players.length - 1; j > players.length / 2; j--) {
                if (isColliding(players[i], players[j])) {
                    System.out.println("Collision between " + players[i].getId() + " and " + players[j].getId());
                }
            }
        }
    }

    private boolean isColliding(Player candidate, Player target) {
        int[] candidateHeight = candidate.getPlayerHeightRange();
        int[] targetHeight = target.getPlayerHeightRange();
        int[] candidateWidth = candidate.getPlayerWidthRange();
        int[] targetWidth = target.getPlayerWidthRange();
        boolean yIntersection = checkForIntersection(candidateHeight, targetHeight);
        boolean xIntersection = checkForIntersection(candidateWidth, targetWidth);
        return yIntersection || xIntersection;
    }

    private boolean checkForIntersection(int[] candidateRange, int[] targetRange) {
        return candidateRange[0] < targetRange[1] && candidateRange[1] > targetRange[0];
    }
}
