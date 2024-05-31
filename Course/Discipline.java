package Course;

/**
 * Class that represents a university discipline
 */
public class Discipline {
    private String disciplineName;
    private int disciplineId;
    private int disciplineCredits;

    /**
     * Public constructor for Discipline class
     * 
     * @param disciplineName: the name of the discipline
     * @param id: the id of the discipline
     * @param credits: the nummber of credits of the discipline
     */
    public Discipline(String disciplineName, int id, int credits){
        this.disciplineName = disciplineName;
        this.disciplineId = id;
        this.disciplineCredits = credits;
    }

    public String getDisciplineName(){
        return this.disciplineName;
    }

    public int getDisciplineId(){
        return this.disciplineId;
    }

    public int getDisciplineCredits(){
        return this.disciplineCredits;
    }
}
