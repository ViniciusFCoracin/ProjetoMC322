package src.Readers.SpaceRelatedReaders;

import org.w3c.dom.Node;

import src.Spaces.*;
import src.Spaces.Classrooms.*;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a space node and returns a space object
 */
public class SpaceReader implements XMLNodeReader {
    private static SpaceReader instance;

    private SpaceReader(){
        // does nothing, but we need this to be private
    }

    public Space readNode(Node spaceNode){
        Space space = null;
        try{
            if ("basicRoom".equals(spaceNode.getNodeName())){
                BasicRoomReader reader = BasicRoomReader.getInstance();
                BasicRoom room = reader.readNode(spaceNode);
                return room;
            }
            else if ("computerRoom".equals(spaceNode.getNodeName())){
                ComputerRoomReader reader = ComputerRoomReader.getInstance();
                ComputerRoom room = reader.readNode(spaceNode);
                return room;
            }
            else if ("slidesRoom".equals(spaceNode.getNodeName())){
                SlidesRoomReader reader = SlidesRoomReader.getInstance();
                SlidesRoom room = reader.readNode(spaceNode);
                return room;
            }
            else if ("auditorium".equals(spaceNode.getNodeName())){
                AuditoriumReader reader = AuditoriumReader.getInstance();
                Auditorium room = reader.readNode(spaceNode);
                return room;
            }
            else if ("laboratory".equals(spaceNode.getNodeName())){
                LaboratoryReader reader = LaboratoryReader.getInstance();
                PhysicLaboratory room = reader.readNode(spaceNode);
                return room;
            }
            else if ("court".equals(spaceNode.getNodeName())){
                CourtReader reader = CourtReader.getInstance();
                Court room = reader.readNode(spaceNode);
                return room;
            }
            else{
                throw new Error("Invalid atribute");
            }
        }
        catch (Exception e){
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return space;
    }

    public static SpaceReader getInstance(){
        if (instance == null)
            instance = new SpaceReader();
        return instance;
    }
}
