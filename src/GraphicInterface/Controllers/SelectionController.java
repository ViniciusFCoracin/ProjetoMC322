package src.GraphicInterface.Controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import src.MainSystem;
import src.GraphicInterface.Views.SelectionView;

public class SelectionController {
	
    @FXML
    private FlowPane spacesFlowPane;
    
    private List<Button> spacesButtons = new ArrayList<>();
	private static List<String> removedSpaces= new ArrayList<String>();
	
	@FXML
    public void initialize() {
        for (Node node : spacesFlowPane.getChildren()) {
            Button button = (Button) node;
            button.setUserData(false);
            button.setStyle("-fx-background-color: white");
            spacesButtons.add(button);
        }
    }
	
	public static List<String> getRemovedSpaces() {
		return removedSpaces;
	}
	
	public void removeSpace(ActionEvent e) {
		Button button = (Button) e.getSource();
		System.out.println(button.getText());
		
		if((Boolean)button.getUserData() == false) {
			removedSpaces.add(button.getText());
			button.setStyle("-fx-background-color: gray");
			button.setUserData(true);
		} else {
			removedSpaces.remove(button.getText());
			button.setStyle("-fx-background-color: white");
			button.setUserData(false);
		}
	}
	
	public void submit(ActionEvent e) {
		MainSystem.startDistribution();
		SelectionView.getInstance(null).closeStage();
	}
}
