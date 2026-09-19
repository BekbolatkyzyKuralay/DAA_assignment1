import java.util.Arrays;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Experiment {
    private final Random random = new Random();
    public long measureMergeSort(int[] input) {
        MergeSorter sorter = new MergeSorter();
        int[] array = Arrays.copyOf(input, input.length);
        long start = System.nanoTime();
        sorter.sort(array);
        long end = System.nanoTime();
        return end - start;
    }
    public long measureQuickSort(int[] input) {
        QuickSorter sorter = new QuickSorter();
        int[] array = Arrays.copyOf(input, input.length);
        long start = System.nanoTime();
        sorter.sort(array);
        long end = System.nanoTime();
        return end - start;
    }
    public long measureSelect(int[] input, int k) {
        DeterministicSelector selector =
                new DeterministicSelector();
        int[] array = Arrays.copyOf(input, input.length);
        long start = System.nanoTime();
        selector.select(array, k);
        long end = System.nanoTime();
        return end - start;
    }
    public long measureClosestPair(Point[] input) {
        ClosestPairSolver solver =
                new ClosestPairSolver();
        Point[] points = Arrays.copyOf(input, input.length);
        long start = System.nanoTime();
        solver.findClosestPair(points);
        long end = System.nanoTime();
        return end - start;
    }
    public int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size * 10 + 1);
        }
        return array;
    }
    public int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
    public int[] generateReverseSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }
    public int[] generateDuplicateArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10);
        }
        return array;
    }
    public Point[] generateRandomPoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            double x = random.nextDouble() * 10000;
            double y = random.nextDouble() * 10000;
            points[i] = new Point(x, y);
        }
        return points;
    }
    public void printSortingResult(int[] input) {
        System.out.println(
                "Input: " + Arrays.toString(input)
        );
        int[] mergeArray = Arrays.copyOf(
                input,
                input.length
        );
        int[] quickArray = Arrays.copyOf(
                input,
                input.length
        );
        MergeSorter mergeSorter = new MergeSorter();
        QuickSorter quickSorter = new QuickSorter();
        long start = System.nanoTime();
        mergeSorter.sort(mergeArray);
        long mergeTime = System.nanoTime() - start;
        start = System.nanoTime();
        quickSorter.sort(quickArray);
        long quickTime = System.nanoTime() - start;
        System.out.println(
                "MergeSort: "
                        + Arrays.toString(mergeArray)
        );
        System.out.println(
                "QuickSort: "
                        + Arrays.toString(quickArray)
        );
        System.out.println(
                "MergeSort time: "
                        + mergeTime
                        + " ns"
        );
        System.out.println(
                "QuickSort time: "
                        + quickTime
                        + " ns"
        );
        System.out.println(
                "MergeSort comparisons: "
                        + mergeSorter.getComparisons()
        );
        System.out.println(
                "QuickSort comparisons: "
                        + quickSorter.getComparisons()
        );
        System.out.println(
                "QuickSort swaps: "
                        + quickSorter.getSwaps()
        );
        System.out.println(
                "MergeSort max depth: "
                        + mergeSorter.getMaxDepth()
        );
        System.out.println(
                "QuickSort max depth: "
                        + quickSorter.getMaxDepth()
        );
    }
    public void runExperiments() {
        int[] sizes = {100, 1000, 10000};

        File folder = new File("results");
        if (!folder.exists()) {
            folder.mkdir();
        }

        try {
            FileWriter writer = new FileWriter("results/results.csv");

            writer.write(
                    "Algorithm,InputType,Size,TimeNs,MaxDepth,Comparisons,Swaps\n"
            );

            for (int size : sizes) {
                System.out.println("Testing size: " + size);

                int[] randomArray = generateRandomArray(size);
                int[] sortedArray = generateSortedArray(size);
                int[] reverseArray = generateReverseSortedArray(size);
                int[] duplicateArray = generateDuplicateArray(size);

                runMerge(writer, randomArray, "Random");
                runMerge(writer, sortedArray, "Sorted");
                runMerge(writer, reverseArray, "Reverse");
                runMerge(writer, duplicateArray, "Duplicates");

                runQuick(writer, randomArray, "Random");
                runQuick(writer, sortedArray, "Sorted");
                runQuick(writer, reverseArray, "Reverse");
                runQuick(writer, duplicateArray, "Duplicates");

                runSelect(writer, randomArray, "Random");
                runSelect(writer, sortedArray, "Sorted");
                runSelect(writer, reverseArray, "Reverse");
                runSelect(writer, duplicateArray, "Duplicates");

                Point[] points = generateRandomPoints(size);
                runClosest(writer, points);
            }

            writer.close();

            System.out.println("Experiments finished.");
            System.out.println("Results saved to results/results.csv");

        } catch (IOException e) {
            System.out.println("Error saving results.");
        }
    }

    private void runMerge(
            FileWriter writer, int[] input, String type
    ) throws IOException {

        int[] array = input.clone();
        MergeSorter sorter = new MergeSorter();

        long start = System.nanoTime();
        sorter.sort(array);
        long time = System.nanoTime() - start;

        writer.write(
                "MergeSort," + type + "," + input.length + ","
                        + time + "," + sorter.getMaxDepth() + ","
                        + sorter.getComparisons() + ",0\n"
        );
    }

    private void runQuick(
            FileWriter writer, int[] input, String type
    ) throws IOException {

        int[] array = input.clone();
        QuickSorter sorter = new QuickSorter();

        long start = System.nanoTime();
        sorter.sort(array);
        long time = System.nanoTime() - start;

        writer.write(
                "QuickSort," + type + "," + input.length + ","
                        + time + "," + sorter.getMaxDepth() + ","
                        + sorter.getComparisons() + ","
                        + sorter.getSwaps() + "\n"
        );
    }

    private void runSelect(
            FileWriter writer, int[] input, String type
    ) throws IOException {

        int[] array = input.clone();
        DeterministicSelector selector = new DeterministicSelector();

        int k = array.length / 2;

        long start = System.nanoTime();
        selector.select(array, k);
        long time = System.nanoTime() - start;

        writer.write(
                "DeterministicSelect," + type + "," + input.length + ","
                        + time + "," + selector.getMaxDepth() + ","
                        + selector.getComparisons() + ","
                        + selector.getSwaps() + "\n"
        );
    }

    private void runClosest(
            FileWriter writer, Point[] points
    ) throws IOException {

        ClosestPairSolver solver = new ClosestPairSolver();

        long start = System.nanoTime();
        solver.findClosestPair(points);
        long time = System.nanoTime() - start;

        writer.write(
                "ClosestPair,Random," + points.length + ","
                        + time + "," + solver.getMaxDepth() + ","
                        + solver.getComparisons() + ",0\n"
        );
    }
}