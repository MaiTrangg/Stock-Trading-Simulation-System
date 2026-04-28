-- Add unique constraints for financial operations idempotency
-- This prevents double charge, double refund, and duplicate transactions

-- 1. Wallet transactions unique constraint on reference_id
-- Prevents duplicate wallet operations (reserve, release, refund, add_balance)
ALTER TABLE wallet_transactions 
ADD CONSTRAINT uk_wallet_transactions_reference_id 
UNIQUE (reference_id);

-- 2. Portfolio transactions unique constraint on reference_id  
-- Prevents duplicate stock operations (lock_stock, release_stock, trade_buy, trade_sell)
ALTER TABLE portfolio_transactions 
ADD CONSTRAINT uk_portfolio_transactions_reference_id 
UNIQUE (reference_id);

-- 3. Trades deduplication constraint
-- Prevents duplicate trades for the same order, price, and quantity
ALTER TABLE trades 
ADD CONSTRAINT uk_trades_order_price_quantity 
UNIQUE (order_id, price, quantity);

-- 4. Additional constraint for order history to prevent duplicate history entries
ALTER TABLE order_history 
ADD CONSTRAINT uk_order_history_order_status_timestamp 
UNIQUE (order_id, old_status, new_status, created_at);

-- 5. Outbox events unique constraint on event_id (already exists but ensuring)
ALTER TABLE outbox_events 
ADD CONSTRAINT uk_outbox_events_event_id 
UNIQUE (event_id);

-- Create indexes for performance
CREATE INDEX idx_wallet_transactions_reference_id ON wallet_transactions(reference_id);
CREATE INDEX idx_portfolio_transactions_reference_id ON portfolio_transactions(reference_id);
CREATE INDEX idx_trades_order_price_quantity ON trades(order_id, price, quantity);
CREATE INDEX idx_order_history_order_status ON order_history(order_id, created_at);
