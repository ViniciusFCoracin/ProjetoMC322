package src.Schedule;

import java.util.Objects;
import src.Course.Course;
import src.Course.Shift;

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

    public static ClassSchedule newSchedule(Course course){
        if (course.getCourseShift() == Shift.FULL_TIME)
            return new ClassSchedule(WeekDay.MONDAY, HourOfClass.EigthAM_TenAM);
        else 
            return new ClassSchedule(WeekDay.MONDAY, HourOfClass.SevenPM_NinePM);
    }

    public static ClassSchedule nextSchedule(ClassSchedule schedule){
        WeekDay day = schedule.getDay();
        HourOfClass hour = schedule.getHourOfClass();

        if (hour == HourOfClass.EigthAM_TenAM) 
            return new ClassSchedule(day, HourOfClass.TenAM_Midday);
        else if (hour == HourOfClass.TenAM_Midday)
            return new ClassSchedule(day, HourOfClass.TwoPM_FourPM);
        else if (hour == HourOfClass.TwoPM_FourPM)
            return new ClassSchedule(day, HourOfClass.FourPM_SixPM);
        else if (hour == HourOfClass.SevenPM_NinePM)
            return new ClassSchedule(day, HourOfClass.NinePM_ElevenPM);
        else if (hour == HourOfClass.FourPM_SixPM){
            if (day == WeekDay.MONDAY)
                return new ClassSchedule(WeekDay.TUESDAY, HourOfClass.EigthAM_TenAM);
            else if (day == WeekDay.TUESDAY)
                return new ClassSchedule(WeekDay.WEDNESDAY, HourOfClass.EigthAM_TenAM);
            else if (day == WeekDay.WEDNESDAY)
                return new ClassSchedule(WeekDay.THURSDAY, HourOfClass.EigthAM_TenAM);
            else if (day == WeekDay.THURSDAY)
                return new ClassSchedule(WeekDay.FRIDAY, HourOfClass.EigthAM_TenAM);               
        }
        else if (hour == HourOfClass.NinePM_ElevenPM){
            if (day == WeekDay.MONDAY)
                return new ClassSchedule(WeekDay.TUESDAY, HourOfClass.SevenPM_NinePM);
            else if (day == WeekDay.TUESDAY)
                return new ClassSchedule(WeekDay.WEDNESDAY, HourOfClass.SevenPM_NinePM);
            else if (day == WeekDay.WEDNESDAY)
                return new ClassSchedule(WeekDay.THURSDAY, HourOfClass.SevenPM_NinePM);
            else if (day == WeekDay.THURSDAY)
                return new ClassSchedule(WeekDay.FRIDAY, HourOfClass.SevenPM_NinePM);
        }
        throw new Error("Schedule does not support so many classes");
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
