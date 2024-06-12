package src.GraphicInterface.Controllers;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import src.Schedule.HourOfClass;
import src.Schedule.WeekDay;

public class GridController {
	
	public static int convertSemesterToNumber(String currentSemester) {
		switch (currentSemester) {
        case "1°": currentSemester = "1"; break;
        case "2°": currentSemester = "2"; break;
        case "3°": currentSemester = "3"; break;
        case "4°": currentSemester = "4"; break;
        default: break;
		}
		return Integer.parseInt(currentSemester);
	}
	
	public static int getColumnFromWeekDay(WeekDay day) {
        switch (day) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            default:
                return 0;
        }
    }
	
	public static int getRowFromHourOfClass(HourOfClass hourOfClass) {
        switch (hourOfClass) {
            case EigthAM_TenAM:
                return 1;
            case TenAM_Midday:
                return 2;
            case TwoPM_FourPM:
                return 3;
            case FourPM_SixPM:
                return 4;
            case SevenPM_NinePM:
                return 5;
            case NinePM_ElevenPM:
                return 6;
            default:
                return 0;
        }
    }
	
	public static Node getNodeByRowColumnIndex(GridPane gridPane, int row, int column) {
		for(Node node : gridPane.getChildren()) {
	        Integer rowIndex = GridPane.getRowIndex(node);
	        rowIndex = rowIndex == null ? 0 : rowIndex;
	        Integer colIndex = GridPane.getColumnIndex(node);
	        colIndex = colIndex == null ? 0 : colIndex;
	        
	        if (rowIndex.equals(row) && colIndex.equals(column)) {
	            return node;
	        }
	    }
	    return null;
	}
}
