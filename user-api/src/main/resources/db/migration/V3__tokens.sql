CREATE TABLE tokens (
    token      VARCHAR(255) PRIMARY KEY,
    user_id    INTEGER      NOT NULL,
    expires_at TIMESTAMP    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
