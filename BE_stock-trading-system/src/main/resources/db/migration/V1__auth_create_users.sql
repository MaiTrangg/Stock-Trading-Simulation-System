CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,

                       status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                       created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       created_by UUID,
                       updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       updated_by UUID,

                       is_delete BOOLEAN NOT NULL DEFAULT FALSE,
                       version BIGINT NOT NULL DEFAULT 0,

                       CONSTRAINT chk_user_status
                           CHECK (status IN ('ACTIVE','INACTIVE','LOCKED','BANNED'))
);

CREATE INDEX idx_users_status_delete ON users(status, is_delete);
CREATE INDEX idx_users_email ON users(email);