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
	private FlowPane spacesFlowPane;
	
	@FXML
	private VBox coursesVBox;
	
	private static List<String> removedCourses = new ArrayList<String>();
	private static List<String> removedSpaces= new ArrayList<String>();
	
	public static List<String> getRemovedCourses() {
		return removedCourses;
	}
	
	public static List<String> getRemovedSpaces() {
		return removedSpaces;
	}
	
	@FXML
	public void initialize() {
		initializeContainer(coursesVBox);
		initializeContainer(spacesFlowPane);
	}
	
	public void removeSpace(ActionEvent e) {
		removedSpaces = remove(e, removedSpaces);
	}
	
	public void removeCourse(ActionEvent e) {
		removedCourses = remove(e, removedCourses);
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
	
	public void submit(ActionEvent e) throws IOException {
		LectureSelector.getInstance().startDistribution();;
		SelectionView.getInstance(null).hideStage();;
		
		Stage stage =  new Stage();
		ScheduleView.getInstance(stage).openStage("schedule");
	}
}
