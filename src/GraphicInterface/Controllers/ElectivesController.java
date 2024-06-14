package src.GraphicInterface.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import src.Course.Lecture;
import src.GraphicInterface.Views.ElectivesView;
import src.Schedule.WeekDay;
import src.System.LectureSelector;

public class ElectivesController {
	
	@FXML
	private GridPane electivesGridPane;
	
	@FXML
	public void initialize() {
		loadSchedule();
	}
	
	public void loadSchedule() {
		for(Lecture lecture : LectureSelector.getInstance().getAllLectures()) {
			if(!lecture.getLectureDiscipline().getIsMandatory()) {
				assignLectureToGrid(lecture);
				String output = ("Lecture: " + lecture.getLectureDiscipline().getDisciplineId() + ", Professor " + lecture.getProfessor() + "\n" +
		}
	}
	
	private void assignLectureToGrid(Lecture lecture) {
		WeekDay day = lecture.getLectureSchedule().getDay();
		int column = GridController.getColumnFromWeekDay(day) - 1;
		
		Label labelDisciplineId = new Label(lecture.getLectureDiscipline().getDisciplineId());
		
		Pane previewPane = new Pane();
        previewPane.setStyle("-fx-background-color: lightgrey; -fx-border-color: black;");
        previewPane.setPrefSize(200, 120);
        
        List<Label> previewPaneLabels = createPreviwPaneLabels(lecture);
		
		VBox previewPaneVBox = new VBox();
		previewPaneVBox.getChildren().addAll(previewPaneLabels);
		
		previewPane.getChildren().add(previewPaneVBox);
		
		Popup previewPopup = new Popup();
        previewPopup.getContent().add(previewPane);
        
        labelDisciplineId.setOnMouseEntered(event -> {
            previewPopup.show(ElectivesView.getInstance(null).getStage(), event.getScreenX(), event.getScreenY() + 10);
        });

        labelDisciplineId.setOnMouseExited(event -> {
            previewPopup.hide();
        });

		VBox vBox = (VBox) GridController.getNodeByRowColumnIndex(electivesGridPane, 1, column);
		vBox.getChildren().addAll(labelDisciplineId);
	}
	
	private List<Label> createPreviwPaneLabels(Lecture lecture) {
		Label labelName = new Label(lecture.getLectureDiscipline().getDisciplineName());
        Label labelProfessor = new Label(lecture.getProfessor());
		Label labelSpace = new Label(lecture.getLectureSpace().getSpaceID());
		Label labelCredits = new Label("Credits: " +  String.valueOf(lecture.getLectureDiscipline().getDisciplineCredits()));
		Label labelGroup = new Label("Group: " + Character.toString(lecture.getLectureGroup()));
		Label labelHourOfClass = new Label(lecture.getLectureSchedule().getHourOfClass().toString());
		
		return new ArrayList<Label>(Arrays.asList(labelName, labelProfessor, labelSpace, labelCredits, labelGroup, labelHourOfClass));
	}
}
