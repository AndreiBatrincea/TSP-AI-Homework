import java.util.*;

// Class implementing the A* Search algorithm
public class A_Search {

    private Graph graph; // Reference to the graph

    // Constructor to initialize the A* Search with a graph
    public A_Search(Graph graph) {
        this.graph = graph;
    }

    // Method to perform A* Search starting from a given vertex
    public void USE_A_STAR_SEARCH(int startVertex) {
        long startTime = System.nanoTime(); // Start time for performance measurement

        // Priority queue to store nodes based on their combined cost and heuristic
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost + n.heuristic));
        queue.add(new Node(startVertex, 0, new ArrayList<>(Collections.singletonList(startVertex)))); // Add the starting node

        List<Integer> bestPath = new ArrayList<>(); // List to store the best path found
        int bestCost = Integer.MAX_VALUE; // Variable to store the best cost found

        // Perform A* Search
        while (!queue.isEmpty()) {
            Node current = queue.poll(); // Get the node with the minimum combined cost and heuristic

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
                    int heuristic = calculateHeuristic(edge.node, startVertex, newPath); // Calculate the heuristic for the new node
                    queue.add(new Node(edge.node, current.cost + edge.cost, newPath, heuristic)); // Add the new node to the queue
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
        System.out.println("A* Search Runtime: " + duration + " seconds" + '\n');
    }

    // Method to calculate the heuristic for a node
    private int calculateHeuristic(int currentVertex, int startVertex, List<Integer> path) {
        int minCost = Integer.MAX_VALUE; // Initialize the minimum cost to a large value
        for (int i = 0; i < graph.vertices; i++) {
            if (!path.contains(i) && i != currentVertex) { // Exclude nodes already in the path and the current node
                int edgeCost = graph.getEdgeCost(currentVertex, i); // Get the cost of the edge to the node
                if (edgeCost != -1) { // If the edge exists
                    minCost = Math.min(minCost, edgeCost); // Update the minimum cost if necessary
                }
            }
        }
        return minCost == Integer.MAX_VALUE ? 0 : minCost; // Return 0 if no heuristic is available
    }

    // Class representing a node in the A* Search
    static class Node {
        int vertex; // Vertex/node
        int cost; // Cost of reaching the node
        int heuristic; // Heuristic value of the node
        List<Integer> path; // Path taken to reach the node

        // Constructor to initialize the node with vertex, cost, path, and heuristic
        public Node(int vertex, int cost, List<Integer> path, int heuristic) {
            this.vertex = vertex;
            this.cost = cost;
            this.path = path;
            this.heuristic = heuristic;
        }

        // Constructor to initialize the node with vertex, cost, and path (default heuristic is 0)
        public Node(int vertex, int cost, List<Integer> path) {
            this(vertex, cost, path, 0);
        }
    }
}
