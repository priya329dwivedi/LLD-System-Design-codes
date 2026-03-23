package org.sortingstrategy;

public class QuickSort implements SortingStrategy{

    @Override
    public void sort(int n) {
        System.out.println("********* Quick Sort ***********");
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}
