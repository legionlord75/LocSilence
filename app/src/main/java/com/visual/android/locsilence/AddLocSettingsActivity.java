package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class AddLocSettingsActivity extends AppCompatActivity {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = GPSSearchActivity.class.getSimpleName();
    String[] volumeTypes = {"Ringtone", "Notifications", "Alarms"};
    final int DEFAULT_RADIUS = 100;


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
        final EditText genProximity = (EditText) findViewById(R.id.editText_genericProxy);
        final CheckBox custProximity = (CheckBox) findViewById(R.id.checkBox_customProx);

        // Set basic ui
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(selectedLocation.getAddress());
        toolbar.setSubtitle("LocSilence");

        // Create and set custom adapter of different volume type settings
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final SettingsAdapter settingsAdapter = new SettingsAdapter(this, volumeTypes,
                selectedLocation.getVolumes(), am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
        ListView settingsListView = (ListView) findViewById(R.id.listView_settings);
        settingsListView.setAdapter(settingsAdapter);

        // Init Listeners
        genProximity.addTextChangedListener(new TextWatcher(){
            // editingText flag used for preventing infinite recursive loop
            boolean editingText = false;
            public void afterTextChanged(Editable s) {
                String valStr = genProximity.getText().toString();
                if(!valStr.equals("") && editingText == false) {
                    int val = Integer.parseInt(valStr);
                    editingText = true;
                    if (val > 300) {
                        //s.replace(0, s.length(), "300", 0, 3);
                        genProximity.setText("");
                        genProximity.setHint(" 300 max");
                    } else if (val < 1) {
                        s.replace(0, s.length(), "1", 0, 1);
                    }
                    editingText = false;
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(custProximity.isChecked()) {
                    custProximity.setChecked(false);
                    // Clear proximity point data if it was set
                }
            }
        });

        custProximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (custProximity.isChecked()) {
                    genProximity.setText("");
                    Intent customProxIntent = new Intent(AddLocSettingsActivity.this, CustomProximityMap.class);
                    customProxIntent.putExtra("selectedLocation", selectedLocation);
                    startActivity(customProxIntent);
                }
                else{
                    // Option 1: If proximity point data was set in lcoation customProximityMap then Clear the data
                    // Option 2: If proximity point data was passed back to this activity through the intent(?) then set that variable to null
                }
            }
        });


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> volumeLevels = settingsAdapter.getVolumeLevels();
                selectedLocation.setVolumes(volumeLevels);
                if(custProximity.isChecked()){
                    // Option 2: here we would set proximity data
                } else if((genProximity.getText().toString()).equals("")){
                    selectedLocation.setRad(DEFAULT_RADIUS);
                } else{
                    selectedLocation.setRad(Integer.parseInt(genProximity.getText().toString()));
                }

                if (db.getLocation(selectedLocation.getId()) == null) {
                    db.addLocation(selectedLocation);
                } else {
                    db.updateLocalGame(selectedLocation);
                }
                selectedLocation.printLocation();
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