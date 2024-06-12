package src.GraphicInterface.Views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class View {
	private Stage stage;
	
	public View(Stage stage) {
		this.stage = stage;
	}
	
	public void openStage(String path) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/src/FXML/"+path+".fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	public void closeStage() {
		stage.close();
	}
	
	public void hideStage() {
		stage.hide();
	}
	
	public void showStage() {
		stage.show();
	}
}