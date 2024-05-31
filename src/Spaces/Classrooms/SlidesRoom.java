package src.Spaces.Classrooms;

/**
 * Class that represents a classroom with a slide projector
 */
public class SlidesRoom extends Classroom{
    /**
     * Public constructor for the SlidesRoom class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public SlidesRoom(String spaceName, int spaceId, int maxCapacity){
        super(spaceName, spaceId, maxCapacity);
    }
}
