package com.example.test3;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;
import android.animation.ObjectAnimator;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;

public class GridManager {

    private final GridLayout gridLayout;
    private final Context context;

    //hash maps to store characters view and object properties (stats of hero/enemy) by its grid position, represented by a pair of ints.
    private final HashMap<Pair<Integer, Integer>, View> characterViews;
    private final HashMap<Pair<Integer, Integer>, Object> characterObjects;


    public GridManager(GridLayout gridLayout, Context context){
        this.gridLayout = gridLayout;
        this.context = context;

        //initialize hash maps
        this.characterViews = new HashMap<>();
        this.characterObjects = new HashMap<>();
    }


    // Method to display the character grid
    public void displayCharacterGrid() {
        gridLayout.removeAllViews(); //clear existing views on grid

        // creates grid tiles, loops through each row and column to create cell for each grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                FrameLayout tileContainer = new FrameLayout(context);

                // Set size for each cell
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                        GridLayout.spec(row),
                        GridLayout.spec(col)
                );
                layoutParams.width = 105;
                layoutParams.height = 105;
                layoutParams.setMargins(6,6,6,6);
                tileContainer.setLayoutParams(layoutParams);
                tileContainer.setBackgroundColor(Color.DKGRAY);

                // Add the container to the grid layout
                gridLayout.addView(tileContainer, layoutParams);
            }
        }

        //Loop through each character position in characterObjects
        for (Map.Entry<Pair<Integer, Integer>, Object> entry : characterObjects.entrySet()) {
            Pair<Integer, Integer> position = entry.getKey();
            Object character = entry.getValue();

            int row = position.first;
            int col = position.second;

            addCharacterToGrid(row, col, character); // look at this
        }
    }

    //helper method to add character to the grid with relevant stats
    private void addCharacterToGrid(int row, int col, Object character) {
        FrameLayout container = new FrameLayout(context);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(row),
                GridLayout.spec(col)
        );
        layoutParams.width = 105;
        layoutParams.height = 105;
        layoutParams.setMargins(6,6,6,6);
        container.setLayoutParams(layoutParams);

        // determine color of circle (temp)
        gridLayout.addView(container);

        //Save both the view and the character object in the hashmaps
        Pair<Integer, Integer> position = new Pair<>(row, col);
        characterViews.put(position, container); // store the visual view
        characterObjects.put(position, character); // store the actual character object.
    }

    // method to move character from one cell to another
    public void moveCharacter(Pair<Integer, Integer> fromPosition, Pair<Integer, Integer> toPosition) {
        View characterView = characterViews.get(fromPosition);
        Object character = characterObjects.get(fromPosition);

        if (characterView != null && character != null) {
            int fromRow = fromPosition.first;
            int fromCol = fromPosition.second;
            int toRow = toPosition.first;
            int toCol = toPosition.second;

            //Retrieve layout params of one cell
            FrameLayout sampleCell = (FrameLayout) gridLayout.getChildAt(0);
            GridLayout.LayoutParams cellParams = (GridLayout.LayoutParams) sampleCell.getLayoutParams();

            // calculate the cell width and height, including ALL margins
            float cellWidth = cellParams.width + cellParams.leftMargin + cellParams.rightMargin;
            float cellHeight = cellParams.height + cellParams.topMargin + cellParams.bottomMargin;

            //calculate translation based on true grid dimensions
            float translationX = (toCol - fromCol) * cellWidth;
            float translationY = (toRow - fromRow) * cellHeight;

            //Animate the character to the new position
            ObjectAnimator objectX = ObjectAnimator.ofFloat(characterView, "translationX", translationX);
            ObjectAnimator objectY = ObjectAnimator.ofFloat(characterView, "translationY", translationY);
            objectX.setDuration(200);
            objectY.setDuration(200);

            // Start animation
            objectX.start();
            objectY.start();

            //update hashmaps to reflect new changes
            characterViews.remove(fromPosition);
            characterViews.put(toPosition, characterView);

            characterObjects.remove(fromPosition);
            characterObjects.put(toPosition, character);
        }
    }
}
