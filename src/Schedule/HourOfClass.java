package src.Schedule;

/**
 * Enum that represents possible hours of class
 */
public enum HourOfClass {
    EigthAM_TenAM,
    TenAM_Midday,
    TwoPM_FourPM,
    FourPM_SixPM,
    SevenPM_NinePM,
    NinePM_ElevenPM;

    public static int compare(HourOfClass hour1, HourOfClass hour2){
        int hour1Value = getNumericValue(hour1);
        int hour2value = getNumericValue(hour2);
        return hour1Value - hour2value;        
    }

    public static int getNumericValue(HourOfClass hour){
        switch (hour){
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
                throw new Error("Indefinite hour of class");
        }
    }

    public static HourOfClass get(int index) {
        return values()[index];
    }
}
