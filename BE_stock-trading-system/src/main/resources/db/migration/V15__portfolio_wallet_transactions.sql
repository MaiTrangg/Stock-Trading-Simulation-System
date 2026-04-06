CREATE TABLE wallet_transactions
(
    id           UUID PRIMARY KEY,

    user_id      UUID           NOT NULL,

    type         VARCHAR(20)    NOT NULL,
    amount       DECIMAL(15, 2) NOT NULL,

    reference_id UUID,

    created_at   TIMESTAMP DEFAULT NOW(),

    is_delete    BOOLEAN   DEFAULT FALSE,
    version      BIGINT    DEFAULT 0
);

CREATE INDEX idx_wallet_user ON wallet_transactions (user_id);