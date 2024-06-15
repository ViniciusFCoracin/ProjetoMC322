package src.GraphicInterface.Views;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class View {
	private Stage stage;
	private Scene scene;
	
	public View() {
		stage = new Stage();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void openStage(String path) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/src/FXML/"+path+".fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
      
        showStage();
	}
	
	public void showStage() {
	    Platform.runLater(() -> {
	        try {
	            stage.show();
	            stage.setMinHeight(stage.heightProperty().get());
	            stage.setMinWidth(stage.widthProperty().get());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
	
	public void closeStage() {
		stage.close();
	}
	
	public void hideStage() {
		stage.hide();
	}
	
	public void printStageSize() {
		System.out.println(stage.heightProperty().get());
		System.out.println(stage.widthProperty().get());
	}
}
