package src.GraphicInterface.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import src.GraphicInterface.Views.ScheduleView;
import src.GraphicInterface.Views.SelectionView;
import src.System.LectureSelector;

/**
 * Controller class for handling the selection of courses, spaces, and electives in the GUI.
 */
public class SelectionController {
	
	@FXML
	private VBox coursesVBox;
	
	@FXML
	private FlowPane spacesFlowPane;
	
	@FXML
	private FlowPane electivesFlowPane;
	
	private static List<String> removedCourses;
	private static List<String> removedSpaces;
	private static List<String> removedElectives;
	
	@FXML
	public void initialize() {
		initializeContainer(coursesVBox);
		initializeContainer(spacesFlowPane);
		initializeContainer(electivesFlowPane);
		
		removedCourses = new ArrayList<String>();
		removedSpaces = new ArrayList<String>();
		removedElectives = new ArrayList<String>();
	}
	
	public static List<String> getRemovedCourses() {
		return removedCourses;
	}
	
	public static List<String> getRemovedSpaces() {
		return removedSpaces;
	}
	
	public static List<String> getRemovedElectives() {
		return removedElectives;
	}
	
	@FXML
	public void removeCourse(ActionEvent e) {
		removedCourses = remove(e, removedCourses);
	}
	
	@FXML
	public void removeSpace(ActionEvent e) {
		removedSpaces = remove(e, removedSpaces);
	}
	
	@FXML
	public void removeElective(ActionEvent e) {
		removedElectives = remove(e, removedElectives);
	}
	
	@FXML
	public void submit(ActionEvent e) throws IOException {
		LectureSelector.getInstance().loadAndFilterResources();
		SelectionView.getInstance().closeStage();
		ScheduleView.getInstance().openStage("schedule");
	}
	
	private List<String> remove(ActionEvent e, List<String> list) {
		Button button = (Button) e.getSource();
		
		if((Boolean)button.getUserData() == false) {
			list.add(button.getText());
			button.getStyleClass().add("removed");
			button.setUserData(true);
		} else {
			list.remove(button.getText());
			button.getStyleClass().remove("removed");
			button.setUserData(false);
		}
		return list;
	}
	
	private void initializeContainer(Parent container) {
		for (Node node : container.getChildrenUnmodifiable()) {			
			Button button = (Button) node;
			button.setUserData(false);
		}
	}	
}
