package src;

import javafx.application.Application;
import javafx.stage.Stage;
import src.GraphicInterface.Views.SelectionView;

import src.System.*;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }

    @Override
	public void start(Stage stage) throws Exception {
		SelectionView.getInstance(stage).openStage();;
	}
}
