package com.bertolozi.Server.Player.Service;

import com.bertolozi.Server.Connection.ClientConnector;
import com.bertolozi.Server.Protocol.MessageTranslator;

public abstract class PlayerService {
    public static void syncMovement(int id, int x, int y) {
        String message = MessageTranslator.movement(id, x, y);
        ClientConnector.getInstance().broadcast(message);
    }
}
