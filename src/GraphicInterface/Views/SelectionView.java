package src.GraphicInterface.Views;

import java.io.IOException;

/**
 * Singleton class responsible for displaying selection stage
 */
public class SelectionView extends View{
	private static SelectionView instance;
	
	private SelectionView() throws IOException {
		super.setPrefWidth(1290);
		super.setPrefHeight(910);
	}
	
	public static SelectionView getInstance() throws IOException{
        if(instance == null) {
            instance= new SelectionView();
        }
        return instance;
    }
}
