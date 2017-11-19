package com.visual.android.locsilence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    }


}



