package src;

import java.util.ArrayList;
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
import src.Course.Shift;
import src.Schedule.ClassSchedule;
import src.Schedule.HourOfClass;
import src.Schedule.WeekDay;

public class ClassScheduler {

    public static List<Lecture> assignSchedules(List<Course> courses, List<Discipline> disciplineList) {
        Graph<String, DefaultEdge> disciplinesGraph = createDisciplinesGraph(courses, disciplineList);
        Coloring<String> coloring = coloringDisciplinesGraph(disciplinesGraph);
        return createLectures(courses, disciplineList, disciplinesGraph, coloring);
    }

    private static Graph<String, DefaultEdge> createDisciplinesGraph(List<Course> courses, List<Discipline> disciplineList) {
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

    private static Coloring<String> coloringDisciplinesGraph(Graph<String, DefaultEdge> graph) {
        GreedyColoring<String, DefaultEdge> coloring = new GreedyColoring<>(graph);
        return coloring.getColoring();
    }

    private static List<Lecture> createLectures(List<Course> courses, List<Discipline> disciplines, Graph<String, DefaultEdge> graph, Coloring<String> coloring) {
        List<Lecture> lectures = new ArrayList<>();
        Map<String, Integer> professorIndexMap = new HashMap<>();
        char groupChar = 'A';

        for (Map.Entry<String, Integer> entry : coloring.getColors().entrySet()) {
            String disciplineId = entry.getKey();
            Integer color = entry.getValue();

            Discipline discipline = disciplines.stream().filter(d -> d.getDisciplineId().equals(disciplineId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Discipline not found " + disciplineId));

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

            for (Course course : courses) {
                for (Semester semester : course.getCourseSemesters()) {
                    if (semester.getDisciplines().contains(disciplineId)) {
                        int totalCredits = discipline.getDisciplineCredits();
                        int lectureHours = 2;
                        int numberOfLectures = totalCredits / lectureHours;

                        // Allocating lectures according to the number of lectures necessary
                        HourOfClass[] possibleHours;
                        if (course.getCourseShift() == Shift.FULL_TIME) {
                            possibleHours = new HourOfClass[]{
                                HourOfClass.EigthAM_TenAM,
                                HourOfClass.TenAM_Midday,
                                HourOfClass.TwoPM_FourPM,
                                HourOfClass.FourPM_SixPM
                            };
                        } else {
                            possibleHours = new HourOfClass[]{
                                HourOfClass.SevenPM_NinePM,
                                HourOfClass.NinePM_ElevenPM
                            };
                        }

                        for (int i = 0; i < numberOfLectures; i++) {
                            int weekDayIndex = (color + i) % WeekDay.values().length;
                            HourOfClass hourOfClass = possibleHours[(color + i) % possibleHours.length];

                            ClassSchedule schedule = new ClassSchedule(WeekDay.values()[weekDayIndex], hourOfClass);
                            lectures.add(new Lecture(null, disciplineId, schedule, instructor, group, course.getCourseName()));
                        }
                    }
                }
            }
        }
        return lectures;
    }
}
