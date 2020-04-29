package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    private String serverAddress;
    private int PORT;

    private GameClient() { }
    public GameClient(String address,int port){
        this.serverAddress=address;
        this.PORT=port;
        try {
            runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runClient() throws IOException {
        try (   Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (
                        new InputStreamReader(socket.getInputStream())) ) {


            String request="";
            Scanner scan= new Scanner(System.in);
            do {
                System.out.println("Awaiting your command");
                request=scan.nextLine();
                out.println(request);
                String response = in.readLine();
                if (request.compareTo("exit")!=0)System.out.println(response);
                else System.out.println("Closing the client.");
            }while(request.compareTo("exit")!=0);
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }

}

