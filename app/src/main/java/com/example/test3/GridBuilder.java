package com.example.test3;

import android.graphics.Color;
import android.util.Pair;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.gridlayout.widget.GridLayout;

import java.util.Map;


public class GridBuilder {

    private final GridLayout characterGrid;
    private final int numRows;
    private final int numCols;
    private final int stagingAreaRows;
    private CharacterController characterController;

    private static final int CELL_WIDTH = 105;
    private static final int CELL_HEIGHT = 105;
    private static final int CELL_MARGIN = 6;

    //grid builder constructor
    public GridBuilder(int numRows, int numCols, int stagingAreaRows, GridLayout gridLayout) {
        this.characterGrid = gridLayout;
        this.numRows = numRows;
        this.numCols = numCols;
        this.stagingAreaRows = stagingAreaRows;
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
        characterGrid.setRowCount(numRows + stagingAreaRows);
        characterGrid.setColumnCount(numCols);

        // Working grid maker, creates standard 9 x 9 grid.
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                FrameLayout tileContainer = new FrameLayout(characterGrid.getContext());

                // Set size for each cell
                GridLayout.LayoutParams layoutParams = createCellLayoutParams(row, col);
                tileContainer.setLayoutParams(layoutParams);
                tileContainer.setBackgroundColor(Color.GRAY);

                // Add the container to the grid layout
                characterGrid.addView(tileContainer, layoutParams);
            }
         }

        //create staging area
        for (int col = 0; col < numCols; col++) {
            FrameLayout stagingTile = new FrameLayout(characterGrid.getContext());

            GridLayout.LayoutParams stagingParams = createCellLayoutParams(numRows, col);
            stagingTile.setLayoutParams(stagingParams);
            stagingTile.setBackgroundColor(Color.LTGRAY); // set staging area to lighter color (temp)

            // Add the container to the grid layout
            characterGrid.addView(stagingTile, stagingParams);
        }

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
                            ViewGroup parent = (ViewGroup) draggedView.getParent();
                            parent.removeView(draggedView); // Remove char from view
                            FrameLayout targetCell = (FrameLayout) v;
                            targetCell.addView(draggedView); // add char to target cell
                            v.setBackgroundColor(Color.GRAY); // reset color
                            return true;
                        case DragEvent.ACTION_DRAG_ENDED:
                            if (!event.getResult()) { // used in case character is placed out of bounds of grid
                                draggedView = (View) event.getLocalState();
                                parent = (ViewGroup) draggedView.getParent();
                                if (parent != null) {
                                    parent.removeView(draggedView); //remove from unintended parent
                                }
                                FrameLayout stagingTile = findAvailableStagingTile();
                                if (stagingTile != null) {
                                    stagingTile.addView(draggedView);
                                }
                            }
                            return true;
                        default:
                            return false;
                    }
                });
            }
        }
    }

    // used to find available tile if placed out of bounds
    private FrameLayout findAvailableStagingTile() {
        for (int col = 0; col < numCols; col++) {
            FrameLayout stagingTile = (FrameLayout) characterGrid.getChildAt(numCols * numCols + col); // find empty tile if placed out of bounds
            if (stagingTile.getChildCount() == 0) {
                return stagingTile;
            }
        }
        return null; // no empty tile found
    }

    public int getStagingAreaStartRows() {
        return numRows;
    }

    public int calculateCellWidth() {
        return characterGrid.getWidth() / numCols;
    }

    public int calculateCellHeight() {
        return characterGrid.getHeight() / (numRows + stagingAreaRows);
    }
}









