package src.Errors;

public class InsuficientSpacesError extends Error {

    /**
     * Parameterless constructor
     */
    public InsuficientSpacesError(){}

    /**
     * Constructor with error message
     * 
     * @param message: the error message
     */
    public InsuficientSpacesError(String message){
        super(message);
    }
}
