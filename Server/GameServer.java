package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    public static final int PORT= 11100;
    List<Game> activeGames=new ArrayList<>();
    public GameServer() throws IOException {
        ServerSocket serverSocket = null ;
        try {
            serverSocket = new ServerSocket(PORT);
            while (!serverSocket.isClosed()) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket,serverSocket,this).start();
            }
        } catch (IOException e) {
            System.err. println (e);
        } finally {
            serverSocket.close();
        }
    }
    public int createGame(Player p){
        Game game=new Game(p);
        p.setGame(game);
        activeGames.add(game);
        return (activeGames.size()-1);
    }

    public int joinGame(Player p){
        for (int i=0;i<activeGames.size();i++){
            Game game=activeGames.get(i);
            if (!game.hasTwoPlayers){
                game.setHasTwoPlayers(true);
                game.setPlayer2(p);
                p.setGame(game);
                return i;
            }
        }
        return -1;
    }
}
