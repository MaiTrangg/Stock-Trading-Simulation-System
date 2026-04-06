CREATE TABLE funds
(
    id                UUID PRIMARY KEY,
    user_id           UUID NOT NULL UNIQUE,

    balance           DECIMAL(15, 2) DEFAULT 0,
    available_balance DECIMAL(15, 2) DEFAULT 0,
    reserved_balance  DECIMAL(15, 2) DEFAULT 0,

    currency          VARCHAR(10)    DEFAULT 'USD',

    created_at        TIMESTAMP      DEFAULT NOW(),
    updated_at        TIMESTAMP      DEFAULT NOW(),

    is_delete         BOOLEAN        DEFAULT FALSE,
    version           BIGINT         DEFAULT 0
);