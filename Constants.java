package com.example.a20smcnamara.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by 20smcnamara on 3/20/18.
 */

public class Constants {
    public static int SCREEN_WIDTH = 1080;
    public static int SCREEN_HEIGHT = 1920;
    public static int SCREEN_WIDTH2;
    public static int SCREEN_HEIGHT2;
    public static double wwRatio;
    public static double hhRatio;

    public static Context CURRENT_CONTEXT;

    public static long INIT_TIME;

    public static final int BLUE = Color.rgb(100,100,255);
    public static final int GREEN = Color.rgb(100,255,100);
    public static final int LGRAY = Color.rgb(200,200,200);
    public static final int GRAY = Color.rgb(160,160,160);
    public static final int Purple = Color.rgb(230,0,225);
    public static final int RED = Color.rgb(255,100,100);

    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;

    public static void setUp(){
        sharedPref = Constants.CURRENT_CONTEXT.getSharedPreferences("settings", 0);
        editor = sharedPref.edit();
    }

    public static void drawCenterText(Canvas canvas, Paint paint, String text, Rect rect) {
        Rect r = copyRect(rect);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    public static Rect copyRect(Rect rect){
        Rect rectangle = new Rect();
        if(!rect.isEmpty()){
            rectangle.set(rect.left,rect.top,rect.right,rect.bottom);
        }
        return rectangle;
    }

    public static Rect scaleRect(Rect rect, Bitmap frame) {
        float whRatio = (float) (frame.getWidth()) / frame.getHeight();
        if (rect.width() > rect.height())
            rect.left = rect.right - (int) (rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int) (rect.width() * (1 / whRatio));
        return rect;
    }

    public static void drawRectQuick(Canvas canvas, Rect rectangle,int color){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    public static void setRatios(){
        hhRatio = SCREEN_HEIGHT2/(double)1920;
        wwRatio = SCREEN_WIDTH2/(double)1080;
    }

    public static Rect scaleRectTwo(Rect rect) {
        int x = (int)(rect.centerX()*wwRatio);
        int y = (int)(rect.centerY()*hhRatio);
        int width = (int)(rect.width()*wwRatio/2);
        int height = (int)(rect.height()*hhRatio/2);
        rect.set((x-width),(y-height),(x+width),(y+height));
        return rect;
    }

    public static void drawBackground(Canvas canvas){
        canvas.drawColor(Color.rgb(235,235,235));
    }

    public static void drawText(Canvas canvas, String str, int color, Rect rectangle,int size){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(str, rectangle.left+rectangle.width()/2, rectangle.top+rectangle.height()/2+size/3, paint);
    }
}
