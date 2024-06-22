package src.Errors;

import src.GraphicInterface.Controllers.SelectionController;

public class NoSpacesAvailableError extends Error {
    
    public NoSpacesAvailableError(){}

    public NoSpacesAvailableError(String message) {
        super(message);
        SelectionController.errorStage(message);
    }
}
