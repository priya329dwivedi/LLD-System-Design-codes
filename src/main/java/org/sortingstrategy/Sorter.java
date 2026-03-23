package org.sortingstrategy;

public class Sorter {
    public SortingStrategy sortingStrategy;
    public Sorter(SortingStrategy sortingStrategy){
        this.sortingStrategy=sortingStrategy;
    }
    public void sort(int n){
        System.out.println("****** Sorting ********");
        System.out.println(sortingStrategy.getName());
        sortingStrategy.sort(n);
    }
    public void autoSort(int n){
        sortingStrategy = SortingStrategyFactory.create(n);
        sortingStrategy.sort(n);
    }
}
