import java.util.*;

// Class for performing Least Cost Search on a graph
public class Least_Cost_Search {

    private Graph graph; // Reference to the graph

    // Constructor to initialize the graph
    public Least_Cost_Search(Graph graph) {
        this.graph = graph;
    }

    // Method to perform Least Cost Search starting from a given vertex
    public void USE_LEAST_COST_SEARCH(int startVertex) {
        long startTime = System.nanoTime(); // Start time for performance measurement

        // Priority queue to store nodes based on their cost
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        queue.add(new Node(startVertex, 0, new ArrayList<>(Collections.singletonList(startVertex)))); // Add the starting node

        List<Integer> bestPath = new ArrayList<>(); // List to store the best path found
        int bestCost = Integer.MAX_VALUE; // Variable to store the minimum cost found

        // Perform Least Cost Search
        while (!queue.isEmpty()) {
            Node current = queue.poll(); // Get the node with the minimum cost

            // If the cost of the current node exceeds the best cost, skip it
            if (current.cost >= bestCost) {
                continue;
            }

            // If all vertices have been visited, check for a cycle to the start
            if (current.path.size() == graph.vertices) {
                int returnCost = graph.getEdgeCost(current.vertex, startVertex);
                if (returnCost != -1) {
                    int totalCost = current.cost + returnCost; // Calculate the total cost
                    if (totalCost < bestCost) {
                        bestPath = new ArrayList<>(current.path); // Update the best path
                        bestPath.add(startVertex); // Complete the cycle
                        bestCost = totalCost; // Update the best cost
                    }
                }
                continue;
            }

            // Explore adjacent nodes
            for (Graph.Edge edge : graph.adjacencyList[current.vertex]) {
                if (!current.path.contains(edge.node)) {
                    List<Integer> newPath = new ArrayList<>(current.path); // Create a new path
                    newPath.add(edge.node); // Add the node to the new path
                    queue.add(new Node(edge.node, current.cost + edge.cost, newPath)); // Add the new node to the queue
                }
            }
        }

        // Print the results
        if (bestPath.size() > 0) {
            System.out.println("Best Path: " + bestPath);
            System.out.println("Total Cost: " + bestCost);
        } else {
            System.out.println("Could not find a valid path.");
        }

        long endTime = System.nanoTime(); // End time for performance measurement
        double duration = (endTime - startTime) / 1e9; // Calculate duration in seconds
        System.out.println("Least Cost Traversal Runtime: " + duration + " seconds" + '\n');
    }

    // Class to represent a node in the search
    static class Node {
        int vertex; // Vertex/node
        int cost; // Cost of reaching the node
        List<Integer> path; // Path taken to reach the node

        // Constructor to initialize the node
        public Node(int vertex, int cost, List<Integer> path) {
            this.vertex = vertex;
            this.cost = cost;
            this.path = path;
        }
    }
}
