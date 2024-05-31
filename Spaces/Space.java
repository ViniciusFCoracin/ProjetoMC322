package Spaces;

public abstract class Space {
    private String spaceName;
    private int spaceId;
    private int maxCapacity;

    public Space(String spaceName, int spaceId, int maxCapacity){
        this.spaceName = spaceName;
        this.spaceId = spaceId;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString(){
        return (spaceName + ", with capacity of " + maxCapacity + "people.");
    }
}