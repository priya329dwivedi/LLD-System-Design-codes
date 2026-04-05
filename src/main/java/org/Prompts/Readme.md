# Prompts

Collection of prompts used across projects.

---

## 1. HLD — Logging & Metrics System

**Usage**: Paste this prompt and replace "Logging and Metrics System" with any HLD topic.

```
Design a Logging and Metrics System (HLD).
Follow the structure below strictly and proceed step by step:

1️⃣ Functional Requirements
Provide at most 2 functional requirements initially
Pause and ask for confirmation before proceeding further

2️⃣ Non-Functional Requirements
Cover scalability, availability, latency, durability, and consistency
Clearly define system goals (e.g., real-time vs near real-time)

3️⃣ Back-of-the-Envelope Estimation
Estimate:
Write QPS (logs/metrics ingestion)
Read QPS (queries, dashboards)
Storage per day/month
Show assumptions clearly

4️⃣ API Design
Define APIs for:
Log ingestion
Metric ingestion
Querying logs/metrics
Include request/response structure

5️⃣ High-Level Design & Flow
Explain end-to-end flow:
How user interacts with system
Include components like:
Load balancer
Rate limiter
Ingestion service
Processing service
Include a diagram representation (textual or conceptual)

6️⃣ Core Services Breakdown
Identify and describe key services:
Log ingestion
Metric aggregation
Query service
Storage service

7️⃣ Database Design
Define:
Storage choices (logs vs metrics)
Schema / entities
Clearly list entities and relationships

8️⃣ Messaging / Queue Design
Introduce async processing using queues
Justify choice (e.g., Kafka vs others)
Explain:
Partitioning
Ordering
Retry handling

9️⃣ Scaling Strategy
Horizontal scaling approach
Partitioning / sharding strategy
Handling high ingestion traffic

🔟 Latency & Performance Considerations
Write vs read latency trade-offs
Caching strategy
Indexing approach

1️⃣1️⃣ Bottlenecks & Deep Dives
Identify potential bottlenecks:
Ingestion
Storage
Query
Provide mitigation strategies

1️⃣2️⃣ Trade-offs
Database selection (SQL vs NoSQL vs TSDB)
Queue selection
Consistency vs availability
Cost vs performance
```
