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

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.Semester;
import src.Schedule.ClassSchedule;
import src.Schedule.WeekDay;
import src.Spaces.Space;

/* This class may be refactored to a Singleton later */
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

    /**
     * This method is responsible for setting the schedule of the disciplines avoiding the conflict in which two lectures of different disciplines,
     * but same course and semester happen at the same time. Note that it's an issue, because the student can't be in the two classes simulteneaously.
     * @param courses: the list of the courses that the university will have as avaiable
     * @return: returns a list of lectures with a discipline, professor, schedule and a non-initialized space
     */
    public static List<Lecture> setSchedule(List<Course> courses) {
        Graph<Discipline, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Adding disciplines (vertices)
        for (Course course : courses) {
            for (Semester semester : course.getCourseSemesters()) {
                for (Discipline discipline : semester.getDisciplines()) {
                    graph.addVertex(discipline);
                }
            }
        }

        // Adding edges (possible conflicts)
        for (Course course : courses) {
            for (Semester semester : course.getCourseSemesters()) {
                List<Discipline> disciplines = semester.getDisciplines();
                for (int i = 0; i < disciplines.size(); i++) {
                    for (int j = i + 1; j < disciplines.size(); j++) {
                        graph.addEdge(disciplines.get(i), disciplines.get(j));
                    }
                }
            }
        }

        // Coloring graphs
        GreedyColoring<Discipline, DefaultEdge> coloring = new GreedyColoring<>(graph);
        Coloring<Discipline> coloringResult = coloring.getColoring();

        // Creating lectures with allocated schedules
        List<Lecture> lectures = new ArrayList<>();
        for (Map.Entry<Discipline, Integer> entry : coloringResult.getColors().entrySet()) {
            Discipline discipline = entry.getKey();
            Integer color = entry.getValue();
            ClassSchedule schedule = new ClassSchedule(WeekDay.values()[color % WeekDay.values().length], 8 + color * 2, 8 + color * 2 + 1); // Simplified schedule
            List<String> instructors = discipline.getProfessors();
            // This part of the code may be an issue. It appears to be only considering the first professor of the list
            int instructorIndex = 0;
            String instructor = instructors.get(instructorIndex % instructors.size());
            instructorIndex++;
            lectures.add(new Lecture(null, discipline, schedule, instructor));
        }

        return lectures;
    }

    /**
     * This method is responsible for setting the lectures their space. The possible issue is that two lectures cannot have the same room, if the have the same schedule.
     * @param availableSpaces: a list of avaible spaces for the lectures
     * @param courses: a list of courses that the university has as avaible
     * @return: returns a Map<Lecture, String> in which we can access the lecture and all it's information (that now shall be initialized)
     */
    public static Map<Lecture, String> allocatingSpaces(List<Space> availableSpaces, List<Course> courses){
        // Calls the setSchedule method to create a list of lectures
        List<Lecture> lectures = MainSystem.setSchedule(courses);
        
        // Creating the graph
        Graph<Lecture, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Adding lectures (vertexes)
        for (Lecture lecture : lectures) {
            graph.addVertex(lecture);
        }

        // Adding edges (possible conflicts)
        for (int i = 0; i < lectures.size(); i++) {
            for (int j = i + 1; j < lectures.size(); j++) {
                if (lectures.get(i).getLectureSchedule().equals(lectures.get(j).getLectureSchedule())) {
                    /* If two lectures have the same schedule, but different courses or semesters, we just have to allocate
                       different spaces */
                    graph.addEdge(lectures.get(i), lectures.get(j));
                }
                // We may have to implement exceptions here so the code throws an error if there isn't a non-conflicting distribution of spaces and schedules
            }
        }

        // Coloring graphs
        GreedyColoring<Lecture, DefaultEdge> coloring = new GreedyColoring<>(graph);
        Coloring<Lecture> coloringResult = coloring.getColoring();

        // Mapping space colors (Rooms, labs, etc)
        Map<Integer, Space> spaceColor = new HashMap<>();
        for (int i = 0; i < availableSpaces.size(); i++) {
            spaceColor.put(i, availableSpaces.get(i));
        }

        // Assigning rooms based on colors
        Map<Lecture, String> lectureSpace = new HashMap<>();
        for (Map.Entry<Lecture, Integer> entry : coloringResult.getColors().entrySet()) {
            Lecture lecture = entry.getKey();
            Integer color = entry.getValue();
            Space space = spaceColor.get(color % availableSpaces.size());
            lecture.setLectureSpace(space);
            lectureSpace.put(lecture, space.getSpaceName());
        }

        return lectureSpace;
    }
}
