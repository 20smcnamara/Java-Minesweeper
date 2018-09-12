package com.example.a20smcnamara.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 20smcnamara on 5/23/18.
 */

public class GameplayScene extends AdvancedScene{

    private ArrayList<ArrayList<Square>> board = new ArrayList<>();

    private basicButton[] buttons = new basicButton[3];

    private Integer curr_X = null;//curr_X is used to find the row
    private Integer curr_Y = null;//curr_Y is used to find the col Example board.get(curr_X).get(curr_Y).update()

    private int countdown = -1;
    private int found = 0;
    private int spaces;
    private boolean first = true;
    private static final int SIZE = 100;
    private static final int RESET_TIME = 50;

    private boolean gameOver = false;

    public GameplayScene(){
        Rect rect1 = new Rect(50, Constants.SCREEN_HEIGHT2-350,(Constants.SCREEN_WIDTH2)/3-10,Constants.SCREEN_HEIGHT2-50);
        Rect rect2 = new Rect((Constants.SCREEN_WIDTH2)/3+10, Constants.SCREEN_HEIGHT2-350,(Constants.SCREEN_WIDTH2)/3*2-10,Constants.SCREEN_HEIGHT2-50);
        Rect rect3 = new Rect((Constants.SCREEN_WIDTH2)/3*2+10, Constants.SCREEN_HEIGHT2-350,Constants.SCREEN_WIDTH2-50,Constants.SCREEN_HEIGHT2-50);
        buttons[0] = new basicButton(rect1,"Choose",1);
        buttons[1] = new basicButton(rect2,"Mark !",2);
        buttons[2] = new basicButton(rect3,"Mark ?",3);
        spaces = 0;
        int num = 0;
        for(int t = SIZE; t <= Constants.SCREEN_WIDTH2-SIZE; t+=SIZE*2){
            num++;
        }
        int index1 = 0;
        for(int i = SIZE+40; i < Constants.SCREEN_HEIGHT2-500; i+=SIZE*2+1){
            int index2 = 0;
            ArrayList<Square> Blist = new ArrayList<>();
            for(int x = SIZE+(Constants.SCREEN_WIDTH2-num*200)/2; x <= Constants.SCREEN_WIDTH2-100; x+=SIZE*2+1){
                Blist.add(new Square(x,i,index1,index2,SIZE));
                index2++;
            }
            index1++;
            board.add(Blist);
        }
    }

    public void draw(Canvas canvas){
        Constants.drawBackground(canvas);
        for(int i = 0; i < board.size(); i++){
            for (int x = 0; x < board.get(0).size(); x++){
                board.get(i).get(x).draw(canvas);
            }
        }
        for(basicButton b: buttons){
            b.draw(canvas, Color.rgb(175,175,175),Color.WHITE);
        }
        if(countdown > -1){
            Constants.drawText(canvas,"Game Over",Color.RED,new Rect(300,200,Constants.SCREEN_WIDTH2-300,800),200);

        }
        Constants.drawText(canvas,"Bombs left: "+((Integer)(15-found)).toString(),Color.RED,new Rect(300,Constants.SCREEN_HEIGHT2-650,Constants.SCREEN_WIDTH2-300,Constants.SCREEN_HEIGHT2-250),75);

    }

    @Override
    public int recieveTouch(MotionEvent event) {
        if(event.getY() < Constants.SCREEN_HEIGHT2-500-95){
            for (int  i = 0; i < board.size(); i++){
                for(int  x = 0; x < board.get(0).size(); x++){
                    int num = board.get(i).get(x).recieveTouch(event);
                    if(num > 0){
                        if(num == 3){
                            countdown = RESET_TIME;
                            return -1;
                        }
                        if(curr_X != null && i != curr_X && x != curr_X) {
                            board.get(curr_X).get(curr_Y).deselect();
                            if (first) {
                                Random r = new Random();
                                while(spaces < 16){
                                    ArrayList<Square> Blist = board.get(r.nextInt(board.size()));
                                    Square b = Blist.get(r.nextInt(board.get(0).size()));
                                    if(b.getContains() == 0) {
                                        b.setContains(1);
                                        spaces++;
                                    }
                                }
                                for(int ii = 0; ii < board.size(); ii ++){
                                    for (int xx = 0; xx <board.get(0).size(); xx++) {
                                        board.get(ii).get(xx).setAround(board);
                                    }
                                }
                            }
                        }
                        curr_X = i;
                        curr_Y = x;

                    }
                }
            }
        }
        else{
            if(curr_X != null && curr_Y != null) {
                int num = 0;
                boolean found = false;
                for (basicButton b : buttons) {
                    num = b.recieveTouch(event);
                    if (num > 0) {
                        found = true;
                        break;
                    }
                }
                if(!found){
                    return -1;
                }
                if(board.get(curr_X).get(curr_Y).getStatus() != 2 && !board.get(curr_X).get(curr_Y).setStatus(num + 1,board)){
                    countdown = RESET_TIME;
                }
            }
        }
        return -1;
    }

    @Override
    public void update(){

    }

    public boolean update(int x){
        if(countdown > -1){
            countdown--;
        }
        if(countdown == 0) {
            gameOver = true;
        }
        found = 0;
        for(int i = 0; i < board.size(); i++){
            for (int xint = 0; x < board.get(0).size(); x++){
                Square s = board.get(i).get(xint);
                if(s.getContains() == 1 && s.getStatus() == 2){
                    countdown = RESET_TIME;
                }
                if(s.getStatus() == 3){
                    found++;
                }
            }
        }
        return gameOver;
    }

    public Square choose(int x, int y, int z){
        board.get(x).get(y).setStatus(z,board);
        return board.get(x).get(y);
    }
}
