package src.Spaces.Classrooms;

import src.Spaces.InstituteAbbr;
import src.Spaces.SpaceType;

/**
 * Class that represents a classroom with a slide projector
 */
public class SlidesRoom extends Classroom{
    private SpaceType spaceType = SpaceType.SLIDES_ROOM;

    /**
     * Public constructor for the SlidesRoom class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public SlidesRoom(String spaceName, int spaceId, int maxCapacity, InstituteAbbr institute){
        super(spaceName, spaceId, maxCapacity, institute);
    }

    @Override
    public SpaceType getSpaceType() {
        return spaceType;
    }
}
