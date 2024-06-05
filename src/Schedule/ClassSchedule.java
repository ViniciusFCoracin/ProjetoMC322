package src.Schedule;

import java.util.Objects;

/**
 * Class that represents a schedule of a class,
 * with the day and hour of start and end.
 */
public class ClassSchedule {
    private WeekDay day;
    private HourOfClass hourOfClass;

    /**
     * Public constructor for ClassSchedule class
     * 
     * @param day: the week day of the class
     * @param startHour: the hour the class starts
     * @param endHour: the hour the class ends
     */
    public ClassSchedule(WeekDay day, HourOfClass hourOfClass){
        this.day = day;
        this.hourOfClass = hourOfClass;
    }

    public WeekDay getDay(){
        return this.day;
    }

    public HourOfClass getHourOfClass(){
        return hourOfClass;
    }

    /**
     * Implementing a particular equals() method so the schedules are properly compared
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassSchedule that = (ClassSchedule) o;
        return this.hourOfClass == that.hourOfClass && this.day == that.day;
    }

    /**
     * Implementing a particular hashCode() method so the schedules are properly compared and to keep consistency with the equals() override
     */
    @Override
    public int hashCode() {
        return Objects.hash(day, hourOfClass);
    }
}
