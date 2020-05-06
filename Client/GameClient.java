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
    private boolean firstMove =false;
    private boolean playing=false;
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

    private boolean runClient() throws IOException {
        try (   Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (
                        new InputStreamReader(socket.getInputStream())) ) {


            String request="";
            Scanner scan= new Scanner(System.in);
            do {
                if (playing){
                    if (firstMove){
                        System.out.println("Submit your move");
                        request= scan.nextLine();
                        out.println(request);
                        String response="";
                        if (request.length() >= 11 && request.substring(0, 11).compareTo("submit move") == 0) {
                            //response=in.readLine();
                            char[] buffer = new char[1024];
                            in.read(buffer);
                            response=new String(buffer);
                            System.out.println(response);
                            if (response.indexOf("won!")!=-1) return true;
                            System.out.println("Waiting for the other player to move");
                            buffer = new char[1024];
                            in.read(buffer);
                            response=new String(buffer);
                            System.out.println(response);
                            if (response.indexOf("won!")!=-1) return true;
                        } else if (request.compareTo("exit") != 0) System.out.println(response);
                        else System.out.println("Closing the client.");
                    } else{
                        System.out.println("Waiting for the other player to move");
                        String response="";
                        char[] buffer = new char[1024];
                        in.read(buffer);
                        response=new String(buffer);
                        System.out.println(response);
                        if (response.indexOf("won!")!=-1) return true;
                        System.out.println("Submit your move");
                        request= scan.nextLine();
                        out.println(request);
                        buffer = new char[1024];
                        in.read(buffer);
                        response=new String(buffer);
                        System.out.println(response);
                        if (response.indexOf("won!")!=-1) return true;
                    }
                } else {
                    System.out.println("Awaiting your command");
                    request = scan.nextLine();
                    out.println(request);
                    if (request.compareTo("create game") == 0) firstMove = true;
                    String response = in.readLine();
                    if (response.length() >= 7 && response.substring(0, 7).compareTo("Playing") == 0) playing = true;
                    if (request.compareTo("exit") != 0) System.out.println(response);
                    else System.out.println("Closing the client.");
                }
            }while(request.compareTo("exit")!=0);
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
        return false;
    }

}

