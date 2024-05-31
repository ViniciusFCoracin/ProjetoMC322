import java.util.List;

public class Course {
    public String courseName;
    public int courseId;
    public List<Semester> semesters;

    public Course(String courseName, int courseId, List<Semester> semesters){
        this.courseName = courseName;
        this.courseId = courseId;
        this.semesters = semesters;
    }
}
