import java.util.*;

// Class for performing Breadth-First Search on a graph
public class Breadth_First_Search {

    // Reference to the graph on which BFS is performed
    private Graph graph;

    // Constructor to initialize the graph
    public Breadth_First_Search(Graph graph) {
        this.graph = graph;
    }

    // Method to perform BFS starting from a given vertex
    public void USE_BFS(int start) {
        long startTime = System.nanoTime(); // Start time for performance measurement
        Queue<State> queue = new LinkedList<>(); // Queue to store states during BFS
        boolean[] visited = new boolean[graph.vertices]; // Array to track visited vertices
        int minCost = Integer.MAX_VALUE; // Variable to store the minimum cost found
        List<Integer> bestPath = new ArrayList<>(); // List to store the best path found

        // Add the initial state to the queue
        queue.add(new State(start, new ArrayList<>(List.of(start)), 0));

        // Perform BFS
        while (!queue.isEmpty()) {
            State currentState = queue.poll(); // Get the current state from the queue
            int currentNode = currentState.node; // Current node
            List<Integer> currentPath = currentState.path; // Current path
            int currentCost = currentState.cost; // Current cost

            // If all vertices have been visited, check for a cycle to the start
            if (currentPath.size() == graph.vertices) {
                for (Graph.Edge edge : graph.adjacencyList[currentNode]) {
                    if (edge.node == start) {
                        int totalCost = currentCost + edge.cost; // Calculate the total cost
                        if (totalCost < minCost) {
                            minCost = totalCost; // Update the minimum cost
                            bestPath = new ArrayList<>(currentPath); // Update the best path
                            bestPath.add(start); // Complete the cycle
                        }
                        break;
                    }
                }
            } else {
                // Explore adjacent nodes
                for (Graph.Edge edge : graph.adjacencyList[currentNode]) {
                    if (!currentPath.contains(edge.node)) {
                        List<Integer> newPath = new ArrayList<>(currentPath); // Create a new path
                        newPath.add(edge.node); // Add the node to the new path
                        queue.add(new State(edge.node, newPath, currentCost + edge.cost)); // Add the new state to the queue
                    }
                }
            }
        }

        // Print the results
        System.out.println("Breadth First Traversal(BFS) starting from city " + start + ":");
        System.out.println("Best Path: " + bestPath);
        System.out.println("Total Cost: " + minCost);

        long endTime = System.nanoTime(); // End time for performance measurement
        double duration = (endTime - startTime) / 1000000000.0; // Calculate duration in seconds
        System.out.println("Cost-Aware BFS Runtime: " + duration + " seconds" + '\n');
    }

    // Class to represent the state in BFS
    static class State {
        int node; // Current node
        List<Integer> path; // Path taken to reach the node
        int cost; // Cost of the path

        public State(int node, List<Integer> path, int cost) {
            this.node = node;
            this.path = path;
            this.cost = cost;
        }
    }

}
