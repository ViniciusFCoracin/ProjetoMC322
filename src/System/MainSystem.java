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
    public static List<Lecture> assignSchedulesAndPlaces(List<Space> availableSpaces, List<Course> courses, List<Discipline> disciplineList) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = separateDisciplinesBySpaceRequirement(disciplineList);
        Map<SpaceType, List<Space>> separatedSpaces = separateSpacesByType(availableSpaces);

        List<Lecture> lectures = new ArrayList<>();
        List<Lecture> allLectures = ClassScheduler.assignSchedules(courses, disciplineList);

        for (Map.Entry<SpaceType, List<Discipline>> entry : separatedDisciplines.entrySet()) {
            SpaceType spaceType = entry.getKey();
            List<Space> spaces = separatedSpaces.get(spaceType);

            if (spaces == null) {
                throw new Error("No available spaces for type: " + spaceType);
            }

            List<Lecture> filteredLectures = new ArrayList<>();
            for (Lecture lecture : allLectures) {
                Discipline discipline = disciplineList.stream()
                    .filter(d -> d.getDisciplineId().equals(lecture.getLectureDisciplineId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Discipline not found " + lecture.getLectureDisciplineId()));

                if (discipline.getRequiredSpaceType() == spaceType) {
                    filteredLectures.add(lecture);
                }
            }

            lectures.addAll(SpaceAllocator.assignPlaces(spaces, filteredLectures));
        }

        return lectures;
    }

    private static Map<SpaceType, List<Discipline>> separateDisciplinesBySpaceRequirement(List<Discipline> disciplineList) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = new HashMap<>();
        for (Discipline discipline : disciplineList) {
            SpaceType requiredSpaceType = discipline.getRequiredSpaceType();
            separatedDisciplines.computeIfAbsent(requiredSpaceType, k -> new ArrayList<>()).add(discipline);
        }
        return separatedDisciplines;
    }

    private static Map<SpaceType, List<Space>> separateSpacesByType(List<Space> spaceList) {
        Map<SpaceType, List<Space>> separatedSpaces = new HashMap<>();
        for (Space space : spaceList) {
            SpaceType spaceType = space.getSpaceType();
            separatedSpaces.computeIfAbsent(spaceType, k -> new ArrayList<>()).add(space);
        }
        return separatedSpaces;
    }
    
    private static List<Space> removeSelectedSpaces(List<Space> allSpaces, List<String> selectedSpaces) {
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
        allSpaces = removeSelectedSpaces(allSpaces, SelectionController.getRemovedSpaces());
        System.out.println("Available spaces after selection:");
        System.out.println(allSpaces);
        
        

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
