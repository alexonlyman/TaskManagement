CREATE TABLE IF NOT EXISTS users (
                       id INT PRIMARY KEY,
                       firstName VARCHAR(255) NOT NULL,
                       lastName VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS task (
                      id INT PRIMARY KEY,
                      status VARCHAR(50) NOT NULL,
                      priority VARCHAR(50) NOT NULL,
                      name VARCHAR(255) NOT NULL,
                      description TEXT,
                      executor VARCHAR(255),
                      user_id INT,
                      FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
                          id INT PRIMARY KEY,
                          comment TEXT NOT NULL,
                          user_id INT,
                          task_id INT,
                          FOREIGN KEY (user_id) REFERENCES users(id),
                          FOREIGN KEY (task_id) REFERENCES task(id)
);

CREATE TABLE IF NOT EXISTS user_task (
                           user_id INT,
                           task_id INT,
                           PRIMARY KEY (user_id, task_id),
                           FOREIGN KEY (user_id) REFERENCES users(id),
                           FOREIGN KEY (task_id) REFERENCES task(id)
);

CREATE TABLE IF NOT EXISTS roles (
                       id INT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');

