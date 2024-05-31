import java.io.*;
import java.util.*;

public class Main {
    public static void main(String args[]) {
        String filename = "src\\main\\resources\\AdjacencyListInputs.txt";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 to generate a random graph, or 2 to read graph from a file:");
        int choice = scanner.nextInt();

        Graph graph = null;

        if (choice == 1)
        {
            System.out.println("Enter the number of cities for the random graph:");
            int vertices = scanner.nextInt();
            graph = Graph.generateRandomGraph(vertices);
        }
        else
            if (choice == 2)
            {
                graph = Graph.readGraphFromFile(filename);
            }
            else
            {
                System.out.println("Invalid choice!");
                System.exit(0);
            }

        System.out.println('\n');
        System.out.println("Adjacency List representation of the graph:");
        graph.showGraph();

        Breadth_First_Search bfsGraph = new Breadth_First_Search(graph);
        bfsGraph.USE_BFS(0);

        System.out.println("Least Cost (Uniform Cost) Search from city 0:");
        Least_Cost_Search ucsGraph = new Least_Cost_Search(graph);
        ucsGraph.USE_LEAST_COST_SEARCH(0);

        System.out.println("A* Search using heuristic function from city 0");
        A_Search aSearchGraph = new A_Search(graph);
        aSearchGraph.USE_A_STAR_SEARCH(0);
    }
}


