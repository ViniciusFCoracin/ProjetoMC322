package src.GraphicInterface.Controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import src.Course.Course;
import src.System.LectureSelector;

public class ScheduleController {
	
	@FXML
	private ComboBox<String> coursesComboBox;
	
	private List<String> allCourses;
	private ObservableList<String> observableCoursesList;
	
	@FXML
	public void initialize() {
		loadCoursesComboBox();
	}
	
	public void loadCoursesComboBox() {
		allCourses = new ArrayList<String>();
		for(Course course : LectureSelector.getInstance().getAllCourses()) {
			allCourses.add(course.getCourseName());
		}
		
		observableCoursesList = FXCollections.observableArrayList(allCourses);
		coursesComboBox.setItems(observableCoursesList);
	}
}