import java.util.Arrays;
import java.util.Comparator;
public class ClosestPairSolver {
    private double bestDistance;
    private Point bestPoint1;
    private Point bestPoint2;
    private long comparisons = 0;
    private int maxDepth = 0;
    public Result findClosestPair(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException(
                    "At least two points are needed"
            );
        }
        Point[] sortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));
        bestDistance = Double.POSITIVE_INFINITY;
        bestPoint1 = null;
        bestPoint2 = null;
        comparisons = 0;
        maxDepth = 0;
        findClosest(sortedByX, 0, sortedByX.length - 1, 1);
        return new Result(bestPoint1, bestPoint2, bestDistance);
    }
    private double findClosest(
            Point[] points,
            int left,
            int right,
            int depth
    ) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        int size = right - left + 1;
        if (size <= 3) {
            return bruteForce(points, left, right);
        }
        int middle = (left + right) / 2;
        double middleX = points[middle].x;
        double leftDistance =
                findClosest(points, left, middle, depth + 1);
        double rightDistance =
                findClosest(points, middle + 1, right, depth + 1);
        double distance = Math.min(leftDistance, rightDistance);
        if (distance < bestDistance) {
            bestDistance = distance;
        }
        Point[] strip = new Point[size];
        int stripSize = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - middleX) < distance) {
                strip[stripSize++] = points[i];
            }
        }
        Arrays.sort(
                strip,
                0,
                stripSize,
                Comparator.comparingDouble(p -> p.y)
        );
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1;
                 j < stripSize &&
                         strip[j].y - strip[i].y < distance;
                 j++) {
                comparisons++;
                double currentDistance =
                        strip[i].distanceTo(strip[j]);
                if (currentDistance < distance) {
                    distance = currentDistance;
                    if (currentDistance < bestDistance) {
                        bestDistance = currentDistance;
                        bestPoint1 = strip[i];
                        bestPoint2 = strip[j];
                    }
                }
            }
        }
        return distance;
    }
    private double bruteForce(
            Point[] points,
            int left,
            int right
    ) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                comparisons++;
                double distance =
                        points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestPoint1 = points[i];
                        bestPoint2 = points[j];
                    }
                }
            }
        }
        return minDistance;
    }
    public long getComparisons() {
        return comparisons;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
    public static class Result {
        private final Point point1;
        private final Point point2;
        private final double distance;
        public Result(
                Point point1,
                Point point2,
                double distance
        ) {
            this.point1 = point1;
            this.point2 = point2;
            this.distance = distance;
        }
        public Point getPoint1() {
            return point1;
        }
        public Point getPoint2() {
            return point2;
        }
        public double getDistance() {
            return distance;
        }
        @Override
        public String toString() {
            return "Closest points: "
                    + point1
                    + " and "
                    + point2
                    + ", distance = "
                    + distance;
        }
    }
}