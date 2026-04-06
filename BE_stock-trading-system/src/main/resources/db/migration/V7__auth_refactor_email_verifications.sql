-- ================================
-- 1. ADD NEW COLUMNS
-- ================================

ALTER TABLE email_verifications
    ADD COLUMN type VARCHAR(50),
    ADD COLUMN status VARCHAR(20),
    ADD COLUMN verify_attempts INT DEFAULT 0,
    ADD COLUMN resend_count INT DEFAULT 0,
    ADD COLUMN last_sent_at TIMESTAMP;

-- ================================
-- 2. MIGRATE OLD DATA
-- ================================

-- map verified -> status
UPDATE email_verifications
SET status = CASE
                 WHEN verified = true THEN 'VERIFIED'
                 WHEN expires_at < NOW() THEN 'EXPIRED'
                 ELSE 'ACTIVE'
    END;

-- default type
UPDATE email_verifications
SET type = 'REGISTER';

-- map attempt_count -> verify_attempts
UPDATE email_verifications
SET verify_attempts = attempt_count;

-- ================================
-- 3. SET NOT NULL
-- ================================

ALTER TABLE email_verifications
    ALTER COLUMN type SET NOT NULL,
    ALTER COLUMN status SET NOT NULL,
    ALTER COLUMN verify_attempts SET NOT NULL,
    ALTER COLUMN resend_count SET NOT NULL;

-- ================================
-- 4. DROP OLD COLUMNS
-- ================================

ALTER TABLE email_verifications
    DROP COLUMN verified,
    DROP COLUMN attempt_count,
    DROP COLUMN is_delete;

-- ================================
-- 5. DROP OLD INDEX
-- ================================

DROP INDEX IF EXISTS uq_email_verification_active;

-- ================================
-- 6. CREATE NEW INDEX
-- ================================

CREATE INDEX idx_ev_user
    ON email_verifications(user_id);

CREATE INDEX idx_ev_verify
    ON email_verifications(user_id, type, status);

CREATE INDEX idx_ev_expiry
    ON email_verifications(expires_at);

CREATE INDEX idx_ev_status
    ON email_verifications(status);

-- ================================
-- 7. UNIQUE ACTIVE OTP
-- ================================

CREATE UNIQUE INDEX uq_ev_active
    ON email_verifications(user_id, type)
    WHERE status = 'ACTIVE';