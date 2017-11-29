package com.visual.android.locsilence;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polygon;
;
import java.util.ArrayList;
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
            CircleOptions opt = new CircleOptions().center(cent).radius(cur.getRad()).strokeColor(Color.BLACK).fillColor(0x88FF6800).clickable(true);
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

    public void pointDraw(GoogleMap map, LatLng cent){
        CircleOptions opt = new CircleOptions().center(cent).radius(5).strokeColor(Color.BLACK).fillColor(R.color.colorAccent);
        final Circle circle = map.addCircle(opt);
    }

    //Perimeter outlining (Work in Progress) LatLngBounds promising to help
    public void perimeterDraw(GoogleMap map, ArrayList<LatLng> points){
        PolygonOptions opt = new PolygonOptions().strokeColor(Color.BLACK).fillColor(0x88FF6800);
        for(int x=0; x<points.size();x++){
            opt.add(points.get(x));
        }
        map.clear();
        final Polygon polygon = map.addPolygon(opt);
    }

        //LatLng spot = new LatLng(2.2,2.2);
    }

