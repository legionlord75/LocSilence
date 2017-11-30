package com.visual.android.locsilence;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by ErikL on 10/19/2017.
 */

public class Graphics extends AppCompatActivity {
    public static boolean setrad = false;
    Context context;
    private Location selectedLocation;

    //On Start drawing the circles for stored locations
    public SQLDatabaseHandler startDraw(GoogleMap map, SQLDatabaseHandler handler){
        List<Location> locations = handler.getAllLocations();

        //iterates through database and draws the circles
        for (Location loc : locations) {
            selectedLocation = loc;
            if (loc.getCustomProximity().equals(Constants.JSON_NULL)) {
                Log.i("rad", "generic: "+loc.getName()+": " + loc.getRad());
                LatLng cent = new LatLng(loc.getLat(), loc.getLng());
                CircleOptions opt = new CircleOptions().center(cent).radius(
                        loc.getRad()).strokeColor(Color.BLACK).fillColor(0x88FF6800).clickable(true);
                final Circle circle = map.addCircle(opt);
                map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                    @Override
                    public void onCircleClick(Circle circle) {
                        setrad = true;
                        Log.e("circle clicked", "Circle " + circle.getId() + " was clicked");
                    }
                });
                loc.setCid(circle.getId());
                handler.updateLocalGame(loc);
            }
            else{
                Log.i("rad", "custom: "+loc.getName()+": " + loc.getCustomProximity());
                String customProxJSON = loc.getCustomProximity();
                ArrayList<LatLng> boundary = Utility.customProxToList(customProxJSON);
                this.perimeterDraw(map, boundary);
            }
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
        CircleOptions opt = new CircleOptions().center(cent).radius(15).strokeColor(Color.BLACK).fillColor(Color.BLACK);
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

