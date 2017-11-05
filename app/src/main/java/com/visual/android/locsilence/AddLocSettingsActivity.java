package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddLocSettingsActivity extends AppCompatActivity {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = GPSSearchActivity.class.getSimpleName();
    String[] volumeTypes = {"ringtone", "media", "alarms", "call"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loc_settings);
        // Get location passed from searchBar activity
        final Location selectedLocation = (Location)getIntent().getParcelableExtra("selectedLocation");
        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);

        // Set Title information passed from searchBar activity
        TextView title = (TextView) findViewById(R.id.title_place);
        TextView lastUpdated = (TextView) findViewById(R.id.title_lastUpdated);
        title.setText(selectedLocation.getName());
        lastUpdated.setText("Last updated: "+ selectedLocation.getUpdatedAt());

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
                int[] volumeLevels = settingsAdapter.getVolumeLevels();
                setLocationVolumes(selectedLocation, volumeLevels);
                printTestVolumes(selectedLocation);

                if (db.getLocation(selectedLocation.getId()) == null) {
                    db.addLocation(selectedLocation);
                } else {
                    db.updateLocalGame(selectedLocation);
                }
                Intent i = new Intent(AddLocSettingsActivity.this, MapsActivity.class);
//                startActivity(i);
            }
        });
    }

    public void setLocationVolumes(Location location, int[] volumeLevels){
        final CheckBox checkBoxSilence = (CheckBox) findViewById(R.id.checkbox_silence);
        // Volumes default to 0, therefore location volumes would not need to be set
        if(!checkBoxSilence.isChecked()){
            location.setVolRingtone(volumeLevels[0]);
            location.setVolMedia(volumeLevels[1]);
            location.setVolAlarms(volumeLevels[2]);
            location.setVolCall(volumeLevels[3]);
        }
    }

    // for testing, USAGE: testVolumes(selectedLocation);
    public void printTestVolumes(Location selectedLocation){
        final TextView volumeLevel = (TextView) findViewById(R.id.volume_levels);
        String currentVolumes = "";
        currentVolumes += selectedLocation.getVolRingtone() + ",";
        currentVolumes += selectedLocation.getVolMedia() + ",";
        currentVolumes += selectedLocation.getVolAlarms() + ",";
        currentVolumes += selectedLocation.getVolCall() + ",";
        volumeLevel.setText(currentVolumes);
    }

    protected void alertToast(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}