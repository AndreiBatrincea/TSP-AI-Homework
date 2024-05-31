import java.io.*;
import java.util.*;

class Graph {
    // Number of vertices in the graph
    protected int vertices;
    // Adjacency list to store edges of the graph
    protected LinkedList<Edge>[] adjacencyList;

    // Edge class to represent an edge with a destination node and a cost
    static class Edge {
        int node; // Destination node
        int cost; // Cost of the edge

        public Edge(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }

    // Constructor to initialize the graph with a given number of vertices
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; ++i) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    // Method to add an edge to the graph
    public void addEdge(int v, int w, int cost) {
        adjacencyList[v].add(new Edge(w, cost)); // Add edge from v to w
        adjacencyList[w].add(new Edge(v, cost)); // Add edge from w to v (undirected graph)
    }

    // Method to get the cost of an edge between two vertices
    public int getEdgeCost(int v1, int v2) {
        for (Edge edge : adjacencyList[v1]) {
            if (edge.node == v2) {
                return edge.cost; // Return the cost if edge is found
            }
        }
        return -1; // Return -1 if edge is not found
    }

    // Method to display the graph
    public void showGraph() {
        for (int i = 0; i < vertices; i++) {
            System.out.print("Vertex " + i + ":");
            for (Edge edge : adjacencyList[i]) {
                System.out.print(" -> (" + edge.node + ", Cost:" + edge.cost + ")");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Method to read a graph from a file
    public static Graph readGraphFromFile(String filename) {
        Graph graph = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int vertices = Integer.parseInt(br.readLine().trim()); // Read number of vertices
            graph = new Graph(vertices);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int source = Integer.parseInt(parts[0]); // Source vertex
                int destination = Integer.parseInt(parts[1]); // Destination vertex
                int cost = Integer.parseInt(parts[2]); // Cost of the edge
                graph.addEdge(source, destination, cost); // Add the edge to the graph
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    // Method to generate a random graph
    public static Graph generateRandomGraph(int vertices) {
        Graph graph = new Graph(vertices);
        Random rand = new Random();
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                int cost = rand.nextInt(20) + 1; // Random cost between 1 and 20
                graph.addEdge(i, j, cost); // Add the edge to the graph
            }
        }
        return graph;
    }
}
