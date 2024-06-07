package src.Course;

import java.util.List;

/**
 * A class that represents an university course
 */
public class Course {
    private String courseName;
    private int courseId;
    private Shift courseShift;
    private List<Semester> courseSemesters;

    /**
     * Public constructor for Course class
     * 
     * @param courseName: the name of the course
     * @param courseId: the id of the course
     * @param semesters: list of the course semesters
     */
    public Course(String courseName, int courseId, Shift courseShift, List<Semester> semesters){
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseShift = courseShift;
        this.courseSemesters = semesters;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public int getCourseId(){
        return this.courseId;
    }

    public Shift getCourseShift(){
        return this.courseShift;
    }

    public List<Semester> getCourseSemesters(){
        return this.courseSemesters;
    }

    @Override
    public String toString(){
        String str = courseName + " (" + courseId + ")\n";
        for (Semester semester : this.courseSemesters)
            str += semester.toString();
        return str;
    }
}
