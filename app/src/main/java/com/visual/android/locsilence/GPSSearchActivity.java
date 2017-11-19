package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GPSSearchActivity extends AppCompatActivity {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = GPSSearchActivity.class.getSimpleName();

    Place selectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpssearch);

        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Location selectedLocation;
                // If place is in db already update location info in db
                if(db.locationInDB(place.getId())) {
                    selectedLocation = db.getLocation(place.getId());
                }
                // If place is new set basic new locations
                else{
                    selectedLocation = new Location(
                            place.getId(),
                            place.getName().toString() + "\n" + place.getAddress().toString(),
                            (float)place.getLatLng().latitude,
                            (float)place.getLatLng().latitude,
                            new Date().toString(),
                            new Date().toString(),
                            "",
                            100);
                }
                // Pass location to settings activity to set the volume settings
                Intent settingsIntent = new Intent(GPSSearchActivity.this, AddLocSettingsActivity.class);
                settingsIntent.putExtra("selectedLocation", selectedLocation);
                startActivity(settingsIntent);
                finish();
            }

            @Override
            public void onError(Status status) {
                alertToast("An error occurred with google location");
                Log.i(TAG, "An error occurred: " + status);
            }
        });
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

    protected void alertToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
