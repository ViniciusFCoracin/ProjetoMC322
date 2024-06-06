package src.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import src.Course.Lecture;
import src.Spaces.Space;

/**
 * Class responsible for allocating spaces for the lectures
 */
public class SpaceAllocator {
    /**
     * Method responsible for assigning places to the lectures
     * 
     * @param availableSpaces: a list of available spaces
     * @param lectures: a list of lectures without a place assigned
     * @return: a list of lectures with places assigned
     */
    public static List<Lecture> assignPlaces(List<Space> availableSpaces, List<Lecture> lectures) {
        Graph<Lecture, DefaultEdge> lecturesGraph = createLectureGraph(lectures);
        Coloring<Lecture> coloring = coloringLecturesGraph(lecturesGraph);
        return assignPlaces(availableSpaces, coloring);
    }

    private static Graph<Lecture, DefaultEdge> createLectureGraph(List<Lecture> lectures) {
        Graph<Lecture, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for (Lecture lecture : lectures) {
            graph.addVertex(lecture);
        }

        for (int i = 0; i < lectures.size(); i++) {
            for (int j = i + 1; j < lectures.size(); j++) {
                if (lectures.get(i).getLectureSchedule().equals(lectures.get(j).getLectureSchedule())) {
                    graph.addEdge(lectures.get(i), lectures.get(j));
                }
            }
        }

        return graph;
    }

    private static Coloring<Lecture> coloringLecturesGraph(Graph<Lecture, DefaultEdge> graph) {
        GreedyColoring<Lecture, DefaultEdge> coloring = new GreedyColoring<>(graph);
        return coloring.getColoring();
    }

    private static List<Lecture> assignPlaces(List<Space> availableSpaces, Coloring<Lecture> coloring) {
        if (availableSpaces.isEmpty()) {
            throw new IllegalArgumentException("No available spaces to allocate.");
        }

        Map<Integer, Space> spaceColor = new HashMap<>();
        for (int i = 0; i < availableSpaces.size(); i++) {
            spaceColor.put(i, availableSpaces.get(i));
        }

        Map<Lecture, String> lectureSpace = new HashMap<>();
        for (Map.Entry<Lecture, Integer> entry : coloring.getColors().entrySet()) {
            Lecture lecture = entry.getKey();
            Integer color = entry.getValue();
            Space space = spaceColor.get(color % availableSpaces.size());
            lecture.setLectureSpace(space);
            lectureSpace.put(lecture, space.getSpaceName());
        }

        List<Lecture> allLectures = new ArrayList<>(lectureSpace.keySet());
        return allLectures;
    }
}
