package src.GraphicInterface.Controllers;

import java.util.Arrays;
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
        List<SpaceType> types = Arrays.asList(spaceType);
        InstituteAbbr institute = parseInstitute(instituteField.getText());
        List<InstituteAbbr> institutes = Arrays.asList(institute);
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
        switch (typeString) {
            case "basicRoom":
                return SpaceType.BASIC_ROOM;
            case "slidesRoom":
                return SpaceType.SLIDES_ROOM;
            case "computerRoom":
                return SpaceType.COMPUTER_ROOM;
            case "physicsLaboratory":
                return SpaceType.PHYSICS_LABORATORY;
            case "chemistryLaboratory":
                return SpaceType.CHEMISTRY_LABORATORY;
            case "auditorium":
                return SpaceType.AUDITORIUM;
            case "court":
                return SpaceType.COURT;
            case "eletronicsLaboratory":
                return SpaceType.ELETRONICS_LABORATORY;
            default:
                throw new IllegalArgumentException("Invalid space type");
        }
    }

    private InstituteAbbr parseInstitute(String instituteString){
        switch (instituteString) {
            case "CB":
                return InstituteAbbr.CB;
            case "PB":
                return InstituteAbbr.PB;
            case "IC":
                return InstituteAbbr.IC;
            case "FEEC":
                return InstituteAbbr.FEEC;
            case "IMECC":
                return InstituteAbbr.IMECC;
            case "IFGW":
                return InstituteAbbr.IFGW;
            case "IEL":
                return InstituteAbbr.IEL;
            case "FEF":
                return InstituteAbbr.FEF;
            case "IE":
                return InstituteAbbr.IE;
            default:
                throw new IllegalArgumentException("Invalid institute");
        }
    }
}
