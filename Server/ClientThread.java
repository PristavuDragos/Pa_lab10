package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    public ClientThread (Socket socket, ServerSocket serverSocket) { this.socket = socket ; this.serverSocket=serverSocket;}
    public void run () {

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String request = in.readLine();
            while (request.compareTo("stop")!=0 && request.compareTo("exit")!=0) {
                String raspuns = "Server received the request!";
                out.println(raspuns);
                out.flush();
                request = in.readLine();
            }
            if (request.compareTo("stop")==0){
                out.println("Server stopped");
                out.flush();
                serverSocket.close();
            }
            else this.socket.close();
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) { System.err.println (e); }
        }
    }

}
