CREATE TABLE IF NOT EXISTS users (
    id         INTEGER      PRIMARY KEY AUTOINCREMENT,
    handle     VARCHAR(255) UNIQUE NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
    id   INTEGER      PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS profiles (
    id      INTEGER       PRIMARY KEY AUTOINCREMENT,
    name    VARCHAR(255),
    company VARCHAR(255),
    type    VARCHAR(255),
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS tickets (
    id             INTEGER      PRIMARY KEY AUTOINCREMENT,
    action         VARCHAR(255) NOT NULL,
    object         VARCHAR(255) NOT NULL,
    details        TEXT,
    status         VARCHAR(50)  NOT NULL DEFAULT 'ABERTO',
    reason         TEXT,
    creator_id     INTEGER      NOT NULL,
    assignee_id    INTEGER      NOT NULL,
    responsible_id INTEGER,
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NOT NULL,
    FOREIGN KEY (creator_id)     REFERENCES users(id),
    FOREIGN KEY (assignee_id)    REFERENCES users(id),
    FOREIGN KEY (responsible_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS ticket_observers (
    ticket_id INTEGER      NOT NULL,
    email     VARCHAR(255) NOT NULL,
    PRIMARY KEY (ticket_id, email),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);
