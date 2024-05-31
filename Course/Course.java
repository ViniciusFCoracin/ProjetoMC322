package Course;
import java.util.List;

/**
 * A class that represents an university course
 */
public class Course {
    public String courseName;
    public int courseId;
    public List<Semester> courseSemesters;

    /**
     * Public constructor for Course class
     * 
     * @param courseName: the name of the course
     * @param courseId: the id of the course
     * @param semesters: list of the course semesters
     */
    public Course(String courseName, int courseId, List<Semester> semesters){
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseSemesters = semesters;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public int getCourseId(){
        return this.courseId;
    }

    public List<Semester> getCourseSemesters(){
        return this.courseSemesters;
    }
}
