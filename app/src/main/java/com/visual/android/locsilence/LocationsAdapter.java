package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
        final TextView locationName = (TextView)convertView.findViewById(R.id.name);
        final TextView locationAddress = (TextView)convertView.findViewById(R.id.address);
        final Button button_edit = (Button)convertView.findViewById(R.id.button_locList_edit);
        final Button button_del = (Button)convertView.findViewById(R.id.button_locList_delete);

        if (locations.get(position) != null) {
            button_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingsIntent = new Intent(context, AddLocSettingsActivity.class);
                    settingsIntent.putExtra("editing", true);
                    settingsIntent.putExtra("selectedLocation", locations.get(position));
                    context.startActivity(settingsIntent);
                }
            });
        }
        if (locations.get(position) != null) {
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
        }


        locationName.setText(cropText(locations.get(position).getName(), 21, " ..."));
        locationAddress.setText(cropText(locations.get(position).getAddress(), 25, " ..."));
        return convertView;
    }


    public String cropText(String text, int max_length, String append){
        if(text.length() > max_length)
            return text.substring(0,max_length-append.length())+ append;
        else
            return text;
    }
    public Location getItem(int position){
        return locations.get(position);
    }

}
