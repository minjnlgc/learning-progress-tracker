package tracker.domain;

import java.util.Comparator;

public class CourseStatsComparators {

    public static class PopularityComparator implements Comparator<CourseStatistics> {
        @Override
        public int compare(CourseStatistics cs1, CourseStatistics cs2) {
            return Integer.compare(cs1.getPopularity(), cs2.getPopularity());
        }
    }

    public static class ActivityComparator implements Comparator<CourseStatistics> {
        @Override
        public int compare(CourseStatistics cs1, CourseStatistics cs2) {
            return Integer.compare(cs1.getActivity(), cs2.getActivity());
        }
    }

    public static class DifficultyComparator implements Comparator<CourseStatistics> {
        @Override
        public int compare(CourseStatistics cs1, CourseStatistics cs2) {
            return Double.compare(cs1.getDifficulties(), cs2.getDifficulties());
        }
    }
}
