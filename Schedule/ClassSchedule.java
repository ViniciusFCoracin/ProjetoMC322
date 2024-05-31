package Schedule;

/**
 * Class that represents a schedule of a class,
 * with the day and hour of start and end.
 */
public class ClassSchedule {
    private WeekDay day;
    private int startHour;
    private int endHour;

    /**
     * Public constructor for ClassSchedule class
     * 
     * @param day: the week day of the class
     * @param startHour: the hour the class starts
     * @param endHour: the hour the class ends
     */
    public ClassSchedule(WeekDay day, int startHour, int endHour){
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public WeekDay getDay(){
        return this.day;
    }

    public int getStartHour(){
        return this.startHour;
    }

    public int getEndHour(){
        return this.endHour;
    }
}
