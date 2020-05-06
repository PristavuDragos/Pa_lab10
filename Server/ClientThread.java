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
    private GameServer gameServer;
    private Player player=null;
    PrintWriter out;
    public ClientThread (Socket socket, ServerSocket serverSocket,GameServer gameServer) {
        this.socket = socket ;
        this.serverSocket=serverSocket;
        this.gameServer=gameServer;
    }
    public void run () {

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            String request = in.readLine();
            while (request.compareTo("stop")!=0 && request.compareTo("exit")!=0) {
                String raspuns = "Bad request!";
                if (request.compareTo("create game")==0){
                    out.println("Insert your name:");
                    out.flush();
                    String name=in.readLine();
                    player=new Player(name,1,this);
                    gameServer.createGame(player);
                }else if (request.compareTo("join game")==0){
                    out.println("Insert your name:");
                    out.flush();
                    String name=in.readLine();
                    player=new Player(name,2,this);
                    if(gameServer.joinGame(player)>=0) {
                        player.game.player1.clientThread.out.println("Playing with "+player.game.player2.name);
                        player.game.player1.clientThread.out.flush();
                        player.game.player2.clientThread.out.println("Playing with "+player.game.player1.name);
                        player.game.player2.clientThread.out.flush();
                    }else{
                        out.println("No available lobby");
                        out.flush();
                    }
                }else if (player!=null && request.substring(0,11).compareTo("submit move")==0 && request.length()<=16){
                    if (request.charAt(12)>='A' && request.charAt(12)<='O'){
                        int line=request.charAt(12)-65;
                        int col;
                        if (request.length()==15 && request.charAt(14)>='0' && request.charAt(14)<='9'){
                            col=Integer.parseInt(request.substring(14));
                            if(player.game.gameBoard.addPiece(line,col,player.pieceType)) {
                                boolean victory=player.game.gameBoard.winCondition(player.pieceType);
                                String board=player.game.gameBoard.toString();
                                //System.out.println(board);
                                if (victory){
                                    String winnerName=player.name;
                                    player.game.player1.clientThread.out.print(winnerName+" has won!");
                                    player.game.player1.clientThread.out.flush();
                                    player.game.player2.clientThread.out.print(winnerName+" has won!");
                                    player.game.player2.clientThread.out.flush();
                                    player.game.player1.clientThread.interrupt();
                                    player.game.player2.clientThread.interrupt();
                                }else {
                                    player.game.player1.clientThread.out.print(board);
                                    player.game.player1.clientThread.out.flush();
                                    player.game.player2.clientThread.out.print(board);
                                    player.game.player2.clientThread.out.flush();
                                }
                            }else{
                                out.println("Invalid move!");
                                out.flush();
                            }
                        }else if(request.length()==16 && request.charAt(14)=='1' && request.charAt(15)>='0' && request.charAt(15)<='4'){
                            col=Integer.parseInt(request.substring(14));
                            if(player.game.gameBoard.addPiece(line,col,player.pieceType)){
                                player.game.player1.clientThread.out.print(player.game.gameBoard.toString());
                                player.game.player1.clientThread.out.flush();
                                player.game.player2.clientThread.out.print(player.game.gameBoard.toString());
                                player.game.player2.clientThread.out.flush();
                            }else{
                                out.println("Invalid move!");
                                out.flush();
                            }
                        }else{
                            out.println("Invalid move!");
                            out.flush();
                        }

                    }else{
                        out.println("Invalid move!");
                        out.flush();
                    }
                }else{
                    out.println(raspuns);
                    out.flush();
                }
                request = in.readLine();
            }
            if (request.compareTo("stop")==0){
                out.println("Server stopped");
                out.flush();
                serverSocket.close();
            }
            this.socket.close();
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) { System.err.println (e); }
        }
    }

}
