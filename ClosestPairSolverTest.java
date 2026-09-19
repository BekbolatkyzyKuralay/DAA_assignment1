import java.util.Random;
public class ClosestPairSolverTest {
    public static void main(String[] args) {
        testSimplePoints();
        testDuplicatePoints();
        testRandomPoints();
    }

    public static void testSimplePoints() {
        Point[] points = {
                new Point(1, 1),
                new Point(5, 5),
                new Point(2, 2),
                new Point(10, 10)
        };

        test(points, "Simple points");
    }

    public static void testDuplicatePoints() {
        Point[] points = {
                new Point(1, 1),
                new Point(3, 4),
                new Point(1, 1),
                new Point(8, 8)
        };
        test(points, "Duplicate points");
    }

    public static void testRandomPoints() {
        Random random = new Random();
        int passed = 0;
        int failed = 0;
        for (int test = 0; test < 100; test++) {
            int size = random.nextInt(100) + 2;
            Point[] points = new Point[size];

            for (int i = 0; i < size; i++) {
                double x = random.nextDouble() * 1000;
                double y = random.nextDouble() * 1000;
                points[i] = new Point(x, y);
            }
            double expected = bruteForce(points);
            ClosestPairSolver solver = new ClosestPairSolver();
            ClosestPairSolver.Result result =
                    solver.findClosestPair(points);
            double actual = result.getDistance();
            if (Math.abs(expected - actual) < 0.000001) {
                passed++;
            } else {
                failed++;
                System.out.println(
                        "Random test " + (test + 1) + " FAILED"
                );
                System.out.println("Expected: " + expected);
                System.out.println("Actual: " + actual);
            }
        }
        System.out.println();
        System.out.println("Random Closest Pair Tests");
        System.out.println("Tests: 100");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        if (failed == 0) {
            System.out.println("All random tests PASSED");
        }
    }

    public static void test(Point[] points, String testName) {
        double expected = bruteForce(points);
        ClosestPairSolver solver = new ClosestPairSolver();
        ClosestPairSolver.Result result =
                solver.findClosestPair(points);
        double actual = result.getDistance();
        if (Math.abs(expected - actual) < 0.000001) {
            System.out.println(testName + ": PASSED");
        } else {
            System.out.println(testName + ": FAILED");
            System.out.println("Expected: " + expected);
            System.out.println("Actual: " + actual);
        }
    }
    public static double bruteForce(Point[] points) {
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance =
                        points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }
}
