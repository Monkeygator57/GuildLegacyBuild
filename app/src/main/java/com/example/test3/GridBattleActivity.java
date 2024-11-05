package com.example.test3;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class GridBattleActivity extends AppCompatActivity {

    private GridManager gridManager;
    private BattleManager battleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_battle); // Load the grid_battle.xml layout

        // Initialize GridManager
        GridLayout gridLayout = findViewById(R.id.characterGrid);
        gridManager = new GridManager(gridLayout, this);

        // Initialize BattleManager with GridManager
        battleManager = new BattleManager(gridManager);

        // Initialize the grid with characters
        gridManager.initializeGrid();

        // Set up button to move hero
        Button moveHeroButton = findViewById(R.id.moveHeroButton);
        moveHeroButton.setOnClickListener(view -> {
            Character character = (Character) gridManager.getCharacterAtPosition(new Pair<>(0, 0));
            if (character != null) {
                int moveSpeed = character.getMoveSpeed();  // Get move speed from character
                gridManager.moveCharacter(new Pair<>(0, 0), new Pair<>(5, 5), moveSpeed);
            }
        });

        // Set up button to start the first floor battle
        Button startFirstFloorButton = findViewById(R.id.startFirstFloorButton);
        startFirstFloorButton.setOnClickListener(view -> {
            // Create Floor 1 using the FloorFactory
            Floor floor1 = FloorFactory.createFloor1(this);
            // Start the new floor battle
            battleManager.startNewFloor(floor1);
        });
    }
}

