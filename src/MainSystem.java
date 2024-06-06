package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.Semester;
import src.GraphicInterface.Controllers.SelectionController;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Schedule.ClassSchedule;
import src.Schedule.WeekDay;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;

import src.Spaces.Space;
import src.Spaces.SpaceType;

public class MainSystem {

    /**
     * This method is responsible for setting the lectures their space. The possible issue is that two lectures cannot have the same room, if the have the same schedule.
     * @param availableSpaces: a list of available spaces for the lectures
     * @param courses: a list of courses that the university has as available
     * @param disciplineList: the list of the disciplines that will be available at the University
     * @return: returns a Map<Lecture, String> in which we can access the lecture and all it's information (that now shall be initialized)
     */
    public static Map<Lecture, String> assignSchedulesAndPlaces(List<Space> availableSpaces, List<Course> courses, List<Discipline> disciplineList){
        List<Lecture> lectures = MainSystem.assignSchedules(courses, disciplineList);
        Graph<Lecture, DefaultEdge> lecturesGraph = createLectureGraph(lectures);
        Coloring<Lecture> coloring = coloringLecturesGraph(lecturesGraph);
        return assignPlaces(availableSpaces, coloring);
    }

    /**
     * This method is responsible for setting the schedule of the disciplines avoiding the conflict in which two lectures of different disciplines,
     * but same course and semester happen at the same time. Note that it's an issue, because the student can't be in the two classes simultaneously.
     * @param courses: the list of the courses that the university will have as available
     * @param disciplineList: the list of the disciplines that will be available at the University
     * @return: returns a list of lectures with a discipline, professor, schedule and a non-initialized space
     */
    private static List<Lecture> assignSchedules(List<Course> courses, List<Discipline> disciplineList){
        Graph<String, DefaultEdge> disciplinesGraph = createDisciplinesGraph(courses, disciplineList);
        Coloring<String> coloring = coloringDisciplinesGraph(disciplinesGraph);
        return createLectures(disciplineList, disciplinesGraph, coloring);
    }

    private static Graph<String, DefaultEdge> createDisciplinesGraph(List<Course> courses, List<Discipline> disciplineList){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Adding disciplines (vertices)
        for (Course course : courses) {
            for (Semester semester : course.getCourseSemesters()) {
                for (String disciplineId : semester.getDisciplines()) {
                    graph.addVertex(disciplineId);
                }
            }
        }

        // Adding edges (possible conflicts)
        for (Course course : courses) {
            for (Semester semester : course.getCourseSemesters()) {
                List<String> disciplineIds = semester.getDisciplines();
                for (int i = 0; i < disciplineIds.size(); i++) {
                    for (int j = i + 1; j < disciplineIds.size(); j++) {
                        graph.addEdge(disciplineIds.get(i), disciplineIds.get(j));
                    }
                }
            }
        }

        return graph;
    }

    private static Graph<Lecture, DefaultEdge> createLectureGraph(List<Lecture> lectures){
        Graph<Lecture, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Adding lectures (vertexes)
        for (Lecture lecture : lectures) {
            graph.addVertex(lecture);
        }


    public static Map<Lecture, String> assignSchedulesAndPlaces(List<Space> availableSpaces, List<Course> courses, List<Discipline> disciplineList) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = separateDisciplinesBySpaceRequirement(disciplineList);
        Map<SpaceType, List<Space>> separatedSpaces = separateSpacesByType(availableSpaces);

        Map<Lecture, String> lectureSpace = new HashMap<>();

        List<Lecture> allLectures = ClassScheduler.assignSchedules(courses, disciplineList);

        for (Map.Entry<SpaceType, List<Discipline>> entry : separatedDisciplines.entrySet()) {
            SpaceType spaceType = entry.getKey();
            List<Space> spaces = separatedSpaces.getOrDefault(spaceType, new ArrayList<>());

            if (spaces.isEmpty()) {
                System.err.println("No available spaces for type: " + spaceType);
                continue;
            }

            List<Lecture> filteredLectures = new ArrayList<>();
            for (Lecture lecture : allLectures) {
                Discipline discipline = disciplineList.stream()
                    .filter(d -> d.getDisciplineId().equals(lecture.getLectureDisciplineId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Discipline not found " + lecture.getLectureDisciplineId()));

                if (discipline.getRequiredSpaceType() == spaceType) {
                    filteredLectures.add(lecture);
                }
            }

            lectureSpace.putAll(SpaceAllocator.assignPlaces(spaces, filteredLectures));
        }

        return lectureSpace;
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
    
    private static List<Space> removeSelectedSpaces(List<Space> allSpaces, List<String> selectedSpaces) {
    	 Iterator<Space> iterator = allSpaces.iterator();
         while (iterator.hasNext()) {
             Space space = iterator.next();
             if (selectedSpaces.contains(space.getSpaceName())) {
                 iterator.remove();
             }
         }
         return allSpaces;
	}
    
    public static void startDistribution() {
        List<Discipline> allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");
        
        List<Space> allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");
        System.out.println("Available spaces before selection:");
        System.out.println(allSpaces);
        System.out.println("==============================================================================");
        allSpaces = removeSelectedSpaces(allSpaces, SelectionController.getRemovedSpaces());
        System.out.println("Available spaces after selection:");
        System.out.println(allSpaces);
        
        
        List<Course> allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");

        Map<Lecture, String> allocatedSpaces = MainSystem.assignSchedulesAndPlaces(allSpaces, allCourses, allDisciplines);
        
        for (Map.Entry<Lecture, String> entry : allocatedSpaces.entrySet()) {
            Lecture lecture = entry.getKey();
            String output = ("Lecture: " + lecture.getLectureDisciplineId() + ", Professor " + lecture.getProfessor() + "\n" +
                             "Day: " + lecture.getLectureSchedule().getDay() + ", " + lecture.getLectureSchedule().getStartHour() + "h - " + lecture.getLectureSchedule().getEndHour() + "h\n" +
                             "Place: " + lecture.getLectureSpace() + "\n" + "Group: " + lecture.getLectureGroup() +"\n");
            //System.out.println(output);
        }
    }
}
