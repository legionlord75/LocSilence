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
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LocationsActivity extends AppCompatActivity {
    private static final String TAG = GPSSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button mapButton = (Button)findViewById(R.id.mapButton);
        Button locationsButton = (Button)findViewById(R.id.locButton);
        locationsButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.locList_place_autocomplete_fragment);
        autocompleteFragment.setHint("Update or add location");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Location selectedLocation;
                // If place is in db already update location info in db
                if (db.locationInDB(place.getId())) {
                    selectedLocation = db.getLocation(place.getId());
                    selectedLocation.setUpdatedAt(new Date().toString());
                }
                // If place is new set basic new locations
                else {
                    selectedLocation = new Location(
                            place.getId(),
                            place.getName().toString(),
                            (float) place.getLatLng().latitude,
                            (float) place.getLatLng().latitude,
                            new Date().toString(),
                            new Date().toString(),
                            "",
                            100);
                }
                // Pass location to settings activity to set the volume settings
                Intent settingsIntent = new Intent(LocationsActivity.this, AddLocSettingsActivity.class);
                settingsIntent.putExtra("selectedLocation", selectedLocation);
                startActivity(settingsIntent);
             }
            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationsActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ListView listView = (ListView)findViewById(R.id.listview);
        List<Location> locations = db.getAllLocations();
        LocationsAdapter locationsAdapter = new LocationsAdapter(this, locations);
        listView.setAdapter(locationsAdapter);

    }

    public void next_page(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}



