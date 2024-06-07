package src.GraphicInterface.Controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import src.GraphicInterface.Views.SelectionView;
import src.System.LectureSelector;

public class SelectionController {
	
    @FXML
    private FlowPane coursesFlowPane, spacesFlowPane;
    
    private List<Button> courseButtons = new ArrayList<Button>();
    private List<Button> spacesButtons = new ArrayList<Button>();
    
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
		inicializeFlowPane(coursesFlowPane);
        inicializeFlowPane(spacesFlowPane);
    }
	
	public void removeSpace(ActionEvent e) {
		removedSpaces = remove(e, removedSpaces);
	}
	
	public void removeCourse(ActionEvent e) {
		removedCourses = remove(e, removedCourses);
	}
	
	
	private List<String> remove(ActionEvent e, List<String> list) {
		Button button = (Button) e.getSource();
		System.out.println(button.getText());
		
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
	
	private void inicializeFlowPane(FlowPane flowPane) {
		for (Node node : flowPane.getChildren()) {
            Button button = (Button) node;
            button.setUserData(false);
            button.setStyle("-fx-background-color: white");
            courseButtons.add(button);
        }
	}
	
	public void submit(ActionEvent e) {
		LectureSelector.startDistribution();
		SelectionView.getInstance(null).closeStage();
	}
}
