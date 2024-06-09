package src.System;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.Errors.*;
import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.LectureComparator;
import src.Spaces.Space;
import src.Spaces.SpaceType;

public class AllocatorSystem {
    private List<Course> allCourses;
    private List<Discipline> allDisciplines;
    private List<Space> allSpaces;
    private HashMap<SpaceType, Integer> errorsPerType;

    public AllocatorSystem(List<Course> allCourses, List<Discipline> allDisciplines, List<Space> allSpaces){
        this.allCourses = allCourses;
        this.allDisciplines = allDisciplines;
        this.allSpaces = allSpaces;

        this.errorsPerType = new HashMap<>();
        for (Space space : allSpaces){
            SpaceType spaceType = space.getSpaceType();
            if (!errorsPerType.containsKey(spaceType))
                errorsPerType.put(spaceType, 0);
        }
    }

    public List<Lecture> allocateSchedulesAndSpaces(){
        List<Lecture> allLectures = null;
        boolean mustContinue = true;
        while (mustContinue) {
            try {
                allLectures = ScheduleAllocator.createLecturesWithSchedules(allCourses, allDisciplines);
                SpaceAllocator.assignPlaces(allSpaces, allLectures, allDisciplines);
                mustContinue = false;
            }
            catch (InsuficientSpacesError e){
                Pattern INSUFFICIENT_SPACES = Pattern.compile("Insufficient spaces of type (.+)");
                Matcher matcher1 = INSUFFICIENT_SPACES.matcher(e.getMessage());
                if (matcher1.find()){
                    allLectures = null;
                    String spaceTypeString = matcher1.group(1);
                    SpaceType spaceType = SpaceType.valueOf(spaceTypeString);
                    mustContinue = continueTheLoop(spaceType);
                    for (Discipline discipline : allDisciplines)
                        discipline.resetGroup();
                }
            }
            catch (NoSpacesAvailableError e){
                mustContinue = false;
                System.err.println(e.getMessage());
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

        return allLectures;
    }

    private boolean continueTheLoop(SpaceType spaceType){
        int errorsInThisType = errorsPerType.get(spaceType);
        errorsPerType.put(spaceType, errorsInThisType++);

        if (errorsInThisType >= 10)
            return false;
        else
            return true;
    }
}
