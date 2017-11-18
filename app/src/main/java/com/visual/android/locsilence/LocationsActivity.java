package com.visual.android.locsilence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class LocationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Locations");
        toolbar.setSubtitle("LocSilence");

        Button mapButton = (Button)findViewById(R.id.mapButton);
        Button locationsButton = (Button)findViewById(R.id.locButton);

        locationsButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationsActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });


        SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        ListView listView = (ListView)findViewById(R.id.listview);
        LocationsAdapter locationsAdapter = new LocationsAdapter(this, db.getAllLocations());
        listView.setAdapter(locationsAdapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapterView, View v, int position, long id){
                Location selectedLocation = (Location) adapterView.getItemAtPosition(position);
                // Pass location to settings activity to set the volume settings
                Intent settingsIntent = new Intent(LocationsActivity.this, AddLocSettingsActivity.class);
                settingsIntent.putExtra("selectedLocation", selectedLocation);
                startActivity(settingsIntent);
                //based on item add info to intent
                startActivity(settingsIntent);
            }
        });*/

    }

    public void next_page(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}



