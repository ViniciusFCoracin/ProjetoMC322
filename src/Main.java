package src;

import java.util.List;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;
import src.System.MainSystem;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Discipline> allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");
        List<Space> allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");
        List<Course> allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");

        List<Lecture> allLectures = MainSystem.assignSchedulesAndPlaces(allSpaces, allCourses, allDisciplines);

        for (Lecture lecture : allLectures) {
            String output = ("Lecture: " + lecture.getLectureDisciplineId() + ", Professor " + lecture.getProfessor() + "\n" +
                             "Day: " + lecture.getLectureSchedule().getDay() + ", " + lecture.getLectureSchedule().getHourOfClass() + "\n" +
                             "Place: " + lecture.getLectureSpace() + "\n" + "Group: " 
                             + lecture.getLectureGroup() +"\n" + lecture.getCourseName() + "\n");
           System.out.println(output);
        }
    }
}
