package src.System;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.CourseRelated.Course;
import src.CourseRelated.Discipline;
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
     */
	 public void loadAndFilterResources() {
		 // reads all XML files
    	 allCourses = CoursesFileReader.getInstance().readFile("XML/courses.xml");
    	 allSpaces = SpacesFileReader.getInstance().readFile("XML/spaces.xml");
    	 allDisciplines = DisciplinesFileReader.getInstance().readFile("XML/disciplines.xml");
    	
    	 // removes the resources chosen by the user
         allSpaces = removeSelectedSpaces(SelectionController.getRemovedSpaces());
         allCourses = removeSelectedCourses(SelectionController.getRemovedCourses());
         allDisciplines = removeSelectedElectives(SelectionController.getRemovedElectives());

         AllocatorSystem system = new AllocatorSystem(allCourses, allDisciplines, allSpaces);
         allLectures = system.allocateSchedulesAndSpaces();
      }
	 
	/**
	 * Remove the courses the user chose to remove
	 * @param selectedCourses: list of the selected courses
	 * @return: the courses that were not removed
	 */
	private List<Course> removeSelectedCourses(List<String> selectedCourses) {
		 List<String> resultList = new ArrayList<>();
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
	
	/**
	 * Remove the spaces the user chose to remove
	 * @param selectedSpaces: list of the selected spaces
	 * @return: the spaces that were not removed
	 */
    private List<Space> removeSelectedSpaces(List<String> selectedSpaces) {
        allSpaces.removeIf(space -> selectedSpaces.contains(space.getSpaceID()));
	    return allSpaces;
   	}
    
	/**
	 * Remove the electives the user chose to remove
	 * @param selectedElectives: list of the selected disciplines
	 * @return: the elective disciplines that were not removed
	 */
    private List<Discipline> removeSelectedElectives(List<String> selectedElectives) {
        allDisciplines.removeIf(discipline -> selectedElectives.contains(discipline.getDisciplineId()));
	    return allDisciplines;
   	}
}