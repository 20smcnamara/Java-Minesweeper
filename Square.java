package com.example.a20smcnamara.minesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by 20smcnamara on 5/23/18.
 */

public class Square {

    private int contains = 0;
    private int status = 0;//0 not vis, not sel; 1 sel, not vis; 2 not sel, vis; 3 not sel, !; 4 not sel, ?
    private int around = 0;
    private int color = Color.rgb(175,175,175);
    private int color2 = Color.rgb(130,130,130);
    private int color3 = Color.rgb(200,200,200);
    private int color4 = color2;
    private int X_pos;
    private int Y_pos;
    private int size;

    private String str = " ";

    private Rect rectangle;

    public Square(int x, int y,int X_pos, int Y_pos,int size){
        this.X_pos = X_pos;
        this.Y_pos = Y_pos;
        this.size = size;
        int s = size-5/2;
        rectangle = new Rect(x-s,y-s,x+s,y+s);
    }

    public void setContains(int x){this.contains = x;}

    public int getContains() {
        return contains;
    }

    public void setAround(ArrayList<ArrayList<Square>> board){
        if(contains == 0) {
            around = 0;
            for (int i = -1; i < 2; i++) {
                for (int x = -1; x < 2; x++) {
                    if (!(x == 0 && i == 0)) {
                        if (x + X_pos > -1 && i + Y_pos > -1 && X_pos + x < board.size() && Y_pos + i < board.get(0).size()) {
                            if (board.get(x + X_pos).get(i + Y_pos).getContains() == 1) {
                                around++;
                            }
                        }
                    }
                }
            }
            str = ((Integer) around).toString();
        }
        else {
            str = "X";
        }
        setColor();
    }

    public void draw(Canvas canvas){
        Constants.drawRectQuick(canvas, rectangle, color);
        if(status == 1) {
            Constants.drawRectQuick(canvas, rectangle, color2);
            Rect temp = new Rect(rectangle.left+20,rectangle.top+20,rectangle.right-20,rectangle.bottom-20);
            Constants.drawRectQuick(canvas, temp, color);
        }
        if(status > 1) {
            if((Integer)color2 != null && str != "") {
                Paint paint = new Paint();
                paint.setColor(color4);
                int size = (rectangle.width() - 25) / str.length() * 2;
                if (size > 100) {
                    size = 100;
                }
                paint.setTextSize(size);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(str, rectangle.left + rectangle.width() / 2, rectangle.top + rectangle.height() / 2 + size / 3, paint);
            }
        }
    }

    public int recieveTouch(MotionEvent event) {//What even is this?
        if(event.getY() < Constants.SCREEN_HEIGHT2-500 && status != 2) {
            Rect r = new Rect((int) event.getX() - 1, (int) event.getY() - 1, (int) event.getX() + 1, (int) event.getY() + 1);
            if (event.getAction() == 0 && r.intersect(rectangle) && status != 1 && status != 2) {
                status = 1;
                return 1;
            }
            if (event.getAction() == 0 && r.intersect(rectangle) && status == 1) {
                status = 0;
                return 2;
            }
            if(!r.intersect(rectangle)){
                status = 0;
                return -2;
            }
        }
        return -1;
    }

    public boolean setStatus(int x,ArrayList<ArrayList<Square>> board){
        status = x;
        if(contains == 1 && status == 2){
            return false;
        }
        preUpdate(board);
        return true;
    }

    public void setStatus(int x,ArrayList<ArrayList<Square>> board,ArrayList<ArrayList<Integer>> calledBy){
        status = x;
        update(board,calledBy);
    }

    public void setColor(){
        if(status == 1 || status > 2){
            color4 = color2;
        }
        if(status == 2) {
            if (around == 1) {
                color4 = Color.rgb(0, 128, 255);
            }
            if (around == 2) {
                color4 = Color.rgb(0, 204, 0);
            }
            if (around == 3) {
                color4 = Color.rgb(255, 51, 51);
            }
            if (around == 4) {
                color4 = Color.rgb(76, 0, 153);
            }
            if (around == 5) {
                color4 = Color.rgb(204, 0, 0);
            }
            if (around == 6) {
                color4 = Color.rgb(153, 0, 0);
            }
        }
        if(status > 2){
            color4 = 1;
        }
    }

    public int getStatus(){
        return status;
    }

    public void deselect(){
        if(status == 1){
            status = 0;
        }
    }

    public void preUpdate(ArrayList<ArrayList<Square>> board){  //The heck did I do. Why would I even put this here it make zero hecking sense. - Sean McNamara (edited)
        update(board,new ArrayList<ArrayList<Integer>>());      //The heck did yo say about me I will have you know that you are right I see no reason to call a method to call a method using only what you gave it. Here is the thing tho you did not have to insult me. You did not have to tell me. It works and even now that I see I don't need to change it I am not going to because if I do there is a 80% chance it will break something not related to it at all. The question is why you had to be so aggressive in your correction of my mistake. All I did was create a few strange lines of code and wham, you just ripped me apart.
    }

    public void update(ArrayList<ArrayList<Square>> board, ArrayList<ArrayList<Integer>> calledBy){
        if(status == 1){
            str = "";
        }
        if(status == 2){
            str = around+"";
            color = color3;
            if(contains == 1){
                str = "X";
            }
            if(around == 0 && contains != 1){
                str = "";
                ArrayList<Integer> add = new ArrayList<>();
                add.add(X_pos);
                add.add(Y_pos);
                calledBy.add(add);
                for (int i = -1; i < 2; i++) {
                    for (int x = -1; x < 2; x++) {
                        boolean b1 = x + X_pos == -1 || i + Y_pos == -1;//On left or top edge
                        boolean b2 = x + X_pos == board.size() || i + Y_pos == board.get(0).size();//On right or bottom edge
                        boolean b3 = true;//Done before
                        for(int num = 0; num < calledBy.size(); num++){//This loop is to check if it has been done before
                            if(calledBy.size() > 0) {
                                for (int number = 0; number < calledBy.get(0).size(); number++){
                                    if(calledBy.get(num).get(0) == X_pos+x && calledBy.get(num).get(1) == Y_pos+i ){
                                        b3 = false;
                                    }
                                }
                            }
                        }
                        //System.out.println(b1+":"+b2+":"+b3+" ("+X_pos+", "+ Y_pos+")"+" ("+(X_pos+x)+", "+ (Y_pos+i)+")");
                        if (!(b1 || b2) && b3) {
                            board.get(x + X_pos).get(i + Y_pos).setStatus(2, board,calledBy);
                        }
                    }
                }
            }
        }
        if(status == 3){
            str = "!";
        }
        if(status == 4){
            str = "?";
        }
    }

    public void update(int x, int y){
        if(x != X_pos && Y_pos != y && status == 1){
            status = 0;
        }
    }

    public int getX(){
        return X_pos;
    }

    public int getY(){
        return Y_pos;
    }

    public int getAround(){
        return around;
    }
}
