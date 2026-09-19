public class DeterministicSelector {

    private long comparisons = 0;
    private long swaps = 0;
    private int maxDepth = 0;

    public int select(int[] arr, int k) {
        comparisons = 0;
        swaps = 0;
        maxDepth = 0;
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Invalid k");
        }
        return select(arr, 0, arr.length - 1, k, 1);
    }

    private int select(
            int[] arr,
            int left,
            int right,
            int k,
            int depth
    ) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        if (left == right) {
            return arr[left];
        }
        int pivot = medianOfMedians(arr, left, right, depth);
        int pivotIndex = partition(arr, left, right, pivot);
        int leftSize = pivotIndex - left;
        if (k == leftSize) {
            return arr[pivotIndex];
        }
        if (k < leftSize) {
            return select(
                    arr,
                    left,
                    pivotIndex - 1,
                    k,
                    depth + 1
            );
        }
        return select(
                arr,
                pivotIndex + 1,
                right,
                k - leftSize - 1,
                depth + 1
        );
    }

    private int medianOfMedians(
            int[] arr,
            int left,
            int right,
            int depth
    ) {
        int size = right - left + 1;
        if (size <= 5) {
            insertionSort(arr, left, right);
            return arr[left + size / 2];
        }
        int numberOfGroups = (size + 4) / 5;
        for (int i = 0; i < numberOfGroups; i++) {

            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);
            insertionSort(arr, groupLeft, groupRight);
            int medianIndex =
                    groupLeft + (groupRight - groupLeft) / 2;
            swap(arr, left + i, medianIndex);
        }

        int medianPosition = left + numberOfGroups / 2;
        return select(
                arr,
                left,
                left + numberOfGroups - 1,
                medianPosition - left,
                depth + 1
        );
    }

    private int partition(
            int[] arr,
            int left,
            int right,
            int pivotValue
    ) {
        int pivotIndex = left;
        for (int i = left; i <= right; i++) {
            comparisons++;
            if (arr[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }
        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            comparisons++;
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private void insertionSort(
            int[] arr,
            int left,
            int right
    ) {
        for (int i = left + 1; i <= right; i++) {
            int value = arr[i];
            int j = i - 1;
            while (j >= left) {

                comparisons++;

                if (arr[j] <= value) {
                    break;
                }

                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = value;
        }
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
