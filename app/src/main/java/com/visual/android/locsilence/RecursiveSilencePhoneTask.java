package com.visual.android.locsilence;

import android.content.Context;
import android.location.LocationManager;
import android.media.AudioManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by RamiK on 11/1/2017.
 */

public class RecursiveSilencePhoneTask extends RetrieveLocation {

    private LocationManager locationManager;
    private SQLDatabaseHandler db;
    private Context context;
    private AudioManager audio;

    public RecursiveSilencePhoneTask(LocationManager locationManager, SQLDatabaseHandler db, Context context) {
        this.locationManager = locationManager;
        this.db = db;
        this.context = context;
        audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        System.out.println("POST");

        super.onPostExecute(latLng);

        List<Location> locations = db.getAllLocations();

        boolean flag = false;
        for (Location location : locations) {
            float[] results = new float[5];
            int radius = location.getRad();
            double lat = location.getLat();
            double lng = location.getLng();
            android.location.Location.distanceBetween(lat, lng, latLng.latitude, latLng.longitude, results);
            // The computed distance is stored in results[0].
            // If results has length 2 or greater, the initial bearing is stored in results[1].
            // If results has length 3 or greater, the final bearing is stored in results[2]
            if (results[0] < radius) {
                try {
                    audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                flag = true;
                break;
            }
        }

        if (!flag) {
            try {
                audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        try {
            // 10 second sleep
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecursiveSilencePhoneTask recursiveSilencePhoneTask = new RecursiveSilencePhoneTask(locationManager, db, context);
        recursiveSilencePhoneTask.execute(this.locationManager);

    }
}
