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

    public LocationsAdapter(Context context, List<Location> locations) {
        super(context, 0, locations);
        this.locations = locations;
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
                    System.out.println("DOES SOMETHING LATER");
                }
            });

            locationName.setText(locations.get(position).getName());
        }

        return convertView;
    }

}
