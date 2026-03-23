# Leaderboard System - LLD

## Problem Statement

Design a real-time leaderboard system for an online gaming platform. Players earn points by completing levels, and the system should maintain a live ranking. The system should support adding scores, fetching top K players, and getting a player's current rank.

## Functional Requirements

- Players can **submit scores** after completing a game.
- The system should return the **top K players** sorted by score.
- The system should return a **specific player's rank**.
- Support **multiple game modes** (Solo, Team, Tournament) — each with its own leaderboard.
- Notify systems when a player enters the **top 10** or beats their **personal best**.

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Ranking algorithm — different sorting/ranking strategies (TreeMap, MinHeap, Sorting) | Swap ranking logic without changing the service |
| **Factory Pattern** | Game mode leaderboards — create the right leaderboard based on game mode | Centralize leaderboard creation |
| **Observer Pattern** | Notify achievement system, push notifications when player enters top 10 or beats personal best | Decouple score submission from downstream reactions |
| **Singleton Pattern** | LeaderboardService — single shared instance | One source of truth for rankings |
