CREATE TABLE users
(
    user_id    VARCHAR(50) PRIMARY KEY,
    login      VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL
);