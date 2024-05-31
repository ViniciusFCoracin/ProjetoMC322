package Spaces.Classrooms;
import Spaces.Space;

/**
 * Abstract class that represents a Classrooom
 */
public abstract class Classroom extends Space {
    /**
     * Public constructor for the Classroom class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public Classroom(String spaceName, int spaceId, int maxCapacity){
        super(spaceName, spaceId, maxCapacity);
    }
}
