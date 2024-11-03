package com.example.test3;

import android.util.Pair;
import java.util.*;

public class AStarPathfinder {

    public List<Pair<Integer, Integer>> findPath(int startX, int startY, int targetX, int targetY) {
        // Priority Queue for open nodes
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(node -> node.fCost()));
        Set<Pair<Integer, Integer>> closedList = new HashSet<>();

        // Initialize the starting node
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

            // Add neighbors to the open list
            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(new Pair<>(neighbor.x, neighbor.y))) continue;

                int tentativeGCost = currentNode.gCost + 1;  // 1 for adjacent move
                if (tentativeGCost < neighbor.gCost) {
                    neighbor.parent = currentNode;
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = calculateHeuristic(neighbor.x, neighbor.y, targetX, targetY);
                    openList.add(neighbor);
                }
            }
        }

        // Return empty list if no path found
        return new ArrayList<>();
    }

    // Heuristic method for A*
    private int calculateHeuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);  // Manhattan distance
    }

    // Method to reconstruct path from target node
    private List<Pair<Integer, Integer>> reconstructPath(Node targetNode) {
        List<Pair<Integer, Integer>> path = new LinkedList<>();
        for (Node node = targetNode; node != null; node = node.parent) {
            path.add(0, new Pair<>(node.x, node.y));  // Add each node in reverse
        }
        return path;
    }

    // Get neighboring nodes (up, down, left, right)
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        neighbors.add(new Node(node.x + 1, node.y, node));
        neighbors.add(new Node(node.x - 1, node.y, node));
        neighbors.add(new Node(node.x, node.y + 1, node));
        neighbors.add(new Node(node.x, node.y - 1, node));
        return neighbors;
    }

    // Inner class to represent a node in A*
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
