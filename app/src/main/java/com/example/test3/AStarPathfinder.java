package com.example.test3;

import android.util.Pair;
import java.util.*;

public class AStarPathfinder {

    private static final int OCCUPIED_PENALTY = 1000;  // High cost to avoid occupied cells

    // Pass occupied cells as a parameter to discourage them in pathfinding
    public List<Pair<Integer, Integer>> findPath(int startX, int startY, int targetX, int targetY, Set<Pair<Integer, Integer>> occupiedCells) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(node -> node.fCost()));
        Set<Pair<Integer, Integer>> closedList = new HashSet<>();

        Node startNode = new Node(startX, startY, null);
        startNode.gCost = 0;
        startNode.hCost = calculateHeuristic(startX, startY, targetX, targetY);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            // Check if reached target
            if (currentNode.x == targetX && currentNode.y == targetY) {
                return reconstructPath(currentNode);
            }

            closedList.add(new Pair<>(currentNode.x, currentNode.y));

            for (Node neighbor : getNeighbors(currentNode)) {
                Pair<Integer, Integer> neighborPos = new Pair<>(neighbor.x, neighbor.y);

                if (closedList.contains(neighborPos)) continue;

                int moveCost = currentNode.gCost + 1; // Standard move cost

                // Apply penalty if the cell is occupied
                if (occupiedCells.contains(neighborPos)) {
                    moveCost += OCCUPIED_PENALTY;
                }

                if (moveCost < neighbor.gCost) {
                    neighbor.parent = currentNode;
                    neighbor.gCost = moveCost;
                    neighbor.hCost = calculateHeuristic(neighbor.x, neighbor.y, targetX, targetY);
                    openList.add(neighbor);
                }
            }
        }

        return new ArrayList<>(); // Return empty path if no path is found
    }

    private int calculateHeuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);  // Manhattan distance
    }

    private List<Pair<Integer, Integer>> reconstructPath(Node targetNode) {
        List<Pair<Integer, Integer>> path = new LinkedList<>();
        for (Node node = targetNode; node != null; node = node.parent) {
            path.add(0, new Pair<>(node.x, node.y));
        }
        return path;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        neighbors.add(new Node(node.x + 1, node.y, node));
        neighbors.add(new Node(node.x - 1, node.y, node));
        neighbors.add(new Node(node.x, node.y + 1, node));
        neighbors.add(new Node(node.x, node.y - 1, node));
        return neighbors;
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
