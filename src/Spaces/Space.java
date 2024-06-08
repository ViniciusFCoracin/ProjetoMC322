package src.Spaces;

/**
 * Abstract class that represents a space in a university
 */
public abstract class Space {
    private String spaceName;
    private int spaceId;
    private int maxCapacity;
    private SpaceType spaceType;

    /**
     * Public class for the Space class
     * 
     * @param spaceName: the name of the space
     * @param spaceId: the id of the space
     * @param maxCapacity: the capacity of people int the space
     */
    public Space(String spaceName, int spaceId, int maxCapacity){
        this.spaceName = spaceName;
        this.spaceId = spaceId;
        this.maxCapacity = maxCapacity;
    }

    public String getSpaceName(){
        return this.spaceName;
    }

    public int getSpaceId(){
        return this.spaceId;
    }

    public int getMaxCapacity(){
        return this.maxCapacity;
    }

    public SpaceType getSpaceType(){
        return this.spaceType;
    }

    @Override
    public String toString(){
        return spaceName;
    }    
}
