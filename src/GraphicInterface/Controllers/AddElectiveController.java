package src.GraphicInterface.Controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.CourseRelated.Discipline;
import src.Spaces.InstituteAbbr;
import src.Spaces.SpaceType;

public class AddElectiveController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField idField;
    @FXML
    private TextField creditsField;
    @FXML
    private TextField locationTypeField;
    @FXML
    private TextField instituteField;
    @FXML
    private TextField professorsField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Discipline electiveDiscipline;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Discipline getElectiveAdded(){
        return this.electiveDiscipline;
    }

    @FXML
    private void handleOk() {
        String name = nameField.getText();
        String id = idField.getText();
        int credits = Integer.parseInt(creditsField.getText());
        SpaceType spaceType = parseSpaceType(locationTypeField.getText());
        List<SpaceType> types = Collections.singletonList(spaceType);
        InstituteAbbr institute = parseInstitute(instituteField.getText());
        List<InstituteAbbr> institutes = Collections.singletonList(institute);
        List<String> professors = Arrays.asList(professorsField.getText().split(","));

        electiveDiscipline = new Discipline(name, id, credits, types, institutes, professors);
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
            default -> throw new IllegalArgumentException("Invalid space type");
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
            default -> throw new IllegalArgumentException("Invalid institute");
        };
    }
}
