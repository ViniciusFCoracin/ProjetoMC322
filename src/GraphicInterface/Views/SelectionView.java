package src.GraphicInterface.Views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SelectionView {
	
	private Stage stage;
	private static SelectionView instance;
	
	private SelectionView(Stage stage){
		this.stage = stage;
	}
	
	public static SelectionView getInstance(Stage stage){
        if(instance == null) {
            instance= new SelectionView(stage);
        }
        return instance;
    }
	
	public void openStage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/src/FXML/selection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	public void closeStage() {
		stage.close();
	}
}
