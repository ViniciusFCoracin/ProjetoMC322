package src.System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.Semester;
import src.Schedule.HourOfClass;
import src.Schedule.LectureSchedule;
import src.Schedule.WeekDay;

public class ScheduleAllocator {
    public static List<Lecture> createLecturesWithSchedules(List<Course> allCourses, List<Discipline> disciplineList){
        return createLectures(allCourses, disciplineList);
    }

    private static List<Lecture> createLectures(List<Course> allCourses, List<Discipline> allDisciplines){
        List<Lecture> allLectures = new ArrayList<>();
        for (Course course : allCourses){
            for (Semester semester : course.getCourseSemesters()){
                List<Lecture> semesterLectures = createSemesterLectures(semester, course, allDisciplines);
                allLectures.addAll(semesterLectures);
            }
        }

        List<Discipline> electiveDisciplines = ScheduleAllocator.filterElectiveDisciplines(allDisciplines);

        List<Lecture> electiveLectures = ScheduleAllocator.createElectiveLectures(electiveDisciplines);
        allLectures.addAll(electiveLectures);

        return allLectures;
    }

    private static List<Lecture> createSemesterLectures(Semester semester, Course course, List<Discipline> allDisciplines){
        List<Lecture> semesterLectures = new ArrayList<>();
        for (String disciplineId : semester.getDisciplineIDs()){
            Discipline discipline = findDiscipline(disciplineId, allDisciplines);
            // If the discipline is from a course semester, it must be mandatory
            discipline.setIsMandatory(true);
            List<Lecture> disciplineLectures = createDisciplineLectures(discipline, course);
            semesterLectures.addAll(disciplineLectures);
        }
        assignSemesterSchedules(semesterLectures, course);
        return semesterLectures;
    }

    private static List<Lecture> createDisciplineLectures(Discipline discipline, Course course){
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

    private static void assignSemesterSchedules(List<Lecture> semesterLectures, Course course){
        Collections.shuffle(semesterLectures);
        LectureSchedule schedule = LectureSchedule.firstSchedule(course);
        
        for (int i = 0; i < semesterLectures.size(); i++){
            Lecture lecture = semesterLectures.get(i);
            lecture.setLectureSchedule(schedule);
            if (i < semesterLectures.size() - 1)
                schedule = LectureSchedule.nextSchedule(schedule);
        }
    }

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

    public static LectureSchedule electiveSchedule(List<Integer> dayIndexes, List<Integer> hourIndexes){
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
