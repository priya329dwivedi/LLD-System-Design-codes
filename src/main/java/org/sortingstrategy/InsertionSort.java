package org.sortingstrategy;

public class InsertionSort implements SortingStrategy{
    @Override
    public void sort(int n) {
        System.out.println("********** Sorting Insertion Sort **************");
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }
}
