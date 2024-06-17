package src.GraphicInterface.Controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import src.CourseRelated.Course;
import src.CourseRelated.LectureRelated.Lecture;
import src.GraphicInterface.Views.ElectivesView;
import src.GraphicInterface.Views.ScheduleView;
import src.GraphicInterface.Views.SelectionView;
import src.Schedule.HourOfClass;
import src.Schedule.WeekDay;
import src.System.LectureSelector;

public class ScheduleController {
	
	@FXML
	private ComboBox<String> coursesComboBox, semesterComboBox;
	
	@FXML
	private Label course, semester;
	
	@FXML
	private GridPane scheduleGridPane;
	
	private List<String> allCourses;
	private List<String> allSemesters;
	private ObservableList<String> observableCoursesList;
	private ObservableList<String> observableSemesterList;
	private String currentCourse;
	private String currentSemester;
	
	@FXML
	public void initialize() {
		loadCoursesComboBox();
		loadSemestersComboBox();
	}
	
	@FXML
	public void loadSchedule() {
		cleanSchedule();
		currentCourse = coursesComboBox.getValue();
		currentSemester = semesterComboBox.getValue();
		
		if(currentCourse == null || currentSemester == null) {
			return;
		} else {
			course.setText(currentCourse);
			semester.setText(currentSemester);
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
	
	
	private void assignLectureToGrid(Lecture lecture) {
		WeekDay day = lecture.getLectureSchedule().getDay();
		HourOfClass hourOfClass = lecture.getLectureSchedule().getHourOfClass();
		
		int column = WeekDay.getNumericValue(day);
		int row = HourOfClass.getNumericValue(hourOfClass);
		
		Label labelDisciplineId = new Label(lecture.getLectureDiscipline().getDisciplineId());
		Label labelProfessor = new Label(lecture.getProfessor());
		Label labelSpace = new Label(lecture.getLectureSpace().getSpaceID());
		Label labelGroup = new Label(Character.toString(lecture.getLectureGroup()));
		
		VBox vBox = (VBox) getNodeByRowColumnIndex(scheduleGridPane, row, column);
		vBox.getChildren().addAll(labelDisciplineId, labelGroup, labelProfessor, labelSpace);
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
}