# Snake Game

## Problem Statement

### Functional Requirements
- Snake moves on a grid one cell at a time in a given direction
- Snake grows by 1 when it eats food; score increments
- Food respawns at a random empty cell after being eaten
- Game ends (GAME_OVER) if snake hits a wall or its own body
- Game ends (WON) if snake fills the entire board

### Non-Functional Requirements
- No UI — logic only; board state queryable programmatically
- Food spawner is swappable — random for real play, fixed for tests/demos

---

## Key Entities and Relationships

```
SnakeGame ──> Board         (knows grid dimensions + bounds check)
SnakeGame ──> Snake         (current body positions + movement)
SnakeGame ──> FoodSpawner   (strategy for placing food)
SnakeGame ──  Position food (current food location)
Snake ──< Position          (body = LinkedList of positions)
Position ── Direction       (Position.move(dir) → new Position)
```

---

## Design Choices and Trade-offs

### LinkedList + HashSet for Snake body
- `LinkedList` as Deque: `addFirst` (new head) and `removeLast` (old tail) are O(1)
- Parallel `HashSet<Position>`: self-collision check is O(1) instead of O(n) list scan
- Trade-off: two structures must stay in sync — managed by `move()` and `grow()`

### Position.move() returns a new Position
Direction logic lives on `Position`, not on `Snake` or `SnakeGame`.
Keeps `Snake` direction-agnostic — it just receives the next head position.

### FoodSpawner Strategy
`RandomFoodSpawner` for real gameplay; `FixedFoodSpawner` for deterministic demos/tests.
Swapping is one constructor argument — `SnakeGame` never changes.

### Known simplification
Self-collision check happens before tail removal. This means moving into the
current tail cell is incorrectly flagged as a collision (the tail would have moved away).
Acceptable for LLD scope — fix by excluding the tail from the check if needed.

---

## Patterns Used

| Pattern | Where | Why |
|---------|-------|-----|
| Strategy | `FoodSpawner` interface | Swap random vs fixed food placement without changing game logic |
| Value Object | `Position` | Immutable, equality by value, safe for HashSet |
| State machine | `GameStatus` enum | RUNNING → GAME_OVER / WON — clear transitions |

---

## How to Extend

| Extension | Where to change |
|-----------|----------------|
| Multiple food items | `SnakeGame`: hold `List<Position> foods` instead of single `food` |
| Speed / levels | Add `tickRate` to `SnakeGame`; caller controls move frequency |
| Obstacles | Add `Set<Position> obstacles` to `Board`; check in `SnakeGame.move()` |
| Wrap-around walls | Replace `isWithinBounds` check with modulo wrapping in `Board` |
| Score multiplier | Add multiplier field; double points for consecutive food without turns |

---

## Demo Flow Walkthrough

**Flow 1 — Eat and grow:**
Snake starts at (5,5). Food placed at (5,6), (5,7), (5,8).
Three RIGHT moves → snake eats all three, grows to size 4, score = 3.

**Flow 2 — Wall collision:**
5×5 board, snake at (2,2). Three RIGHT moves → (2,5) is out of bounds → GAME OVER.

**Flow 3 — Self collision:**
Snake grows to size 4 via food: body = [(5,8),(5,7),(5,6),(5,5)].
Moves UP→LEFT→LEFT→LEFT→DOWN loops head back to (5,5) which is in the body → GAME OVER.
