package tracker.Controller;

import tracker.domain.Course;
import tracker.domain.CourseStatistics;
import tracker.domain.CourseStatsComparators;
import tracker.domain.Student;

import java.util.*;
import java.util.stream.Collectors;

public class LearningPlatform {
    private int lastAssignedId = 9999;
    private HashMap<Integer, Student> students;
    private HashMap<String, CourseStatistics> courseStatsMap;
    final static String JAVA = "Java";
    final static String DSA = "DSA";
    final static String DATABASES = "Databases";
    final static String SPRING = "Spring";

    public LearningPlatform() {
        students = new HashMap<>();
        courseStatsInit();
    }

    private void courseStatsInit() {
        courseStatsMap = new HashMap<>();

        courseStatsMap.put(JAVA, new CourseStatistics(JAVA, 600));
        courseStatsMap.put(DSA, new CourseStatistics(DSA, 400));
        courseStatsMap.put(DATABASES, new CourseStatistics(DATABASES, 480));
        courseStatsMap.put(SPRING, new CourseStatistics(SPRING, 550));
    }

    public HashMap<Integer, Student> getStudents() {
        return students;
    }

    public void addStudentIntoPlatform(String firstName, String lastName, String email) {
        int id = nextId();
        students.put(id, new Student(firstName, lastName, email, id));
    }

    private int nextId() {
        return ++lastAssignedId;
    }

    public boolean uniqueEmailChecker(String email) {
        for (Student student : this.getStudents().values()) {
            if (student.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public void addPointsToCourse(String[] inputs) {

        Student student = null;
        int id = 0;

        try {
            id = Integer.parseInt(inputs[0]);
            student = findStudentById(id);

        } catch (NumberFormatException e) {
            System.out.printf("No student is found for id=%s.\n", inputs[0]);
            return;
        }

        if (student == null) {
            System.out.printf("No student is found for id=%s.\n", id);
            return;
        }

        for (int i = 1; i < inputs.length; i++) {
            try {
                int point = Integer.parseInt(inputs[i]);
                if (point < 0) {
                    System.out.println("Incorrect points format.");
                    return;
                }
                // update student's course points
                student.addPointsToCourse(i - 1, point);

                // update the course stats according to course name
                String courseName = student.getCourses().get(i - 1).getName();
                if (point > 0) {
                    courseStatsMap.get(courseName).enrollStudent(id);
                    courseStatsMap.get(courseName).addTotalPoints(point);
                    courseStatsMap.get(courseName).addSubmission();
                }

            } catch (NumberFormatException e) {
                System.out.println("Incorrect points format.");
                return;
            }
        }

        System.out.println("Points updated.");
    }


    public void findStudentCourseDetails(int inputId) {
        Student student = this.findStudentById(inputId);
        if (student == null) {
            System.out.printf("No student is found for id=%s.\n", inputId);
        } else {
            List<Course> courses = student.getCourses();
            System.out.printf("%s points: Java=%s; DSA=%s; Databases=%s; Spring=%s\n",
                    inputId, courses.get(0).getPoint(), courses.get(1).getPoint(),
                    courses.get(2).getPoint(), courses.get(3).getPoint());
        }
    }

    public Student findStudentById(int id) {
        for (int key : this.students.keySet()) {
            if (key == id) {
                return students.get(id);
            }
        }
        return null;
    }


    public void showAllCourseStats() {
        CourseStatistics maxPopularity = this.courseStatsMap.values()
                .stream()
                .max(new CourseStatsComparators.PopularityComparator())
                .orElse(null);

        CourseStatistics minPopularity = this.courseStatsMap.values()
                .stream()
                .min(new CourseStatsComparators.PopularityComparator())
                .orElse(null);

        CourseStatistics maxActivity = this.courseStatsMap.values()
                .stream()
                .max(new CourseStatsComparators.ActivityComparator())
                .orElse(null);

        CourseStatistics minActivity = this.courseStatsMap.values()
                .stream()
                .min(new CourseStatsComparators.ActivityComparator())
                .orElse(null);

        CourseStatistics maxDifficulty = this.courseStatsMap.values()
                .stream()
                .max(new CourseStatsComparators.DifficultyComparator())
                .orElse(null);

        CourseStatistics minDifficulty = this.courseStatsMap.values()
                .stream()
                .min(new CourseStatsComparators.DifficultyComparator())
                .orElse(null);

        List<String> popularCourseNames = this.courseStatsMap.values()
                .stream()
                .filter(course -> course.getPopularity() == maxPopularity.getPopularity())
                .map(CourseStatistics::getCourseName)
                .collect(Collectors.toList());

        List<String> leastPopularCourseNames = this.courseStatsMap.values()
                .stream()
                .filter(course -> course.getPopularity() == minPopularity.getPopularity())
                .map(CourseStatistics::getCourseName)
                .collect(Collectors.toList());

        List<String> activeCourseNames = this.courseStatsMap.values()
                .stream()
                .filter(course -> course.getActivity() == maxActivity.getActivity())
                .map(CourseStatistics::getCourseName)
                .collect(Collectors.toList());

        List<String> leastActiveCourseNames = this.courseStatsMap.values()
                .stream()
                .filter(course -> course.getActivity() == minActivity.getActivity())
                .map(CourseStatistics::getCourseName)
                .collect(Collectors.toList());

        List<String> easyCourseNames = this.courseStatsMap.values()
                .stream()
                .filter(course -> course.getDifficulties() == maxDifficulty.getDifficulties())
                .map(CourseStatistics::getCourseName)
                .collect(Collectors.toList());

        List<String> hardCourseNames = this.courseStatsMap.values()
                .stream()
                .filter(course -> course.getDifficulties() == minDifficulty.getDifficulties())
                .map(CourseStatistics::getCourseName)
                .collect(Collectors.toList());

        System.out.printf("""
                        Most popular: %s
                        Least popular: %s
                        Highest activity: %s
                        Lowest activity: %s
                        Easiest course: %s
                        Hardest course: %s
                        """,
                (maxPopularity.getPopularity() != 0 ? String.join(", ", popularCourseNames) : "n/a"),
                (minPopularity.getPopularity() != 0 && !leastPopularCourseNames.equals(popularCourseNames) ? String.join(", ", leastPopularCourseNames) : "n/a"),
                (maxActivity.getActivity() != 0 ? String.join(", ", activeCourseNames) : "n/a"),
                (minActivity.getActivity() != 0 && !activeCourseNames.equals(leastActiveCourseNames) ? String.join(", ", leastActiveCourseNames) : "n/a"),
                (maxDifficulty.getDifficulties() != 0.0  ? String.join(", ", easyCourseNames) : "n/a"),
                (minDifficulty.getDifficulties() != 0.0 && !easyCourseNames.equals(hardCourseNames) ? String.join(", ", hardCourseNames) : "n/a"));
    }


    private String findMatchingKey(String courseName) {
        for (String key : courseStatsMap.keySet()) {
            if (key.equalsIgnoreCase(courseName)) {
                return key;
            }
        }
        return null;
    }

    public void showCourseLearners(String courseName) {

        String matchingKey = findMatchingKey(courseName);

        if (matchingKey == null) {
            System.out.println("Unknown course.");
            return;
        }

        System.out.println(matchingKey);
        System.out.println("id    points    completed");

        CourseStatistics courseStatistics = this.courseStatsMap.get(matchingKey);

        if (courseStatistics.getPopularity() != 0) {
            LinkedHashSet<Integer> enrolledStudents = courseStatistics.getEnrolledStudents();

            ArrayList<Integer> sortedStudentId = enrolledStudents.stream()
                    .sorted(Comparator.comparingDouble(id -> students.get(id).getStudentProgress(courseStatistics)).reversed())
                    .collect(Collectors.toCollection(ArrayList::new));

            for (int i = 0; i < sortedStudentId.size(); i++) {

                int studentId = sortedStudentId.get(i);
                Student student = students.get(studentId);
                int studentPoints = student.findCourse(matchingKey).getPoint();
                double studentProgress = student.getStudentProgress(courseStatistics);

                System.out.printf("%s %3s        %.1f%%\n", studentId, studentPoints, studentProgress * 100);
            }
        }
    }

    public void notifyCompletedStudent() {
        HashSet<Student> notifiedStudents = new HashSet<>();
        HashMap<String, Integer> completedPointsMap = getCompletedPointsMap();

        for (Student student : students.values()) {
            List<String> completeCourseName = student.checkCompletedCourse(completedPointsMap);
            if (completeCourseName.size() != 0) {
                for (String courseName : completeCourseName) {
                    generateNotifyMessage(courseName, student);
                    notifiedStudents.add(student);
                }
            }
        }
        System.out.printf("Total %d students have been notified.\n", notifiedStudents.size());
    }


    private void generateNotifyMessage(String courseName, Student student) {
        System.out.printf(
                """
                To: %s
                Re: Your Learning Progress
                Hello, %s %s! You have accomplished our %s course!
                """,
                student.getEmail(),
                student.getFirstName(),
                student.getLastName(),
                courseName);
    }

    private HashMap<String, Integer> getCompletedPointsMap() {
        HashMap<String, Integer> completedPointsMap = new HashMap<>();
        for (Map.Entry<String, CourseStatistics> entry : courseStatsMap.entrySet()) {
            int completePoints = entry.getValue().getCompletePoints();
            completedPointsMap.put(entry.getKey(), completePoints);
        }
        return completedPointsMap;
    }

}
