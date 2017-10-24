package com.bertolozi.Server.Player.Service;

import com.bertolozi.Server.Connection.ClientConnector;
import com.bertolozi.Server.Protocol.MessageTranslator;

public abstract class PlayerService {
    public static void syncMovement(int id, int x, int y, boolean attack) {
        String message = MessageTranslator.movement(id, x, y, attack);
        ClientConnector.getInstance().broadcast(message);
    }
}
