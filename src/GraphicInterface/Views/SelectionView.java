package src.GraphicInterface.Views;

/**
 * Singleton class responsible for displaying selection stage
 */
public class SelectionView extends View{
	private static SelectionView instance;
	
	private SelectionView() {
		// does nothing, but we need this to be private
	}
	
	public static SelectionView getInstance(){
        if(instance == null) {
            instance= new SelectionView();
        }
        return instance;
    }
}
