package com.visual.android.locsilence;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.visual.android.locsilence.R.mipmap.ic_launcher;

/**
 * Created by RamiK on 11/1/2017.
 */

public class RecursiveSilencePhoneTask extends RetrieveLocation {

    private LocationManager locationManager;
    private SQLDatabaseHandler db;
    private Context context;
    private AudioManager audio;

    boolean recently_silenced = false;

    NotificationCompat.Builder notification;
    private static final int notifID = 12345;



    public RecursiveSilencePhoneTask(LocationManager locationManager, SQLDatabaseHandler db, Context context) {
        this.locationManager = locationManager;
        this.db = db;
        this.context = context;
        audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //notification = new NotificationCompat.Builder(context);
        //notification.setAutoCancel(true);
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
                    sendNotification("Silencing activated");
                    recently_silenced = true;
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
                if (recently_silenced) {
                    sendNotification("Silencing deactivated");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        RecursiveSilencePhoneTask recursiveSilencePhoneTask = new RecursiveSilencePhoneTask(locationManager, db, context);
        recursiveSilencePhoneTask.execute(this.locationManager);

    }

    public void sendNotification(String message) {
        NotificationManager nm = (NotificationManager) this.context.getSystemService(NOTIFICATION_SERVICE);

        if(isNotificationVisible()){
            nm.cancel(notifID);
        }

        notification = new NotificationCompat.Builder(this.context);
        notification.setAutoCancel(true);

        notification.setSmallIcon(ic_launcher);
        notification.setTicker("");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("LocSilence");
        notification.setContentText(message);

        Intent intent = new Intent(this.context, MapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //issues notification
        nm.notify(notifID, notification.build());
    }

    private boolean isNotificationVisible() {
        Intent notificationIntent = new Intent(context, RecursiveSilencePhoneTask.class);
        PendingIntent test = PendingIntent.getActivity(context, notifID, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }
}
