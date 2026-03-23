# YouTube Channel Notification System - Observer Pattern

## Problem Statement

Design a notification system similar to YouTube subscriptions where users can subscribe to channels. Whenever a creator uploads a new video, all subscribers should automatically receive notifications.

## Notification Channels

The system should support the following notification mechanisms:

1. **Email Notification** - Sends notification via email when a new video is uploaded.
2. **Mobile Push Notification** - Sends push notification to the subscriber's device.
3. **In-App Alert** - Shows an in-app alert to the subscriber.

## Requirements

- Whenever a creator uploads a new video, all subscribers should **automatically receive notifications**.
- Users should be able to **subscribe** or **unsubscribe** from channels at any time.
- The system should support **multiple notification mechanisms** and be extensible for new ones in the future.

## Why Observer Pattern?

This is a good example of the **Observer Pattern**, where the channel is the **subject** and subscribers are the **observers**. This allows:

- Decoupling the channel (subject) from the subscribers (observers).
- Adding or removing subscribers at runtime (subscribe/unsubscribe).
- Easily extending with new notification mechanisms without changing existing code (Open/Closed Principle).
