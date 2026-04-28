CREATE TABLE portfolio_transactions
(
    id               UUID PRIMARY KEY     DEFAULT gen_random_uuid(),

    user_id          UUID        NOT NULL,
    stock_id         UUID        NOT NULL,

    quantity         INTEGER     NOT NULL,

    transaction_type VARCHAR(50) NOT NULL,

    reference_id     UUID        NOT NULL UNIQUE,

    created_at       TIMESTAMP   NOT NULL DEFAULT NOW()
);