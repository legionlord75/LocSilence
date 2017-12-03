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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ErikL on 10/19/2017.
 */

public class Graphics extends AppCompatActivity {
    public static boolean setRadius = false;

    //On Start drawing the circles for stored locations
    public SQLDatabaseHandler startDraw(GoogleMap map, SQLDatabaseHandler handler) {
        List<Location> allLocations = handler.getAllLocations();
        //iterates through database and draws the circles
        for (Location location : allLocations) {
            LatLng center = new LatLng(location.getLat(), location.getLng());

            CircleOptions opt = new CircleOptions().center(center).radius(location.getRadius()).strokeColor(Color.BLACK).fillColor(0x88FF6800).clickable(true);
            final Circle circle = map.addCircle(opt);
            map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    setRadius = true;
                    Log.e("circle clicked", "Circle " + circle.getId() + " was clicked");
                }
            });
            location.setCircleId(circle.getId());
            handler.updateLocalGame(location);
        }
        return handler;
    }

    public static boolean getIfClicked() {
        return setRadius;
    }

    public static void setIfClicked() {
        setRadius = false;
    }

    public void pointDraw(GoogleMap map, LatLng center) {
        CircleOptions circleOptions = new CircleOptions().center(center).radius(15).strokeColor(Color.BLACK).fillColor(Color.BLACK);
        final Circle circle = map.addCircle(circleOptions);
    }

    //Perimeter outlining (Work in Progress) LatLngBounds promising to help
    public void perimeterDraw(GoogleMap map, ArrayList<LatLng> points) {
        PolygonOptions polygonOptions = new PolygonOptions().strokeColor(Color.BLACK).fillColor(0x88FF6800);
        for (LatLng point : points) {
            polygonOptions.add(point);
        }
        map.clear();
        final Polygon polygon = map.addPolygon(polygonOptions);
    }
}

