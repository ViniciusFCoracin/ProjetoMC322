package src.GraphicInterface;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import src.MainSystem;

public class SelectionController {
	
	private Button button;
	private static List<String> removedAreas = new ArrayList<String>();
	
	public static List<String> getRemovedAreas() {
		return removedAreas;
	}
	
	public void removeArea(ActionEvent e) {
		button = (Button) e.getSource();
		System.out.println(button.getText());
		removedAreas.add(button.getText());
	}
	
	public void submit(ActionEvent e) {
		MainSystem.startDistribution();
	}
}
