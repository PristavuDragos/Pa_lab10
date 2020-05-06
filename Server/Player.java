package com.company;

public class Player {
    String name;
    int pieceType;
    ClientThread clientThread;
    Game game;

    public Player(String name, int pieceType, ClientThread clientThread) {
        this.name = name;
        this.pieceType = pieceType;
        this.clientThread = clientThread;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
