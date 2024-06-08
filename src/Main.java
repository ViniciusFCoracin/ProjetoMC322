package src;

import java.util.List;
import java.util.Collections;

import javafx.application.Application;
import javafx.stage.Stage;
import src.GraphicInterface.Views.SelectionView;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Course.LectureComparator;
import src.Readers.CourseRelatedReaders.CoursesFileReader;
import src.Readers.DisciplineRelatedReaders.DisciplinesFileReader;
import src.Readers.SpaceRelatedReaders.SpacesFileReader;
import src.Spaces.Space;
import src.System.*;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        Application.launch(args);
        LectureSelector.startDistribution();
    }

    @Override
	public void start(Stage stage) throws Exception {
		SelectionView.getInstance(stage).openStage();;
	}
}
