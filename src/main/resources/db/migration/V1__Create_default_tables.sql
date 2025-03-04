CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    role       VARCHAR(10)


);

CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    status      VARCHAR(50),
    priority    VARCHAR(50),
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    executor    VARCHAR(255),
    user_id     INT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE comments
(
    id      SERIAL PRIMARY KEY,
    comment TEXT NOT NULL,
    user_id INT,
    task_id INT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE user_task
(
    user_id INT,
    task_id INT,
    PRIMARY KEY (user_id, task_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE roles
(
    id        SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);


INSERT INTO roles (role_name)
VALUES ('USER');
INSERT INTO roles (role_name)
VALUES ('ADMIN');

ALTER TABLE users
    ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ADD COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ADD COLUMN version INT DEFAULT 0 NOT NULL;

ALTER TABLE tasks
    ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ADD COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ADD COLUMN version INT DEFAULT 0 NOT NULL;

ALTER TABLE comments
    ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ADD COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ADD COLUMN version INT DEFAULT 0 NOT NULL;



