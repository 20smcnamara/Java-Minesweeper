package com.example.a20smcnamara.minesweeper;

import java.util.List;

/**
 * Created by 20smcnamara on 5/29/18.
 */

public class SuperList {
    private Square[][] list;

    public SuperList(){
        list = new Square[0][0];
    }

    public void add(Square square,int row,int col){
        int xx = 0;
        int yy = 0;
        if(row > list.length-1){
            xx=1;
        }
        if(row > list[0].length-1){
            yy=1;
        }
        Square[][] temp = new Square[list.length+xx][list[0].length+yy];
        for(int  i = 0; i < list.length; i++){
            for(int  x = 0; x < list.length; x++){
                temp[i][x] = list[i][x];
            }
        }
        temp[row][col] = square;
        list = temp;
    }

    public Square[][] get(){
        return list;
    }

    public Square get(int row, int col){
        return list[row][col];
    }
}
