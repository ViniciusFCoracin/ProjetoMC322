package src.Spaces.Classrooms;

import src.Spaces.SpaceType;

/**
 * Class that represents a basic classroom 
 */
public class BasicRoom extends Classroom{
    private SpaceType spaceType = SpaceType.BASIC_ROOM;

    /**
     * Public constructor for the BasicRoom class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public BasicRoom(String spaceName, int spaceId, int maxCapacity){
        super(spaceName, spaceId, maxCapacity);
    }

    @Override
    public SpaceType getSpaceType() {
        return spaceType;
    }
}
