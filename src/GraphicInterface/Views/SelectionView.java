package src.GraphicInterface.Views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SelectionView {
	
	public SelectionView(Stage stage) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/src/FXML/selection.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
}
