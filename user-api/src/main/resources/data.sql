INSERT INTO roles (name) VALUES
    ('ROLE_USER'),
    ('ROLE_GUEST'),
    ('ROLE_VIEWER')
;

INSERT INTO users (handle, email, password, created_at)
VALUES 
    ('marcio', 'marcio@mail.com', 'password', CURRENT_TIMESTAMP),
    ('josue', 'josue@mail.com', 'password', CURRENT_TIMESTAMP)
;

INSERT INTO users_roles (user_id, role_id) 
VALUES
    (1, 1), -- Marcio has ROLE_USER
    (1, 3), -- Marcio has ROLE_VIEWER
    (2, 2)  -- Josue has ROLE_GUEST
;

INSERT INTO profiles (id, name, company, type)
VALUES
    (1, 'Marcio Ramos', 'Empresa 1', 'PROFESSIONAL'),
    (2, 'Josue Torres', 'Empresa 2', 'FREE')
;

-- RELACIONAL
INSERT INTO vulnerability_reports (system_under_test, created_at, updated_at, user_id) 
VALUES
    ('System A', '2025-01-01 10:00:00', '2025-01-01 10:00:00', 1),
    ('System B', '2025-01-02 10:00:00', '2025-01-02 10:00:00', 1),
    ('System C', '2025-01-03 10:00:00', '2025-01-03 10:00:00', 2),
    ('System D', '2025-01-04 10:00:00', '2025-01-04 10:00:00', 1),
    ('System E', '2025-01-05 10:00:00', '2025-01-05 10:00:00', 2),
    ('System F', '2025-01-06 10:00:00', '2025-01-06 10:00:00', 1),
    ('System G', '2025-01-07 10:00:00', '2025-01-07 10:00:00', 2),
    ('System H', '2025-01-08 10:00:00', '2025-01-08 10:00:00', 1),
    ('System I', '2025-01-09 10:00:00', '2025-01-09 10:00:00', 2),
    ('System J', '2025-01-10 10:00:00', '2025-01-10 10:00:00', 1),
    ('System K', '2025-01-11 10:00:00', '2025-01-11 10:00:00', 2),
    ('System L', '2025-01-12 10:00:00', '2025-01-12 10:00:00', 1),
    ('System M', '2025-01-13 10:00:00', '2025-01-13 10:00:00', 2),
    ('System N', '2025-01-14 10:00:00', '2025-01-14 10:00:00', 1),
    ('System O', '2025-01-15 10:00:00', '2025-01-15 10:00:00', 2),
    ('System P', '2025-01-16 10:00:00', '2025-01-16 10:00:00', 1),
    ('System Q', '2025-01-17 10:00:00', '2025-01-17 10:00:00', 2),
    ('System R', '2025-01-18 10:00:00', '2025-01-18 10:00:00', 1),
    ('System S', '2025-01-19 10:00:00', '2025-01-19 10:00:00', 2),
    ('System T', '2025-01-20 10:00:00', '2025-01-20 10:00:00', 1),
    ('System U', '2025-01-21 10:00:00', '2025-01-21 10:00:00', 2),
    ('System V', '2025-01-22 10:00:00', '2025-01-22 10:00:00', 1),
    ('System W', '2025-01-23 10:00:00', '2025-01-23 10:00:00', 2),
    ('System X', '2025-01-24 10:00:00', '2025-01-24 10:00:00', 1),
    ('System Y', '2025-01-25 10:00:00', '2025-01-25 10:00:00', 2)
;

INSERT INTO vulnerabilities (description, severity, report_id, created_at, updated_at)
VALUES
    ('SQL Injection vulnerability in login form', 'HIGH', 1, '2025-01-01 10:00:00', '2025-01-01 10:00:00'),
    ('Cross-Site Scripting (XSS) in user profile page', 'MEDIUM', 1, '2025-01-01 10:00:00', '2025-01-01 10:00:00'),
    ('Insecure Direct Object Reference (IDOR) in file download feature', 'HIGH', 2, '2025-01-02 10:00:00', '2025-01-02 10:00:00'),
    ('Broken Authentication in session management', 'CRITICAL', 3, '2025-01-03 10:00:00', '2025-01-03 10:00:00'),
    ('Sensitive Data Exposure in API response', 'HIGH', 4, '2025-01-04 10:00:00', '2025-01-04 10:00:00'),
    ('Security Misconfiguration in server headers', 'LOW', 5, '2025-01-05 10:00:00', '2025-01-05 10:00:00'),
    ('Using Components with Known Vulnerabilities', 'MEDIUM', 6, '2025-01-06 10:00:00', '2025-01-06 10:00:00'),
    ('Insufficient Logging and Monitoring', 'LOW', 7, '2025-01-07 10:00:00', '2025-01-07 10:00:00'),
    ('XML External Entity (XXE) injection', 'HIGH', 8, '2025-01-08 10:00:00', '2025-01-08 10:00:00'),
    ('Broken Access Control in admin panel', 'CRITICAL', 9, '2025-01-09 10:00:00', '2025-01-09 10:00:00')
;