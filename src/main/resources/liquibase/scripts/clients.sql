-- liquibase formatted sql

-- changeset pupuskjaer:1

CREATE TABLE clients(
id SERIAL PRIMARY KEY,
chat_id BIGINT NOT NUll,
client_name TEXT,
contact_number TEXT
)