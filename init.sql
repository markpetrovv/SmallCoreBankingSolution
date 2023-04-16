CREATE TABLE accounts (
                          account_id SERIAL PRIMARY KEY,
                          customer_id BIGINT NOT NULL,
                          country VARCHAR(255) NOT NULL
);

CREATE TABLE balances (
                          balance_id SERIAL PRIMARY KEY,
                          account_id BIGINT NOT NULL,
                          available_amount NUMERIC(19, 2) NOT NULL,
                          currency VARCHAR(3) NOT NULL,
                          FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);

CREATE TABLE transactions (
                              transaction_id SERIAL PRIMARY KEY,
                              account_id BIGINT NOT NULL,
                              amount NUMERIC(19, 2) NOT NULL,
                              currency VARCHAR(3) NOT NULL,
                              direction VARCHAR(3) NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              balance_after_transaction NUMERIC(19, 2) NOT NULL,
                              FOREIGN KEY (account_id) REFERENCES accounts (account_id)
);