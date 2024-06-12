package src.GraphicInterface.Views;

import javafx.stage.Stage;

public class ElectivesView extends View{
	private static ElectivesView instance;
	
	private ElectivesView(Stage stage){
		super(stage);
	}
	
	public static ElectivesView getInstance(Stage stage){
        if(instance == null) {
            instance= new ElectivesView(stage);
        }
        return instance;
    }
}

