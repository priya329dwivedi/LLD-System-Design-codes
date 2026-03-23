# Problem Statement 2: Sorting Strategy

## Problem Description

Build a **Sorter** that can swap sorting strategies at runtime based on array size:
- **QuickSort**
- **MergeSort**
- **InsertionSort**

The Sorter should automatically pick the optimal strategy based on array size using a factory.

## Design Focus

- **Encapsulate Algorithms**: Each sorting algorithm is encapsulated in its own class
- **Runtime Strategy Selection**: Client can swap strategies dynamically
- **Factory Pattern**: Factory picks best algorithm based on array size

## Project Structure

```
org/sortingstrategy/
├── README.md
├── SortingStrategy.java        # Interface
├── QuickSort.java              # Concrete strategy
├── MergeSort.java              # Concrete strategy
├── InsertionSort.java          # Concrete strategy
├── SortingStrategyFactory.java # Factory for strategy selection
├── Sorter.java                 # Context class
└── SortingStrategyDemo.java    # Demo/test class
```

## Class Diagram

```
+--------------------+         +------------------+
|  SortingStrategy   |<--------|     Sorter       |
|    <<interface>>   |         +------------------+
+--------------------+         | - strategy       |
| + sort(int n)      |         +------------------+
| + getName()        |         | + sort(n)        |
+--------------------+         | + autoSort(n)    |
         ^                     +------------------+
         |                             |
    +----+----+----+                   | uses
    |         |    |                   v
+-------+ +-------+ +----------+  +------------------------+
| Quick | | Merge | | Insertion|  | SortingStrategyFactory |
| Sort  | | Sort  | |   Sort   |  +------------------------+
+-------+ +-------+ +----------+  | + create(n): Strategy  |
                                  +------------------------+
```

## Strategy Selection Heuristic (in Factory)

| Array Size | Selected Strategy | Reason |
|------------|-------------------|--------|
| n < 10 | InsertionSort | Low overhead, fast for small arrays |
| 10 <= n < 100 | MergeSort | Stable, O(n log n) guaranteed |
| n >= 100 | QuickSort | Best average case performance |

## Design Patterns Used

- **Strategy Pattern**: Encapsulates sorting algorithms, makes them interchangeable
- **Factory Pattern**: Creates appropriate strategy based on array size

## Key Principles

- **Open/Closed Principle**: Add new sorting algorithms without modifying Sorter
- **Single Responsibility**: Sorter sorts, Factory creates strategies
- **Dependency Inversion**: Sorter depends on interface, not concrete classes

## Usage

```java
// Manual strategy selection via constructor
Sorter sorter = new Sorter(new QuickSort());
sorter.sort(500);

// Auto-sort: Factory picks best strategy and sorts
sorter.autoSort(5);   // Factory creates InsertionSort
sorter.autoSort(50);  // Factory creates MergeSort
sorter.autoSort(500); // Factory creates QuickSort
```

## How It Works

1. `sort(n)` - Uses strategy passed in constructor
2. `autoSort(n)` - Calls `SortingStrategyFactory.create(n)` to get best strategy, then sorts

```java
// Inside Sorter.autoSort()
public void autoSort(int n){
    sortingStrategy = SortingStrategyFactory.create(n);
    sortingStrategy.sort(n);
}
```
