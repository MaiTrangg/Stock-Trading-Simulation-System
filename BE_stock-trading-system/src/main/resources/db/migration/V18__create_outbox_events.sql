CREATE TABLE outbox_events
(
    id             UUID PRIMARY KEY,
    event_id       UUID         NOT NULL UNIQUE,

    event_type     VARCHAR(100) NOT NULL,
    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id   UUID         NOT NULL,

    payload        JSONB        NOT NULL,

    status         VARCHAR(20)  NOT NULL DEFAULT 'PENDING',

    retry_count    INT                   DEFAULT 0,
    last_error     TEXT,

    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    processed_at   TIMESTAMP
);

CREATE INDEX idx_outbox_status_created
    ON outbox_events (status, created_at);

CREATE INDEX idx_outbox_event_id
    ON outbox_events (event_id);