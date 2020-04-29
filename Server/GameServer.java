package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    public static final int PORT= 11100;
    public GameServer() throws IOException {
        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket(PORT);
            while (!serverSocket.isClosed()) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket,serverSocket).start();
            }
        } catch (IOException e) {
            System.err. println (e);
        } finally {
            serverSocket.close();
        }
    }
}
