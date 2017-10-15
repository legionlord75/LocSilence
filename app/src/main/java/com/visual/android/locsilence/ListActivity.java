package com.visual.android.locsilence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button button_to_map = (Button) findViewById(R.id.button_to_map);

        button_to_map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                next_page(v);


            }
        });

    }

    public void next_page(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}



