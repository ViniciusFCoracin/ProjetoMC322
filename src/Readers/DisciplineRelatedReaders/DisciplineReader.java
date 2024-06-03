package src.Readers.DisciplineRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.List;
import java.util.ArrayList;

import src.Course.Discipline;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a course node and returns a Course object
 */
public class DisciplineReader implements XMLNodeReader {
    private static DisciplineReader instance;

    private DisciplineReader(){
        // does nothing, but we need this to be private
    }

    public Discipline readNode(Node courseNode){
        Discipline discipline = null;
        try{
            NodeList childNodes = courseNode.getChildNodes();
            String disciplineName = null;
            String disciplineId = null;
            int credits = 0;
            List<String> professors = new ArrayList<>();
            for (int i = 0; i < childNodes.getLength(); i++){
                Node node = childNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                else if ("disciplineName".equals(node.getNodeName())){
                    disciplineName = node.getTextContent().trim();
                }
                else if ("disciplineId".equals(node.getNodeName())){
                    disciplineId = node.getTextContent().trim();
                }
                else if ("credits".equals(node.getNodeName())){
                    credits = Integer.parseInt(node.getTextContent().trim());
                }
                else if ("professors".equals(node.getNodeName())){
                    NodeList professorsList = node.getChildNodes();
                    for (int j = 0; j < professorsList.getLength(); j++){
                        Node professorNode = professorsList.item(j);
                        String professorStr = professorNode.getTextContent().trim();
                        professors.add(professorStr);
                    }
                }
                else{
                    throw new Error("Invalid atribute");
                }
            }
            if (disciplineName != null && disciplineId != null && credits != 0)
                discipline = new Discipline(disciplineName, disciplineId, credits, professors);
            else
                throw new Error("Atribute missing");
        }
        catch (Exception e){
            System.err.println("Error reading file: " + e.getMessage());
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
