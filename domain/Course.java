package tracker.domain;

public class Course {
    private String name;
    private int point;

    public Course(String name) {
        this.name = name;
        this.point = 0;
    }

    public int getPoint() {
        return point;
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public String getName() {
        return name;
    }


}
