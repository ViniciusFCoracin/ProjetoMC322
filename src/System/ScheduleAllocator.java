package src.System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.Semester;
import src.Schedule.*;

/**
 * Class responsible for create lectures and allocate schedules to them.
 */
public class ScheduleAllocator {

    /**
     * Static method responsible for create lectures and allocate schedules to them
     * 
     * @param allCourses: a list of all courses
     * @param allDisciplines: a list of all disciplines (mandatory and electives)
     * @return: a list of lectures with schedules allocated, but spaces not defined yet
     */
    public static List<Lecture> createLecturesWithSchedules(List<Course> allCourses, List<Discipline> allDisciplines){
        return createLectures(allCourses, allDisciplines);
    }

    /**
     * Method responsible for create lectures with schedules, but no places yet
     * 
     * @param allCourses: a list of all courses
     * @param allDisciplines: a list of all disciplines
     * @return: a list of lectures with schedule assigned, but place set to null  
     */
    private static List<Lecture> createLectures(List<Course> allCourses, List<Discipline> allDisciplines){
        List<Lecture> allLectures = new ArrayList<>();
        for (Course course : allCourses){
            for (Semester semester : course.getCourseSemesters()){
                List<Lecture> semesterLectures = createSemesterMandatoryLectures(semester, course, allDisciplines);
                allLectures.addAll(semesterLectures);
            }
    }

        List<Discipline> electiveDisciplines = ScheduleAllocator.filterElectiveDisciplines(allDisciplines);
        List<Lecture> electiveLectures = ScheduleAllocator.createElectiveLectures(electiveDisciplines);
        allLectures.addAll(electiveLectures);

        return allLectures;
    }

    /**
     * Method responsible for create the mandatory lectures of a course's semester.
     * 
     * @param semester: the semester 
     * @param course: the course
     * @param allDisciplines: list of all the disciplines available
     * @return: a list of lectures with schedules, but space is set to null 
     */
    private static List<Lecture> createSemesterMandatoryLectures(Semester semester, Course course, List<Discipline> allDisciplines){
        List<Lecture> semesterLectures = new ArrayList<>();
        for (String disciplineID : semester.getDisciplineIDs()){
            Discipline discipline = findDiscipline(disciplineID, allDisciplines);

            // If the discipline is from a course semester, it must be mandatory
            discipline.setIsMandatory(true);

            List<Lecture> mandatoryLectures = createMandatoryDisciplineLectures(discipline, course);
            semesterLectures.addAll(mandatoryLectures);
        }

        assignSemesterSchedules(semesterLectures, course.getCourseShift());
        return semesterLectures;
    }

    /**
     * Create the number of lectures needed for a mandatory discipline. Assign schedules to them,
     * but not places yet
     * 
     * @param discipline: the discipline
     * @param course: the course that contains the discipline
     * @return: a list of lectures
     */
    private static List<Lecture> createMandatoryDisciplineLectures(Discipline discipline, Course course){
        List<Lecture> disciplineLectures = new ArrayList<>();
        int numberOfLectures = discipline.numberOfLectures();
        String professor = discipline.selectProfessor();
        char group = discipline.selectGroup();
        
        for (int i = 0; i < numberOfLectures; i++){
            Lecture lecture= new Lecture(null, discipline, null,
                                         professor, group, course);
            disciplineLectures.add(lecture);
        }
        return disciplineLectures;
    }

    /**
     * Assign schedules to the disciplines of a semester
     * 
     * @param semesterLectures: list of semester lectures, without schedules
     * @param courseShift: the shift of the course
     */
    private static void assignSemesterSchedules(List<Lecture> semesterLectures, Shift courseShift){
        Collections.shuffle(semesterLectures);
        LectureSchedule schedule = LectureSchedule.firstSchedule(courseShift);
        
        for (int i = 0; i < semesterLectures.size(); i++){
            Lecture lecture = semesterLectures.get(i);
            lecture.setLectureSchedule(schedule);
            if (i < semesterLectures.size() - 1)
                schedule = LectureSchedule.nextSchedule(schedule);
        }
    }

    private static List<Lecture> createElectiveLectures(List<Discipline> electiveDisciplines){
        List<Lecture> electiveLectures = new ArrayList<>();
        for(Discipline discipline : electiveDisciplines){
            int numberOfLectures = discipline.numberOfLectures();
            String professor = discipline.selectProfessor();
            char group = discipline.selectGroup();
            List<Integer> daysIndexes = new ArrayList<>();
            List<Integer> hoursIndexes = new ArrayList<>();
            
            for (int i = 0; i < numberOfLectures; i++){
                LectureSchedule schedule = electiveSchedule(daysIndexes, hoursIndexes);
                Lecture lecture= new Lecture(null, discipline, schedule,
                                            professor, group, null);
                electiveLectures.add(lecture);

            }
        }
        return electiveLectures;
    }

    /**
     * Method that receives a discipline ID and gives the corresponding discipline object.
     * If it doesn't find, throws an error
     * 
     * @param disciplineID: the desired discipline ID 
     * @param allDisciplines: a list of all the disciplines available
     * @return: the corresponding discipline
     */
    private static Discipline findDiscipline(String disciplineID, List<Discipline> allDisciplines){
        for (Discipline discipline : allDisciplines){
            if (discipline.getDisciplineId().equals(disciplineID))
                return discipline;
        }
        throw new Error("Discipline " + disciplineID + "not found");
    }

    private static List<Discipline> filterElectiveDisciplines(List<Discipline> allDisciplines){
        List<Discipline> electiveDisciplines = new ArrayList<>();

        for(Discipline candidateDiscipline : allDisciplines){
            if(!candidateDiscipline.getIsMandatory()){
                electiveDisciplines.add(candidateDiscipline);
            }
        }

        return electiveDisciplines;
    }

    private static LectureSchedule electiveSchedule(List<Integer> dayIndexes, List<Integer> hourIndexes){
        Random random = new Random();
        int dayIndex = random.nextInt(5);
        int hourIndex = random.nextInt(6);
        boolean didAdd = false;

        while(!didAdd){

            if(!dayIndexes.contains(dayIndex) || !hourIndexes.contains(hourIndex)){

                if(!dayIndexes.contains(dayIndex))
                    dayIndexes.add(dayIndex);

                if(!hourIndexes.contains(hourIndex))
                    hourIndexes.add(hourIndex);

                didAdd = true;

            } else {

                if(dayIndexes.contains(dayIndex))
                    dayIndex = random.nextInt(5);

                if(hourIndexes.contains(hourIndex))
                    hourIndex = random.nextInt(6);

            }
        }
        
        return new LectureSchedule(WeekDay.get(dayIndex), HourOfClass.get(hourIndex));
    }
}
