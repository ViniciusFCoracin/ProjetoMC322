package src.GraphicInterface.Views;

public class SelectionView extends View{
	private static SelectionView instance;
	
	private SelectionView() {
		super();
	}
	
	public static SelectionView getInstance(){
        if(instance == null) {
            instance= new SelectionView();
        }
        return instance;
    }
}
