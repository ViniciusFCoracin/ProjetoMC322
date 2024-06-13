package src.GraphicInterface.Views;

import javafx.stage.Stage;

public class ScheduleView extends View {
	private static ScheduleView instance;
	
	private ScheduleView(Stage stage){
		super(stage);
	}
	
	public static ScheduleView getInstance(Stage stage){
        if(instance == null) {
            instance= new ScheduleView(stage);
        }
        return instance;
    }
}
