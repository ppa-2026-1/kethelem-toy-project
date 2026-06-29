CREATE TABLE IF NOT EXISTS tickets (
    id             INTEGER      PRIMARY KEY AUTOINCREMENT,
    action         VARCHAR(255) NOT NULL,
    object         VARCHAR(255) NOT NULL,
    details        TEXT,
    status         VARCHAR(50)  NOT NULL DEFAULT 'ABERTO',
    reason         TEXT,
    creator_email  VARCHAR(255) NOT NULL,
    assignee_email VARCHAR(255) NOT NULL,
    responsible_email VARCHAR(255),
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket_observers (
    ticket_id INTEGER      NOT NULL,
    email     VARCHAR(255) NOT NULL,
    PRIMARY KEY (ticket_id, email),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);
