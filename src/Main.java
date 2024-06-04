package src;

import java.util.List;
import java.util.Map;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Spaces.Space;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Discipline> allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");
        List<Space> allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");
        List<Course> allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");

        Map<Lecture, String> allocatedSpaces = MainSystem.allocatingSpaces(allSpaces, allCourses, allDisciplines);

        for (Map.Entry<Lecture, String> entry : allocatedSpaces.entrySet()) {
            Lecture lecture = entry.getKey();
            String output = ("Lecture: " + lecture.getLectureDisciplineId() + ", Professor " + lecture.getProfessor() + "\n" +
                             "Day: " + lecture.getLectureSchedule().getDay() + ", " + lecture.getLectureSchedule().getStartHour() + "h - " + lecture.getLectureSchedule().getEndHour() + "h\n" +
                             "Place: " + lecture.getLectureSpace() + "\n");
           System.out.println(output);
        }
    }
}
