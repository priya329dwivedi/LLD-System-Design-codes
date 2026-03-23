package org.sortingstrategy;

public class SortingStrategyDemo {

    public static void main(String[] args) {
        System.out.println("=== SORTING STRATEGY PATTERN DEMO ===\n");

        // Demo 1: Manual strategy selection via constructor
        System.out.println("--- Demo 1: Manual Strategy Selection ---");
        Sorter sorter1 = new Sorter(new QuickSort());
        sorter1.sort(500);

        System.out.println();

        // Demo 2: Auto-sort for Small Array
        System.out.println("--- Demo 2: Auto-Sort for Small Array ---");
        Sorter sorter2 = new Sorter(new QuickSort());
        sorter2.autoSort(5);

        System.out.println();

        // Demo 3: Auto-sort for Medium Array
        System.out.println("--- Demo 3: Auto-Sort for Medium Array ---");
        Sorter sorter3 = new Sorter(new InsertionSort());
        sorter3.autoSort(50);

        System.out.println();

        // Demo 4: Auto-sort for Large Array
        System.out.println("--- Demo 4: Auto-Sort for Large Array ---");
        Sorter sorter4 = new Sorter(new InsertionSort());
        sorter4.autoSort(500);

        System.out.println();
        System.out.println("=== DEMO COMPLETE ===");
    }
}
