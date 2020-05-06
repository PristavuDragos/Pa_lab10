package com.company;

public class Game {
    Board gameBoard;
    boolean hasTwoPlayers=false;
    Player player1;
    Player player2;

    public Game(Player p){
        gameBoard= new Board();
        this.player1=p;
    }

    public void setHasTwoPlayers(boolean hasTwoPlayers) {
        this.hasTwoPlayers = hasTwoPlayers;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
