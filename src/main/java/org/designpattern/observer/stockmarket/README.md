# Observer Pattern - Stock Market Example

## Problem Statement

### Scenario
A **StockMarket** object tracks stock prices. Multiple observers like **MobileApp**, **WebDashboard**, and **EmailAlertService** subscribe to updates. Whenever a stock price changes, all observers get notified automatically.

### Why Observer Pattern?
- Very easy to visualize the publisher-subscriber relationship
- Classic implementation of the publisher-subscriber model
- Promotes loose coupling between components

### Acceptance Criteria
- Observers auto-update when stock price changes
- Adding/removing observers should not break the system

## Database Design

### ER Diagram

```
+------------------+       +--------------------+       +------------------+
|      STOCK       |       |   SUBSCRIPTION     |       |    OBSERVER      |
+------------------+       +--------------------+       +------------------+
| PK stock_id      |<----->| PK subscription_id |<----->| PK observer_id   |
|    symbol        |       | FK stock_id        |       |    observer_type |
|    price         |       | FK observer_id     |       |    name          |
|    last_updated  |       |    subscribed_at   |       |    config        |
+------------------+       |    is_active       |       |    created_at    |
                           +--------------------+       +------------------+
                                                                 |
                                                                 |
                           +--------------------+                |
                           |   NOTIFICATION     |                |
                           +--------------------+                |
                           | PK notification_id |<---------------+
                           | FK observer_id     |
                           | FK stock_id        |
                           |    old_price       |
                           |    new_price       |
                           |    sent_at         |
                           |    status          |
                           +--------------------+
```

### Tables

#### 1. STOCK
Stores stock information and current prices.

| Column       | Type         | Constraints      | Description                    |
|--------------|--------------|------------------|--------------------------------|
| stock_id     | INT          | PK, AUTO_INCREMENT | Unique identifier            |
| symbol       | VARCHAR(10)  | UNIQUE, NOT NULL | Stock ticker (e.g., AAPL)     |
| price        | DECIMAL(10,2)| NOT NULL         | Current stock price           |
| last_updated | TIMESTAMP    | NOT NULL         | Last price update time        |

#### 2. OBSERVER
Stores different observer types and their configurations.

| Column        | Type         | Constraints      | Description                    |
|---------------|--------------|------------------|--------------------------------|
| observer_id   | INT          | PK, AUTO_INCREMENT | Unique identifier            |
| observer_type | VARCHAR(50)  | NOT NULL         | Type: MOBILE_APP, WEB_DASHBOARD, EMAIL_ALERT |
| name          | VARCHAR(100) | NOT NULL         | Observer name/identifier      |
| config        | JSON         | NULL             | Observer-specific config (email, app settings) |
| created_at    | TIMESTAMP    | NOT NULL         | Creation timestamp            |

#### 3. SUBSCRIPTION
Junction table linking stocks to observers (many-to-many).

| Column          | Type      | Constraints      | Description                    |
|-----------------|-----------|------------------|--------------------------------|
| subscription_id | INT       | PK, AUTO_INCREMENT | Unique identifier            |
| stock_id        | INT       | FK -> STOCK      | Reference to stock            |
| observer_id     | INT       | FK -> OBSERVER   | Reference to observer         |
| subscribed_at   | TIMESTAMP | NOT NULL         | When subscription was created |
| is_active       | BOOLEAN   | DEFAULT TRUE     | Subscription status           |

#### 4. NOTIFICATION
Audit log of all notifications sent to observers.

| Column          | Type         | Constraints      | Description                    |
|-----------------|--------------|------------------|--------------------------------|
| notification_id | INT          | PK, AUTO_INCREMENT | Unique identifier            |
| observer_id     | INT          | FK -> OBSERVER   | Observer who received notification |
| stock_id        | INT          | FK -> STOCK      | Stock that changed            |
| old_price       | DECIMAL(10,2)| NOT NULL         | Price before change           |
| new_price       | DECIMAL(10,2)| NOT NULL         | Price after change            |
| sent_at         | TIMESTAMP    | NOT NULL         | When notification was sent    |
| status          | VARCHAR(20)  | NOT NULL         | SENT, FAILED, PENDING         |

### SQL Schema

```sql
CREATE TABLE stock (
    stock_id INT PRIMARY KEY AUTO_INCREMENT,
    symbol VARCHAR(10) UNIQUE NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE observer (
    observer_id INT PRIMARY KEY AUTO_INCREMENT,
    observer_type VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    config JSON,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subscription (
    subscription_id INT PRIMARY KEY AUTO_INCREMENT,
    stock_id INT NOT NULL,
    observer_id INT NOT NULL,
    subscribed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (stock_id) REFERENCES stock(stock_id),
    FOREIGN KEY (observer_id) REFERENCES observer(observer_id),
    UNIQUE KEY unique_subscription (stock_id, observer_id)
);

CREATE TABLE notification (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    observer_id INT NOT NULL,
    stock_id INT NOT NULL,
    old_price DECIMAL(10,2) NOT NULL,
    new_price DECIMAL(10,2) NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (observer_id) REFERENCES observer(observer_id),
    FOREIGN KEY (stock_id) REFERENCES stock(stock_id)
);
```

### Sample Data

```sql
-- Stocks
INSERT INTO stock (symbol, price) VALUES ('AAPL', 150.25);
INSERT INTO stock (symbol, price) VALUES ('GOOGL', 2800.50);
INSERT INTO stock (symbol, price) VALUES ('MSFT', 310.75);

-- Observers
INSERT INTO observer (observer_type, name, config)
VALUES ('MOBILE_APP', 'StockTracker Pro', '{"platform": "iOS"}');

INSERT INTO observer (observer_type, name, config)
VALUES ('WEB_DASHBOARD', 'Dashboard-001', '{"refresh_interval": 5}');

INSERT INTO observer (observer_type, name, config)
VALUES ('EMAIL_ALERT', 'Email Service', '{"email": "investor@example.com"}');

-- Subscriptions
INSERT INTO subscription (stock_id, observer_id) VALUES (1, 1);
INSERT INTO subscription (stock_id, observer_id) VALUES (1, 2);
INSERT INTO subscription (stock_id, observer_id) VALUES (1, 3);
```

## Class Diagram

```
        +-------------+          +------------+
        |   Subject   |          |  Observer  |
        |  interface  |          | interface  |
        +-------------+          +------------+
        | +register() |          | +update()  |
        | +remove()   |          +-----^------+
        | +notify()   |                |
        +------^------+                |
               |              +--------+--------+--------+
               |              |        |        |        |
        +------+------+  +----+---+ +--+---+ +--+--------+--+
        | StockMarket |  |Mobile | | Web  | |   Email      |
        +-------------+  | App   | | Dash | |   Alert      |
        | -observers  |  +-------+ +------+ |   Service    |
        | -stockSymbol|                     +--------------+
        | -price      |
        +-------------+
```

## File Structure

```
org/observer/
├── README.md
├── Observer.java           # Observer interface
├── Subject.java            # Subject interface
├── StockMarket.java        # Concrete subject
├── MobileApp.java          # Concrete observer
├── WebDashboard.java       # Concrete observer
├── EmailAlertService.java  # Concrete observer
└── StockMarketDemo.java    # Demo/Main class
```
