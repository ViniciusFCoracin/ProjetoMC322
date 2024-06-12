package src.GraphicInterface.Controllers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import src.Course.Course;
import src.Course.Lecture;
import src.Schedule.HourOfClass;
import src.Schedule.WeekDay;
import src.System.LectureSelector;

public class ScheduleController {
	
	@FXML
	private ComboBox<String> coursesComboBox, semesterComboBox;
	
	@FXML
	private Label course, semester;
	
	@FXML
	private GridPane scheduleGridPane;
	
	private List<String> allCourses;
	private List<String> allSemesters;
	private ObservableList<String> observableCoursesList;
	private ObservableList<String> observableSemesterList;
	private String currentCourse;
	private String currentSemester;
	
	@FXML
	public void initialize() {
		loadCoursesComboBox();
		loadSemestersComboBox();
	}
	
	public void loadCoursesComboBox() {
		allCourses = new ArrayList<String>();
		for(Course course : LectureSelector.getInstance().getAllCourses()) 
			allCourses.add(course.getCourseName());
		
		observableCoursesList = FXCollections.observableArrayList(allCourses);
		coursesComboBox.setItems(observableCoursesList);
	}

	public void loadSemestersComboBox() {
		allSemesters = new ArrayList<String>(Arrays.asList("1°", "2°", "3°", "4°"));
		observableSemesterList = FXCollections.observableArrayList(allSemesters);
		semesterComboBox.setItems(observableSemesterList);
	}
	
	public void loadSchedule() {
		cleanSchedule();
		currentCourse = coursesComboBox.getValue();
		currentSemester = semesterComboBox.getValue();
		course.setText(currentCourse);
		semester.setText(currentSemester);
		int currentSemesterInt = convertSemesterToNumber(currentSemester);
		for(Lecture lecture : LectureSelector.getInstance().getAllLectures()) {
			if(lecture.getLectureDiscipline().getIsMandatory()) {
				if(lecture.getCourse().getCourseName().equals(currentCourse) && lecture.getCourse().getDisciplineSemester(lecture.getLectureDiscipline()) == currentSemesterInt) {
					assignLectureToGrid(lecture);
					String output = ("Lecture: " + lecture.getLectureDiscipline().getDisciplineId() + ", Professor " + lecture.getProfessor() + "\n" +
                            "Day: " + lecture.getLectureSchedule().getDay() + ", " + lecture.getLectureSchedule().getHourOfClass() + "\n" +
                            "Place: " + lecture.getLectureSpace() + "\n" + "Group: " 
                            + lecture.getLectureGroup() + "\n" + lecture.getCourse().getCourseName() + ", " + lecture.getCourse().getDisciplineSemester(lecture.getLectureDiscipline()) + " semester\n");
					//System.out.println(output);
				}
			} 
		}
	}
	
	public void assignLectureToGrid(Lecture lecture) {
		WeekDay day = lecture.getLectureSchedule().getDay();
		HourOfClass hourOfClass = lecture.getLectureSchedule().getHourOfClass();
		
		int column = getColumnFromWeekDay(day);
		int row = getRowFromHourOfClass(hourOfClass);
		
		Label labelDisciplineId = new Label(lecture.getLectureDiscipline().getDisciplineId());
		Label labelProfessor = new Label(lecture.getProfessor());
		Label labelSpace = new Label(lecture.getLectureSpace().getSpaceName());
		Label labelGroup = new Label(Character.toString(lecture.getLectureGroup()));
		
		VBox vBox = (VBox) getNodeByRowColumnIndex(row, column);
		vBox.getChildren().addAll(labelDisciplineId, labelGroup, labelProfessor, labelSpace);
	}
	
	private int convertSemesterToNumber(String currentSemester) {
		switch (currentSemester) {
        case "1°": currentSemester = "1"; break;
        case "2°": currentSemester = "2"; break;
        case "3°": currentSemester = "3"; break;
        case "4°": currentSemester = "4"; break;
        default: break;
		}
		return Integer.parseInt(currentSemester);
	}
	
	private int getColumnFromWeekDay(WeekDay day) {
        switch (day) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            default:
                return 0;
        }
    }
	
	private int getRowFromHourOfClass(HourOfClass hourOfClass) {
        switch (hourOfClass) {
            case EigthAM_TenAM:
                return 1;
            case TenAM_Midday:
                return 2;
            case TwoPM_FourPM:
                return 3;
            case FourPM_SixPM:
                return 4;
            case SevenPM_NinePM:
                return 5;
            case NinePM_ElevenPM:
                return 6;
            default:
                return 0;
        }
    }
	
	public Node getNodeByRowColumnIndex(int row, int column) {
		for(Node node : scheduleGridPane.getChildren()) {
	        Integer rowIndex = GridPane.getRowIndex(node);
	        Integer colIndex = GridPane.getColumnIndex(node);
	        if (rowIndex != null && colIndex != null && rowIndex.equals(row) && colIndex.equals(column)) {
	            return node;
	        }
	    }
	    return null;
	}
	
	public void cleanSchedule() {
	    for (int row = 1; row <= 6; row++) {
	        for (int column = 1; column <= 5; column++) {
	            VBox vBox = (VBox) getNodeByRowColumnIndex(row, column);
	            vBox.getChildren().clear();
	        }
	    }
	}
}