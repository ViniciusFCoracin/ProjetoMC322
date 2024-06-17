package src.Schedule;

/**
 * Enum that represents days of the week
 */
public enum WeekDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY;

    public static int compare(WeekDay day1, WeekDay day2){
        int day1Value = getNumericValue(day1);
        int day2value = getNumericValue(day2);
        return day1Value - day2value;        
    }

    public static int getNumericValue(WeekDay day){
        switch (day){
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
                throw new Error("Indefinite week day");
        }
    }

    public static WeekDay get(int index) {
        return values()[index];
    }
}
