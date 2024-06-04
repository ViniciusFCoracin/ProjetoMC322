package src.Course;
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
    
    public boolean containsDiscipline(String discipline) {
        for (Semester semester : courseSemesters){
            if (semester.getDisciplines().contains(discipline))
                return true;
        }
        return false;
    }
        

    public int getSemesterOfLecture(Lecture lecture) {
        for (Semester semester : courseSemesters) {
            for (String disciplineId : semester.getDisciplines()){
                if (disciplineId.equals(lecture.getLectureDisciplineId()))
                    return semester.getSemesterPeriod();
            }
        }
        throw new IllegalArgumentException("Lecture not found in this course");
    }

    @Override
    public String toString(){
        String str = courseName + " (" + courseId + ")\n";
        for (Semester semester : this.courseSemesters)
            str += semester.toString();
        return str;
    }
}
