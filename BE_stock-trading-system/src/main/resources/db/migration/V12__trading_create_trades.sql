CREATE TABLE trades
(
    id           UUID PRIMARY KEY,

    order_id     UUID           NOT NULL,

    buyer_id     UUID,
    seller_id    UUID,

    stock_id     UUID           NOT NULL,

    side         VARCHAR(10)    NOT NULL,

    price        DECIMAL(15, 2) NOT NULL,
    quantity     INT            NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,

    executed_at  TIMESTAMP      NOT NULL,

    is_delete    BOOLEAN DEFAULT FALSE,
    version      BIGINT  DEFAULT 0,

    CONSTRAINT fk_trade_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_trade_stock FOREIGN KEY (stock_id) REFERENCES stocks (id)
);

CREATE INDEX idx_trades_order ON trades (order_id);
CREATE INDEX idx_trades_stock ON trades (stock_id);