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

        List<Lecture> allLectures = ClassScheduler.assignSchedules(courses, disciplineList);

        for (Map.Entry<SpaceType, List<Discipline>> entry : separatedDisciplines.entrySet()) {
            SpaceType spaceType = entry.getKey();
            List<Space> spaces = separatedSpaces.getOrDefault(spaceType, new ArrayList<>());

            if (spaces.isEmpty()) {
                System.err.println("No available spaces for type: " + spaceType);
                continue;
            }

            List<Lecture> filteredLectures = new ArrayList<>();
            for (Lecture lecture : allLectures) {
                Discipline discipline = disciplineList.stream()
                    .filter(d -> d.getDisciplineId().equals(lecture.getLectureDisciplineId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Discipline not found " + lecture.getLectureDisciplineId()));

                if (discipline.getRequiredSpaceType() == spaceType) {
                    filteredLectures.add(lecture);
                }
            }

            lectureSpace.putAll(SpaceAllocator.assignPlaces(spaces, filteredLectures));
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
