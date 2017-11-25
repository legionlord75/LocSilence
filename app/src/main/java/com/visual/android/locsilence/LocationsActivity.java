package com.visual.android.locsilence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Date;
import java.util.List;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Date;
import java.util.List;


public class LocationsActivity extends AppCompatActivity {
    private static final String TAG = GPSSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Init info
        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);
        List<Location> locations = db.getAllLocations();
        ListView listView = (ListView) findViewById(R.id.listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_toolbar);
        Button mapButton = (Button) findViewById(R.id.mapButton);

        // Set Basic ui
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LocSilence");
        toolbar.setSubtitle("Locations List");

        LocationsAdapter locationsAdapter = new LocationsAdapter(this, locations, db);
        listView.setAdapter(locationsAdapter);

        // Set searchBar
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.locList_place_autocomplete_fragment);
        autocompleteFragment.setHint("Update or add location");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Location selectedLocation = getSelectedLocation(place, db);
                Intent settingsIntent = new Intent(LocationsActivity.this, AddLocSettingsActivity.class);
                settingsIntent.putExtra("selectedLocation", selectedLocation);
                startActivity(settingsIntent);
            }

            @Override
            public void onError(Status status) {
                Utility.alertToast(getApplicationContext(), "An error occurred with google location");
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(LocationsActivity.this, MapsActivity.class);
                startActivity(mapIntent);
                finish();
            }
        });
    }

    // Return either a location object if 'place' already exists in db, else a new location object
    public Location getSelectedLocation(Place place, SQLDatabaseHandler db) {
        // If place is in db already update location info in db
        Location selectedLocation;
        if (db.locationInDB(place.getId())) {
            selectedLocation = db.getLocation(place.getId());
        }
        // If place is new set basic new locations
        else {
            selectedLocation = new Location(
                    place.getId(),
                    place.getName().toString(),
                    place.getAddress().toString(),
                    (float) place.getLatLng().latitude,
                    (float) place.getLatLng().longitude,
                    new Date().toString(),
                    new Date().toString(),
                    "");
        }
        return selectedLocation;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.setting_id:
                //Go to settings activity
                //Toast.makeText(getApplicationContext(), "Settings button hit", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LocationsActivity.this, SettingsActivity.class);
                startActivity(intent);
                //startActivity(new Intent(MapsActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void next_page(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }


}



