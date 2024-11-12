package com.example.test3;

import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.gridlayout.widget.GridLayout;



public class GridBuilder {

    private final GridLayout characterGrid;
    private final int numRows;
    private final int numCols;
    private final int stagingAreaRows;

    private static final int CELL_WIDTH = 105;
    private static final int CELL_HEIGHT = 105;
    private static final int CELL_MARGIN = 6;

    public GridBuilder(int numRows, int numCols, int stagingAreaRows, GridLayout gridLayout) {
        this.characterGrid = gridLayout;
        this.numRows = numRows;
        this.numCols = numCols;
        this.stagingAreaRows = stagingAreaRows;
        createGrid();
    }

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


    private void createGrid() {
        characterGrid.removeAllViews();
        characterGrid.setRowCount(numRows + stagingAreaRows);
        characterGrid.setColumnCount(numCols);

        // Working grid maker
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

        for (int col = 0; col < numCols; col++) {
            FrameLayout stagingTile = new FrameLayout(characterGrid.getContext());

            GridLayout.LayoutParams stagingParams = createCellLayoutParams(numRows, col);
            stagingTile.setLayoutParams(stagingParams);

            stagingTile.setBackgroundColor(Color.LTGRAY);

            characterGrid.addView(stagingTile, stagingParams);
        }

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
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
                            if (!event.getResult()) {
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









