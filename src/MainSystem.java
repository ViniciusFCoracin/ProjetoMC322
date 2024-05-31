package src;

import java.util.ArrayList;
import java.util.Arrays;
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
/*This class may be refactored to a Singleton later */
public class MainSystem {

    private List<Lecture> lectureList;
    private List<Space> spaceList;

    public MainSystem(List<Lecture> newLectureList, Space... spaces){
        this.lectureList = newLectureList;
        this.spaceList = new ArrayList<Space>(Arrays.asList(spaces));
    }

    public List<Lecture> getLectureList(){
        return this.lectureList;
    }

    public List<Space> getSpaceList(){
        return this.spaceList;
    }

    public static Map<Lecture, String> allocatingSpaces(List<Space> availableSpaces,Lecture... lectures) {
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
        for (int i = 0; i < availableSpaces.size(); i++) {
            spaceColor.put(i, availableSpaces.get(i).getSpaceName());
        }

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
}
