# Top K Frequently Played Songs API - LLD

## Problem Statement

Design an API to fetch the most frequently played K songs from a music platform.

## Functional Requirements

- Users can **play songs**, and the system tracks play counts.
- The API should return the **top K most frequently played songs**.
- The system should support **adding new songs** and **incrementing play counts** in real time.
- Results should be sorted by **play count in descending order**.

## Core Components

1. **Song** - Represents a song with id, name, artist, and play count.
2. **SongService** - Manages songs and play counts.
3. **TopKSongsAPI** - Fetches top K songs using a ranking strategy.

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Ranking algorithm — the logic to determine "top K" could vary (e.g., MinHeap, Sorting, TreeMap) | Allows swapping ranking algorithms without changing the API |
| **Observer Pattern** | When a song is played, notify systems like analytics, leaderboard updater, recommendation engine | Decouples play event from downstream reactions |
| **Factory Pattern** | Creating different types of songs (e.g., Single, Album Track, Podcast Episode) if the system supports multiple media types | Centralizes object creation |
| **Singleton Pattern** | SongService or PlayCountTracker — single source of truth for play counts | Ensures one shared instance across the system |
