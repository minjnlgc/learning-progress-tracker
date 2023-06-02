package tracker.domain;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

public class CourseStatistics {

    private LinkedHashSet<Integer> enrolledStudents;
    private int totalSubmission;
    private int totalPoints;
    private int completePoints;
    private String courseName;

    public CourseStatistics(String courseName, int completePoints) {
        this.enrolledStudents = new LinkedHashSet<>();
        this.courseName = courseName;
        this.completePoints = completePoints;
        this.totalSubmission = 0;
        this.totalPoints = 0;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public int getPopularity() {
        return this.enrolledStudents.size();
    }

    public int getActivity() {
        return this.totalSubmission;
    }

    public void addSubmission() {
        this.totalSubmission++;
    }

    public void addTotalPoints(int point) {
        this.totalPoints+=point;
    }

    public double getDifficulties() {
        if (this.totalSubmission == 0) {
            return 0.0;
        } else {
            return (double) this.totalPoints / this.totalSubmission;
        }
    }

    public int getCompletePoints() {
        return this.completePoints;
    }

    public void enrollStudent(int studentId) {
        this.enrolledStudents.add(studentId);
    }

    public LinkedHashSet<Integer> getEnrolledStudents() {
        return enrolledStudents;
    }

}
