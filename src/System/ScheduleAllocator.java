package src.System;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.Semester;
import src.Schedule.LectureSchedule;

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
        return allLectures;
    }

    private static List<Lecture> createSemesterLectures(Semester semester, Course course, List<Discipline> allDisciplines){
        List<Lecture> semesterLectures = new ArrayList<>();
        for (String disciplineId : semester.getDisciplineIDs()){
            Discipline discipline = findDiscipline(disciplineId, allDisciplines);
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
}
