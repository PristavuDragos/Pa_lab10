package com.company;

import java.util.Arrays;

public class Board {
    int[][] pieces=new int[15][15];

    public Board(){}

    public boolean addPiece(int line,int col,int pieceType){
        System.out.flush();
        if (pieces[line][col]==0){
            pieces[line][col]=pieceType;
            return true;
        }else{
            return false;
        }
    }

    public boolean winCondition(int pieceType){
        int countL=0;
        int countC=0;
        for (int i=0;i<15;i++){
            for (int j=0;j<15;j++){
                if (pieces[i][j]==pieceType) countL++;
                else countL=0;
                if (countL == 5) return true;
                if (pieces[j][i]==pieceType) countC++;
                else countC=0;
                if (countC == 5) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String result="   ";
        for (int i=0;i<15;i++){
            result+=i+" ";
            if (i<10) result+=" ";
        }
        for (int i=0;i<15;i++){
            result=result+"\n"+((char)('A'+i))+"  ";
            for (int j=0;j<15;j++){
                result+=pieces[i][j]+"  ";
            }
        }
        return result;
    }
}
