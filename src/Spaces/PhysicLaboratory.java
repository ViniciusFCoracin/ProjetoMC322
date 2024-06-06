package src.Spaces;

/**
 * Public class that represents a laboratory
 */
public class PhysicLaboratory extends Space{
    SpaceType type = SpaceType.PHYSIC_LABORATORY;

    /**
     * Public constructor for the Laboratory class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public PhysicLaboratory(String spaceName, int spaceId, int maxCapacity){
        super(spaceName, spaceId, maxCapacity);
    }
}
