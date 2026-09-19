public class MergeSorter {
    private long comparisons = 0;
    private int maxDepth = 0;
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1, 1);
    }
    private void mergeSort(int[] arr, int[] temp,
                           int left, int right, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr, temp, left, mid, depth + 1);
        mergeSort(arr, temp, mid + 1, right, depth + 1);
        merge(arr, temp, left, mid, right);
    }
    private void merge(int[] arr, int[] temp,
                       int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            comparisons++;
            if (arr[i] <= arr[j]) {
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            temp[k] = arr[i];
            i++;
            k++;
        }
        while (j <= right) {
            temp[k] = arr[j];
            j++;
            k++;
        }
        for (int p = left; p <= right; p++) {
            arr[p] = temp[p];
        }
    }
    public long getComparisons() {
        return comparisons;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
}