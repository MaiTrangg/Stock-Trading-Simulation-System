CREATE TABLE portfolios
(
    id         UUID PRIMARY KEY,

    user_id    UUID           NOT NULL,
    stock_id   UUID           NOT NULL,

    quantity   INT            NOT NULL,
    avg_price  DECIMAL(15, 2) NOT NULL,

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    is_delete  BOOLEAN   DEFAULT FALSE,
    version    BIGINT    DEFAULT 0,

    CONSTRAINT uq_user_stock UNIQUE (user_id, stock_id),
    CONSTRAINT fk_portfolio_stock FOREIGN KEY (stock_id) REFERENCES stocks (id)
);