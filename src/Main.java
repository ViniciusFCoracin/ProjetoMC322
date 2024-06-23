package src;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import src.GraphicInterface.Views.ScheduleView;
import src.GraphicInterface.Views.SelectionView;


/**
 * Main class of the system
 */
public class Main extends Application {

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }

    @Override
	public void start(Stage stage) throws IOException {
    	SelectionView selectionView = SelectionView.getInstance();
		selectionView.setStage(stage);
		selectionView.stagePrefSize();
		stage.centerOnScreen();
		stage.setMaximized(true);
    	selectionView.loadScene("selection");
		selectionView.showStage();
	}
}
