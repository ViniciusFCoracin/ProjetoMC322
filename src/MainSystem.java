package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Course.Course;
import src.Course.Discipline;
import src.Course.Lecture;
import src.Spaces.Space;
import src.Spaces.SpaceType;

public class MainSystem {

    public static Map<Lecture, String> assignSchedulesAndPlaces(List<Space> availableSpaces, List<Course> courses, List<Discipline> disciplineList) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = separateDisciplinesBySpaceRequirement(disciplineList);
        Map<SpaceType, List<Space>> separatedSpaces = separateSpacesByType(availableSpaces);

        Map<Lecture, String> lectureSpace = new HashMap<>();

        for (Map.Entry<SpaceType, List<Discipline>> entry : separatedDisciplines.entrySet()) {
            SpaceType spaceType = entry.getKey();
            List<Discipline> disciplines = entry.getValue();
            List<Space> spaces = separatedSpaces.getOrDefault(spaceType, new ArrayList<>());

            List<Lecture> lectures = ClassScheduler.assignSchedules(courses, disciplines);
            lectureSpace.putAll(SpaceAllocator.assignPlaces(spaces, lectures));
        }

        return lectureSpace;
    }

    private static Map<SpaceType, List<Discipline>> separateDisciplinesBySpaceRequirement(List<Discipline> disciplineList) {
        Map<SpaceType, List<Discipline>> separatedDisciplines = new HashMap<>();
        for (Discipline discipline : disciplineList) {
            SpaceType requiredSpaceType = discipline.getRequiredSpaceType();
            separatedDisciplines.computeIfAbsent(requiredSpaceType, k -> new ArrayList<>()).add(discipline);
        }
        return separatedDisciplines;
    }

    private static Map<SpaceType, List<Space>> separateSpacesByType(List<Space> spaceList) {
        Map<SpaceType, List<Space>> separatedSpaces = new HashMap<>();
        for (Space space : spaceList) {
            SpaceType spaceType = space.getSpaceType();
            separatedSpaces.computeIfAbsent(spaceType, k -> new ArrayList<>()).add(space);
        }
        return separatedSpaces;
    }
}
