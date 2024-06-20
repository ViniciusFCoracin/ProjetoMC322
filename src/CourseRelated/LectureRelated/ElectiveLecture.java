package src.CourseRelated.LectureRelated;

import src.CourseRelated.Disciplines.Discipline;
import src.Schedule.LectureSchedule;
import src.Spaces.Space;

public class ElectiveLecture extends Lecture{
    public ElectiveLecture(Space space, Discipline discipline, LectureSchedule schedule, String professor, char group){
        super(space, discipline, schedule, professor, group);
    }
}
