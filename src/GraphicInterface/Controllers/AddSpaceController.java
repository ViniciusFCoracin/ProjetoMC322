package src.GraphicInterface.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.Spaces.InstituteAbbr;
import src.Spaces.Space;
import src.Spaces.SpaceType;

public class AddSpaceController {
    @FXML
    private TextField spaceNameField;
    @FXML
    private TextField instituteField;
    @FXML
    private TextField typeField;
    
    private Stage dialogStage;
    private boolean okClicked = false;
    private Space space;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    public Space getSpace() {
        return this.space;
    }
    
    @FXML
    private void handleOk() {
        String spaceID = spaceNameField.getText();
        SpaceType type = parseSpaceType(typeField.getText());
        InstituteAbbr institute = parseInstitute(instituteField.getText());

        space = new Space(spaceID, type, institute);
        okClicked = true;
        dialogStage.close();
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private SpaceType parseSpaceType(String typeString){
        SpaceType type = null;
        if (typeString.equals("basicRoom"))
            type = SpaceType.BASIC_ROOM;
        else if (typeString.equals("slidesRoom"))
            type = SpaceType.SLIDES_ROOM;
        else if (typeString.equals("computerRoom"))
            type = SpaceType.COMPUTER_ROOM;
        else if (typeString.equals("physicsLaboratory"))
            type = SpaceType.PHYSICS_LABORATORY;
        else if (typeString.equals("chemistryLaboratory"))
            type = SpaceType.CHEMISTRY_LABORATORY;
        else if (typeString.equals("auditorium"))
            type = SpaceType.AUDITORIUM;
        else if (typeString.equals("court"))
            type = SpaceType.COURT;
        else if (typeString.equals("eletronicsLaboratory"))
            type = SpaceType.ELETRONICS_LABORATORY;
        else
            throw new Error("Invalid space type");
        return type;
    }

    private InstituteAbbr parseInstitute(String instituteString){
        InstituteAbbr institute = null;
        if (instituteString.equals("CB"))
            institute = InstituteAbbr.CB;
        else if (instituteString.equals("PB"))
            institute = InstituteAbbr.PB;
        else if (instituteString.equals("IC"))
            institute = InstituteAbbr.IC;
        else if (instituteString.equals("FEEC"))
            institute = InstituteAbbr.FEEC;
        else if (instituteString.equals("IMECC"))
            institute = InstituteAbbr.IMECC;
        else if (instituteString.equals("IFGW"))
            institute = InstituteAbbr.IFGW;
        else if (instituteString.equals("IEL"))
            institute = InstituteAbbr.IEL;
        else if (instituteString.equals("FEF"))
            institute = InstituteAbbr.FEF;
        else if (instituteString.equals("IE"))
            institute = InstituteAbbr.IE;
        else
            throw new Error("Invalid institute");

        return institute;
    }
}
