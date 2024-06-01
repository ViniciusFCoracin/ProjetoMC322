package src.Readers.SpaceRelatedReaders;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import src.Readers.XMLFileReader;
import src.Spaces.*;

public class SpaceReader implements XMLFileReader {
    @Override
    public List<Space> readFile(String path){
        List<Space> spaces = new ArrayList<>();

        try{
            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("space");

            for (int i = 0; i < nodeList.getLength(); i++){
                Element spaceElement = (Element) nodeList.item(i);
                String name = spaceElement.getElementsByTagName("name").item(0).getTextContent().trim();
                int id = Integer.parseInt(spaceElement.getElementsByTagName("id").item(0).getTextContent().trim());
                int capacity = Integer.parseInt(spaceElement.getElementsByTagName("capacity").item(0).getTextContent().trim());
                Space space = null;
                spaces.add(space);
            }
        } catch (Exception e){
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        return spaces;
    }

}
