CREATE TABLE email_verifications (
                                     id UUID PRIMARY KEY,

                                     user_id UUID NOT NULL,
                                     token VARCHAR(255) NOT NULL, -- save OTP hashed

                                     expires_at TIMESTAMP NOT NULL,
                                     verified BOOLEAN NOT NULL DEFAULT FALSE,

                                     attempt_count INT NOT NULL DEFAULT 0, -- brute-force

                                     created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                     created_by UUID,
                                     updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                     updated_by UUID,

                                     is_delete BOOLEAN NOT NULL DEFAULT FALSE,
                                     version BIGINT NOT NULL DEFAULT 0,

                                     CONSTRAINT fk_email_verification_user
                                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- INDEX
CREATE INDEX idx_email_verification_user
    ON email_verifications(user_id);

CREATE INDEX idx_email_verification_expired
    ON email_verifications(expires_at);

CREATE INDEX idx_email_verification_status
    ON email_verifications(verified, is_delete);

-- only 1 OTP active / user
CREATE UNIQUE INDEX uq_email_verification_active
    ON email_verifications(user_id)
    WHERE is_delete = false;