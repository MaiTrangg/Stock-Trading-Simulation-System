CREATE TABLE processed_events
(
    event_id     VARCHAR(100) PRIMARY KEY,
    event_type   VARCHAR(50),
    processed_at TIMESTAMP DEFAULT NOW()
);