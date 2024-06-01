package src.Readers.CourseRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import src.Course.Discipline;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a discipline node and returns a Discipline object
 */
public class DisciplineReader implements XMLNodeReader {
    private static DisciplineReader instance;

    private DisciplineReader(){
        // does nothing, but we need this to be private
    }

    public Discipline readNode(Node disciplineNode){
        Discipline discipline = null;
        try{
            NodeList nodeList = disciplineNode.getChildNodes();
            String name = null;
            String id = null;
            int credits = 0;
            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                else if ("name".equals(node.getNodeName()))
                    name = node.getTextContent().trim();
                else if ("id".equals(node.getNodeName()))
                    id = node.getTextContent().trim();
                else if ("credits".equals(node.getNodeName()))
                    credits = Integer.parseInt(node.getTextContent().trim());
                else
                    throw new Error("Invalid atribute");
            }
            if (name != null && id != null && credits != 0)
                discipline = new Discipline(name, id, credits);
            else
                throw new Error("Atribute missing");
        }
        catch (Exception e){
            System.err.println("Error reading file:  " + e.getMessage());
            e.printStackTrace();
        }

        return discipline;
    }

    public static DisciplineReader getInstance(){
        if (instance == null)
            instance = new DisciplineReader();
        return instance;
    }
}
