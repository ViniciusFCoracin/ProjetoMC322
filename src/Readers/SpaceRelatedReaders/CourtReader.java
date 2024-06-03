package src.Readers.SpaceRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import src.Spaces.Court;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a court node and creates a Court object
 */
public class CourtReader implements XMLNodeReader {
    private static CourtReader instance;

    private CourtReader(){
        // does nothing, but we need this to be private
    }

    public Court readNode(Node courseNode){
        Court court = null;
        try{
            NodeList childNodes = courseNode.getChildNodes();
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
                court = new Court(name, id, capacity);
            else
                throw new Error("Atribute missing");
        }
        catch (Exception e){
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return court;
    }

    public static CourtReader getInstance(){
        if (instance == null)
            instance = new CourtReader();
        return instance;
    }
}
