# Blackjack Game — LLD

## Problem Statement

Design the card game Blackjack. Two players — a Dealer and a Player — play against each other using a standard deck of cards. The goal is to get as close to 21 as possible without going over. The first person to reach 21 wins. If both finish without busting, the higher hand wins.

## Game Rules

- **Deck**: Standard 52-card deck (4 suits × 13 ranks).
- **Card values**:
  - Number cards (2-10) → face value
  - Face cards (Jack, Queen, King) → 10
  - Ace → 1 or 11 (whichever benefits the hand without busting)
- **Initial deal**: Both dealer and player get 2 cards. One of the dealer's cards is **hidden** (face-down).
- **Player turn**: Player can choose:
  - **HIT** → get one more card
  - **STAND** → stop and pass turn to dealer
- **Dealer turn**: Dealer **must HIT if total < 17**, must STAND if total >= 17.
- **Bust**: If total exceeds 21, that player loses immediately.
- **Blackjack**: Exactly 21 with first 2 cards (Ace + 10-value card) → instant win.
- **Winner**: Closest to 21 without busting. Tie if equal.

## Functional Requirements

- **Shuffle deck** and deal cards to player and dealer.
- **Display hands** — Show player's full hand, dealer's hand with one card hidden.
- **Player actions** — HIT or STAND.
- **Dealer logic** — Auto-hit if total < 17.
- **Calculate hand value** — Handle Ace as 1 or 11 dynamically.
- **Determine winner** — Compare totals after both stand, handle busts and blackjack.
- **Play again** — Option to restart with a fresh/reshuffled deck.

## Key Entities & Schema

| Entity | Fields |
|---|---|
| **Card** | suit (HEARTS/DIAMONDS/CLUBS/SPADES), rank (ACE/TWO/.../KING), value |
| **Deck** | list of cards, shuffle(), dealCard() |
| **Hand** | list of cards, addCard(), calculateValue(), isBusted(), isBlackjack() |
| **Player** | name, hand, type (PLAYER/DEALER), makeMove() |
| **Game** | deck, player, dealer, status, playRound() |

### Relationships
- Game → has one → Deck
- Game → has one → Player + Dealer
- Player/Dealer → has one → Hand
- Hand → has many → Cards
- Deck → has many → Cards

## Design Patterns That May Apply

| Pattern | Where | Why |
|---|---|---|
| **Strategy Pattern** | Player move — HumanStrategy (input HIT/STAND), DealerStrategy (auto-hit <17) | Different decision logic for player vs dealer |
| **Factory Pattern** | Deck creation — create 52 cards from suits × ranks | Encapsulate card/deck creation |
| **Observer Pattern** | Notify on card dealt, bust, blackjack, game over (for UI/logging) | Decouple game logic from display |
| **State Pattern** | Game states — DEALING → PLAYER_TURN → DEALER_TURN → GAME_OVER | Clean state transitions |

## Ace Value Calculation

```
Hand: [Ace, 7]
  → Try 11 + 7 = 18 ✓ (use 11)

Hand: [Ace, 7, 8]
  → Try 11 + 7 + 8 = 26 BUST
  → Try 1 + 7 + 8 = 16 ✓ (use 1)

Hand: [Ace, Ace, 9]
  → Try 11 + 11 + 9 = 31 BUST
  → Try 11 + 1 + 9 = 21 ✓ (one as 11, one as 1)

Logic:
  1. Start with all Aces as 11
  2. While total > 21 AND there are Aces counted as 11
     → Convert one Ace from 11 to 1
```

## Game Flow

```
                    +──────────────+
                    │  Start Game  │
                    +──────┬───────+
                           │
                           v
                    +──────────────+
                    │ Shuffle Deck │
                    +──────┬───────+
                           │
                           v
                    +──────────────+
                    │  Deal Cards  │
                    │  Player: 2   │
                    │  Dealer: 2   │
                    │  (1 hidden)  │
                    +──────┬───────+
                           │
                    Check Blackjack
                           │
                  +────────+────────+
                  │                 │
                  v                 v
          ┌────────────┐    ┌────────────┐
          │ Blackjack! │    │ No BJ      │
          │ Instant win│    │ Continue   │
          └────────────┘    └─────┬──────┘
                                  │
                                  v
                    +──────────────────────+
                    │    PLAYER'S TURN     │
                    │                      │
                    │  ┌─────┐  ┌───────┐  │
                    │  │ HIT │  │ STAND │  │
                    │  └──┬──┘  └───┬───┘  │
                    │     │         │      │
                    │     v         │      │
                    │  Add card     │      │
                    │  total>21?    │      │
                    │  Yes→BUST     │      │
                    │  No→ask again │      │
                    +───────────────┬──────+
                                   │
                                   v
                    +──────────────────────+
                    │    DEALER'S TURN     │
                    │                      │
                    │  Reveal hidden card  │
                    │  While total < 17:   │
                    │    → HIT             │
                    │  total > 21?         │
                    │    → BUST            │
                    │  Else → STAND        │
                    +──────────┬───────────+
                               │
                               v
                    +──────────────────────+
                    │   DETERMINE WINNER   │
                    │                      │
                    │  Player bust → Dealer│
                    │  Dealer bust → Player│
                    │  Higher total wins   │
                    │  Equal → Tie         │
                    +──────────────────────+
```

## Interview Follow-ups to Consider

- How do you **handle Ace** value dynamically? (Start as 11, demote to 1 if bust)
- How would you add **multiple players**? (List of players, turn order)
- How would you implement **betting/chips**? (Player has balance, place bet before deal)
- How would you add **Split** (two hands from a pair)? (Player holds List<Hand>)
- How would you handle **multiple decks** (casino shoe)? (Deck takes deckCount param, multiply cards)
- How would you add a **UI layer** without changing game logic? (Observer pattern for events)
