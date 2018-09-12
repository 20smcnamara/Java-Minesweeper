package com.example.a20smcnamara.minesweeper;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 20smcnamara on 6/11/18.
 */

public abstract class Player implements Scene{
    public Player(int s, int t, boolean b){
    }
}
    /*
    private GameplayScene scene = new GameplayScene();

    private ArrayList<ArrayList<Square>> board;
    private ArrayList<Square> used;

    private int left = 16;
    private int chosen = 0;
    private int fitness = 0;

    private int setting1;//Place if _ many are around
    private int setting2;//PLace if _ many not around
    private boolean setting3;


    private int aroundThis;
    private int aroundThisPicked;
    private int around;

    public Player(int s, int t, boolean b){
        Random random = new Random();
        used.add(scene.choose(random.nextInt(scene.board.size()),random.nextInt(scene.board.get(0).size()), 2));
        chosen++;
        board = scene.board;
        setting1 = s;
        setting2 = t;
        setting3 = b;

    }

    public void terminate(){
        scene.terminate();
    }
    public void draw(Canvas canvas){
        scene.draw(canvas);
    }

    public void findAround(int x, int y){
        for(int i = -1; i < 2; i++){
            for (int c = -1; c < 2; c++){
                if(c != 0 && i != 0 && (i+x > -1 && c+y > -1)){
                    if(board.get(i+x).get(c+y).getStatus() == 2){
                        aroundThisPicked++;
                    }
                    if(i+x > -1 && c+y > -1){
                        aroundThis++;
                    }
                }
            }
        }
        around = board.get(x).get(y).getAround();
    }

    public void update(){
        for(int i = 0; i < used.size(); i++){
            findAround(used.get(i).getX(),used.get(i).getY());
            if(aroundThisPicked < aroundThis){//Just for the sake of speed
               //if(setting3 && around < )
            }

        }
        scene.update();
    }

    public int recieveTouch(MotionEvent event){
        return -1;
    }










    public void setFound(){//No clue what this was
        chosen = 0;
        for(int c = 0;c  < board.size(); c++){
            for(int i = 0; i < board.get(0).size(); i++){
                if(board.get(c).get(i).getStatus()==2){
                    chosen++;
                }
            }
        }
    }
}


/*
Inputs to machine include:

The board
    Distances from closest square
    How many around squares
    Check next closest three



For later
How many bombs left             used,compare to found and chosen
How many bombs found            used,compare to left and chosen
How many spaces chosen          used,compare to found and left
 */