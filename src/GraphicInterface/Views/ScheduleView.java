package src.GraphicInterface.Views;

import java.io.IOException;

/**
 * Singleton class responsible for displaying schedule stage
 */
public class ScheduleView extends View {
	private static ScheduleView instance;
	
	private ScheduleView() throws IOException {
		super.setPrefWidth(1080);
		super.setPrefHeight(700);
	}
	
	public static ScheduleView getInstance() throws IOException{
        if(instance == null) {
            instance= new ScheduleView();
        }
        return instance;
    }
}
