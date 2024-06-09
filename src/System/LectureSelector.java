package src.System;

import java.util.Iterator;
import java.util.List;

import src.Course.Course;
import src.Course.Discipline;
import src.GraphicInterface.Controllers.SelectionController;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;

public class LectureSelector {
    
    public static void startDistribution() {
    	List<Course> allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");
        List<Space> allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");
        List<Discipline> allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");

        System.out.println("Available spaces before selection:");
        System.out.println(allSpaces);
        allSpaces = removeSelectedSpaces(allSpaces, SelectionController.getRemovedSpaces());
        System.out.println("Available spaces after selection:");
        System.out.println(allSpaces);        
        
        System.out.println("");
        
        System.out.println("Available courses before selection:");
        for(Course course : allCourses) {
        	System.out.print(course.getCourseName()+ ", ");
        }
        allCourses= removeSelectedCourses(allCourses, SelectionController.getRemovedCourses());
        System.out.println("\nAvailable courses after selection:");
        for(Course course : allCourses) {
        	System.out.print(course.getCourseName()+ ", ");
        }

        AllocatorSystem system = new AllocatorSystem(allCourses, allDisciplines, allSpaces);
        system.allocateSchedulesAndSpaces();
    }

    private static List<Space> removeSelectedSpaces(List<Space> allSpaces, List<String> selectedSpaces) {	
		Iterator<Space> iterator = allSpaces.iterator();
		while (iterator.hasNext()) {
            Space space = iterator.next();
            if (selectedSpaces.contains(space.getSpaceName()))
                iterator.remove();
        }
	    return allSpaces;
	}
	    
    private static List<Course> removeSelectedCourses(List<Course> allCourses, List<String> selectedCourses) {
      	Iterator<Course> iterator = allCourses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (selectedCourses.contains(course.getCourseName()))
                iterator.remove();
        }
        return allCourses;
   	}
}
