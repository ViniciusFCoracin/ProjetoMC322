package src.Readers.CourseRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.List;

import src.Course.Discipline;
import src.Course.Semester;
import src.Readers.XMLNodeReader;


/**
 * Singleton class that reads a semester node and returns a Semester object
 */
public class SemesterReader implements XMLNodeReader {
    private static SemesterReader instance;

    private SemesterReader(){
        // does nothing, but we need this to be private
    }

    public Semester readNode(Node semesterNode){
        Semester semester = null;
        try{
            NodeList childNodes = semesterNode.getChildNodes();
            int period = 0;
            List<Discipline> disciplines = new ArrayList<>();
            for (int i = 0; i < childNodes.getLength(); i++){
                Node node = childNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                else if ("period".equals(node.getNodeName())){
                    period = Integer.parseInt(node.getTextContent().trim());
                }
                else if ("discipline".equals(node.getNodeName())){
                    DisciplineReader reader = DisciplineReader.getInstance();
                    Discipline discipline = reader.readNode(node);
                    disciplines.add(discipline);
                }
                else{
                    throw new Error("Atributo inválido");
                }
            }
            if (period != 0 && disciplines.size() != 0)
                semester = new Semester(period, disciplines);
            else
                throw new Error("Atributo faltando");
        }
        catch (Exception e){
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        return semester;
    }

    public static SemesterReader getInstance(){
        if (instance == null)
            instance = new SemesterReader();
        return instance;
    }
}
