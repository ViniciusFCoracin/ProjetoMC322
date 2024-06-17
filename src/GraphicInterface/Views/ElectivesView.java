package src.GraphicInterface.Views;

/**
 * Singleton class responsible for displaying electives stage
 */
public class ElectivesView extends View{
	private static ElectivesView instance;
	
	private ElectivesView() {
		 // does nothing, but we need this to be private
	}
	
	public static ElectivesView getInstance(){
        if(instance == null) {
            instance= new ElectivesView();
        }
        return instance;
    }
}

