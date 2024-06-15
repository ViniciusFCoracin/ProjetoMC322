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
import javafx.stage.Stage;
import src.GraphicInterface.Views.ScheduleView;
import src.GraphicInterface.Views.SelectionView;
import src.System.LectureSelector;

public class SelectionController {
	
	@FXML
	private VBox coursesVBox;
	
	@FXML
	private FlowPane spacesFlowPane;
	
	@FXML
	private FlowPane electivesFlowPane;
	
	private static List<String> removedCourses = new ArrayList<String>();
	private static List<String> removedSpaces = new ArrayList<String>();
	private static List<String> removedElectives = new ArrayList<String>();
	
	@FXML
	public void initialize() {
		initializeContainer(coursesVBox);
		initializeContainer(spacesFlowPane);
		initializeContainer(electivesFlowPane);
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
	
	private List<String> remove(ActionEvent e, List<String> list) {
		Button button = (Button) e.getSource();
		
		if((Boolean)button.getUserData() == false) {
			list.add(button.getText());
			button.setStyle("-fx-background-color: gray");
			button.setUserData(true);
		} else {
			list.remove(button.getText());
			button.setStyle("-fx-background-color: white");
			button.setUserData(false);
		}
		return list;
	}
	
	private void initializeContainer(Parent container) {
		for (Node node : container.getChildrenUnmodifiable()) {			
			Button button = (Button) node;
			button.setUserData(false);
			button.setStyle("-fx-background-color: white");
		}
	}
	
	@FXML
	public void submit(ActionEvent e) throws IOException {
		LectureSelector.getInstance().startDistribution();
		
		SelectionView.getInstance().closeStage();
		ScheduleView.getInstance().openStage("schedule");
	}
}
