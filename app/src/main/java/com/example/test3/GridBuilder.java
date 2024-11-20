package com.example.test3;

import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.gridlayout.widget.GridLayout;



import java.util.Map;


public class GridBuilder {

    private final GridLayout characterGrid;
    private final int numRows;
    private final int numCols;
    private final CharacterController characterController;

    private static final int CELL_WIDTH = 105;
    private static final int CELL_HEIGHT = 105;
    private static final int CELL_MARGIN = 6;

    //grid builder constructor
    public GridBuilder(int numRows, int numCols, GridLayout gridLayout, CharacterController characterController) {
        this.characterGrid = gridLayout;
        this.numRows = numRows;
        this.numCols = numCols;
        this.characterController = characterController;
        createGrid();
    }

    // layout parameters for a single, used to avoid redundant code
    private GridLayout.LayoutParams createCellLayoutParams(int row, int col) {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec(row),
                GridLayout.spec(col)
        );
        layoutParams.width = CELL_WIDTH;
        layoutParams.height = CELL_HEIGHT;
        layoutParams.setMargins(CELL_MARGIN, CELL_MARGIN, CELL_MARGIN, CELL_MARGIN);
        return layoutParams;
    }

    // main grid creation method
    private void createGrid() {
        characterGrid.removeAllViews();

        characterGrid.setRowCount(numRows);
        characterGrid.setColumnCount(numCols);
        characterGrid.setClipChildren(false);
        characterGrid.setClipToPadding(false);

        // Working grid maker, creates standard 9 x 9 grid.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                FrameLayout tileContainer = new FrameLayout(characterGrid.getContext());

                // Set size for each cell
                GridLayout.LayoutParams layoutParams = createCellLayoutParams(row, col);
                tileContainer.setLayoutParams(layoutParams);
                tileContainer.setBackgroundColor(Color.GRAY);

                tileContainer.setTag(new Pair<>(row, col));

                // Add the container to the grid layout
                characterGrid.addView(tileContainer, layoutParams);
            }
        }


        characterController.displayCharacterGrid();

        // create onDragListener for each cell
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols - 6; col++) {
                    int cellIndex = row * numCols + col;
                    View gridCell = characterGrid.getChildAt(cellIndex);
                    gridCell.setOnDragListener((v, event) -> {
                        switch (event.getAction()) {
                            case DragEvent.ACTION_DRAG_STARTED:
                                return true;
                            case DragEvent.ACTION_DRAG_ENTERED:
                                v.setBackgroundColor(Color.LTGRAY);
                                return true;
                            case DragEvent.ACTION_DRAG_EXITED:
                                v.setBackgroundColor(Color.GRAY);
                                return true;
                            case DragEvent.ACTION_DROP:
                                View draggedView = (View) event.getLocalState();
                                Pair<Integer, Integer> oldPosition = characterController.getCharacterPositionFromView(draggedView);

                                Pair<Integer, Integer> newPosition = (Pair<Integer, Integer>) v.getTag();
                                int newRow = newPosition.first;
                                int newCol = newPosition.second;

                                Character character = characterController.getCharacterAtPosition(oldPosition);
                                if (character != null) {
                                    CharacterController.moveCharacterForDrag(
                                            oldPosition.first, oldPosition.second, newRow, newCol, character
                                    );
                                }

                                GridLayout.LayoutParams updatedParams = new GridLayout.LayoutParams(
                                        GridLayout.spec(newRow),
                                        GridLayout.spec(newCol)
                                );
                                draggedView.setLayoutParams(updatedParams);

                                v.setBackgroundColor(Color.GRAY); // reset color


                                return true;
                            case DragEvent.ACTION_DRAG_ENDED:
                                return true;
                            default:
                                return false;
                        }
                    });
                }
            }
        }


    public void updateGridWithCharacters(CharacterController characterController) {
        // map of character views, since we are basically "switching" to the allCharacters map after battle starts
        Map<Pair<Integer, Integer>, View> characterViewMap = characterController.getCharacterViews();

        for (Map.Entry<Pair<Integer, Integer>, View> entry : characterViewMap.entrySet()) {
            Pair<Integer, Integer> position = entry.getKey();
            View characterView = entry.getValue();

            if (characterView == null) {
                Log.e("GridBuilder", "Character view is null for position: " + position);
                continue; // Skip adding this view to avoid null references
            }

            // Update layout parameters for the existing view to ensure it stays correctly positioned
            GridLayout.LayoutParams layoutParams = createCellLayoutParams(position.first, position.second);
            characterView.setLayoutParams(layoutParams);


            // Ensure the character's state is properly updated in the SpriteSheetImageView
            if (characterView instanceof FrameLayout) {
                View childView = ((FrameLayout) characterView).getChildAt(0);
                if (childView instanceof SpriteSheetImageView) {
                    Character character = characterController.getCharacterAtPosition(position.first, position.second);
                    if (character != null) {
                        ((SpriteSheetImageView) childView).setCharacter(character);
                    }
                }
            }
        }
    }

    public int calculateCellWidth() {
        return characterGrid.getWidth() / numCols;
    }

    public int calculateCellHeight() {
        return characterGrid.getHeight() / numRows;
    }
}









