package src.GraphicInterface.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.GraphicInterface.Views.ScheduleView;
import src.GraphicInterface.Views.SelectionView;
import src.System.LectureSelector;

/**
 * Controller class for handling the selection of courses, spaces, and electives in the GUI.
 */
public class SelectionController {
    
    // FXML fields
    @FXML
    private VBox coursesVBox;

    @FXML
    private FlowPane spacesFlowPane;

    @FXML
    private FlowPane electivesFlowPane;

    // Lists for removed items chosen by the user 
    private static List<String> removedCourses = new ArrayList<>();
    private static List<String> removedSpaces = new ArrayList<>();
    private static List<String> removedElectives = new ArrayList<>();

    @FXML
    public void initialize() {
        initializeContainer(coursesVBox);
        initializeContainer(spacesFlowPane);
        initializeContainer(electivesFlowPane);
    }

    /**
     * Handles the removal of a course.
     */
    @FXML
    public void removeCourse(ActionEvent e) {
        removedCourses = remove(e, removedCourses);
    }

    /**
     * Handles the removal of a space.
     */
    @FXML
    public void removeSpace(ActionEvent e) {
        removedSpaces = remove(e, removedSpaces);
    }

    /**
     * Handles the removal of a elective.
     */
    @FXML
    public void removeElective(ActionEvent e) {
        removedElectives = remove(e, removedElectives);
    }

    /**
     * Handles the removal or re-addition of an item.
     */
    private List<String> remove(ActionEvent e, List<String> list) {
    	Button button = (Button) e.getSource();
    	
    	if ((Boolean) button.getUserData() == false) {
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
    
    /**
     * Transition from selection view to schedule view.
     */
    @FXML
    public void submit(ActionEvent e) throws IOException {
        LectureSelector.getInstance().loadAndFilterResources();

        ScheduleView scheduleView = ScheduleView.getInstance();
        scheduleView.setStage(SelectionView.getInstance().getStage());
        scheduleView.loadScene("schedule");
        scheduleView.showStage();
    }

    /**
     * Initializes a container by setting user data for each button to false.
     * @param container: parent container
     */
    private void initializeContainer(Parent container) {
        for (Node node : container.getChildrenUnmodifiable()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setUserData(false);
            }
        }
    }

    /**
     * Displays an error message in a new stage.
     * @param message: message to be displayed
     */
    public static void errorStage(String message) {
        Label label = new Label(message);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(label);
        vbox.setStyle("-fx-font-family: 'Liberation Serif'; -fx-font-size: 20; -fx-alignment: center; -fx-padding: 10;");

        Scene scene = new Scene(vbox);

        Stage stage = new Stage();
        stage.setTitle("Error message");
        stage.setScene(scene);
        stage.setMinWidth(430);
        stage.setMaxHeight(150);
        stage.centerOnScreen();
        stage.show();
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
}