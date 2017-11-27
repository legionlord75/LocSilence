package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class AddLocSettingsActivity extends AppCompatActivity {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = GPSSearchActivity.class.getSimpleName();
    String[] volumeTypes = {"Ringtone", "Notifications", "Alarms"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loc_settings);

        // Init info
        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        final Location selectedLocation = (Location)getIntent().getParcelableExtra("selectedLocation");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Button setButton = (Button) findViewById(R.id.button_setSettings);
        final Button deleteButton = (Button) findViewById(R.id.button_deleteSettings);

        // Set basic ui
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(selectedLocation.getAddress());
//        toolbar.setSubtitle("Last updated: "+ selectedLocation.getUpdatedAt());
//        selectedLocation.setUpdatedAt(new Date().toString());
        toolbar.setSubtitle("LocSilence");
        // Create and set custom adapter of different volume type settings
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final SettingsAdapter settingsAdapter = new SettingsAdapter(this, volumeTypes,
                selectedLocation.getVolumes(), am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
        ListView settingsListView = (ListView) findViewById(R.id.listView_settings);
        settingsListView.setAdapter(settingsAdapter);
        final NumberPicker rad = (NumberPicker) findViewById(R.id.numberPicker_radius);
        rad.setMaxValue(1000);
        rad.setMinValue(50);
        // Init Listeners
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> volumeLevels = settingsAdapter.getVolumeLevels();
                selectedLocation.setVolumes(volumeLevels);
                if(selectedLocation.getRad() == rad.getValue()){
                    selectedLocation.setRad(rad.getValue());
                    db.updateLocalGame(selectedLocation);
                }
                if (db.getLocation(selectedLocation.getId()) == null) {
                    db.addLocation(selectedLocation);
                } else {
                    db.updateLocalGame(selectedLocation);
                }
                Intent i = new Intent(AddLocSettingsActivity.this, MapsActivity.class);
                db.close();
                startActivity(i);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getLocation(selectedLocation.getId()) != null) {
                    db.deleteLocalGame(selectedLocation.getId());
                }
                Intent i = new Intent(AddLocSettingsActivity.this, MapsActivity.class);
                db.close();
                startActivity(i);
                finish();
            }
        });
    }
}