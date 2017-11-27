package com.visual.android.locsilence;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by ErikL on 10/19/2017.
 */

public class Graphics extends AppCompatActivity {
    public static boolean setrad = false;
    //On Start drawing the circles for stored locations
    public SQLDatabaseHandler startDraw(GoogleMap map, SQLDatabaseHandler handler){
        List<Location> enslavingall = handler.getAllLocations();
        //iterates through database and draws the circles
        for (int x = 0; x < enslavingall.size(); x++) {
            Location cur = enslavingall.get(x);
            //Where i will implement
            LatLng cent = new LatLng(cur.getLat(),cur.getLng());
            CircleOptions opt = new CircleOptions().center(cent).radius(cur.getRad()).strokeColor(Color.BLACK).fillColor(0x88FF9800).clickable(true);
            final Circle circle = map.addCircle(opt);
            map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    setrad = true;
                    Log.e("circle clicked", "Circle " + circle.getId() + " was clicked");
                }
            });
            cur.setCid(circle.getId());
            handler.updateLocalGame(cur);
        }
        return handler;
    }

    public static boolean getIfClicked(){
        return setrad;
    }
    public static void setIfClicked(){
        setrad=false;
    }


    //Perimeter outlining (Work in Progress) LatLngBounds promising to help
    public void perimeter(GoogleMap map){
        SQLDatabaseHandler handler = new SQLDatabaseHandler(this);
        List<Location> enslavingall = handler.getAllLocations();
        for (int x = 0; x < enslavingall.size(); x++) {

        }
    }

        //LatLng spot = new LatLng(2.2,2.2);
    }

