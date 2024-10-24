package com.example.test3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;

public class GridManager {

    private final GridLayout gridLayout;
    private final Context context;

    //Arrays to represent grids data
    private final Hero[][] heroGrid;
    private final Enemy[][] enemyGrid;

    public GridManager(GridLayout gridLayout, Context context){
        this.gridLayout = gridLayout;
        this.context = context;

        //Initialize the 9x9 grid for heroes and enemies
        heroGrid = new Hero[9][9];
        enemyGrid = new Enemy[9][9];

    }

    //method to initialize the grid with example data.
    public void initializeGrid() {
        // example setup of initial heroes and enemies,
        heroGrid[0][0] = new Hero("Warrior", 100,30,10,0, "Warrior");
        enemyGrid[6][0] = new Enemy("Goblin",50, 20,5, 0, false);

        heroGrid[1][0] = new Hero("Warrior", 100,30,10,0, "Warrior");
        enemyGrid[6][1] = new Enemy("Goblin",50, 20,5, 0, false);

        heroGrid[1][1] = new Hero("Warrior", 100,30,10,0, "Warrior");
        enemyGrid[7][0] = new Enemy("Goblin",50, 20,5, 0, false);

        heroGrid[2][0] = new Hero("Warrior", 100,30,10,0, "Warrior");
        enemyGrid[7][1] = new Enemy("Goblin",50, 20,5, 0, false);
    }

    // Method to display grid
    public void displayCharacterGrid() {
        gridLayout.removeAllViews();

        //loop through each row and column
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                FrameLayout container = new FrameLayout(context);

                // Set size for each cell
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.width = 50;
                layoutParams.height = 50;
                layoutParams.setMargins(8,8,8,8);
                container.setLayoutParams(layoutParams);

                // Add a hero or Enemy to the grid cell
                if (heroGrid[row][col] != null) {
                    addCharacterToGrid(container, heroGrid[row][col], Color.BLUE);
                } else if (enemyGrid[row][col] != null) {
                    addCharacterToGrid(container, enemyGrid[row][col], Color.RED);
                }

                // Add the container to the grid layout
                gridLayout.addView(container, layoutParams);
            }
        }
    }

    //helper method to add character to the grid with relevant stats
    private void addCharacterToGrid(FrameLayout container, Character character, int color) {
        // create a circle to visually represent the character
        View circle = new View(context);
        circle.setBackground(createCircleDrawable(color));

        //Set layout parameters for the circle
        FrameLayout.LayoutParams circleParams = new FrameLayout.LayoutParams(100,100);
        circleParams.gravity = Gravity.CENTER;
        circle.setLayoutParams(circleParams);


        //Create a label for the character (temp)
        TextView label = new TextView(context);
        label.setText(character.getName());
        label.setTextColor(Color.GRAY);
        label.setTextSize(12);
        label.setGravity(Gravity.CENTER);


        //add the circle and label to the container
        container.addView(circle);
        container.addView(label);

    }

    // help method to create a circular drawable
    private ShapeDrawable createCircleDrawable(int color) {
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.setIntrinsicHeight(100);
        circle.setIntrinsicWidth(100);
        circle.getPaint().setColor(color);
        return circle;
    }


}
