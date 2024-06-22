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
import src.CourseRelated.LectureRelated.Lecture;
import src.GraphicInterface.Views.ElectivesView;
import src.Schedule.HourOfClass;
import src.Schedule.WeekDay;
import src.System.LectureSelector;

/**
 * Controller class for handling the electives view in the GUI.
 */
public class ElectivesController {
	
	@FXML
	private GridPane electivesGridPane;
	
	private List<Lecture> electivesLectures;
	
	@FXML
	public void initialize() {
		loadSchedule();
	}
	
	public void loadSchedule() {
		electivesLectures = new ArrayList<Lecture>();
		for(Lecture lecture : LectureSelector.getInstance().getAllLectures()) {
			if(!lecture.getLectureDiscipline().getIsMandatory())
				electivesLectures.add(lecture);
		}
		
		sortLecturesByHourOfClass(electivesLectures);
	}
	
	private void assignLectureToGrid(Lecture lecture, int i) {
		WeekDay day = lecture.getLectureSchedule().getDay();
        int column = WeekDay.getNumericValue(day) - 1;

        Pane previewPane = new Pane();
        VBox previewPaneVBox = new VBox();
        List<Label> previewPaneLabels = createPreviwPaneLabels(lecture);
        Popup previewPopup = new Popup();
        
        previewPaneVBox.getChildren().addAll(previewPaneLabels);
        previewPane.getChildren().add(previewPaneVBox);
        previewPopup.getContent().add(previewPane);

        Label labelDisciplineId = new Label(lecture.getLectureDiscipline().getDisciplineId());
        labelDisciplineId.setOnMouseEntered(event -> {
            previewPopup.show(ElectivesView.getInstance().getStage(), event.getScreenX(), event.getScreenY() + 10);
        });
        labelDisciplineId.setOnMouseExited(event -> {
            previewPopup.hide();
        });

        VBox vBox = (VBox) ScheduleController.getNodeByRowColumnIndex(electivesGridPane, 1, column);
        vBox.getChildren().add(labelDisciplineId);
        
        labelDisciplineId.getStyleClass().addAll("elective");
        previewPaneVBox.setStyle("-fx-font-family: 'Liberation Serif'; -fx-font-size: 15; -fx-alignment: center; -fx-padding: 10;");
        previewPane.setStyle("-fx-background-color: lightgrey; -fx-border-color: black;");
	}
	
	private List<Label> createPreviwPaneLabels(Lecture lecture) {
		Label labelName = new Label(lecture.getLectureDiscipline().getDisciplineName());
		labelName.setStyle("-fx-font-weight: bold");
        Label labelProfessor = new Label(lecture.getProfessor());
		Label labelSpace = new Label(lecture.getLectureSpace().getSpaceID());
		Label labelCredits = new Label("Credits: " +  String.valueOf(lecture.getLectureDiscipline().getDisciplineCredits()));
		Label labelGroup = new Label("Group: " + Character.toString(lecture.getLectureGroup()));
		Label labelHourOfClass = new Label(lecture.getLectureSchedule().getHourOfClass().toString());
		
		return new ArrayList<Label>(Arrays.asList(labelName, labelProfessor, labelSpace, labelCredits, labelGroup, labelHourOfClass));
	}
	
	private void sortLecturesByHourOfClass(List<Lecture> electiveLectures) {
		for(int i = 1; i <= 6; i++) {
			for(Lecture lecture : electiveLectures) {
				HourOfClass hourOfClass = lecture.getLectureSchedule().getHourOfClass();
				int intHourOfClass =  HourOfClass.getNumericValue(hourOfClass);
				
				if(intHourOfClass == i) 
					assignLectureToGrid(lecture, i);
			}
		}
	}	
}
