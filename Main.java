import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Design and Analysis of Algorithms Assignment1 ===");
        System.out.println();
        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPair();

        System.out.println();
        System.out.println("All basic tests finished.");

        System.out.println();
        System.out.println("Running experiments...");

        Experiment experiment = new Experiment();
        experiment.runExperiments();
    }
    private static void testMergeSort() {
        System.out.println("--- MergeSort test ---");
        int[] array = {8, 3, 5, 1, 9, 2, 7, 4};
        int[] expected = Arrays.copyOf(array, array.length);
        Arrays.sort(expected);
        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);
        System.out.println("Result:   " + Arrays.toString(array));
        System.out.println("Expected: " + Arrays.toString(expected));
        if (Arrays.equals(array, expected)) {
            System.out.println("MergeSort: PASS");
        } else {
            System.out.println("MergeSort: FAIL");
        }
        System.out.println(
                "Comparisons: " + sorter.getComparisons()
        );
        System.out.println(
                "Max depth: " + sorter.getMaxDepth()
        );
        System.out.println();
    }
    private static void testQuickSort() {
        System.out.println("--- QuickSort test ---");
        int[] array = {10, 4, 7, 2, 8, 1, 9, 3, 6, 5};
        int[] expected = Arrays.copyOf(array, array.length);
        Arrays.sort(expected);
        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);
        System.out.println("Result:   " + Arrays.toString(array));
        System.out.println("Expected: " + Arrays.toString(expected));
        if (Arrays.equals(array, expected)) {
            System.out.println("QuickSort: PASS");
        } else {
            System.out.println("QuickSort: FAIL");
        }
        System.out.println(
                "Comparisons: " + sorter.getComparisons()
        );
        System.out.println(
                "Swaps: " + sorter.getSwaps()
        );
        System.out.println(
                "Max depth: " + sorter.getMaxDepth()
        );
        System.out.println();
    }
    private static void testDeterministicSelect() {
        System.out.println("--- Deterministic Select test ---");
        int[] array = {10, 4, 7, 2, 8, 1, 9, 3, 6, 5};
        int k = 4;
        int[] sorted = Arrays.copyOf(array, array.length);
        Arrays.sort(sorted);
        int expected = sorted[k];
        DeterministicSelector selector =
                new DeterministicSelector();
        int result = selector.select(array, k);
        System.out.println("k = " + k);
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
        if (result == expected) {
            System.out.println("Deterministic Select: PASS");
        } else {
            System.out.println("Deterministic Select: FAIL");
        }
        System.out.println(
                "Comparisons: " + selector.getComparisons()
        );
        System.out.println(
                "Swaps: " + selector.getSwaps()
        );
        System.out.println(
                "Max depth: " + selector.getMaxDepth()
        );
        System.out.println();
    }
    private static void testClosestPair() {
        System.out.println("--- Closest Pair test ---");
        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };
        ClosestPairSolver solver =
                new ClosestPairSolver();
        ClosestPairSolver.Result result =
                solver.findClosestPair(points);
        System.out.println(result);
        System.out.println(
                "Comparisons: " + solver.getComparisons()
        );
        System.out.println(
                "Max depth: " + solver.getMaxDepth()
        );
        System.out.println();
    }
}