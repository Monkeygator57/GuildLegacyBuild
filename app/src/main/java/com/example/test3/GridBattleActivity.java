package com.example.test3;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class GridBattleActivity extends AppCompatActivity {

    private GridManager gridManager;

    @Override
    protected void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_battle); //Load the grid_battle.xml layout

        GridLayout gridLayout = findViewById(R.id.characterGrid);
        gridManager = new GridManager(gridLayout, this);

        gridManager.initializeGrid(); // initialize grid with characters.

        Button moveHeroButton = findViewById(R.id.moveHeroButton);
        moveHeroButton.setOnClickListener(view -> {
            //move a hero from 0,0 to 1,1
            gridManager.moveCharacter(new Pair<>(0,0), new Pair<>(5,5));
        });
    }
}
