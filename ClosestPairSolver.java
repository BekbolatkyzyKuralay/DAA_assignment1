import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
        Point[] sortedByY = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(sortedByY, Comparator.comparingDouble(p -> p.y));

        bestDistance = Double.POSITIVE_INFINITY;
        bestPoint1 = null;
        bestPoint2 = null;
        comparisons = 0;
        maxDepth = 0;
        findClosest(sortedByX, sortedByY, 1);
        return new Result(
                bestPoint1,
                bestPoint2,
                bestDistance
        );
    }

    private double findClosest(
            Point[] sortedByX,
            Point[] sortedByY,
            int depth
    ) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        int size = sortedByX.length;
        if (size <= 3) {
            return bruteForce(sortedByX);
        }
        int middle = size / 2;
        double middleX = sortedByX[middle].x;
        Point[] leftX =
                Arrays.copyOfRange(sortedByX, 0, middle);
        Point[] rightX =
                Arrays.copyOfRange(sortedByX, middle, size);
        Set<Point> leftPoints = new HashSet<>();
        for (Point point : leftX) {
            leftPoints.add(point);
        }
        Point[] leftY = new Point[leftX.length];
        Point[] rightY = new Point[rightX.length];
        int leftIndex = 0;
        int rightIndex = 0;

        for (Point point : sortedByY) {
            if (leftPoints.contains(point)) {
                leftY[leftIndex] = point;
                leftIndex++;
            } else {
                rightY[rightIndex] = point;
                rightIndex++;
            }
        }

        double leftDistance =
                findClosest(leftX, leftY, depth + 1);
        double rightDistance =
                findClosest(rightX, rightY, depth + 1);
        double distance =
                Math.min(leftDistance, rightDistance);
        Point[] strip = new Point[size];
        int stripSize = 0;
        for (Point point : sortedByY) {
            if (Math.abs(point.x - middleX) < distance) {
                strip[stripSize] = point;
                stripSize++;
            }
        }

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
                }
                if (currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                    bestPoint1 = strip[i];
                    bestPoint2 = strip[j];
                }
            }
        }
        return distance;
    }

    private double bruteForce(Point[] points) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                comparisons++;
                double distance =
                        points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                }
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestPoint1 = points[i];
                    bestPoint2 = points[j];
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
