CREATE TABLE user_roles (
                            id UUID PRIMARY KEY,

                            user_id UUID NOT NULL,
                            role_id UUID NOT NULL,

                            status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                            created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                            created_by UUID,
                            updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                            updated_by UUID,

                            is_delete BOOLEAN NOT NULL DEFAULT FALSE,
                            version BIGINT NOT NULL DEFAULT 0,

                            CONSTRAINT fk_user_roles_user
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

                            CONSTRAINT fk_user_roles_role
                                FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,

                            CONSTRAINT uq_user_role UNIQUE (user_id, role_id),

                            CONSTRAINT chk_user_role_status
                                CHECK (status IN ('ACTIVE','INACTIVE'))
);

CREATE INDEX idx_user_roles_user ON user_roles(user_id);
CREATE INDEX idx_user_roles_role ON user_roles(role_id);
CREATE INDEX idx_user_roles_status_delete ON user_roles(status, is_delete);