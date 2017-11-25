package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RamiK on 10/23/2017.
 */

public class LocationsAdapter extends ArrayAdapter<Location> {

    List<Location> locations;
    private Context context;
    SQLDatabaseHandler db;

    public LocationsAdapter(Context context, List<Location> locations, SQLDatabaseHandler db) {
        super(context, 0, locations);
        this.locations = locations;
        this.context = context;
        this.db = db;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_location, parent, false);
        }

        // Init info
        final TextView locationName = (TextView) convertView.findViewById(R.id.name);
        final TextView locationAddress = (TextView) convertView.findViewById(R.id.address);
        final Button button_edit = (Button) convertView.findViewById(R.id.button_locList_edit);
        final Button button_del = (Button) convertView.findViewById(R.id.button_locList_delete);

        // Set basic ui
        locationName.setText(Utility.cropText(locations.get(position).getName(), 21, " ..."));
        locationAddress.setText(Utility.cropText(locations.get(position).getAddress(), 35, " ..."));

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(context, AddLocSettingsActivity.class);
                settingsIntent.putExtra("editing", true);
                settingsIntent.putExtra("selectedLocation", locations.get(position));
                context.startActivity(settingsIntent);
            }
        });

        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.getLocation(locations.get(position).getId()) != null) {
                    db.deleteLocalGame(locations.get(position).getId());
                }
                locations.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public Location getItem(int position) {
        return locations.get(position);
    }
}
