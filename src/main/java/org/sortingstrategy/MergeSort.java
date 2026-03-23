package org.sortingstrategy;

public class MergeSort implements SortingStrategy{

    @Override
    public void sort(int n) {
        System.out.println("********** Sorting Merge Sort **************");
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }
}
