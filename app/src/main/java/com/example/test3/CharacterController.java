package com.example.test3;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.gridlayout.widget.GridLayout;


import android.util.Log;
import android.util.Pair;

import java.util.*;

import android.os.Handler;

public class CharacterController {

    private final GridLayout gridLayout;
    private final Context context;

    // Hash maps to store character views and objects by their grid position.
    private final HashMap<Pair<Integer, Integer>, View> characterViews;
    private final HashMap<Pair<Integer, Integer>, Character> characterObjects;

    private final int numRows = 10;
    private final int numCols = 9;

    // Add getter methods
    public int getNumRows() {
        return numRows;
    }
    public int getNumCols() {
        return numCols;
    }

    public CharacterController(GridLayout gridLayout, Context context) {
        this.gridLayout = gridLayout;
        this.context = context;

        // Initialize hash maps
        this.characterViews = new HashMap<>();
        this.characterObjects = new HashMap<>();
    }

    // Method to remove a character from the grid at a specified position
    public void removeCharacter(int row, int col) {
        Pair<Integer, Integer> position = new Pair<>(row, col);

        // Remove the character object from the characterObjects map
        characterObjects.remove(position);

        // Remove the view from the characterViews map and the grid layout if it exists
        View characterView = characterViews.remove(position);
        if (characterView != null) {
            gridLayout.removeView(characterView);
        }
    }

    // Method to get character view at a specific position
    public SpriteSheetImageView getCharacterViewAtPosition(int row, int col) {
        Pair<Integer, Integer> position = new Pair<>(row, col);
        View characterView = characterViews.get(position);
        /*if (characterView instanceof SpriteSheetImageView) {
            return (SpriteSheetImageView) characterView;
        }*/
        if (characterView instanceof FrameLayout) {
            FrameLayout container = (FrameLayout) characterView;
            if (container.getChildCount() > 0) {
                View childView = container.getChildAt(0);
                if (childView instanceof SpriteSheetImageView) {
                    return (SpriteSheetImageView) childView;
                }
            }
        }
        return null;
    }

    // Method to get all character objects
    public Map<Pair<Integer, Integer>, Character> getCharacterObjects() {
        return characterObjects;
    }
    public Map<Pair<Integer, Integer>, View> getCharacterViews() {return characterViews;}

    // Method to get all currently occupied positions
    public Set<Pair<Integer, Integer>> getOccupiedPositions() {
        return new HashSet<>(characterObjects.keySet());
    }

    public Character getCharacterAtPosition(int row, int col) {
        Pair<Integer, Integer> position = new Pair<>(row, col);
        return characterObjects.get(position);
    }

    // Method to display the characters on the grid
    public void displayCharacterGrid() {
        // Loop through each character position in characterObjects
        for (Map.Entry<Pair<Integer, Integer>, Character> entry : characterObjects.entrySet()) {
            Pair<Integer, Integer> position = entry.getKey();
            Character character = entry.getValue();

            int row = position.first;
            int col = position.second;
            // add charcter objects to grid
            addCharacterToGrid(row, col, character);
        }
    }

    // Helper method to add character to the grid
    public void addCharacterToGrid(int row, int col, Character character) {
        FrameLayout container = new FrameLayout(context);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(row),
                GridLayout.spec(col)
        );
        layoutParams.width = 105;
        layoutParams.height = 105;
        layoutParams.setMargins(6, 6, 6, 6);
        container.setLayoutParams(layoutParams);

        // Create and configure the SpriteSheetImageView for the character
        SpriteSheetImageView spriteView = new SpriteSheetImageView(context, null);
        spriteView.setCharacter(character); // Set the character

        // Set layout parameters for the sprite
        FrameLayout.LayoutParams spriteParams = new FrameLayout.LayoutParams(80, 80);
        spriteParams.gravity = Gravity.CENTER;
        spriteView.setLayoutParams(spriteParams);

        // Add the sprite to the container
        container.addView(spriteView);

        gridLayout.addView(container);

        // Save both the view and the character object in the hashmaps
        Pair<Integer, Integer> position = new Pair<>(row, col);
        characterViews.put(position, container); // Store the visual view
        characterObjects.put(position, character); // Store the character object

        //enable drag and drop for this character view
        enableDragAndDrop(container);
    }

    // Method to move character from one cell to another
    public void moveCharacter(int oldRow, int oldCol, int newRow, int newCol, Character character) {
        Pair<Integer, Integer> fromPosition = new Pair<>(oldRow, oldCol);
        Pair<Integer, Integer> toPosition = new Pair<>(newRow, newCol);

        View characterView = characterViews.get(fromPosition);

        if (characterView != null && character != null) {
            // Step 1: Generate the path using A* Pathfinding and limit to moveSpeed
            AStarPathfinder pathfinder = new AStarPathfinder();
            Set<Pair<Integer, Integer>> occupiedCells = new HashSet<>(characterObjects.keySet());
            occupiedCells.remove(fromPosition); // Exclude the starting position of the character being moved

            // Get the grid size
            int numRows = getNumRows();
            int numCols = getNumCols();

            List<Pair<Integer, Integer>> fullPath = pathfinder.findPath(
                    oldRow, oldCol,
                    newRow, newCol,
                    occupiedCells, numRows, numCols
            );

            if (fullPath == null || fullPath.size() < 2) {
                // No path found or already at destination
                return;
            }

            // Limit path to the character's movement speed
            int moveSpeed = character.getMoveSpeed();
            int maxSteps = Math.min(moveSpeed, fullPath.size() - 1);
            List<Pair<Integer, Integer>> path = fullPath.subList(0, maxSteps + 1); // Include starting position

            // Step 2: Retrieve layout params of one cell to calculate true cell dimensions
            FrameLayout sampleCell = (FrameLayout) gridLayout.getChildAt(0);
            GridLayout.LayoutParams cellParams = (GridLayout.LayoutParams) sampleCell.getLayoutParams();
            float cellWidth = cellParams.width + cellParams.leftMargin + cellParams.rightMargin;
            float cellHeight = cellParams.height + cellParams.topMargin + cellParams.bottomMargin;

            // Step 3: Animate along the path step-by-step
            Handler handler = new Handler();
            int animationDuration = 200; // Duration for each step in milliseconds

            for (int i = 0; i < path.size() - 1; i++) {
                final Pair<Integer, Integer> currentStep = path.get(i);
                final Pair<Integer, Integer> nextStep = path.get(i + 1);

                int finalI = i;
                handler.postDelayed(() -> {
                    // Calculate the translation for the next step
                    float translationX = (nextStep.second - currentStep.second) * cellWidth;
                    float translationY = (nextStep.first - currentStep.first) * cellHeight;

                    // Animate to the next position
                    characterView.animate()
                            .translationXBy(translationX)
                            .translationYBy(translationY)
                            .setDuration(animationDuration)
                            .start();
                }, i * animationDuration);  // Delay each step by the step index * animationDuration
            }

            // Step 4: Update HashMaps after reaching the final position in the path
            handler.postDelayed(() -> {
                // Update hashmaps to reflect the new position
                characterViews.remove(fromPosition);
                characterViews.put(toPosition, characterView);

                characterObjects.remove(fromPosition);
                characterObjects.put(toPosition, character);

                // Reset the translation so the view is back to its grid position
                characterView.setTranslationX(0);
                characterView.setTranslationY(0);

                // Update the character's sprite state back to idle
                character.setSpriteState(Character.SpriteState.IDLE);
                if (characterView instanceof FrameLayout) {
                    View spriteView = ((FrameLayout) characterView).getChildAt(0);
                    if (spriteView instanceof SpriteSheetImageView) {
                        ((SpriteSheetImageView) spriteView).setCharacter(character);
                    }
                }

                Log.d("CharacterController", character.getName() + " has moved to (" + newRow + ", " + newCol + ")");
            }, (path.size() - 1) * animationDuration);  // Delay this update to happen after the final animation step
        }
    }

    // Method to check if a position is occupied
    public boolean isPositionOccupied(int row, int col) {
        Pair<Integer, Integer> position = new Pair<>(row, col);
        return characterObjects.containsKey(position);
    }


    //Drag and drop functionality for heroes
    private void enableDragAndDrop(View characterView) {
        characterView.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData dragData = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(dragData, shadowBuilder, view, 0);
                return true;
            }

            return false;
        });
    }

    // Method to check if a position is within the grid bounds
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }
}
