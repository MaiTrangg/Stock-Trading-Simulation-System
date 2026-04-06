CREATE TABLE roles (
                       id UUID PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,

                       status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                       created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       created_by UUID,
                       updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       updated_by UUID,

                       is_delete BOOLEAN NOT NULL DEFAULT FALSE,
                       version BIGINT NOT NULL DEFAULT 0,

                       CONSTRAINT chk_role_status
                           CHECK (status IN ('ACTIVE','INACTIVE'))
);

CREATE INDEX idx_roles_status_delete ON roles(status, is_delete);