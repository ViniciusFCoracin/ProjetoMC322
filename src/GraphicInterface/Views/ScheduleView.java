package src.GraphicInterface.Views;

public class ScheduleView extends View {
	private static ScheduleView instance;
	
	private ScheduleView(){

	}
	
	public static ScheduleView getInstance(){
        if(instance == null) {
            instance= new ScheduleView();
        }
        return instance;
    }
}
