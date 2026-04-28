-- Add unique constraints for production-grade idempotency and race condition prevention

-- 1. Unique constraint for trades (prevent duplicate trades)
ALTER TABLE trades 
ADD CONSTRAINT uk_trade_unique 
UNIQUE (order_id, price, quantity, executed_at);

-- 2. Unique constraint for wallet transactions (prevent double charges)
ALTER TABLE wallet_transactions 
ADD CONSTRAINT uk_wallet_tx_unique 
UNIQUE (user_id, reference_id);

-- 3. Unique constraint for processed events (prevent duplicate processing)
ALTER TABLE processed_events 
ADD CONSTRAINT uk_processed_event_unique 
UNIQUE (event_id, consumer_group);

-- 4. Unique constraint for outbox events (prevent duplicate outbox entries)
ALTER TABLE outbox_events 
ADD CONSTRAINT uk_outbox_event_unique 
UNIQUE (event_id);

-- 5. Indexes for performance
CREATE INDEX idx_orders_status_created ON orders(status, created_at);
CREATE INDEX idx_trades_order_created ON trades(order_id, created_at);
CREATE INDEX idx_wallet_tx_user_ref ON wallet_transactions(user_id, reference_id);
CREATE INDEX idx_outbox_status_created ON outbox_events(status, created_at);
