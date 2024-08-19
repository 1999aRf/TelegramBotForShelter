-- liquibase formatted sql

-- changeset 1999aRf:2

CREATE TABLE shelters(
id SERIAL PRIMARY KEY,
chat_id BIGINT NOT NUll,
client_name TEXT,
contact_number TEXT
)