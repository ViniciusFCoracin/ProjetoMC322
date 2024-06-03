package src.Readers.SpaceRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import src.Spaces.*;
import src.Spaces.Classrooms.*;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a course node and returns a Course object
 */
public class SpaceReader implements XMLNodeReader {
    private static SpaceReader instance;

    private SpaceReader(){
        // does nothing, but we need this to be private
    }

    public Space readNode(Node spaceNode){
        Space space = null;
        try{
            NodeList childNodes = spaceNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++){
                Node node = childNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                else if ("basicRoom".equals(node.getNodeName())){
                    BasicRoomReader reader = BasicRoomReader.getInstance();
                    BasicRoom room = reader.readNode(spaceNode);
                    return room;
                }
                else if ("computerRoom".equals(node.getNodeName())){
                    ComputerRoomReader reader = ComputerRoomReader.getInstance();
                    ComputerRoom room = reader.readNode(spaceNode);
                    return room;
                }
                else if ("slidesRoom".equals(node.getNodeName())){
                    SlidesRoomReader reader = SlidesRoomReader.getInstance();
                    SlidesRoom room = reader.readNode(spaceNode);
                    return room;
                }
                else if ("auditorium".equals(node.getNodeName())){
                    AuditoriumReader reader = AuditoriumReader.getInstance();
                    Auditorium room = reader.readNode(spaceNode);
                    return room;
                }
                else if ("laboratory".equals(node.getNodeName())){
                    LaboratoryReader reader = LaboratoryReader.getInstance();
                    Laboratory room = reader.readNode(spaceNode);
                    return room;
                }
                else if ("court".equals(node.getNodeName())){
                    CourtReader reader = CourtReader.getInstance();
                    Court room = reader.readNode(spaceNode);
                    return room;
                }
                else{
                    throw new Error("Invalid atribute");
                }
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
