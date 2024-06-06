package src;

import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;

import src.GraphicInterface.Views.SelectionView;
import src.Spaces.Space;

import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;

public class Main extends Application{

    public static void main(String[] args) throws Exception {
    	Application.launch(args);

    }

	@Override
	public void start(Stage stage) throws Exception {
		SelectionView.getInstance(stage).openStage();;
	}
}
