package src.GraphicInterface.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.GraphicInterface.LectureSelector;
import src.GraphicInterface.Views.ScheduleView;
import src.GraphicInterface.Views.SelectionView;
import src.Spaces.Space;
import src.CourseRelated.Discipline;

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

    public static List<String> getRemovedCourses() {
        return removedCourses;
    }

    public static List<String> getRemovedSpaces() {
        return removedSpaces;
    }

    public static List<String> getRemovedElectives() {
        return removedElectives;
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
     * Transition from selection view to schedule view.
     */
    @FXML
    public void submit(ActionEvent e) throws IOException {
        LectureSelector.getInstance().filterResourcesAndAllocate();

        ScheduleView scheduleView = ScheduleView.getInstance();
        scheduleView.setStage(SelectionView.getInstance().getStage());
        scheduleView.loadScene("schedule");
        scheduleView.showStage();
    }

    @FXML
    public void initialize() {
        initializeContainer(coursesVBox);
        initializeContainer(spacesFlowPane);
        initializeContainer(electivesFlowPane);
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

    @FXML
    private void handleAddSpace(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../FXML/addSpace.fxml"));
            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adicionar Local");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddSpaceController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                Space newSpace = controller.getSpace();
                LectureSelector.getInstance().addSpace(newSpace);
                addNewSpaceButton(newSpace);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewSpaceButton(Space newSpace) {
        Button spaceButton = new Button(newSpace.getSpaceID());
        spaceButton.setOnAction(this::removeSpace);
        spaceButton.getStyleClass().add("selection-button");
        spaceButton.setUserData(Boolean.FALSE);
        spacesFlowPane.getChildren().add(spaceButton);
    }

    @FXML
    private void handleAddElective(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../FXML/addElective.fxml"));
            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adicionar Eletiva");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddElectiveController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                Discipline newDiscipline = controller.getElectiveAdded();
                LectureSelector.getInstance().addDiscipline(newDiscipline);
                addElectiveButton(newDiscipline);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addElectiveButton(Discipline discipline) {
        Button electiveButton = new Button(discipline.getDisciplineId());
        electiveButton.setOnAction(this::removeElective);
        electiveButton.getStyleClass().add("selection-button");
        electiveButton.setUserData(Boolean.FALSE);
        electivesFlowPane.getChildren().add(electiveButton);
    }
}