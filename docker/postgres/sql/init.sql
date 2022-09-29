CREATE FUNCTION audit_trigger() RETURNS TRIGGER AS
$audit_trigger$
BEGIN
    IF (tg_op = 'INSERT') THEN
        new.inserted_timestamp := CURRENT_TIMESTAMP;
        new.inserted_user := CURRENT_USER;
    END IF;

    new.modified_timestamp := CURRENT_TIMESTAMP;
    new.modified_user := CURRENT_USER;
    RETURN new;
END;
$audit_trigger$ LANGUAGE plpgsql;

CREATE ROLE core_usr
    LOGIN
    PASSWORD 'core_pw';

CREATE SCHEMA core;

CREATE TABLE core.account
(
    acco_id            BIGINT PRIMARY KEY,
    cust_id            BIGINT                   NOT NULL,
    status_code        VARCHAR(1)               NOT NULL,
    country            VARCHAR(2)               NOT NULL,
    inserted_dtime     TIMESTAMP WITH TIME ZONE NOT NULL,

    inserted_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    inserted_user      VARCHAR(255),
    modified_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_user      VARCHAR(255)
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
    bala_id            BIGINT PRIMARY KEY,
    acco_id            BIGINT                   NOT NULL,
    amount             NUMERIC(16, 2)           NOT NULL,
    ccy                VARCHAR(3)               NOT NULL,

    inserted_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    inserted_user      VARCHAR(255),
    modified_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_user      VARCHAR(255)
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
