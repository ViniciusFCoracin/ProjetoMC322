package src.Course;
import java.util.List;


/**
 * Class that represents a semester of an university course
 */
public class Semester {
    private int semesterPeriod;
    private List<String> disciplineIds;

    /**
     * Public constructor for the Semester class
     * 
     * @param semesterPeriod: the number of the semester in the course
     * @param disciplines: the list of disciplines in the semester
     */
    public Semester(int semesterPeriod, List<String> disciplineIds){
        this.semesterPeriod = semesterPeriod;
        this.disciplineIds = disciplineIds;
    }

    public int getSemesterPeriod(){
        return this.semesterPeriod;
    }

    public List<String> getDisciplines(){
        return this.disciplineIds;
    }
}
