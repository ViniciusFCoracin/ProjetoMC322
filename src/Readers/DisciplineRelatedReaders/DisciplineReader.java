package src.Readers.DisciplineRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.List;
import java.util.ArrayList;

import src.Course.Discipline;
import src.Readers.XMLNodeReader;
import src.Spaces.SpaceType;
import src.Spaces.InstituteAbbr;

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
            SpaceType type = null;
            List<InstituteAbbr> institutes = new ArrayList<>();
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
                else if ("requiredSpace".equals(node.getNodeName())){
                    String text = node.getTextContent().trim();
                    if (text.equals("BasicRoom"))
                        type = SpaceType.BASIC_ROOM;
                    else if (text.equals("SlidesRoom"))
                        type = SpaceType.SLIDES_ROOM;
                    else if (text.equals("ComputerRoom"))
                        type = SpaceType.COMPUTER_ROOM;
                    else if (text.equals("Auditorium"))
                        type = SpaceType.AUDITORIUM;
                    else if (text.equals("Court"))
                        type = SpaceType.COURT;
                    else if (text.equals("PhysicLaboratory"))
                        type = SpaceType.PHYSICS_LABORATORY;
                    else if (text.equals("Chemistry Laboratory"))
                        type = SpaceType.CHEMISTRY_LABORATORY;
                    else
                        throw new Error("Invalid space type");
                }
                else if ("requiredInstitute".equals(node.getNodeName())){
                    NodeList institutesList = node.getChildNodes();
                    for (int j = 0; j < institutesList.getLength(); j++){
                        Node instituteNode = institutesList.item(j);
                        if (instituteNode.getNodeType() != Node.ELEMENT_NODE)
                            continue;
                        String text = instituteNode.getTextContent().trim();
                        if (text.equals("CB"))
                            institutes.add(InstituteAbbr.CB);
                        else if (text.equals("PB"))
                            institutes.add(InstituteAbbr.PB);
                        else if (text.equals("IC"))
                            institutes.add(InstituteAbbr.IC);
                        else if (text.equals("FEEC"))
                            institutes.add(InstituteAbbr.FEEC);
                        else if (text.equals("IMECC"))
                            institutes.add(InstituteAbbr.IMECC);
                        else if (text.equals("IFGW"))
                            institutes.add(InstituteAbbr.IFGW);
                        else if (text.equals("IEL"))
                            institutes.add(InstituteAbbr.IEL);
                        else if (text.equals("FEF"))
                            institutes.add(InstituteAbbr.FEF);
                        else
                            throw new Error("Invalid institute");
                    }
                }
                else if ("professors".equals(node.getNodeName())){
                    NodeList professorsList = node.getChildNodes();
                    for (int j = 0; j < professorsList.getLength(); j++){
                        Node professorNode = professorsList.item(j);
                        if (professorNode.getNodeType() != Node.ELEMENT_NODE)
                            continue;
                        String professorStr = professorNode.getTextContent().trim();
                        professors.add(professorStr);
                    }
                }
                else{
                    throw new Error("Invalid atribute");
                }
            }
            if (disciplineName != null && disciplineId != null && type != null && institutes.size() != 0 && credits != 0)
                discipline = new Discipline(disciplineName, disciplineId, credits, type, professors, institutes);
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
