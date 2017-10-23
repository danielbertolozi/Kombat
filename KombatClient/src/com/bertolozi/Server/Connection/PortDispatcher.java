package com.bertolozi.Server.Connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PortDispatcher {
    private final ServerSocket serverSocket;

    public PortDispatcher(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public Socket dispatchIncomingConnection() throws IOException {
        Socket connectedSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(connectedSocket.getOutputStream(), true);
        ServerSocket newConnection = new ServerSocket(0);
        out.println(newConnection.getLocalPort());
        out.close();
        return newConnection.accept();
    }
}
