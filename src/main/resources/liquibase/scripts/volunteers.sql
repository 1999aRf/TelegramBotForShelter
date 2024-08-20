-- liquibase formatted sql

-- changeset pupuskjaer:1

CREATE TABLE volunteers(
id SERIAL PRIMARY KEY,
chat_id BIGINT NOT NULL,
name TEXT,
contacts TEXT
)