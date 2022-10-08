CREATE FUNCTION audit_trigger() RETURNS TRIGGER AS
$audit_trigger$
BEGIN
    IF (tg_op = 'INSERT') THEN
        new.inserted_timestamp := CURRENT_TIMESTAMP;
        new.inserted_session_id := CURRENT_SETTING('app.session_id');
    END IF;

    new.modified_timestamp := CURRENT_TIMESTAMP;
    new.modified_session_id := CURRENT_SETTING('app.session_id');
    RETURN new;
END;
$audit_trigger$ LANGUAGE plpgsql;

CREATE ROLE core_usr
    LOGIN
    PASSWORD 'core_pw';

CREATE SCHEMA core;

CREATE TABLE core.account
(
    acco_id             BIGINT PRIMARY KEY,
    cust_id             BIGINT                   NOT NULL,
    status_code         VARCHAR(1)               NOT NULL,
    country             VARCHAR(2)               NOT NULL,
    inserted_dtime      TIMESTAMP WITH TIME ZONE NOT NULL,

    inserted_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    inserted_session_id VARCHAR(255),

    modified_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_session_id VARCHAR(255)
);

CREATE SEQUENCE core.acco_seq AS
    BIGINT INCREMENT BY 1
    START WITH 1000;

GRANT SELECT, INSERT, UPDATE ON core.account TO core_usr;

CREATE TRIGGER account_audit_trigger
    BEFORE INSERT OR UPDATE
    ON core.account
    FOR EACH ROW
EXECUTE FUNCTION audit_trigger();

CREATE TABLE core.balance
(
    bala_id             BIGINT PRIMARY KEY,
    acco_id             BIGINT                   NOT NULL,
    amount              NUMERIC(16, 2)           NOT NULL,
    ccy                 VARCHAR(3)               NOT NULL,
    valid_to_timestamp  TIMESTAMP WITH TIME ZONE,

    inserted_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    inserted_session_id VARCHAR(255),
    modified_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_session_id VARCHAR(255)
);

CREATE SEQUENCE core.bala_seq AS
    BIGINT INCREMENT BY 1
    START WITH 1000;

GRANT SELECT, INSERT, UPDATE ON core.balance TO core_usr;

CREATE TRIGGER balance_audit_trigger
    BEFORE INSERT OR UPDATE
    ON core.balance
    FOR EACH ROW
EXECUTE FUNCTION audit_trigger();

CREATE TABLE core.allowed_currency
(
    ccy                 VARCHAR(3) UNIQUE        NOT NULL,
    enabled             BOOLEAN                  NOT NULL,

    inserted_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    inserted_session_id VARCHAR(255),
    modified_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_session_id VARCHAR(255)
);

CREATE TRIGGER allowed_currency_audit_trigger
    BEFORE INSERT OR UPDATE
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
