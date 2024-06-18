package src.System;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.CourseRelated.Course;
import src.CourseRelated.Disciplines.Discipline;
import src.CourseRelated.LectureRelated.Lecture;
import src.GraphicInterface.Controllers.SelectionController;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;

/**
 * Singleton class that serves as model in the MVC pattern. 
 * The class is responsible for storing and updating a list of all lectures.
 */
public class LectureSelector {
	private static LectureSelector instance;

    private List<Lecture> allLectures;
    private List<Course> allCourses;
	private List<Space> allSpaces;
	private List<Discipline> allDisciplines;
	
	private LectureSelector(){
		 // does nothing, but we need this to be private
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
	
	/**
     * Generates the lists of all available
     * courses, spaces, disciplines and lectures. 
     * 
     * @param allCourses: list of all the course available
     * @param allSpaces: list of all the spaces available
     * @param allDisciplines: list of all the disciplines available
     */
	 public void loadAndFilterResources() {
		 // reads all XML files
    	 allCourses = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");
    	 allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");
    	 allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");
    	
    	 // removes the resources chosen by the user
         allSpaces = removeSelectedSpaces(SelectionController.getRemovedSpaces());
         allCourses = removeSelectedCourses(SelectionController.getRemovedCourses());
         allDisciplines = removeSelectedElectives(SelectionController.getRemovedElectives());

         AllocatorSystem system = new AllocatorSystem(allCourses, allDisciplines, allSpaces);
         allLectures = system.allocateSchedulesAndSpaces();
      }
	 
	 private List<Course> removeSelectedCourses(List<String> selectedCourses) {
		 
		 
		 List<String> resultList = new ArrayList<String>();
         for (String item : selectedCourses) {
             String[] parts = item.split(" - ", 2);
             if (parts.length > 1) {
                 resultList.add(parts[1]);
             }
         }
         selectedCourses = resultList;
	        
		 Iterator<Course> iterator = allCourses.iterator();
		 while (iterator.hasNext()) {
			 Course course = iterator.next();
			 if (selectedCourses.contains(course.getCourseName()))
				 iterator.remove();
		 }
		 return allCourses;
	 }
	 
    private List<Space> removeSelectedSpaces(List<String> selectedSpaces) {	
		Iterator<Space> iterator = allSpaces.iterator();
		while (iterator.hasNext()) {
            Space space = iterator.next();
            if (selectedSpaces.contains(space.getSpaceID()))
                iterator.remove();
        }
	    return allSpaces;
   	}
    
    private List<Discipline> removeSelectedElectives(List<String> selectedElectives) {	
		Iterator<Discipline> iterator = allDisciplines.iterator();
		while (iterator.hasNext()) {
            Discipline discipline = iterator.next();
            if (selectedElectives.contains(discipline.getDisciplineId())) 
                iterator.remove();
        }
	    return allDisciplines;
   	}
}