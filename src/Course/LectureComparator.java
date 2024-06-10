package src.Course;

import java.util.Comparator;
import src.Schedule.LectureSchedule;

public class LectureComparator implements Comparator<Lecture> {
    @Override
    public int compare(Lecture lecture1, Lecture lecture2){
        LectureSchedule schedule1 = lecture1.getLectureSchedule();
        LectureSchedule schedule2 = lecture2.getLectureSchedule();
        Course course1 = lecture1.getCourse();
        Course course2 = lecture2.getCourse();
        
        if (course1 == null && course2 == null) {
            // If both courses are null, compare the schedules
            return LectureSchedule.compare(schedule1, schedule2);
        } else if (course1 == null) {
            // If only course1 is null, put it in the end
            return 1;
        } else if (course2 == null) {
            // If only course2 is null, put it in the end
            return -1;
        } else {

            int semester1 = course1.getDisciplineSemester(lecture1.getLectureDiscipline());
            int semester2 = course2.getDisciplineSemester(lecture2.getLectureDiscipline());

            if (lecture1.getLectureSchedule().equals(lecture2.getLectureSchedule()))
                return 0;
            else if (Course.compare(course1, course2) > 0 ||
                     Course.compare(course1, course2) == 0 && semester1 > semester2 ||
                     Course.compare(course1, course2) == 0 && semester1 == semester2 && LectureSchedule.compare(schedule1, schedule2) > 0)
                return 1;
            else
                return -1;

        }
    }
}
