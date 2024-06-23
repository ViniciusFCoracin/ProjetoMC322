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
        return switch (typeString) {
            case "basicRoom" -> SpaceType.BASIC_ROOM;
            case "slidesRoom" -> SpaceType.SLIDES_ROOM;
            case "computerRoom" -> SpaceType.COMPUTER_ROOM;
            case "physicsLaboratory" -> SpaceType.PHYSICS_LABORATORY;
            case "chemistryLaboratory" -> SpaceType.CHEMISTRY_LABORATORY;
            case "auditorium" -> SpaceType.AUDITORIUM;
            case "court" -> SpaceType.COURT;
            case "eletronicsLaboratory" -> SpaceType.ELETRONICS_LABORATORY;
            default -> throw new Error("Invalid space type");
        };
    }

    private InstituteAbbr parseInstitute(String instituteString){
        return switch (instituteString) {
            case "CB" -> InstituteAbbr.CB;
            case "PB" -> InstituteAbbr.PB;
            case "IC" -> InstituteAbbr.IC;
            case "FEEC" -> InstituteAbbr.FEEC;
            case "IMECC" -> InstituteAbbr.IMECC;
            case "IFGW" -> InstituteAbbr.IFGW;
            case "IEL" -> InstituteAbbr.IEL;
            case "FEF" -> InstituteAbbr.FEF;
            case "IE" -> InstituteAbbr.IE;
            default -> throw new Error("Invalid institute");
        };
    }
}
