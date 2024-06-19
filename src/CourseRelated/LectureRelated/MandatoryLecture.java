package src.CourseRelated.LectureRelated;

import src.CourseRelated.Course;
import src.CourseRelated.Disciplines.Discipline;
import src.Schedule.LectureSchedule;
import src.Spaces.Space;

public class MandatoryLecture extends Lecture{

    private Course course;

    public MandatoryLecture(Space space, Discipline discipline, LectureSchedule schedule, String professor, char group, Course lectureCourse){
        super(space, discipline, schedule, professor, group);

        this.course = lectureCourse;

    }

    public Course getLectureCourse(){
        return this.course;
    }
}
