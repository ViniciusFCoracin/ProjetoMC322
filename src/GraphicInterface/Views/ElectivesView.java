package src.GraphicInterface.Views;

public class ElectivesView extends View{
	private static ElectivesView instance;
	
	private ElectivesView() {

	}
	
	public static ElectivesView getInstance(){
        if(instance == null) {
            instance= new ElectivesView();
        }
        return instance;
    }
}

