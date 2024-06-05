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
     * @param disciplineList: the list of the disciplines that will be availble at the University
     * @return: returns a list of lectures with a discipline, professor, schedule and a non-initialized space
     */
    public static List<Lecture> setSchedule(List<Course> courses, List<Discipline> disciplineList) {
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

        // Coloring graphs
        GreedyColoring<String, DefaultEdge> coloring = new GreedyColoring<>(graph);
        Coloring<String> coloringResult = coloring.getColoring();

        // Creating lectures with allocated schedules
        List<Lecture> lectures = new ArrayList<>();
        Map<String, Integer> professorIndexMap = new HashMap<>();
        char groupChar = 'A';

        
        for (Map.Entry<String, Integer> entry : coloringResult.getColors().entrySet()) {
            String disciplineId = entry.getKey();
            Integer color = entry.getValue();

            Discipline discipline = disciplineList.stream().filter(d -> d.getDisciplineId().equals(disciplineId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Discipline not found " + disciplineId));

            // Calculating the total number of lectures of given discipline
            int totalCredits = discipline.getDisciplineCredits();
            int lectureHours = 2;
            int numberOfLectures = totalCredits / lectureHours;

            // Selecting a professor of the professor list of the discipline
            List<String> instructors = discipline.getProfessors();

            String group = String.valueOf(groupChar);
            groupChar++;

            if (instructors.isEmpty()) {
                throw new IllegalArgumentException("The discipline " + discipline.getDisciplineName() + " doesn't have a professor.");
            }

            int instructorIndex = professorIndexMap.getOrDefault(disciplineId, 0);
            String instructor = instructors.get(instructorIndex % instructors.size());
            // Updating the index to be accessed of the professor list of the discipline
            professorIndexMap.put(disciplineId, (instructorIndex + 1) % instructors.size());         

            // Allocating lectures according to the number of lectures necessary
            for (int i = 0; i < numberOfLectures; i++) {
                int weekDayIndex = (color + i) % WeekDay.values().length;
                int startHour = 8 + (color + i) * 2;
                int endHour = startHour + 2;

                ClassSchedule schedule = new ClassSchedule(WeekDay.values()[weekDayIndex], startHour, endHour);
    
                lectures.add(new Lecture(null, disciplineId, schedule, instructor, group));
            }
        }
    
        return lectures;
    }
    
    /**
     * This method is responsible for setting the lectures their space. The possible issue is that two lectures cannot have the same room, if the have the same schedule.
     * @param availableSpaces: a list of avaible spaces for the lectures
     * @param courses: a list of courses that the university has as avaible
     * @param disciplineList: the list of the disciplines that will be availble at the University
     * @return: returns a Map<Lecture, String> in which we can access the lecture and all it's information (that now shall be initialized)
     */
    public static Map<Lecture, String> allocatingSpaces(List<Space> availableSpaces, List<Course> courses, List<Discipline> disciplineList){
        // Calls the setSchedule method to create a list of lectures
        List<Lecture> lectures = MainSystem.setSchedule(courses, disciplineList);
        
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
