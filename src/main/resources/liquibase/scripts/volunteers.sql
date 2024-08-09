-- liquibase formatted sql

-- changeset pupuskjaer:1

CREATE TABLE volunteers(
id SERIAL PRIMARY KEY,
chat_id INT,
name TEXT,
contacts SMALLINT
)