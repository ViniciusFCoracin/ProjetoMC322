package src;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import src.Course.Lecture;

import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.color.Coloring;

public class Main {

    public static Map<Lecture, String> allocatingSpaces(Lecture... lectures) {
        // Creating the graph
        Graph<Lecture, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Adding lectures (vertexes)
        for (Lecture lecture : lectures) {
            graph.addVertex(lecture);
        }

        // Adding edges (possible conflicts)
        for (int i = 0; i < lectures.length; i++) {
            for (int j = i + 1; j < lectures.length; j++) {
                if (lectures[i].getLectureSchedule().equals(lectures[j].getLectureSchedule())
                    /** || (lectures[i].course.equals(lectures[j].course) && 
                     * lectures[i].semester.equals(lectures[j].semester)**/) {
                    graph.addEdge(lectures[i], lectures[j]);
                }
            }
        }

        // Coloring graphs
        GreedyColoring<Lecture, DefaultEdge> coloring = new GreedyColoring<>(graph);
        Coloring<Lecture> coloringResult = coloring.getColoring();
        // Map<Lecture, Integer> colors = coloring.getColoring();

        // Mapping space colors (Rooms, labs, etc)
        Map<Integer, String> spaceColor = new HashMap<>();
        spaceColor.put(0, "Room 1");
        spaceColor.put(1, "Room 2");
        spaceColor.put(2, "Room 3");

        // Assigning rooms based on colors
        Map<Lecture, String> lectureSpace = new HashMap<>();
        for (Map.Entry<Lecture, Integer> entry : coloringResult.getColors().entrySet()) {
            Lecture lecture = entry.getKey();
            Integer color = entry.getValue();
            String space = spaceColor.get(color);
            lectureSpace.put(lecture, space);
        }

        return lectureSpace;
    }
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