package com.example.test3;

import android.os.Bundle;
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

        gridManager.initializeGrid();
        gridManager.displayCharacterGrid();
    }
}
