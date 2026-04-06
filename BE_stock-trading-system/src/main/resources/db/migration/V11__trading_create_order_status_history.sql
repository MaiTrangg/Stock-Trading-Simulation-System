CREATE TABLE order_status_history
(
    id         UUID PRIMARY KEY,
    order_id   UUID        NOT NULL,

    old_status VARCHAR(20) NOT NULL,
    new_status VARCHAR(20) NOT NULL,

    changed_at TIMESTAMP DEFAULT NOW(),
    changed_by UUID,

    created_at TIMESTAMP DEFAULT NOW(),

    is_delete  BOOLEAN   DEFAULT FALSE,
    version    BIGINT    DEFAULT 0,

    CONSTRAINT fk_order_status FOREIGN KEY (order_id) REFERENCES orders (id)
);