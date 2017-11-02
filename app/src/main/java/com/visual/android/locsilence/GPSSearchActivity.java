package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
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
        /*
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
        */

        final SQLDatabaseHandler db = new SQLDatabaseHandler(this);

        System.out.println("BOI: " + db.getAllLocations());

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                selectedPlace = place;
            }

            @Override
            public void onError(Status status) {
                alertToast("An error occurred with google location");
                Log.i(TAG, "An error occurred: " + status);
            }
        });

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
                        1);

                if (db.getLocation(selectedPlace.getId()) == null) {
                    db.addLocation(location);
                } else {
                    db.updateLocalGame(location);
                }

                Intent i = new Intent(GPSSearchActivity.this, MapsActivity.class);
                startActivity(i);

                // setLocationInDB(selectedPlace);
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

    protected void setLocationInDB(Place place){
        final SQLDatabaseHandler dbReference = new SQLDatabaseHandler(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Log.i(TAG, "Place: " + place.getName());
        Log.i(TAG, "Time: " + currentDateandTime);
        Log.i(TAG, "LatLng: " + place.getLatLng());

        if(dbReference.locationInDB(place.getId())) {
            Log.i(TAG, "updating location in DB, id: " + place.getId());
            Location location = dbReference.getLocation(place.getId());
            // NOTE: check later, DB Names might save as different (saved as only "Napa" once not "Napa, CA")
            location.setName(place.getName().toString());
            // TODO: add functionality to have different volumes (0,1, for testing)
            location.setVolume(1);
            location.setUpdatedAt(currentDateandTime);
            dbReference.updateLocalGame(location);
        }
        else{
            Log.i(TAG, "adding location to DB, id: " + place.getId());
            Location location = new Location(place.getId(), place.getName().toString(),
                    (float)place.getLatLng().latitude,(float)place.getLatLng().latitude,
                    currentDateandTime, currentDateandTime, 2);
            dbReference.addLocation(location);
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
