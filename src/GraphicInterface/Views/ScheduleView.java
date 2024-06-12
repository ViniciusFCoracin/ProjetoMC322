package src.GraphicInterface.Views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScheduleView {
	private Stage stage;
	private static ScheduleView instance;
	
	private ScheduleView(Stage stage){
		this.stage = stage;
	}
	
	public static ScheduleView getInstance(Stage stage){
        if(instance == null) {
            instance= new ScheduleView(stage);
        }
        return instance;
    }
	
	public void openStage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/src/FXML/schedule.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	public void closeStage() {
		stage.close();
	}
}
