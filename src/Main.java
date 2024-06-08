package src;

import java.util.List;
import java.util.Collections;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.LectureComparator;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;
import src.System.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Discipline> allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");
        List<Space> allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");
        List<Course> allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");

        List<Lecture> allLectures = null;
        while (allLectures == null){
            try {
                allLectures = ScheduleAllocator.createLecturesWithSchedules(allCourses, allDisciplines);
                SpaceAllocator.assignPlaces(allSpaces, allLectures, allDisciplines);
            }
            catch (Error e){
                allLectures = null;
                for (Discipline discipline : allDisciplines)
                    discipline.resetGroup();
            }
        }

        Collections.sort(allLectures, new LectureComparator());

        for (Lecture lecture : allLectures) {
            String output = ("Lecture: " + lecture.getLectureDiscipline().getDisciplineId() + ", Professor " + lecture.getProfessor() + "\n" +
                             "Day: " + lecture.getLectureSchedule().getDay() + ", " + lecture.getLectureSchedule().getHourOfClass() + "\n" +
                             "Place: " + lecture.getLectureSpace() + "\n" + "Group: " 
                             + lecture.getLectureGroup() +"\n" + lecture.getCourse().getCourseName() + ", " + lecture.getCourse().getDisciplineSemester(lecture.getLectureDiscipline()) + " semester\n");
           System.out.println(output);
        }
    }
}
