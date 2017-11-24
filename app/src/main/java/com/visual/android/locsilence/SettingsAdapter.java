package com.visual.android.locsilence;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thomas Brochard on 10/27/2017.
 */

public class SettingsAdapter extends ArrayAdapter<String>{

    String[] volumeTypes;
    int[] volumeLevels;
    int maxVolume;
    CheckBox silenceCheckBox;
    List<CheckBox> box;

    public SettingsAdapter(Context context, String[] volumeTypes, int[] defaultVolumes, int maxVolume) {
        super(context, R.layout.settings_row, volumeTypes);
        this.volumeTypes = volumeTypes;
        this.maxVolume = maxVolume;
        this.volumeLevels = defaultVolumes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.settings_row, parent, false);

        // Set volume titles
        final TextView volumeTitle = (TextView) customView.findViewById(R.id.title_volumeType);
        volumeTitle.setText(getItem(position));

        // Set seekbar and listener
        final SeekBar volumeSeekbar = (SeekBar) customView.findViewById(R.id.seekBar_volume);
        final CheckBox defaultCheckBox = (CheckBox) customView.findViewById(R.id.check_default);
        volumeSeekbar.setMax(this.maxVolume);
        volumeSeekbar.setProgress(volumeLevels[position]);
        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeLevels[position] = progress;
                defaultCheckBox.setChecked(false);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set checkbox and listener
        defaultCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(defaultCheckBox.isChecked()) {
                    volumeLevels[position] = -1;
                    volumeSeekbar.setEnabled(false);
                    // In future grey out seekbar to demonstrate that it is not being used
                }
                else {
                    volumeSeekbar.setEnabled(true);
                    volumeLevels[position] = volumeSeekbar.getProgress();
                }
            }
        });
        return customView;
    }

    // returns an array representation of the current volume settings
    public int[] getVolumeLevels(){
        return volumeLevels;
    }


    @Override
    public String getItem(int position) {
        return volumeTypes[position];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
