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

import src.Course.Discipline;
import src.Course.Lecture;
import src.Spaces.Space;
import src.Spaces.SpaceType;

/**
 * Class responsible for allocating spaces for the lectures
 */
public class SpaceAllocator {
    public static void assignPlaces(List<Space> allSpaces, List<Lecture> allLectures, List<Discipline> allDisciplines) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = separateDisciplinesBySpaceRequirement(allDisciplines);
        Map<SpaceType, List<Space>> separatedSpaces = separateSpacesByType(allSpaces);

        for (Map.Entry<SpaceType, List<Discipline>> entry : separatedDisciplines.entrySet()) {
            SpaceType spaceType = entry.getKey();
            List<Space> spacesOfType = separatedSpaces.get(spaceType);

            if (spacesOfType == null)
                throw new Error("No available spaces for type: " + spaceType);

            List<Lecture> filteredLectures = new ArrayList<>();
            for (Lecture lecture : allLectures){
                Discipline discipline = lecture.getLectureDiscipline();
                if (discipline.getRequiredSpaceType() == spaceType)
                    filteredLectures.add(lecture);
            }

            SpaceAllocator.assignPlacesPerType(spacesOfType, filteredLectures);
        }
    }
    
    private static List<Lecture> assignPlacesPerType(List<Space> spacesOfType, List<Lecture> filteredLectures) {
        Graph<Lecture, DefaultEdge> lecturesGraph = createLecturesGraph(filteredLectures);
        Coloring<Lecture> coloring = coloringLecturesGraph(lecturesGraph);
        return assignPlaces(spacesOfType, coloring);
    }

    private static Graph<Lecture, DefaultEdge> createLecturesGraph(List<Lecture> filteredLectures){
        Graph<Lecture, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for (Lecture lecture : filteredLectures)
            graph.addVertex(lecture);

        for (int i = 0; i < filteredLectures.size(); i++) {
            for (int j = i + 1; j < filteredLectures.size(); j++) {
                if (filteredLectures.get(i).getLectureSchedule().equals(filteredLectures.get(j).getLectureSchedule()))
                    graph.addEdge(filteredLectures.get(i), filteredLectures.get(j));
            }
        }

        return graph;
    }

    private static Coloring<Lecture> coloringLecturesGraph(Graph<Lecture, DefaultEdge> lecturesGraph) {
        GreedyColoring<Lecture, DefaultEdge> coloring = new GreedyColoring<>(lecturesGraph);
        return coloring.getColoring();
    }

    private static List<Lecture> assignPlaces(List<Space> spacesOfType, Coloring<Lecture> coloring) {
        if (spacesOfType.isEmpty())
            throw new IllegalArgumentException("No available spaces to allocate.");

        Map<Integer, Space> spaceColor = new HashMap<>();
        for (int i = 0; i < spacesOfType.size(); i++)
            spaceColor.put(i, spacesOfType.get(i));

        Map<Lecture, String> lectureSpace = new HashMap<>();
        for (Map.Entry<Lecture, Integer> entry : coloring.getColors().entrySet()) {
            Lecture lecture = entry.getKey();
            Integer color = entry.getValue();

            if (color >= spacesOfType.size())
                throw new Error("Insuficent spaces of type " + spacesOfType.get(0).getSpaceName());

            Space space = spaceColor.get(color);
            lecture.setLectureSpace(space);
            lectureSpace.put(lecture, space.getSpaceName());
        }

        List<Lecture> allLectures = new ArrayList<>(lectureSpace.keySet());
        return allLectures;
    }

    private static Map<SpaceType, List<Discipline>> separateDisciplinesBySpaceRequirement(List<Discipline> disciplineList) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = new HashMap<>();
        for (Discipline discipline : disciplineList) {
            SpaceType requiredSpaceType = discipline.getRequiredSpaceType();
            separatedDisciplines.computeIfAbsent(requiredSpaceType, k -> new ArrayList<>()).add(discipline);
        }
        return separatedDisciplines;
    }

    private static Map<SpaceType, List<Space>> separateSpacesByType(List<Space> spaceList) {
        Map<SpaceType, List<Space>> separatedSpaces = new HashMap<>();
        for (Space space : spaceList) {
            SpaceType spaceType = space.getSpaceType();
            separatedSpaces.computeIfAbsent(spaceType, k -> new ArrayList<>()).add(space);
        }
        return separatedSpaces;
    }
}
