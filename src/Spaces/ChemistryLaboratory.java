package src.Spaces;

/**
 * Public class that represents a laboratory
 */
public class ChemistryLaboratory extends Space{
    /**
     * Public constructor for the Laboratory class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public ChemistryLaboratory(String spaceName, int spaceId, int maxCapacity){
        super(spaceName, spaceId, maxCapacity);
    }
}
