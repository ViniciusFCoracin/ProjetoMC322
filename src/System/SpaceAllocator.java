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
import src.Errors.*;
import src.Spaces.Space;
import src.Spaces.SpaceType;

/**
 * Class responsible for allocating spaces for the lectures
 */
public class SpaceAllocator {

    /**
     * Assign places to the lectures. Two lectures in the same schedule cannot
     * occur in the same place.
     * 
     * @param allSpaces: list of all spaces available
     * @param allLectures: list of all lectures created, without places assigned
     * @param allDisciplines: list of all disciplines
     */
    public static void assignPlaces(List<Space> allSpaces, List<Lecture> allLectures, List<Discipline> allDisciplines) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = separateDisciplinesBySpaceRequirement(allDisciplines);
        Map<SpaceType, List<Space>> separatedSpaces = separateSpacesByType(allSpaces);

        for (Map.Entry<SpaceType, List<Discipline>> typeDiscipline : separatedDisciplines.entrySet()) {
            SpaceType spaceType = typeDiscipline.getKey();
            List<Space> spacesOfType = separatedSpaces.get(spaceType);

            if (spacesOfType == null)
                throw new NoSpacesAvailableError("No spaces of type " + spaceType);

            List<Lecture> filteredLectures = new ArrayList<>();
            for (Lecture lecture : allLectures){
                Discipline discipline = lecture.getLectureDiscipline();
                if (discipline.getRequiredSpaceType() == spaceType)
                    filteredLectures.add(lecture);
            }

            SpaceAllocator.assignPlacesPerType(spacesOfType, filteredLectures, spaceType);
        }
    }
    
    private static List<Lecture> assignPlacesPerType(List<Space> spacesOfType, List<Lecture> filteredLectures, SpaceType type) {
        Graph<Lecture, DefaultEdge> lecturesGraph = createLecturesGraph(filteredLectures);
        Coloring<Lecture> coloring = coloringLecturesGraph(lecturesGraph);
        return assignPlacesOfType(spacesOfType, coloring, type);
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

    private static List<Lecture> assignPlacesOfType(List<Space> spacesOfType, Coloring<Lecture> coloring, SpaceType type) {
        if (spacesOfType.isEmpty())
            throw new NoSpacesAvailableError("No spaces of type " + type);

        Map<Integer, Space> spaceColor = new HashMap<>();
        for (int i = 0; i < spacesOfType.size(); i++)
            spaceColor.put(i, spacesOfType.get(i));

        Map<Lecture, String> lectureSpace = new HashMap<>();
        for (Map.Entry<Lecture, Integer> entry : coloring.getColors().entrySet()) {
            Lecture lecture = entry.getKey();
            Integer color = entry.getValue();

            if (color >= spacesOfType.size())
                throw new InsuficientSpacesError("Insufficent spaces of type " + spacesOfType.get(0).getSpaceType());

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
