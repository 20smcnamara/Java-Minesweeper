package com.example.a20smcnamara.minesweeper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by 20smcnamara on 4/5/18.
 */

public class AdvancedScene implements Scene {

    public void update() {
        System.out.println("You are doing it wrong UP");
    }

    public void terminate(){
        System.out.println("Not implemented TE");
    }


    public void draw(Canvas canvas){
        System.out.println("Not implemented DR");
    }

    public int recieveTouch(MotionEvent event){
        System.out.println("Not implemented RE");
        return -123456789;
    }

    public int recieveTouch(MotionEvent event, int coins,ArrayList<Integer> owned){
        System.out.println("Not implemented RE-INT-INT_ARL");
        return -123456789;
    }

    public int recieveTouch(MotionEvent event, int coins){
        System.out.println("Not implemented RE-INT");
        return -123456789;
    }

    public boolean update(int x) {
        System.out.println("Not implemented UP-int");
        return false;
    }
}
