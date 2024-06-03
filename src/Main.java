package src;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import src.Course.Discipline;
import src.Course.Lecture;
import src.Schedule.ClassSchedule;
import src.Schedule.WeekDay;
import src.Spaces.Space;
import src.Spaces.Classrooms.BasicRoom;

public class Main {

    public static void main(String[] args) {

        BasicRoom newRoom1 = new BasicRoom("Classroom 1", 1, 40);
        List<String> professors1 = List.of("GPT");
        Discipline newDiscipline1 = new Discipline("MC202", "202", 6, professors1);
        ClassSchedule newSchedule1 = new ClassSchedule(WeekDay.MONDAY, 19, 21);

        BasicRoom newRoom2 = new BasicRoom("Classroom 2", 2, 40);
        List<String> professors2 = List.of("Rezende");
        Discipline newDiscipline2 = new Discipline("MC358", "358", 4, professors2);

        List<String> professors3 = List.of("Weber");
        Discipline newDiscipline3 = new Discipline("MA211", "211", 6, professors3);
        ClassSchedule newSchedule3 = new ClassSchedule(WeekDay.FRIDAY, 21, 23);
        
        Lecture newLecture1 = new Lecture(newRoom1, newDiscipline1, newSchedule1, "GPT");
        Lecture newLecture2 = new Lecture(newRoom1, newDiscipline2, newSchedule1, "Rezende");
        Lecture newLecture3 = new Lecture(newRoom1, newDiscipline3, newSchedule3, "Weber");

        List<Space> mainSpaceList = Arrays.asList(newRoom1, newRoom2);
        Map<Lecture, String> allocatedSpaces = MainSystem.allocatingSpaces(mainSpaceList, newLecture1, newLecture2, newLecture3);
        
        for (Map.Entry<Lecture, String> entry : allocatedSpaces.entrySet()) {
            Lecture lecture = entry.getKey();
            String space = entry.getValue();
            System.out.println("Lecture: " + lecture.getLectureDiscipline().getDisciplineName() + ", Professor: " 
            + lecture.getProfessor()  + " Day: " + lecture.getLectureSchedule().getDay() + ", Start: " + 
            lecture.getLectureSchedule().getStartHour() + ", End: " + lecture.getLectureSchedule().getEndHour() +
            ", Allocated Space: " + space);
        }
    }
}
