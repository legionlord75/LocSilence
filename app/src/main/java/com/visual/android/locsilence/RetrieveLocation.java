package com.visual.android.locsilence;

import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by RamiK on 11/1/2017.
 */

public abstract class RetrieveLocation extends AsyncTask<LocationManager, Void, LatLng> {

    @Override
    protected LatLng doInBackground(LocationManager... locationManagers) {
        System.out.println("DO IN BACKGROUND");

        Criteria criteria = new Criteria();
        android.location.Location location = null;
        try {
            location = locationManagers[0].getLastKnownLocation(locationManagers[0]
                    .getBestProvider(criteria, false));
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        return new LatLng(latitude, longitude);
    }
}
