CREATE TABLE watchlists
(
    id         UUID PRIMARY KEY,

    user_id    UUID NOT NULL,
    stock_id   UUID NOT NULL,

    status     VARCHAR(20) DEFAULT 'ACTIVE',

    created_at TIMESTAMP   DEFAULT NOW(),
    created_by UUID,
    updated_at TIMESTAMP   DEFAULT NOW(),
    updated_by UUID,

    is_delete  BOOLEAN     DEFAULT FALSE,
    version    BIGINT      DEFAULT 0,

    CONSTRAINT uq_user_stock_watch UNIQUE (user_id, stock_id)
);

CREATE INDEX idx_watchlist_status ON watchlists (status, is_delete);