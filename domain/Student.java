package tracker.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> courses;
    private List<Course> completedCourses;

    int id;

    public Student(String firstName, String lastName, String email, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.completedCourses = new ArrayList<>();
        courseInit();
    }

    private void courseInit() {
        this.courses = new ArrayList<>();
        courses.add(new Course("Java"));
        courses.add(new Course("DSA"));
        courses.add(new Course("Databases"));
        courses.add(new Course("Spring"));

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Course findCourse(String courseName) {
        for (Course course : courses) {
            if (course.getName().equals(courseName)) return course;
        }
        return null;
    }


    public void addPointsToCourse(int index, int point) {
        this.getCourses().get(index).addPoint(point);
    }

    public double getStudentProgress(CourseStatistics courseStatistics) {
        Course course = findCourse(courseStatistics.getCourseName());
        return (double) course.getPoint() / courseStatistics.getCompletePoints();
    }

    public List<String> checkCompletedCourse(HashMap<String, Integer> completedPointsMap) {
        List<String> completedCourseName = new ArrayList<>();
        for (Course course : this.courses) {
            if (course.getPoint() == completedPointsMap.get(course.getName())) {
                completedCourseName.add(course.getName());
                this.completedCourses.add(course);
            }
        }
        for (Course completedCourse : this.completedCourses) {
            this.courses.remove(completedCourse);
        }
        return completedCourseName;
    }


}
