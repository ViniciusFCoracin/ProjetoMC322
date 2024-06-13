package src.Readers.SpaceRelatedReaders;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import src.Spaces.*;
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
            NodeList childNodes = spaceNode.getChildNodes();
            String id = null;
            SpaceType type = null;
            InstituteAbbr institute = null;
            for (int i = 0; i < childNodes.getLength(); i++){
                Node node = childNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                else if ("type".equals(node.getNodeName())){
                    String text = node.getTextContent().trim();
                    if (text.equals("basicRoom"))
                        type = SpaceType.BASIC_ROOM;
                    else if (text.equals("slidesRoom"))
                        type = SpaceType.SLIDES_ROOM;
                    else if (text.equals("computerRoom"))
                        type = SpaceType.COMPUTER_ROOM;
                    else if (text.equals("physicsLaboratory"))
                        type = SpaceType.PHYSICS_LABORATORY;
                    else if (text.equals("chemistryLaboratory"))
                        type = SpaceType.CHEMISTRY_LABORATORY;
                    else if (text.equals("auditorium"))
                        type = SpaceType.AUDITORIUM;
                    else if (text.equals("court"))
                        type = SpaceType.COURT;
                    else
                        throw new Error("Invalid institute");
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
                    else if (text.equals("IE"))
                        institute = InstituteAbbr.IE;
                    else
                        throw new Error("Invalid institute");
                }
                else if ("id".equals(node.getNodeName())){
                    id = node.getTextContent().trim();
                }
                else{
                    throw new Error("Invalid atribute");
                }
            }
            if (id != null && type != null && institute != null)
                space = new Space(id, type, institute);
            else
                throw new Error("Atribute missing");
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
