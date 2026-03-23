package org.sortingstrategy;

public class SortingStrategyFactory {

    public static SortingStrategy create(int n) {
        System.out.println("****** Factory creating strategy for size: " + n + " ********");
        if (n < 10) {
            System.out.println("Created: InsertionSort (best for small arrays)");
            return new InsertionSort();
        } else if (n < 100) {
            System.out.println("Created: MergeSort (best for medium arrays)");
            return new MergeSort();
        } else {
            System.out.println("Created: QuickSort (best for large arrays)");
            return new QuickSort();
        }
    }
}
