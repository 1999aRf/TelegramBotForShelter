-- liquibase formatted sql

-- changeset 1999aRf:2

CREATE TABLE shelters(
id SERIAL PRIMARY KEY,

chat_id BIGINT ,
client_name TEXT,
contact_number TEXT,
mediaType Text,
data BYTEA

)