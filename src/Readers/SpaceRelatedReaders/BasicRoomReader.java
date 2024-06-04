package src.Readers.SpaceRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import src.Spaces.Classrooms.BasicRoom;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a basicRoom node and creates a BasicRoom object
 */
public class BasicRoomReader implements XMLNodeReader {
    private static BasicRoomReader instance;

    private BasicRoomReader(){
        // does nothing, but we need this to be private
    }

    public BasicRoom readNode(Node spaceNode){
        BasicRoom basicRoom = null;
        try{
            NodeList childNodes = spaceNode.getChildNodes();
            String name = null;
            int id = 0;
            int capacity = 0;
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
                else{
                    throw new Error("Invalid atribute");
                }
            }
            if (name != null && id != 0 && capacity != 0)
                basicRoom = new BasicRoom(name, id, capacity);
            else
                throw new Error("Atribute missing");
        }
        catch (Exception e){
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return basicRoom;
    }

    public static BasicRoomReader getInstance(){
        if (instance == null)
            instance = new BasicRoomReader();
        return instance;
    }
}
