package src.Spaces;

/**
 * A class that represents an auditorium
 */
public class Auditorium extends Space{
    SpaceType spaceType = SpaceType.AUDITORIUM;

    /**
     * Public constructor for the Auditorium class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public Auditorium(String spaceName, int spaceId, int maxCapacity){
        super(spaceName, spaceId, maxCapacity);
    }
}
