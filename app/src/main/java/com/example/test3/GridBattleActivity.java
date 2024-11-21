package com.example.test3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


public class GridBattleActivity extends AppCompatActivity {

    private CharacterController characterController;
    private BattleManager battleManager;
    private FloorFactory floorFactory;
    private GridBuilder gridBuilder;
    private GridBattleActivity gridBattleActivity;
    private Floor currentFloor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_battle); // Load the grid_battle.xml layout

        // Initialize characterController
        GridLayout gridLayout = findViewById(R.id.characterGrid);
        characterController = new CharacterController(gridLayout, this);
        Log.d("GridBattleActivity", "CharacterController initialized.");

        // Initialize FloorFactory
        floorFactory = new FloorFactory();
        Log.d("GridBattleActivity", "FloorFactory initialized.");

        // Initialize BattleManager with CharacterController and FloorFactory
        battleManager = new BattleManager(this, characterController, floorFactory);
        Log.d("GridBattleActivity", "BattleManager initialized.");

        // Build visual grid
        gridBuilder = new GridBuilder(9,9, gridLayout, characterController);
        Log.d("GridBattleActivity", "GridBuilder initialized.");

        //Build Floor0 to create heroes and enemies
        Floor floor0 = floorFactory.createFloor1(this);
        battleManager.startNewFloor(floor0);

        // update grid with character positions/views/objects
        gridBuilder.updateGridWithCharacters(characterController);

        // Set up button to start the first floor battle
        Button startFirstFloorButton = findViewById(R.id.startBattle);
        startFirstFloorButton.setOnClickListener(view -> {
            Log.d("GridBattleActivity", "Start First Floor button clicked.");


            Floor floor1 = floorFactory.createFloor1(this);
            currentFloor = floor1;
            battleManager.startBattle();
        });

        // **Added Move Hero Button Back**
        // Set up button to move hero
        Button moveHeroButton = findViewById(R.id.moveHeroButton);
        moveHeroButton.setOnClickListener(view -> {
            Log.d("GridBattleActivity", "Move Hero button clicked.");

            int oldRow = 9;
            int oldCol = 2;

            // Retrieve the character at the old position
            Character character = characterController.getCharacterAtPosition(oldRow, oldCol);
            if (character != null) {
                int newRow = 5;
                int newCol = 5;

                // Move the character using GridManager
                characterController.moveCharacterForDrag(oldRow, oldCol, newRow, newCol, character);

                Log.d("GridBattleActivity", character.getName() + " moved from (" + oldRow + ", " + oldCol + ") to (" + newRow + ", " + newCol + ")");
            } else {
                Log.d("GridBattleActivity", "No character found at (" + oldRow + ", " + oldCol + ")");
            }
        });
    }

    public void advanceToNextFloor() {
        // Increment the floor number
        Floor.nextFloorNumber();

        List<Hero> savedHeroes = new ArrayList<>();
        battleManager.saveHeroStates(savedHeroes);

        // Update the current floor with the next floor
        currentFloor = floorFactory.createFloor(this, Floor.floorNumber, savedHeroes);

        // Call advanceToNextFloor() on BattleManager
        battleManager.startNewFloor(currentFloor);

        gridBuilder.updateGridWithCharacters(characterController);
    }

}

