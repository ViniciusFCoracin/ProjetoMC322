package src.GraphicInterface.Views;

import javafx.stage.Stage;

public class SelectionView extends View{
	private static SelectionView instance;
	
	private SelectionView(Stage stage){
		super(stage);
	}
	
	public static SelectionView getInstance(Stage stage){
        if(instance == null) {
            instance= new SelectionView(stage);
        }
        return instance;
    }
}
