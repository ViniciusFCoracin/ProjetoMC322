package Spaces.Classrooms;

/**
 * Class that represents a basic classroom 
 */
public class BasicRoom extends Classroom{
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
}
