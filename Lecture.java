import Spaces.*;

public class Lecture {
    private Space lectureSpace;
    private Discipline lectureDiscipline;
    private ClassSchedule lectureSchedule;
    private String professor;

    public Lecture(Space space, Discipline discipline, ClassSchedule schedule, String professor){
        this.lectureSpace = space;
        this.lectureDiscipline = discipline;
        this.lectureSchedule = schedule;
        this.professor = professor;
    }
}
