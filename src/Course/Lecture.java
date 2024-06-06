package src.Course;
import src.Schedule.ClassSchedule;
import src.Spaces.Space;

/**
 * Class that represents a lecture
 */
public class Lecture {
    private Space lectureSpace;
    private String lectureDisciplineId;
    private ClassSchedule lectureSchedule;
    private String professor;
    private String group;
    private String courseName;
    /**
     * Public constructor for the Lecture class
     * 
     * @param space: where the lecture occurs
     * @param disciplineId: the discipline of the lecture
     * @param schedule: day and hour of the lecture
     * @param professor: teacher of the lecture
     * @param group: lecture group
     */
    public Lecture(Space space, String disciplineId, ClassSchedule schedule, String professor, String group, String courseName){
        this.lectureSpace = space;
        this.lectureDisciplineId = disciplineId;
        this.lectureSchedule = schedule;
        this.professor = professor;
        this.group = group;
        this.courseName = courseName;
    }

    public Space getLectureSpace(){
        return this.lectureSpace;
    }

    public void setLectureSpace(Space newSpace){
        this.lectureSpace = newSpace;
    }

    public String getLectureDisciplineId(){
        return this.lectureDisciplineId;
    }

    public ClassSchedule getLectureSchedule(){
        return this.lectureSchedule;
    }

    public String getProfessor(){
        return this.professor;
    }

    public String getLectureGroup(){
        return this.group;
    }

    public String getCourseName(){
        return this.courseName;
    }
}
