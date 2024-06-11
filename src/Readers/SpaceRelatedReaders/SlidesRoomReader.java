package src.Readers.SpaceRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import src.Spaces.Classrooms.SlidesRoom;
import src.Readers.XMLNodeReader;
import src.Spaces.InstituteAbbr;

/**
 * Singleton class that reads a slideRoom node and creates a slideRoom object
 */
public class SlidesRoomReader implements XMLNodeReader {
    private static SlidesRoomReader instance;

    private SlidesRoomReader(){
        // does nothing, but we need this to be private
    }

    public SlidesRoom readNode(Node courseNode){
        SlidesRoom slidesRoom = null;
        try{
            NodeList childNodes = courseNode.getChildNodes();
            String name = null;
            int id = 0;
            int capacity = 0;
            InstituteAbbr institute = null;
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
                else if ("institute".equals(node.getNodeName())){
                    String text = node.getTextContent().trim();
                    if (text.equals("CB"))
                        institute = InstituteAbbr.CB;
                    else if (text.equals("PB"))
                        institute = InstituteAbbr.PB;
                    else if (text.equals("IC"))
                        institute = InstituteAbbr.IC;
                    else if (text.equals("FEEC"))
                        institute = InstituteAbbr.FEEC;
                    else if (text.equals("IMECC"))
                        institute = InstituteAbbr.IMECC;
                    else if (text.equals("IFGW"))
                        institute = InstituteAbbr.IFGW;
                    else if (text.equals("IEL"))
                        institute = InstituteAbbr.IEL;
                    else if (text.equals("FEF"))
                        institute = InstituteAbbr.FEF;
                    else
                        throw new Error("Invalid institute");
                }
                else{
                    throw new Error("Invalid atribute");
                }
            }
            if (name != null && id != 0 && capacity != 0 && institute != null)
                slidesRoom = new SlidesRoom(name, id, capacity, institute);
            else
                throw new Error("Atribute missing");
        }
        catch (Exception e){
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return slidesRoom;
    }

    public static SlidesRoomReader getInstance(){
        if (instance == null)
            instance = new SlidesRoomReader();
        return instance;
    }
}
