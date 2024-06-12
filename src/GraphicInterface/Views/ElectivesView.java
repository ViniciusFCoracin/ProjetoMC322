package src.GraphicInterface.Views;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ElectivesView {
	
	private Stage stage;
	private static ElectivesView instance;
	
	private ElectivesView(Stage stage){
		this.stage = stage;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public static ElectivesView getInstance(Stage stage){
        if(instance == null) {
            instance= new ElectivesView(stage);
        }
        return instance;
    }
	
	public void openStage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/src/FXML/electives.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	public void closeStage() {
		stage.close();
	}
}

