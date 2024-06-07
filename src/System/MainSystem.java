package src.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.GraphicInterface.Controllers.SelectionController;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;
import src.Spaces.SpaceType;

public class MainSystem {
    /*private static List<Space> removeSelectedSpaces(List<Space> allSpaces, List<String> selectedSpaces) {
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
        List<Course> allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");

        System.out.println("Available spaces before selection:");
        System.out.println(allSpaces);
        System.out.println("==============================================================================");
        //allSpaces = removeSelectedSpaces(allSpaces, SelectionController.getRemovedSpaces());
        System.out.println("Available spaces after selection:");
        System.out.println(allSpaces);
        
        //List<Lecture> allLectures = MainSystem.assignSchedulesAndPlaces(allSpaces, allCourses, allDisciplines);
        List<Lecture> allLectures = ClassScheduler.createLecturesWithSchedules(allCourses, allDisciplines);

        for (Lecture lecture : allLectures) {
            String output = ("Lecture: " + lecture.getLectureDisciplineId() + ", Professor " + lecture.getProfessor() + "\n" +
                             "Day: " + lecture.getLectureSchedule().getDay() + ", " + lecture.getLectureSchedule().getHourOfClass() + "\n" +
                             "Place: " + lecture.getLectureSpace() + "\n" + "Group: " 
                             + lecture.getLectureGroup() +"\n" + lecture.getCourseName() + "\n");
           System.out.println(output);
        }
    }
    */
}
