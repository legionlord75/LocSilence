package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddLocSettingsActivity extends AppCompatActivity {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = GPSSearchActivity.class.getSimpleName();
    Location location;
    String[] volumeTypes = {"ringtone", "media", "alarms", "call"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loc_settings);
        // Get location passed from searchBar activity
        this.location = (Location)getIntent().getParcelableExtra("selectedLocation");
        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);

        // Set Title information passed from searchBar activity
        TextView title = (TextView) findViewById(R.id.title_place);
        TextView lastUpdated = (TextView) findViewById(R.id.title_lastUpdated);
        title.setText(location.getName());
        lastUpdated.setText("Last updated: "+ location.getUpdatedAt());

        // Create and set listView of different volume type settings
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final SettingsAdapter settingsAdapter = new SettingsAdapter(this, volumeTypes,
                                                  am.getStreamMaxVolume(AudioManager.STREAM_RING));
        ListView settingsListView = (ListView) findViewById(R.id.listView_settings);
        settingsListView.setAdapter(settingsAdapter);

        final Button setButton = (Button) findViewById(R.id.button_setSettings);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  testVolumes(settingsAdapter.getVolume());

/*                if (db.getLocation(location.getId()) == null) {
                    db.addLocation(location);
                } else {
                    // TODO: 10/22/2017 Add onDuplicateKey functionality
                    System.out.println("EXISTS");
                }
                Intent i = new Intent(AddLocSettingsActivity.this, MapsActivity.class);
                startActivity(i);
*/

            }
        });
/*
        final Button setButton = (Button)findViewById(R.id.setSettingsButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(selectedPlace.getId());

                Location location = new Location(
                        selectedPlace.getId(),
                        selectedPlace.getName().toString(),
                        selectedPlace.getLatLng().latitude,
                        selectedPlace.getLatLng().longitude,
                        new Date().toString(),
                        new Date().toString(),
                        1,
                        "",
                        100);

                if (db.getLocation(selectedPlace.getId()) == null) {
                    db.addLocation(location);
                } else {
                    db.updateLocalGame(location);
                }

                Intent i = new Intent(GPSSearchActivity.this, MapsActivity.class);
                startActivity(i);

                // setLocationInDB(selectedPlace);
            }
        });*/

    }

    // EX: testVolumes(settingsAdapter.getVolume());
    public void testVolumes(int[] vol){
        final TextView volumeLevel = (TextView) findViewById(R.id.volume_levels);
        String currentVolumes = "";
        for(int i=0; i<vol.length; i++){
            currentVolumes += vol[i] + ",";
        }
        volumeLevel.setText(currentVolumes);
    }

    protected void setLocationInDB(Place place) {
        final SQLDatabaseHandler dbReference = new SQLDatabaseHandler(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Log.i(TAG, "Place: " + place.getName());
        Log.i(TAG, "Time: " + currentDateandTime);
        Log.i(TAG, "LatLng: " + place.getLatLng());

        if (dbReference.locationInDB(place.getId())) {
            Log.i(TAG, "updating location in DB, id: " + place.getId());
            Location location = dbReference.getLocation(place.getId());
            location.setName(place.getName().toString());
            // TODO: add functionality to have different volumes (0,1, for testing)
            location.setVolume(1);
            location.setUpdatedAt(currentDateandTime);
            dbReference.updateLocalGame(location);
        } else {
            Log.i(TAG, "adding location to DB, id: " + place.getId());
            Location location = new Location(place.getId(), place.getName().toString(),
                    (float) place.getLatLng().latitude, (float) place.getLatLng().latitude,
                    currentDateandTime, currentDateandTime, 2, "", 100);
            dbReference.addLocation(location);
        }
    }

    protected void alertToast(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

        /*    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    Log.i(TAG, "Place: " + place.getName());
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    // TODO: Handle the error.
                    Log.i(TAG, status.getStatusMessage());

                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            }
        }
    */

}