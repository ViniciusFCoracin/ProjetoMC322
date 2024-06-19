package src.GraphicInterface.Controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import src.CourseRelated.Course;
import src.CourseRelated.LectureRelated.Lecture;
import src.GraphicInterface.Views.ElectivesView;
import src.GraphicInterface.Views.ScheduleView;
import src.GraphicInterface.Views.SelectionView;
import src.Schedule.HourOfClass;
import src.Schedule.WeekDay;
import src.System.LectureSelector;

/**
 * Controller class for handling the schedule view in the GUI.
 */
public class ScheduleController {
	
	@FXML
	private ComboBox<String> coursesComboBox, semesterComboBox, scheduleComboBox;
	
	@FXML
	private Label course, semester, invalidCourse, invalidSemester, invalidSchedule;
	
	@FXML
	private GridPane scheduleGridPane;
	
	private List<String> allCourses;
	private List<String> allSemesters;
	private List<String> schedules;
	private ObservableList<String> observableCoursesList;
	private ObservableList<String> observableSemesterList;
	private ObservableList<String> observableSchedulesList;
	private String currentCourse;
	private String currentSemester;
	private String currentSchedule;
	private List<Lecture> selectedLectures;
	Font font = new Font("Liberation Serif", 15);
	
	@FXML
	public void initialize() {
		loadCoursesComboBox();
		loadSemestersComboBox();
		loadSchedulesComboBox();
	}
	
	@FXML
	public void loadSchedule() {
		cleanSchedule();
		currentCourse = coursesComboBox.getValue();
		currentSemester = semesterComboBox.getValue();
		invalidCourse.setText("");
		invalidSemester.setText("");
		
		if (currentCourse == null || currentSemester == null) {
		    if (currentCourse == null) 
		        invalidCourse.setText("Please choose a course");

		    if (currentSemester == null) 
		        invalidSemester.setText("Please choose a semester");
    
		    return;
		} else {
			course.setText(currentCourse);
			semester.setText("Semester: " + currentSemester);
		}
		
		int currentSemesterInt = convertSemesterToNumber(currentSemester);
		for(Lecture lecture : LectureSelector.getInstance().getAllLectures()) {
			if(lecture.getLectureDiscipline().getIsMandatory()) {
				if(lecture.getCourse().getCourseName().equals(currentCourse) && lecture.getCourse().getDisciplineSemester(lecture.getLectureDiscipline()) == currentSemesterInt) 
					assignLectureToGrid(lecture);
			} 
		}
	}
	
	@FXML
	public void rebuildSchedule() {
		LectureSelector.getInstance().loadAndFilterResources();
		loadSchedule();
	}
	
	@FXML
	public void viewElectives() throws IOException {
		ElectivesView.getInstance().closeStage();
		ElectivesView.getInstance().openStage("electives");
	}
	
	@FXML
	public void goBack() {
		ScheduleView.getInstance().closeStage();;
		SelectionView.getInstance().showStage();
	}
	
	@FXML
	private void saveSchedule() {
		selectedLectures = new ArrayList<Lecture>();
		currentCourse = coursesComboBox.getValue();
		currentSemester = semesterComboBox.getValue();
		currentSchedule = scheduleComboBox.getValue();
		invalidCourse.setText("");
		invalidSemester.setText("");
		invalidSchedule.setText("");
		
		if ((currentSchedule == null || currentSchedule == "Current Schedule") && (currentCourse == null || currentSemester == null)) {
		    if (currentCourse == null) 
		        invalidCourse.setText("Please choose a course");

		    if (currentSemester == null) 
		        invalidSemester.setText("Please choose a semester");
    
		    return;
		}
		
		if(currentSchedule == null || currentSchedule == "Current Schedule" ) {
			int currentSemesterInt = convertSemesterToNumber(currentSemester);
			for(Lecture lecture : LectureSelector.getInstance().getAllLectures()) {
				if(lecture.getLectureDiscipline().getIsMandatory()) {
					if(lecture.getCourse().getCourseName().equals(currentCourse) && lecture.getCourse().getDisciplineSemester(lecture.getLectureDiscipline()) == currentSemesterInt) 
						selectedLectures.add(lecture);
				} 
			}
		} else {
			selectedLectures = LectureSelector.getInstance().getAllLectures();
		}
		
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save XML File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files (*.xml)", "*.xml"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            saveSelectedLecturesToXML(file);
        }
	}

	private void loadCoursesComboBox() {
		allCourses = new ArrayList<String>();
		for(Course course : LectureSelector.getInstance().getAllCourses()) 
			allCourses.add(course.getCourseName());
		
		observableCoursesList = FXCollections.observableArrayList(allCourses);
		coursesComboBox.setItems(observableCoursesList);
	}
	
	private void loadSemestersComboBox() {
		allSemesters = new ArrayList<String>(Arrays.asList("1°", "2°", "3°", "4°", "5°", "6°", "7°", "8°", "9°", "10°"));
		observableSemesterList = FXCollections.observableArrayList(allSemesters);
		semesterComboBox.setItems(observableSemesterList);
	}
	
	private void loadSchedulesComboBox() {
		schedules = new ArrayList<String>(Arrays.asList("Current Schedule", "All Lectures"));
		observableSchedulesList = FXCollections.observableArrayList(schedules);
		scheduleComboBox.setItems(observableSchedulesList);
	}
	
	private void assignLectureToGrid(Lecture lecture) {
	    WeekDay day = lecture.getLectureSchedule().getDay();
	    HourOfClass hourOfClass = lecture.getLectureSchedule().getHourOfClass();

	    int column = WeekDay.getNumericValue(day);
	    int row = HourOfClass.getNumericValue(hourOfClass);

	    Label labelDisciplineId = new Label(lecture.getLectureDiscipline().getDisciplineId());
	    Label labelProfessor = new Label(lecture.getProfessor());
	    Label labelSpace = new Label(lecture.getLectureSpace().getSpaceID());
	    Label labelGroup = new Label(Character.toString(lecture.getLectureGroup())); 
	    List<Label> labels = new ArrayList<Label>(Arrays.asList(labelDisciplineId, labelGroup, labelProfessor, labelSpace));
	    
	    updateFont(font, labels);

	    VBox vBox = (VBox) getNodeByRowColumnIndex(scheduleGridPane, row, column);
	    vBox.getChildren().addAll(labels);

	    vBox.heightProperty().addListener((obs, oldHeight, newHeight) -> {
	        double newSize = newHeight.doubleValue() / 5;
	        double minFontSize = 15.0;
	        double maxFontSize = 20.0;
	        double adjustedFontSize = Math.max(minFontSize, Math.min(maxFontSize, newSize));
	        
	        font = new Font("Liberation Serif", adjustedFontSize);
	        updateFont(font, labels);
	    });
	}
	
	public  static Node getNodeByRowColumnIndex(GridPane gridPane, int row, int column) {
		for(Node node : gridPane.getChildren()) {
	        Integer rowIndex = GridPane.getRowIndex(node);
	        rowIndex = rowIndex == null ? 0 : rowIndex;
	        Integer colIndex = GridPane.getColumnIndex(node);
	        colIndex = colIndex == null ? 0 : colIndex;
	        
	        if (rowIndex.equals(row) && colIndex.equals(column)) {
	            return node;
	        }
	    }
	    return null;
	}
	
	private void cleanSchedule() {
	    for (int row = 1; row <= 6; row++) {
	        for (int column = 1; column <= 5; column++) {
	            VBox vBox = (VBox) getNodeByRowColumnIndex(scheduleGridPane, row, column);
	            vBox.getChildren().clear();
	        }
	    }
	}
	
	private int convertSemesterToNumber(String currentSemester) {
		String numericString = currentSemester.replace("°", "");
		
		return Integer.parseInt(numericString);
	}
	
	private void updateFont (Font font, List<Label> labels) {
		for(Label label : labels) {
			label.setFont(font);
		}
	}
	
	private void saveSelectedLecturesToXML(File file) {
		try {
	        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	        Document document = documentBuilder.newDocument();
	        Element root = document.createElement("lectures");
	        document.appendChild(root);

	        for (Lecture lecture : selectedLectures) {
	            root.appendChild(createLectureElement(document, lecture));
	        }

	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();

	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	        DOMSource domSource = new DOMSource(document);
	        StreamResult streamResult = new StreamResult(file);
	        transformer.transform(domSource, streamResult);

	    } catch (ParserConfigurationException | TransformerException pce) {
	        pce.printStackTrace();
	    }
	}

	private Element createLectureElement(Document document, Lecture lecture) {
	    Element lectureElement = document.createElement("lecture");
	    
	    if(lecture.getCourse() != null) {
	    	Element courseNameElement = document.createElement("courseName");
	    	courseNameElement.appendChild(document.createTextNode(lecture.getCourse().getCourseName()));
	    	lectureElement.appendChild(courseNameElement);
	    }
	    
	    Element disciplineIdElement = document.createElement("disciplineId");
	    disciplineIdElement.appendChild(document.createTextNode(lecture.getLectureDiscipline().getDisciplineId()));
	    lectureElement.appendChild(disciplineIdElement);
	    
	    if(lecture.getCourse() != null) {
	    	Element semesterElement = document.createElement("semester");
		    semesterElement.appendChild(document.createTextNode(Integer.toString(lecture.getCourse().getDisciplineSemester(lecture.getLectureDiscipline()))));
		    lectureElement.appendChild(semesterElement);
	    }
	    
	    Element professorElement = document.createElement("professor");
	    professorElement.appendChild(document.createTextNode(lecture.getProfessor()));
	    lectureElement.appendChild(professorElement);

	    Element weekDayElement = document.createElement("weekDay");
	    weekDayElement.appendChild(document.createTextNode(lecture.getLectureSchedule().getDay().name()));
	    lectureElement.appendChild(weekDayElement);

	    Element hourOfClassElement = document.createElement("hourOfClass");
	    hourOfClassElement.appendChild(document.createTextNode(lecture.getLectureSchedule().getHourOfClass().name()));
	    lectureElement.appendChild(hourOfClassElement);

	    Element placeElement = document.createElement("place");
	    placeElement.appendChild(document.createTextNode(lecture.getLectureSpace().getSpaceID()));
	    lectureElement.appendChild(placeElement);

	    Element groupElement = document.createElement("group");
	    groupElement.appendChild(document.createTextNode(Character.toString(lecture.getLectureGroup())));
	    lectureElement.appendChild(groupElement);
	    
	    Element creditsElement = document.createElement("credits");
	    creditsElement.appendChild(document.createTextNode(Integer.toString(lecture.getLectureDiscipline().getDisciplineCredits())));
	    lectureElement.appendChild(creditsElement);

	    return lectureElement;
	}
}