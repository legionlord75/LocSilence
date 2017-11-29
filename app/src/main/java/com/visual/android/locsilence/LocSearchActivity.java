package com.visual.android.locsilence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Date;

public class LocSearchActivity extends AppCompatActivity {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = LocSearchActivity.class.getSimpleName();

    Place selectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_search);

        // Init info
        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);

        // Set searchBar
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Location selectedLocation = getSelectedLocation(place, db);
                Intent settingsIntent = new Intent(LocSearchActivity.this, LocSettingsActivity.class);
                settingsIntent.putExtra("selectedLocation", selectedLocation);
                db.close();
                startActivity(settingsIntent);
            }

            @Override
            public void onError(Status status) {
                Utility.alertToast(getApplicationContext(),"An error occurred with google location");
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    // Return either a location object if 'place' already exists in db, else a new location object
    public Location getSelectedLocation(Place place, SQLDatabaseHandler db){
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

}
