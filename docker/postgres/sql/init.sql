CREATE FUNCTION audit_trigger() RETURNS TRIGGER AS
$audit_trigger$
BEGIN
    IF (tg_op = 'INSERT') THEN
        new.created_timestamp := CURRENT_TIMESTAMP;
        new.created_session_id := CURRENT_SETTING('app.session_id');
    END IF;

    new.modified_timestamp := CURRENT_TIMESTAMP;
    new.modified_session_id := CURRENT_SETTING('app.session_id');

    -- https://dba.stackexchange.com/questions/58455/insert-record-into-a-table-in-a-trigger-function
    -- Actually, you do not have to expand the record manually.
    -- As long as number, sequence and types of columns match between the two tables,
    -- you can use this much simpler form:
    EXECUTE 'INSERT INTO ' || tg_table_schema::TEXT || '_audit' || '.' || tg_table_name::TEXT ||
            '_audit' ||
            ' SELECT ' || QUOTE_LITERAL(CLOCK_TIMESTAMP()) || 'AS audit_timestamp, ' || QUOTE_LITERAL(tg_op::TEXT) ||
            ' AS event, ($1).*' USING new;
    RETURN new;
END;
$audit_trigger$ LANGUAGE plpgsql;

CREATE ROLE core_usr
    LOGIN
    PASSWORD 'core_pw';

CREATE SCHEMA core;
CREATE SCHEMA core_audit;

CREATE TABLE core.account
(
    acco_id             BIGINT PRIMARY KEY,
    cust_id             BIGINT                   NOT NULL,
    status_code         VARCHAR(1)               NOT NULL,
    country             VARCHAR(2)               NOT NULL,
    inserted_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,

    created_timestamp   TIMESTAMP WITH TIME ZONE NOT NULL,
    created_session_id  VARCHAR(255)             NOT NULL,
    modified_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_session_id VARCHAR(255)             NOT NULL
);

CREATE SEQUENCE core.acco_seq AS
    BIGINT INCREMENT BY 1
    START WITH 1000;

GRANT SELECT, INSERT, UPDATE ON core.account TO core_usr;

CREATE TRIGGER account_audit_trigger
    BEFORE INSERT OR UPDATE OR DELETE
    ON core.account
    FOR EACH ROW
EXECUTE FUNCTION audit_trigger();

CREATE TABLE core_audit.account_audit
(
    audit_timestamp     TIMESTAMP WITH TIME ZONE,
    event               VARCHAR(6),

    acco_id             BIGINT,
    cust_id             BIGINT,
    status_code         VARCHAR(1),
    country             VARCHAR(2),
    inserted_timestamp  TIMESTAMP WITH TIME ZONE,

    created_timestamp   TIMESTAMP WITH TIME ZONE,
    created_session_id  VARCHAR(255),
    modified_timestamp  TIMESTAMP WITH TIME ZONE,
    modified_session_id VARCHAR(255)
);

CREATE TABLE core.balance
(
    bala_id             BIGINT PRIMARY KEY,
    acco_id             BIGINT                   NOT NULL,
    amount              NUMERIC(16, 2)           NOT NULL,
    ccy                 VARCHAR(3)               NOT NULL,
    valid_to_timestamp  TIMESTAMP WITH TIME ZONE,

    created_timestamp   TIMESTAMP WITH TIME ZONE NOT NULL,
    created_session_id  VARCHAR(255)             NOT NULL,
    modified_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_session_id VARCHAR(255)             NOT NULL
);

CREATE SEQUENCE core.bala_seq AS
    BIGINT INCREMENT BY 1
    START WITH 1000;

GRANT SELECT, INSERT, UPDATE ON core.balance TO core_usr;
CREATE TABLE core_audit.balance_audit
(
    audit_timestamp     TIMESTAMP WITH TIME ZONE,
    event               VARCHAR(6),

    bala_id             BIGINT,
    acco_id             BIGINT,
    amount              NUMERIC(16, 2),
    ccy                 VARCHAR(3),
    valid_to_timestamp  TIMESTAMP WITH TIME ZONE,

    created_timestamp   TIMESTAMP WITH TIME ZONE,
    created_session_id  VARCHAR(255),
    modified_timestamp  TIMESTAMP WITH TIME ZONE,
    modified_session_id VARCHAR(255)
);

CREATE TRIGGER balance_audit_trigger
    BEFORE INSERT OR UPDATE OR DELETE
    ON core.balance
    FOR EACH ROW
EXECUTE FUNCTION audit_trigger();

CREATE TABLE core.allowed_currency
(
    ccy                 VARCHAR(3) UNIQUE        NOT NULL,
    enabled             BOOLEAN                  NOT NULL,

    created_timestamp   TIMESTAMP WITH TIME ZONE NOT NULL,
    created_session_id  VARCHAR(255)             NOT NULL,
    modified_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_session_id VARCHAR(255)             NOT NULL
);

CREATE TABLE core_audit.allowed_currency_audit
(
    audit_timestamp     TIMESTAMP WITH TIME ZONE,
    event               VARCHAR(6),

    ccy                 VARCHAR(3),
    enabled             BOOLEAN,

    created_timestamp   TIMESTAMP WITH TIME ZONE,
    created_session_id  VARCHAR(255),
    modified_timestamp  TIMESTAMP WITH TIME ZONE,
    modified_session_id VARCHAR(255)
);

CREATE TRIGGER allowed_currency_audit_trigger
    BEFORE INSERT OR UPDATE OR DELETE
    ON core.allowed_currency
    FOR EACH ROW
EXECUTE FUNCTION audit_trigger();

GRANT SELECT ON core.balance TO core_usr;

SELECT SET_CONFIG('app.session_id', 'init.sql', FALSE);
INSERT INTO core.allowed_currency (ccy, enabled)
VALUES ('EUR', TRUE),
       ('SEK', TRUE),
       ('GBP', TRUE),
       ('USD', TRUE);

CREATE TABLE core.transaction
(
    tran_id             BIGINT PRIMARY KEY,
    acco_id             BIGINT                   NOT NULL,
    amount              NUMERIC(16, 2)           NOT NULL,
    ccy                 VARCHAR(3)               NOT NULL,
    direction           VARCHAR(3)               NOT NULL,
    description         VARCHAR(140)             NOT NULL,
    inserted_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,

    created_timestamp   TIMESTAMP WITH TIME ZONE NOT NULL,
    created_session_id  VARCHAR(255)             NOT NULL,
    modified_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_session_id VARCHAR(255)             NOT NULL
);

CREATE TABLE core_audit.transaction_audit
(
    audit_timestamp     TIMESTAMP WITH TIME ZONE,
    event               VARCHAR(6),

    tran_id             BIGINT,
    acco_id             BIGINT,
    amount              NUMERIC(16, 2),
    ccy                 VARCHAR(3),
    direction           VARCHAR(3),
    description         VARCHAR(140),
    inserted_timestamp  TIMESTAMP WITH TIME ZONE,

    created_timestamp   TIMESTAMP WITH TIME ZONE,
    created_session_id  VARCHAR(255),
    modified_timestamp  TIMESTAMP WITH TIME ZONE,
    modified_session_id VARCHAR(255)
);

CREATE SEQUENCE core.tran_seq AS
    BIGINT INCREMENT BY 1
    START WITH 1000;

GRANT SELECT, INSERT, UPDATE ON core.transaction TO core_usr;

CREATE TRIGGER transaction_audit_trigger
    BEFORE INSERT OR UPDATE OR DELETE
    ON core.transaction
    FOR EACH ROW
EXECUTE FUNCTION audit_trigger();
