import java.util.Arrays;
public class QuickSorterTest {
    public static void main(String[] args) {
        testRandomArray();
        testSortedArray();
        testReverseArray();
        testDuplicates();
        testEmptyArray();
        testSingleElement();
    }

    public static void testRandomArray() {
        int[] array = {7, 2, 9, 1, 5, 3};
        test(array, "Random array");
    }

    public static void testSortedArray() {
        int[] array = {1, 2, 3, 4, 5, 6};
        test(array, "Sorted array");
    }

    public static void testReverseArray() {
        int[] array = {6, 5, 4, 3, 2, 1};
        test(array, "Reverse array");
    }

    public static void testDuplicates() {
        int[] array = {3, 5, 3, 1, 5, 3};
        test(array, "Duplicate array");
    }

    public static void testEmptyArray() {
        int[] array = {};
        test(array, "Empty array");
    }

    public static void testSingleElement() {
        int[] array = {10};
        test(array, "Single element");
    }

    public static void test(int[] array, String testName) {
        int[] expected = array.clone();
        Arrays.sort(expected);
        QuickSorter sorter = new QuickSorter();
        sorter.sort(array);
        if (Arrays.equals(array, expected)) {
            System.out.println(testName + ": PASSED");
        } else {
            System.out.println(testName + ": FAILED");
            System.out.println("Expected: " + Arrays.toString(expected));
            System.out.println("Actual:   " + Arrays.toString(array));
        }
    }
}