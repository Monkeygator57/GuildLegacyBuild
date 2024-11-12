package com.example.test3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;


public class GridBattleActivity extends AppCompatActivity {

    private CharacterController characterController;
    private BattleManager battleManager;
    private FloorFactory floorFactory;
    private GridBuilder gridBuilder;
    private GridLayout gridLayout;
    public boolean isBattleStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_battle); // Load the grid_battle.xml layout

        // Initialize GridManager
        GridLayout gridLayout = findViewById(R.id.characterGrid);
        characterController = new CharacterController(gridLayout, this);
        Log.d("GridBattleActivity", "GridManager initialized.");

        // Initialize FloorFactory
        floorFactory = new FloorFactory();
        Log.d("GridBattleActivity", "FloorFactory initialized.");

        // Initialize BattleManager with GridManager and FloorFactory
        battleManager = new BattleManager(new CharacterController(gridLayout, this), floorFactory);
        Log.d("GridBattleActivity", "BattleManager initialized.");


        // Build grid
        gridBuilder = new GridBuilder(9,9, 3, gridLayout);
        Log.d("GridBattleActivity", "GridBuilder initialized.");


        // Initialize the grid
        characterController.displayCharacterGrid();
        Log.d("GridBattleActivity", "Grid initialized.");

        // Set up button to start the first floor battle
        Button startFirstFloorButton = findViewById(R.id.startFirstFloorButton);
        startFirstFloorButton.setOnClickListener(view -> {
            Log.d("GridBattleActivity", "Start First Floor button clicked.");

            // Create Floor 1 using the FloorFactory
            Floor floor1 = floorFactory.createFloor1(this);

            // Start the new floor battle
            battleManager.startNewFloor(floor1);
            isBattleStarted = true;
        });

        // **Added Move Hero Button Back**
        // Set up button to move hero
        Button moveHeroButton = findViewById(R.id.moveHeroButton);
        moveHeroButton.setOnClickListener(view -> {
            Log.d("GridBattleActivity", "Move Hero button clicked.");

            int oldRow = 0;
            int oldCol = 0;

            // Retrieve the character at the old position
            Character character = characterController.getCharacterAtPosition(oldRow, oldCol);
            if (character != null) {
                int newRow = 5;
                int newCol = 5;

                // Move the character using GridManager
                characterController.moveCharacter(oldRow, oldCol, newRow, newCol, character);

                // Update the character's position if needed
                // If character maintains its own position, update it here
                // character.setPosition(new Pair<>(newRow, newCol));

                Log.d("GridBattleActivity", character.getName() + " moved from (" + oldRow + ", " + oldCol + ") to (" + newRow + ", " + newCol + ")");
            } else {
                Log.d("GridBattleActivity", "No character found at (" + oldRow + ", " + oldCol + ")");
            }
        });
    }
}
