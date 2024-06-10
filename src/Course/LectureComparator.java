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
