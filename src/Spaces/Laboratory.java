package src.Spaces;

/**
 * Public class that represents a laboratory
 */
public class Laboratory extends Space{
    private SpaceType spaceType = SpaceType.PHYSICS_LABORATORY;

    /**
     * Public constructor for the Laboratory class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public Laboratory(String spaceName, int spaceId, int maxCapacity, InstituteAbbr institute){
        super(spaceName, spaceId, maxCapacity, institute);
    }

    @Override
    public SpaceType getSpaceType() {
        return spaceType;
    }
}
