package src.Course;

import java.util.List;

import src.Spaces.InstituteAbbr;
import src.Spaces.SpaceType;

/**
 * Class that represents a university discipline
 */
public class Discipline {
    private int counterProfessors = 0;
    private int counterGroups = 0;
    private String disciplineName;
    private String disciplineId;
    private int disciplineCredits;
    private SpaceType requiredSpace;
    private List<String> professors;
    private boolean isMandatory;
    private List<InstituteAbbr> possibleInstitutes; 

    /**
     * Public constructor for Discipline class
     * 
     * @param disciplineName: the name of the discipline
     * @param id: the id of the discipline
     * @param credits: the number of credits of the discipline
     * @param requiredSpace: the required type of space
     * @param professors: list of the discipline professors
     */
    public Discipline(String disciplineName, String id, int credits, SpaceType requiredSpace, List<String> professors){
        this.disciplineName = disciplineName;
        this.disciplineId = id;
        this.disciplineCredits = credits;
        this.requiredSpace = requiredSpace;
        this.professors = professors;
        this.isMandatory = false;
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

    public boolean getIsMandatory(){
        return this.isMandatory;
    }

    public void setIsMandatory(boolean newObligation){
        this.isMandatory = newObligation;
    }

    public List<InstituteAbbr> getPossibleInstitutes(){
        return this.possibleInstitutes;
    }

    /**
     * Method that selects a professor in the professor's list. Each time this
     * method is called, the next professor in the list is selected. When the list
     * runs out of professors, the first one is selected again.
     * 
     * @return: a professor in the professor's list
     */
    public String selectProfessor(){
        if (counterProfessors >= professors.size())
            counterProfessors = 0;
        return professors.get(counterProfessors++);
    }

    /**
     * Method that keeps tracking the groups of the discipline. The first group
     * of the discipline receives the ID "A", the next one "B", and so on.
     * 
     * @return: The group's id of discipline
     */
    public char selectGroup(){
        return (char) ('A' + this.counterGroups++);
    }

    public void resetGroup(){
        this.counterGroups = 0;
    }

    /**
     * Calculates the discipline number of lectures in a week. Here,
     * we supose that every discipline has an even number of credits,
     * and each lecture has 2 hours of duration.
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
