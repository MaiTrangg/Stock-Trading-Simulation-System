CREATE TABLE stocks
(
    id           UUID PRIMARY KEY,
    symbol       VARCHAR(20)  NOT NULL UNIQUE,
    company_name VARCHAR(200) NOT NULL,
    exchange     VARCHAR(50)  NOT NULL,
    sector       VARCHAR(100),
    industry     VARCHAR(100),
    status       VARCHAR(20) DEFAULT 'ACTIVE',

    created_at   TIMESTAMP   DEFAULT NOW(),
    created_by   UUID,
    updated_at   TIMESTAMP   DEFAULT NOW(),
    updated_by   UUID,

    is_delete    BOOLEAN     DEFAULT FALSE,
    version      BIGINT      DEFAULT 0
);

CREATE INDEX idx_stocks_status ON stocks (status, is_delete);