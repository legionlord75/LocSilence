package com.visual.android.locsilence;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by ErikL on 10/19/2017.
 */

public class Graphics extends AppCompatActivity {
    //On Start drawing the circles for stored locations
    public void startDraw(GoogleMap map){
        SQLDatabaseHandler handler = new SQLDatabaseHandler(this);
        List<Location> enslavingall = handler.getAllLocations();
        for(int x=0; x<enslavingall.size();x++){
            Circle circle = map.addCircle(new CircleOptions().center(new LatLng(enslavingall.get(x).getLat(),enslavingall.get(x).getLng())).radius(100).strokeColor(Color.BLACK).fillColor(0x88FF0000));
            circle.setClickable(true);
            enslavingall.get(x).setCid(circle.getId());
            enslavingall.get(x).setRad(100);
            //implement
        }
    }

    public Circle newLocDraw(GoogleMap map,Location loc){
        Circle circle = map.addCircle(new CircleOptions().center(new LatLng(loc.getLat(),loc.getLng())).radius(100).strokeColor(Color.BLACK).fillColor(0x88FF0000));
        circle.setClickable(true);
        return circle;
    }

    //On Click radius changing (Work in Progress)
    public void radChange(GoogleMap map,String id){

        map.clear();
        SQLDatabaseHandler handle = new SQLDatabaseHandler(this);

    }

    //Perimeter outlining (Work in Progress)
    public void perimeter(GoogleMap map){
        SQLDatabaseHandler handler = new SQLDatabaseHandler(this);
        List<Location> enslavingall = handler.getAllLocations();
        for(int x=0; x<enslavingall.size();x++){


        }

        //LatLng spot = new LatLng(2.2,2.2);
    }
}
