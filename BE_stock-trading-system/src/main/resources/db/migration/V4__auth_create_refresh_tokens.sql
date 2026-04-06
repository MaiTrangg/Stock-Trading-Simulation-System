CREATE TABLE refresh_tokens (
                                id UUID PRIMARY KEY,

                                user_id UUID NOT NULL,
                                token TEXT NOT NULL UNIQUE,

                                expires_at TIMESTAMP NOT NULL,
                                revoked BOOLEAN NOT NULL DEFAULT FALSE,

                                created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                created_by UUID,
                                updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                updated_by UUID,

                                is_delete BOOLEAN NOT NULL DEFAULT FALSE,
                                version BIGINT NOT NULL DEFAULT 0,

                                CONSTRAINT fk_refresh_user
                                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_user ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_token_revoked ON refresh_tokens(revoked);