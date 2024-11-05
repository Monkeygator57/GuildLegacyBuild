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

import java.util.*;

import android.os.Handler;

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

    // Method to remove a character from the grid at a specified position
    public void removeCharacter(Pair<Integer, Integer> position) {
        // Remove the character object from the characterObjects map
        characterObjects.remove(position);

        // Remove the view from the characterViews map and the grid layout if it exists
        View characterView = characterViews.remove(position);
        if (characterView != null) {
            gridLayout.removeView(characterView);
        }
    }

    // Add method to get character view at a specific position
    public SpriteSheetImageView getCharacterViewAtPosition(Pair<Integer, Integer> position) {
        View characterView = characterViews.get(position);
        if (characterView instanceof SpriteSheetImageView) {
            return (SpriteSheetImageView) characterView;
        }
        return null;
    }

    // Add method to get all character objects
    public Map<Pair<Integer, Integer>, Object> getCharacterObjects() {
        return characterObjects;
    }

    // Method to get all currently occupied positions
    public Set<Pair<Integer, Integer>> getOccupiedPositions() {
        return new HashSet<>(characterObjects.keySet());
    }

    public Character getCharacterAtPosition(Pair<Integer, Integer> position){
        return (Character) characterObjects.get(position);
    }

    //method to initialize the grid with example data. (place hero/enemy)
    public void initializeGrid() {
        displayCharacterGrid(); // look at this
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
    public void addCharacterToGrid(int row, int col, Object character) {
        FrameLayout container = new FrameLayout(context);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(row),
                GridLayout.spec(col)
        );
        layoutParams.width = 105;
        layoutParams.height = 105;
        layoutParams.setMargins(6,6,6,6);
        container.setLayoutParams(layoutParams);

        // Create and configure the SpriteSheetImageView for the character
        SpriteSheetImageView spriteView = new SpriteSheetImageView(context, null);
        spriteView.setCharacter((Character) character); // Set the character

        // Set layout parameter for the sprite
        FrameLayout.LayoutParams spriteParams = new FrameLayout.LayoutParams(80, 80);
        spriteParams.gravity = Gravity.CENTER;
        spriteView.setLayoutParams(spriteParams);

        //Create a label for the character (temp)
        TextView label = new TextView(context);
        label.setText(((Character) character).getName());
        label.setTextColor(Color.GRAY);
        label.setTextSize(12);
        label.setGravity(Gravity.CENTER);

        // Add the sprite and label to the container
        container.addView(spriteView);
        //container.addView(label);

        gridLayout.addView(container);

        //Save both the view and the character object in the hashmaps
        Pair<Integer, Integer> position = new Pair<>(row, col);
        characterViews.put(position, container); // store the visual view
        characterObjects.put(position, character); // store the actual character object.
    }

    // method to move character from one cell to another
    public void moveCharacter(Pair<Integer, Integer> fromPosition, Pair<Integer, Integer> toPosition, int moveSpeed) {
        View characterView = characterViews.get(fromPosition);
        Object character = characterObjects.get(fromPosition);

        if (characterView != null && character != null) {
            // Step 1: Generate the path using A* Pathfinding and limit it to moveSpeed
            AStarPathfinder pathfinder = new AStarPathfinder();
            Set<Pair<Integer, Integer>> occupiedCells = new HashSet<>(characterObjects.keySet());
            occupiedCells.remove(fromPosition); // Exclude the starting position of the character being moved

            List<Pair<Integer, Integer>> fullPath = pathfinder.findPath(
                    fromPosition.first, fromPosition.second,
                    toPosition.first, toPosition.second,
                    occupiedCells
            );

            if (fullPath == null || fullPath.isEmpty()) {
                // No valid path found, exit
                return;
            }

            // Limit the path to the character's movement speed
            List<Pair<Integer, Integer>> path = fullPath.subList(0, Math.min(moveSpeed + 1, fullPath.size()));

            // Step 2: Animate along the path step-by-step
            if (characterView instanceof SpriteSheetImageView) {
                animatePath((SpriteSheetImageView) characterView, fromPosition, path, 200);
            }
        }
    }

    public void animatePath(SpriteSheetImageView characterView, Pair<Integer, Integer> fromPosition, List<Pair<Integer, Integer>> path, int animationDurationPerStep) {
        if (characterView == null || path.isEmpty()) {
            return; // If no character is found or the path is empty, do nothing
        }

        // Step 1: Retrieve layout params of one cell to calculate true cell dimensions
        FrameLayout sampleCell = (FrameLayout) gridLayout.getChildAt(0);
        GridLayout.LayoutParams cellParams = (GridLayout.LayoutParams) sampleCell.getLayoutParams();
        float cellWidth = cellParams.width + cellParams.leftMargin + cellParams.rightMargin;
        float cellHeight = cellParams.height + cellParams.topMargin + cellParams.bottomMargin;

        // Step 2: Animate along the path step-by-step
        Handler handler = new Handler();

        for (int i = 0; i < path.size() - 1; i++) {
            final Pair<Integer, Integer> currentStep = path.get(i);
            final Pair<Integer, Integer> nextStep = path.get(i + 1);

            handler.postDelayed(() -> {
                // Calculate the translation for the next step
                float translationX = (nextStep.second - currentStep.second) * cellWidth;
                float translationY = (nextStep.first - currentStep.first) * cellHeight;

                // Animate to the next position
                ObjectAnimator objectX = ObjectAnimator.ofFloat(characterView, "translationX", characterView.getTranslationX() + translationX);
                ObjectAnimator objectY = ObjectAnimator.ofFloat(characterView, "translationY", characterView.getTranslationY() + translationY);
                objectX.setDuration(animationDurationPerStep);
                objectY.setDuration(animationDurationPerStep);
                objectX.start();
                objectY.start();
            }, i * animationDurationPerStep);  // Delay each step by the step index * animationDuration
        }

        // Step 3: Update HashMaps after reaching the final position in the path
        Pair<Integer, Integer> lastPosition = path.get(path.size() - 1);
        handler.postDelayed(() -> {
            // Update hashmaps to reflect the new position
            characterViews.remove(fromPosition);
            characterViews.put(lastPosition, characterView);

            characterObjects.remove(fromPosition);
            characterObjects.put(lastPosition, characterObjects.get(fromPosition));
        }, (path.size() - 1) * animationDurationPerStep);  // Delay this update to happen after the final animation step
    }

}