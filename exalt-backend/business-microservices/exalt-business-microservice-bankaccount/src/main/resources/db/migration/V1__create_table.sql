CREATE TABLE IF NOT EXISTS bank_accounts
(
    account_id   VARCHAR(255) PRIMARY KEY,
    state        VARCHAR(255) NOT NULL,
    balance      DECIMAL      NOT NULL,
    created_date VARCHAR(255) NOT NULL,
    customer_id  VARCHAR(255) NOT NULL
);