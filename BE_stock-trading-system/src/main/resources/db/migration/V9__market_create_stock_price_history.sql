CREATE TABLE stock_price_history
(
    id          UUID PRIMARY KEY,
    stock_id    UUID           NOT NULL,

    open_price  DECIMAL(15, 2) NOT NULL,
    close_price DECIMAL(15, 2) NOT NULL,
    high_price  DECIMAL(15, 2) NOT NULL,
    low_price   DECIMAL(15, 2) NOT NULL,
    volume      BIGINT         NOT NULL,

    price_date  DATE           NOT NULL,

    created_at  TIMESTAMP DEFAULT NOW(),

    is_delete   BOOLEAN   DEFAULT FALSE,
    version     BIGINT    DEFAULT 0,

    CONSTRAINT fk_stock_price FOREIGN KEY (stock_id) REFERENCES stocks (id)
);

CREATE INDEX idx_stock_price_date ON stock_price_history (stock_id, price_date);