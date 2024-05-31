package Spaces.Classrooms;

/**
 * Class that represents a computer room
 */
public class ComputerRoom extends Classroom {
    private int numComputers;

    /**
     * Public constructor for the ComputerRoom class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     * @param numComputers: the number of computers in the room
     */
    public ComputerRoom(String spaceName, int spaceId, int maxCapacity, int numComputers){
        super(spaceName, spaceId, maxCapacity);
        this.numComputers = numComputers;
    }

    public int getNumComputers(){
        return this.numComputers;
    }
}
