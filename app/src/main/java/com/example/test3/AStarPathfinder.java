package com.example.test3;

import android.util.Log;
import android.util.Pair;
import java.util.*;

public class AStarPathfinder {

    private static final String TAG = "AStarPathfinder";
    private static final int MAX_ITERATIONS = 10000;  // Limit to prevent infinite loops

    public List<Pair<Integer, Integer>> findPath(int startX, int startY, int targetX, int targetY, Set<Pair<Integer, Integer>> occupiedCells, int numRows, int numCols) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(node -> node.fCost()));
        Map<Pair<Integer, Integer>, Node> allNodes = new HashMap<>();
        Set<Pair<Integer, Integer>> closedSet = new HashSet<>();

        Pair<Integer, Integer> startPos = new Pair<>(startX, startY);
        Node startNode = new Node(startX, startY, null);
        startNode.gCost = 0;
        startNode.hCost = calculateHeuristic(startX, startY, targetX, targetY);
        openList.add(startNode);
        allNodes.put(startPos, startNode);

        int iterations = 0;

        while (!openList.isEmpty()) {
            if (iterations++ > MAX_ITERATIONS) {
                Log.e(TAG, "Max iterations reached, aborting pathfinding to prevent infinite loop.");
                break;
            }

            Node currentNode = openList.poll();
            Pair<Integer, Integer> currentPos = new Pair<>(currentNode.x, currentNode.y);

            if (currentNode.x == targetX && currentNode.y == targetY) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentPos);

            for (Node neighbor : getNeighbors(currentNode, numRows, numCols)) {
                Pair<Integer, Integer> neighborPos = new Pair<>(neighbor.x, neighbor.y);

                if (closedSet.contains(neighborPos)) continue;

                // Apply penalty if the cell is occupied
                int moveCost = currentNode.gCost + 1;
                if (occupiedCells.contains(neighborPos)) {
                    continue;  // Skip occupied cells entirely
                }

                Node existingNeighbor = allNodes.get(neighborPos);
                if (existingNeighbor == null || moveCost < existingNeighbor.gCost) {
                    neighbor.gCost = moveCost;
                    neighbor.hCost = calculateHeuristic(neighbor.x, neighbor.y, targetX, targetY);
                    neighbor.parent = currentNode;

                    openList.add(neighbor);
                    allNodes.put(neighborPos, neighbor);
                }
            }
        }

        Log.w(TAG, "No path found from (" + startX + ", " + startY + ") to (" + targetX + ", " + targetY + ")");
        return null; // Return null if no path is found
    }

    private int calculateHeuristic(int x1, int y1, int x2, int y2) {
        // Use Manhattan distance
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private List<Pair<Integer, Integer>> reconstructPath(Node targetNode) {
        List<Pair<Integer, Integer>> path = new LinkedList<>();
        Node current = targetNode;
        while (current != null) {
            path.add(0, new Pair<>(current.x, current.y));
            current = current.parent;
        }
        return path;
    }

    private List<Node> getNeighbors(Node node, int numRows, int numCols) {
        List<Node> neighbors = new ArrayList<>();

        int[][] directions = {
                {0, -1},  // Up
                {0, 1},   // Down
                {-1, 0},  // Left
                {1, 0}    // Right
        };

        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];

            if (isValidPosition(newX, newY, numRows, numCols)) {
                neighbors.add(new Node(newX, newY, node));
            }
        }

        return neighbors;
    }

    private boolean isValidPosition(int x, int y, int numRows, int numCols) {
        return x >= 0 && x < numRows && y >= 0 && y < numCols;
    }

    private static class Node {
        int x, y;
        Node parent;
        int gCost = Integer.MAX_VALUE;
        int hCost = Integer.MAX_VALUE;

        Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }

        int fCost() {
            return gCost + hCost;
        }
    }
}
