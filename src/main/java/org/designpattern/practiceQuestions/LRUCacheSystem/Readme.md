# LRU Cache System - LLD

## Problem Statement

Design a Least Recently Used (LRU) Cache system that stores key-value pairs with a fixed capacity. When the cache is full and a new entry is added, the least recently used entry should be evicted. The system should support different eviction strategies and notify monitoring systems on cache events.

## Functional Requirements

- **put(key, value)** — Insert or update a key-value pair. If cache is full, evict based on eviction strategy.
- **get(key)** — Retrieve the value. Mark the key as recently used. Return -1 if not found.
- Support **different eviction strategies**:
  - **LRU** — Evict least recently used.
  - **LFU** — Evict least frequently used.
  - **FIFO** — Evict the oldest entry.
- Cache should have a **configurable capacity**.
- Notify monitoring systems on **cache hit**, **cache miss**, and **eviction** events.

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Eviction policy — LRU, LFU, FIFO are different algorithms for the same task | Swap eviction logic at runtime |
| **Observer Pattern** | Cache events — notify monitoring, logging, metrics systems on hit/miss/eviction | Decouple cache operations from monitoring |
| **Singleton Pattern** | CacheManager — single shared cache instance | One cache across the application |
| **Factory Pattern** | Create the right eviction strategy based on config | Centralize strategy creation |
