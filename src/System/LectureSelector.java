package src.System;

import java.util.Iterator;
import java.util.List;
import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.GraphicInterface.Controllers.SelectionController;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;

public class LectureSelector {

    private List<Lecture> allLectures;
    private List<Course> allCourses;
	private List<Space> allSpaces;
	private List<Discipline> allDisciplines;

	private static LectureSelector instance;
	
	private LectureSelector(){
		
	}
	
	public static LectureSelector getInstance(){
		if(instance == null) {
			instance= new LectureSelector();
		}
		return instance;
	}
	
	public List<Lecture> getAllLectures() {
		return allLectures;
	}
	
	public List<Course> getAllCourses() {
		return allCourses;
	}
	
	 public void startDistribution() {
	    	allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");
	    	allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");
	    	allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");
	    	
	        allSpaces = removeSelectedSpaces(allSpaces, SelectionController.getRemovedSpaces());
	        allCourses= removeSelectedCourses(allCourses, SelectionController.getRemovedCourses());

	        AllocatorSystem system = new AllocatorSystem(allCourses, allDisciplines, allSpaces);
	        allLectures = system.allocateSchedulesAndSpaces();
	    }

    private static List<Space> removeSelectedSpaces(List<Space> allSpaces, List<String> selectedSpaces) {	
		Iterator<Space> iterator = allSpaces.iterator();
		while (iterator.hasNext()) {
            Space space = iterator.next();
            if (selectedSpaces.contains(space.getSpaceID()))
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