package com.example.test3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Set up button click listeners
        Button beginButton = findViewById(R.id.begin_button);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Begin Game", Toast.LENGTH_SHORT).show();
            }
        });

        Button eventsButton = findViewById(R.id.events_button);
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Events Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        Button questsButton = findViewById(R.id.quests_button);
        questsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to QuestActivity
                Intent intent = new Intent(MainMenu.this, QuestActivity.class);
                startActivity(intent);
            }
        });


        // Bag Button: Open BagActivity
        Button bagButton = findViewById(R.id.bag_button);
        bagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start BagActivity
                Intent intent = new Intent(MainMenu.this, BagActivity.class);
                startActivity(intent);
            }
        });

        Button worldMapButton = findViewById(R.id.world_map_button);
        worldMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
                {
                    // Navigate to World Map
                    Intent intent = new Intent(MainMenu.this, activity_world_map.class);
                    startActivity(intent);
                }

        });

        Button campaignButton = findViewById(R.id.campaign_button);
        campaignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Campaign Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button heroesButton = findViewById(R.id.heroes_button);
        heroesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Heroes Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button guildButton = findViewById(R.id.guild_button);
        guildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Guild Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
