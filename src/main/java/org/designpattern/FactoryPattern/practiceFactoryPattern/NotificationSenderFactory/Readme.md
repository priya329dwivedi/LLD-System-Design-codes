# Notification Sender Factory - Factory Pattern

## Problem Statement

Design a notification system that can send notifications through different channels such as Email, SMS, and Push Notifications.

## Notification Types

The system should support the following notification channels:

1. **Email Notification** - Sends notifications via email.
2. **SMS Notification** - Sends notifications via SMS.
3. **Push Notification** - Sends notifications via push notifications.

## Requirements

- The client only specifies the notification type, and the system should create the correct notification sender internally.
- The object creation logic should be **encapsulated** so that adding new notification channels like Slack or WhatsApp **does not require changes in the client code**.

## Why Factory Pattern?

This is well suited for the **Factory Pattern**, where a factory class is responsible for creating the correct notification sender implementation. This allows:

- Centralizing object creation logic in one place.
- The client to remain decoupled from concrete notification sender implementations.
- Adding new notification channels without changing existing client code (Open/Closed Principle).
