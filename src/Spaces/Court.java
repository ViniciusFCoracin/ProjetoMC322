package src.Spaces;

/**
 * A class that represents a court
 */
public class Court extends Space {
    private SpaceType spaceType = SpaceType.COURT;

    /**
     * Public constructor for the Court class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public Court(String spaceName, int spaceId, int maxCapacity){
        super(spaceName, spaceId, maxCapacity);
    }

    @Override
    public SpaceType getSpaceType() {
        return spaceType;
    }
}
