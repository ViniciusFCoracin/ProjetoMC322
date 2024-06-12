package src.GraphicInterface.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import src.Course.Lecture;
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
                        "Day: " + lecture.getLectureSchedule().getDay() + ", " + lecture.getLectureSchedule().getHourOfClass() + "\n" +
                        "Place: " + lecture.getLectureSpace() + "\n" + "Group: " 
                        + lecture.getLectureGroup() +"\n");
				//System.out.println(output);
			} 
		}
	}
	
	private void assignLectureToGrid(Lecture lecture) {
		WeekDay day = lecture.getLectureSchedule().getDay();
		int column = GridController.getColumnFromWeekDay(day) - 1;
		
		Label labelDisciplineId = new Label(lecture.getLectureDiscipline().getDisciplineId());
		
		VBox vBox = (VBox) GridController.getNodeByRowColumnIndex(electivesGridPane, 1, column);
		vBox.getChildren().addAll(labelDisciplineId);
	}
}
