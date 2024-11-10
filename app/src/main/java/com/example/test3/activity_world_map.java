package com.example.test3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class WorldMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_map);

        findViewById(R.id.region1_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Region 1
                Intent intent = new Intent(WorldMapActivity.this, Region1Activity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.region2_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for Region 2
                Intent intent = new Intent(WorldMapActivity.this, Region2Activity.class);
                startActivity(intent);
            }
        });
    }
}
