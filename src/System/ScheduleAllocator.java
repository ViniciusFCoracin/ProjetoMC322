package src.System;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

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
            
            for (int i = 0; i < numberOfLectures; i++){
                Lecture lecture= new Lecture(null, discipline, null,
                                             professor, group, null);
                electiveLectures.add(lecture);

            }
        }
        assignElectiveSchedules(electiveLectures);
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

    private static void assignElectiveSchedules(List<Lecture> electiveLectures){
        Random random = new Random();
        
        for (int i = 0; i < electiveLectures.size(); i++){
            Lecture lecture = electiveLectures.get(i);
            WeekDay day = WeekDay.get(random.nextInt(WeekDay.values().length));
            HourOfClass hour = HourOfClass.get(random.nextInt(HourOfClass.values().length));
            LectureSchedule schedule = new LectureSchedule(day, hour);
            lecture.setLectureSchedule(schedule);
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
}
