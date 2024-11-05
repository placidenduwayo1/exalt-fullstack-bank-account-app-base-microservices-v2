CREATE TABLE IF NOT EXISTS bank_accounts
(
    account_id   VARCHAR(255) PRIMARY KEY,
    type         VARCHAR(255) NOT NULL,
    state        VARCHAR(255) NOT NULL,
    balance      DECIMAL      NOT NULL,
    created_date VARCHAR(255) NOT NULL,
    overdraft    DECIMAL,
    interest_rate DECIMAL,
    customer_id  VARCHAR(255) NOT NULL
);