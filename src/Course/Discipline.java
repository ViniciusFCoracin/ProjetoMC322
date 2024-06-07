package src.Course;

import java.util.Arrays;
import java.util.List;

import src.Spaces.SpaceType;

/**
 * Class that represents a university discipline
 */
public class Discipline {
    private int countProfessors = 0;
    private int countGroups = 0;
    private String disciplineName;
    private String disciplineId;
    private int disciplineCredits;
    private SpaceType requiredSpace;
    private List<String> professors;

    /**
     * Public constructor for Discipline class
     * 
     * @param disciplineName: the name of the discipline
     * @param id: the id of the discipline
     * @param credits: the nummber of credits of the discipline
     * @param professors: the professors of the discipline
     */
    public Discipline(String disciplineName, String id, int credits, SpaceType requiredSpace, String... professors){
        this.disciplineName = disciplineName;
        this.disciplineId = id;
        this.disciplineCredits = credits;
        this.requiredSpace = requiredSpace;
        this.professors = Arrays.asList(professors);
    }

    /**
     * Public constructor for Discipline class
     * 
     * @param disciplineName: the name of the discipline
     * @param id: the id of the discipline
     * @param credits: the number of credits of the discipline
     * @param professors: the professors of the discipline
     */
    public Discipline(String disciplineName, String id, int credits, SpaceType requiredSpace, List<String> professors){
        this.disciplineName = disciplineName;
        this.disciplineId = id;
        this.disciplineCredits = credits;
        this.requiredSpace = requiredSpace;
        this.professors = professors;
    }

    public String getDisciplineName(){
        return this.disciplineName;
    }

    public String getDisciplineId(){
        return this.disciplineId;
    }

    public int getDisciplineCredits(){
        return this.disciplineCredits;
    }

    public List<String> getProfessors(){
        return this.professors;
    }

    public SpaceType getRequiredSpaceType(){
        return this.requiredSpace;
    }

    public String selectProfessor(){
        if (countProfessors >= professors.size())
            countProfessors = 0;
        return professors.get(countProfessors++);
    }

    public char selectGroup(){
        return (char) ('A' + countGroups++);
    }

    /**
     * Calculate the discipline number of lectures in a week
     * 
     * @return: the number of lectures in a week
     */
    public int numberOfLectures(){
        return this.disciplineCredits / 2;
    }

    @Override
    public String toString(){
        return (this.disciplineName + " (" + this.disciplineId + ").\n" +
                "Credits: " + this.disciplineCredits + "\n" +
                "Professors: " + String.join(", ", this.professors) + "\n");
    }
}
