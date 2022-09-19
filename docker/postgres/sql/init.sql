CREATE ROLE core_usr
    LOGIN
    PASSWORD 'core_pw';

CREATE SCHEMA core;

CREATE TABLE core.account
(
    acco_id        BIGINT                   NOT NULL,
    cust_id        BIGINT                   NOT NULL,
    iban           VARCHAR(34)              NOT NULL,
    inserted_dtime TIMESTAMP WITH TIME ZONE NOT NULL,
    status_code    VARCHAR(1)               NOT NULL
);

CREATE SEQUENCE core.acco_seq AS
    BIGINT INCREMENT BY 1
    START WITH 1000;

GRANT SELECT, INSERT, UPDATE ON core.account TO core_usr;

CREATE TABLE core.balance
(
    bala_id          BIGINT                   NOT NULL,
    acco_id          BIGINT                   NOT NULL,
    amount           NUMERIC(16, 2)           NOT NULL,
    ccy              VARCHAR(3)               NOT NULL,
    valid_from_dtime TIMESTAMP WITH TIME ZONE NOT NULL,
    valid_to_dtime   TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE SEQUENCE core.bala_seq AS
    BIGINT INCREMENT BY 1
    START WITH 1000;

GRANT SELECT, INSERT, UPDATE ON core.balance TO core_usr;
