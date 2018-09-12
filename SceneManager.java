package com.example.a20smcnamara.minesweeper;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 20smcnamara on 3/26/18.
 */

public class SceneManager {
    private ArrayList<AdvancedScene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public SceneManager() {
        for(int x  = 100; x < 1000; x++){
            System.out.println(x +", "+((x-(double)3)/x));
        }
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
        sharedPref = Constants.sharedPref;
        editor = Constants.editor;
    }

    public void update(){
        for(int x = 0; x < scenes.size(); x++) {
            scenes.get(x).update(0);
        }
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
    
    public void setScene(int scene){
        ACTIVE_SCENE = scene;
    }
}