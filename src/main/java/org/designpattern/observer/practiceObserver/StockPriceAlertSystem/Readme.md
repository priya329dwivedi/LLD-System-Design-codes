# Stock Price Alert System - Observer Pattern

## Problem Statement

Design a system that tracks stock prices in real time and notifies subscribed users whenever the price of a stock changes.

## Observers

The system should support the following:

1. **Users** - Can subscribe or unsubscribe to specific stocks (e.g., AAPL, TSLA).
2. **Notification Channels** - Should support adding new notification channels (email, push, SMS) in the future.

## Requirements

- Whenever a stock price is updated, all subscribed users should **automatically receive a notification**.
- Users should be able to **subscribe** or **unsubscribe** to specific stocks.
- The system should support adding new notification channels in the future **without modifying existing code**.

## Why Observer Pattern?

This is well suited for the **Observer Pattern**, where the stock acts as the **subject** and users act as **observers** who get notified whenever the stock price changes. This allows:

- Decoupling the stock (subject) from the users (observers).
- Adding or removing observers at runtime (subscribe/unsubscribe).
- Easily extending with new notification channels without changing existing code (Open/Closed Principle).
