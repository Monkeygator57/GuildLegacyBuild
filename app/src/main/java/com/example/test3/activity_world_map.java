package com.example.test3;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_world_map extends AppCompatActivity { // Updated class name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_map);

        // Ensure buttons exist in the XML layout
        findViewById(R.id.region1_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Confirm Region 1 button was clicked
                Toast.makeText(activity_world_map.this, "Region 1 clicked", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.region2_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Confirm Region 2 button was clicked
                Toast.makeText(activity_world_map.this, "Region 2 clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
