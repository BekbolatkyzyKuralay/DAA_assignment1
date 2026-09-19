import java.util.Random;
public class QuickSorter {
    private long comparisons = 0;
    private long swaps = 0;
    private int maxDepth = 0;
    private final Random random = new Random();
    public void sort(int[] arr) {
        comparisons = 0;
        swaps = 0;
        maxDepth = 0;
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1, 1);
    }
    private void quickSort(int[] arr, int low, int high, int depth) {
        while (low < high) {
            if (depth > maxDepth) {
                maxDepth = depth;
            }
            int pivotIndex = low + random.nextInt(high - low + 1);
            swap(arr, pivotIndex, high);
            int pivot = partition(arr, low, high);
            int leftSize = pivot - low;
            int rightSize = high - pivot;
            if (leftSize < rightSize) {
                if (low < pivot - 1) {
                    quickSort(arr, low, pivot - 1, depth + 1);
                }
                low = pivot + 1;
            } else {
                if (pivot + 1 < high) {
                    quickSort(arr, pivot + 1, high, depth + 1);
                }
                high = pivot - 1;
            }
        }
    }
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }
    private void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        swaps++;
    }
    public long getComparisons() {
        return comparisons;
    }
    public long getSwaps() {
        return swaps;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
}