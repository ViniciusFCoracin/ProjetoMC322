package src.Course;
import java.util.List;


/**
 * Class that represents a semester of an university course
 */
public class Semester {
    private int semesterPeriod;
    private List<Discipline> disciplines;

    /**
     * Public constructor for the Semester class
     * 
     * @param semesterPeriod: the number of the semester in the course
     * @param disciplines: the list of disciplines in the semester
     */
    public Semester(int semesterPeriod, List<Discipline> disciplines){
        this.semesterPeriod = semesterPeriod;
        this.disciplines = disciplines;
    }

    public int getSemesterPeriod(){
        return this.semesterPeriod;
    }

    public List<Discipline> getDisciplines(){
        return this.disciplines;
    }
}