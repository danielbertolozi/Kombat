package com.bertolozi.Client;

import com.bertolozi.Message.MessageTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client2ServerConnector {
    private BufferedReader in;
    private PrintWriter out;
    private MessageTranslator message = new MessageTranslator();

    public void connect(int port) {
        try {
            Socket s = new Socket("localhost", port);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getIdForSelf() {
        String serverInput = "";
        try {
            serverInput = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(serverInput);
    }

    public String read() throws IOException {
        return in.readLine();
    }

    public void send(String code) {
        out.println(code);
    }

    public void disconnect(int id) {
        String msg = message.delete(id);
        out.println(msg);
    }
}
