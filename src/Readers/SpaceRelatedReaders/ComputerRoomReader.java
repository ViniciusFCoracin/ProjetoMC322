package src.Readers.SpaceRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import src.Spaces.Classrooms.ComputerRoom;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a computerRoom node and creates a ComputerRoom object
 */
public class ComputerRoomReader implements XMLNodeReader {
    private static ComputerRoomReader instance;

    private ComputerRoomReader(){
        // does nothing, but we need this to be private
    }

    public ComputerRoom readNode(Node courseNode){
        ComputerRoom computerRoom = null;
        try{
            NodeList childNodes = courseNode.getChildNodes();
            String name = null;
            int id = 0;
            int capacity = 0;
            int numComputers = 0;
            for (int i = 0; i < childNodes.getLength(); i++){
                Node node = childNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                else if ("name".equals(node.getNodeName())){
                    name = node.getTextContent().trim();
                }
                else if ("id".equals(node.getNodeName())){
                    id = Integer.parseInt(node.getTextContent().trim());
                }
                else if ("capacity".equals(node.getNodeName())){
                    capacity = Integer.parseInt(node.getTextContent().trim());
                }
                else if ("numComputers".equals(node.getNodeName())){
                    numComputers = Integer.parseInt(node.getTextContent().trim());
                }
                else{
                    throw new Error("Invalid atribute");
                }
            }
            if (name != null && id != 0 && capacity != 0)
                computerRoom = new ComputerRoom(name, id, capacity, numComputers);
            else
                throw new Error("Atribute missing");
        }
        catch (Exception e){
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return computerRoom;
    }

    public static ComputerRoomReader getInstance(){
        if (instance == null)
            instance = new ComputerRoomReader();
        return instance;
    }
}
