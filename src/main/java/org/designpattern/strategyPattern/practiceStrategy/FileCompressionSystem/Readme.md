# File Compression System - Strategy Pattern

## Problem Statement

Design a file compression module that allows users to compress files using different compression algorithms.

## Compression Strategies

The system should support the following compression algorithms:

1. **ZIP Compression** - Standard compression using the ZIP algorithm.
2. **RAR Compression** - Compression using the RAR algorithm.
3. **7z Compression** - Compression using the 7z algorithm.

## Requirements

- Each algorithm uses a different technique and compression logic.
- The system should allow users to choose the compression method at **runtime**.
- The design should make it easy to **add new compression algorithms** in the future **without modifying existing code**.

## Why Strategy Pattern?

This is a good use case for the **Strategy Pattern**, where each compression algorithm is implemented as a separate strategy and the compression context selects the appropriate one dynamically. This allows:

- Swapping compression algorithms at runtime.
- Adding new compression strategies without changing existing code (Open/Closed Principle).
- Clean separation of concerns — each strategy handles its own compression logic.
