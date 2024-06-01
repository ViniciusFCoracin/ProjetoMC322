package src.Readers.CourseRelatedReaders;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.List;
import java.util.ArrayList;

import src.Course.Course;
import src.Course.Semester;
import src.Readers.XMLNodeReader;

/**
 * Singleton class that reads a course node and returns a Course object
 */
public class CourseReader implements XMLNodeReader {
    private static CourseReader instance;

    private CourseReader(){
        // does nothing, but we need this to be private
    }

    public Course readNode(Node courseNode){
        Course course = null;
        try{
            NodeList childNodes = courseNode.getChildNodes();
            String courseName = null;
            int courseId = 0;
            List<Semester> semesters = new ArrayList<>();
            for (int i = 0; i < childNodes.getLength(); i++){
                Node node = childNodes.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                else if ("courseName".equals(node.getNodeName())){
                    courseName = node.getTextContent().trim();
                }
                else if ("courseId".equals(node.getNodeName())){
                    courseId = Integer.parseInt(node.getTextContent().trim());
                }
                else if ("semester".equals(node.getNodeName())){
                    SemesterReader reader = SemesterReader.getInstance();
                    Semester semester = reader.readNode(node);
                    semesters.add(semester);
                }
                else{
                    throw new Error("Invalid atribute");
                }
            }
            if (courseId != 0 && courseName != null && semesters.size() != 0)
                course = new Course(courseName, courseId, semesters);
            else
                throw new Error("Atribute missing");
        }
        catch (Exception e){
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return course;
    }

    public static CourseReader getInstance(){
        if (instance == null)
            instance = new CourseReader();
        return instance;
    }
}
