package com.visual.android.locsilence;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thomas Brochard on 10/27/2017.
 */

public class SettingsAdapter extends ArrayAdapter<String> {

    String[] volumeTypes;
    List<Integer> volumeLevels;
    int maxVolume;
    CheckBox silenceCheckBox;
    List<CheckBox> box;

    public SettingsAdapter(Context context, String[] volumeTypes, List<Integer> defaultVolumes, int maxVolume) {
        super(context, R.layout.settings_row, volumeTypes);
        this.volumeTypes = volumeTypes;
        this.maxVolume = maxVolume;
        this.volumeLevels = defaultVolumes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.settings_row, parent, false);

        // Init info
        final TextView volumeTitle = (TextView) customView.findViewById(R.id.title_volumeType);
        volumeTitle.setText(getItem(position));
        final SeekBar volumeSeekbar = (SeekBar) customView.findViewById(R.id.seekBar_volume);
        final CheckBox defaultCheckBox = (CheckBox) customView.findViewById(R.id.check_default);

        // Set seekbar to default value
        volumeSeekbar.setMax(this.maxVolume);
        if (volumeLevels.get(position) >= 0) {
            volumeSeekbar.setProgress(volumeLevels.get(position));
        } else {
            defaultCheckBox.setChecked(true);
            volumeSeekbar.setEnabled(false);
        }

        // Init Listeners
        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeLevels.set(position, progress);
                defaultCheckBox.setChecked(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Set checkbox and listener
        defaultCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultCheckBox.isChecked()) {
                    volumeLevels.set(position, -1);
                    volumeSeekbar.setEnabled(false);
                } else {
                    volumeSeekbar.setEnabled(true);
                    volumeLevels.set(position, volumeSeekbar.getProgress());
                }
            }
        });
        return customView;
    }


    // returns an array representation of the current volume settings
    public List<Integer> getVolumeLevels() {
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
