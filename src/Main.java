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
//import src.Readers.CourseRelatedReaders.CoursesFileReader;
//import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
//import src.Readers.SpaceRelatedReaders.SpacesFileReader;

public class Main {

    public static void main(String[] args) throws Exception {
        //List<Discipline> allDisciplines = DisciplinesFileReader.getInstance().readFile("src/XML/disciplines.xml");
        //List<Space> allSpaces = SpacesFileReader.getInstance().readFile("src/XML/spaces.xml");

        // O leitor de cursos ainda não está funcionando 
        //List<Course> allCursos = CoursesFileReader.getInstance().readFile("src/XML/courses.xml");
        //for (Course course : allCursos)
        //    System.out.println(course);

        Discipline newDiscipline1 = new Discipline("MC202", "MC202", 6, "GPT");
        Discipline newDiscipline2 = new Discipline("MC358", "MC358", 4, "Rezende", "Chris");
        Discipline newDiscipline3 = new Discipline("MA211", "MA211", 6, "Weber", "Renato");
        Discipline newDiscipline4 = new Discipline("QO321", "QO321", 6, "Rosana", "Rogério");
        Discipline newDiscipline5 = new Discipline("QG108", "QG108", 6, "Amanda", "Carlos");
          
        BasicRoom newRoom1 = new BasicRoom("Classroom 1", 1, 40);
        BasicRoom newRoom2 = new BasicRoom("Classroom 2", 2, 40);

        List<Discipline> mainDisciplineList = Arrays.asList(newDiscipline1, newDiscipline2, newDiscipline3,
                                                            newDiscipline4, newDiscipline5);
        List<String> disciplineIds1 = Arrays.asList("MC202", "MC358", "MA211");
        Semester newSemester1 = new Semester(2, disciplineIds1);
        List<Semester> mainSemesterList = Arrays.asList(newSemester1);
        Course computer_science = new Course("Computer Science", 42, mainSemesterList);

        List<String> disciplineIds2 = Arrays.asList("QO321", "QG108");
        Semester newSemester2 = new Semester(2, disciplineIds2);
        List<Semester> secSemesterList = Arrays.asList(newSemester2);
        Course chem_eng = new Course("Chemical Engineering", 33, secSemesterList);

        List<Space> mainSpaceList = Arrays.asList(newRoom1, newRoom2);
        List<Course> mainCourseList = Arrays.asList(computer_science, chem_eng);
        Map<Lecture, String> allocatedSpaces = MainSystem.allocatingSpaces(mainSpaceList, mainCourseList, mainDisciplineList);
        
        for (Map.Entry<Lecture, String> entry : allocatedSpaces.entrySet()) {
            Lecture lecture = entry.getKey();
            System.out.println("Lecture: " + lecture.getLectureDisciplineId() + ", Professor: " 
            + lecture.getProfessor()  + " Day: " + lecture.getLectureSchedule().getDay() + ", Start: " + 
            lecture.getLectureSchedule().getStartHour() + ", End: " + lecture.getLectureSchedule().getEndHour() +
            ", Allocated Space: " + lecture.getLectureSpace().getSpaceName());
        }
    }
}
