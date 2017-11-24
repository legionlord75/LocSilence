package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RamiK on 10/23/2017.
 */

public class LocationsAdapter extends ArrayAdapter<Location> {

    List<Location> locations;
    private Context context;

    public LocationsAdapter(Context context, List<Location> locations) {
        super(context, 0, locations);
        this.locations = locations;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_location, parent, false);
        }

        final TextView locationName = (TextView)convertView.findViewById(R.id.name);
        if (locations.get(position) != null) {
            locationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingsIntent = new Intent(context, AddLocSettingsActivity.class);
                    settingsIntent.putExtra("editing", true);
                    settingsIntent.putExtra("selectedLocation", locations.get(position));
                    context.startActivity(settingsIntent);
                }
            });

            locationName.setText(locations.get(position).getName());
        }

        return convertView;
    }

    public Location getItem(int position){
        return locations.get(position);
    }

}
