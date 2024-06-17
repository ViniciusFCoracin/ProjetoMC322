package src.GraphicInterface.Views;

/**
 * Singleton class responsible for displaying schedule stage
 */
public class ScheduleView extends View {
	private static ScheduleView instance;
	
	private ScheduleView(){
		// does nothing, but we need this to be private
	}
	
	public static ScheduleView getInstance(){
        if(instance == null) {
            instance= new ScheduleView();
        }
        return instance;
    }
}
