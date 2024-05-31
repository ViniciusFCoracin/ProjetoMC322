import java.util.List;

public class Semester {
    private int semesterPeriod;
    private List<Discipline> disciplines;

    public Semester(int semesterPeriod, List<Discipline> disciplines){
        this.semesterPeriod = semesterPeriod;
        this.disciplines = disciplines;
    }
}
