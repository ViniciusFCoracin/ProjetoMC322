package src;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.color.GreedyColoring;

public class Main {
    /* somente teste */
    public static void main(String[] args) {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");

        GreedyColoring<String, DefaultEdge> coloring = new GreedyColoring<>(graph);
        System.out.println("Vertex Colors: " + coloring.getColoring());
    }
}
