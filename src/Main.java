package src;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.Semester;
import src.Spaces.Space;
import src.Spaces.Classrooms.BasicRoom;

public class Main {

    public static void main(String[] args) throws Exception {

        Discipline newDiscipline1 = new Discipline("MC202", "202", 6, "GPT");
        Discipline newDiscipline2 = new Discipline("MC358", "358", 4, "Rezende", "Chris");
        Discipline newDiscipline3 = new Discipline("MA211", "211", 6, "Weber", "Renato");
        Discipline newDiscipline4 = new Discipline("QO321", "321", 6, "Rosana", "Rogério");
        Discipline newDiscipline5 = new Discipline("QG108", "211", 6, "Amanda", "Carlos");
          
        BasicRoom newRoom1 = new BasicRoom("Classroom 1", 1, 40);
        BasicRoom newRoom2 = new BasicRoom("Classroom 2", 2, 40);

        List<Discipline> mainDisciplineList = Arrays.asList(newDiscipline1, newDiscipline2, newDiscipline3);
        Semester newSemester1 = new Semester(2, mainDisciplineList);
        List<Semester> mainSemesterList = Arrays.asList(newSemester1);
        Course computer_science = new Course("Computer Science", 42, mainSemesterList);

        List<Discipline> secDisciplineList = Arrays.asList(newDiscipline4, newDiscipline5);
        Semester newSemester2 = new Semester(2, secDisciplineList);
        List<Semester> secSemesterList = Arrays.asList(newSemester2);
        Course chem_eng = new Course("Chemical Engineering", 33, secSemesterList);

        List<Space> mainSpaceList = Arrays.asList(newRoom1, newRoom2);
        List<Course> mainCourseList = Arrays.asList(computer_science, chem_eng);
        Map<Lecture, String> allocatedSpaces = MainSystem.allocatingSpaces(mainSpaceList, mainCourseList);
        
        for (Map.Entry<Lecture, String> entry : allocatedSpaces.entrySet()) {
            Lecture lecture = entry.getKey();
            System.out.println("Lecture: " + lecture.getLectureDisciplineId() + ", Professor: " 
            + lecture.getProfessor()  + " Day: " + lecture.getLectureSchedule().getDay() + ", Start: " + 
            lecture.getLectureSchedule().getStartHour() + ", End: " + lecture.getLectureSchedule().getEndHour() +
            ", Allocated Space: " + lecture.getLectureSpace().getSpaceName());
        }
    }
}
