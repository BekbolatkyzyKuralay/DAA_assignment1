public class Main {

    public static void main(String[] args) {
        System.out.println("Design and Analysis of Algorithms");
        System.out.println("Assignment 1");
        System.out.println();

        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPair();

        System.out.println();
        System.out.println("Running experiments...");

        Experiment experiment = new Experiment();
        experiment.runExperiments();
    }

    private static void testMergeSort() {
        int[] array = {8, 3, 5, 1, 9, 2};
        MergeSorter sorter = new MergeSorter();
        sorter.sort(array);
        System.out.print("MergeSort: ");
        for (int value : array) {
            System.out.print(value + " ");
        }

        System.out.println();
        System.out.println(
                "Comparisons: " + sorter.getComparisons()
        );
        System.out.println(
                "Max depth: " + sorter.getMaxDepth()
        );
        System.out.println();
    }

    private static void testQuickSort() {
        int[] array = {8, 3, 5, 1, 9, 2};
        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);
        System.out.print("QuickSort: ");
        for (int value : array) {
            System.out.print(value + " ");
        }

        System.out.println();
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
        int[] array = {8, 3, 5, 1, 9, 2};
        DeterministicSelector selector =
                new DeterministicSelector();
        int k = 2;
        int result = selector.select(array, k);
        System.out.println(
                "Deterministic Select (k = "
                        + k + "): " + result
        );
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
        System.out.println("Closest Pair:");
        System.out.println(result);
        System.out.println(
                "Comparisons: " + solver.getComparisons()
        );
        System.out.println(
                "Max depth: " + solver.getMaxDepth()
        );
    }
}
