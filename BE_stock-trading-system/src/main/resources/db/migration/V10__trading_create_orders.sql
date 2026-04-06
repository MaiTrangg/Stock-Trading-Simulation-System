CREATE TABLE orders
(
    id                 UUID PRIMARY KEY,
    order_no           VARCHAR(50) NOT NULL UNIQUE,

    user_id            UUID        NOT NULL,
    stock_id           UUID        NOT NULL,

    side               VARCHAR(10) NOT NULL,
    type               VARCHAR(10) NOT NULL,

    price              DECIMAL(15, 2),
    quantity           INT         NOT NULL,

    executed_quantity  INT         DEFAULT 0,
    remaining_quantity INT         NOT NULL,

    status             VARCHAR(20) DEFAULT 'PENDING',

    created_at         TIMESTAMP   DEFAULT NOW(),
    updated_at         TIMESTAMP   DEFAULT NOW(),

    is_delete          BOOLEAN     DEFAULT FALSE,
    version            BIGINT      DEFAULT 0,

    CONSTRAINT fk_order_stock FOREIGN KEY (stock_id) REFERENCES stocks (id)
);

CREATE INDEX idx_orders_user ON orders (user_id);
CREATE INDEX idx_orders_stock ON orders (stock_id);
CREATE INDEX idx_orders_status ON orders (status);