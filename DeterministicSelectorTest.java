import java.util.Arrays;
import java.util.Random;
public class DeterministicSelectorTest {
    public static void main(String[] args) {
        Random random = new Random();
        int passed = 0;
        int failed = 0;

        for (int test = 0; test < 100; test++) {
            int size = random.nextInt(50) + 1;
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(100);
            }

            int k = random.nextInt(size);
            int[] copy = array.clone();
            Arrays.sort(copy);
            int expected = copy[k];
            DeterministicSelector selector = new DeterministicSelector();
            int actual = selector.select(array, k);
            if (actual == expected) {
                passed++;
            } else {
                failed++;
                System.out.println("Test " + (test + 1) + " FAILED");
                System.out.println("k = " + k);
                System.out.println("Expected: " + expected);
                System.out.println("Actual: " + actual);
            }
        }

        System.out.println();
        System.out.println("Deterministic Select Test");
        System.out.println("Random tests: 100");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        if (failed == 0) {
            System.out.println("All tests PASSED");
        }
    }
}