-- liquibase formatted sql

-- changeset pupuskjaer:3

CREATE TABLE shelters(
id SERIAL PRIMARY KEY,
chat_id BIGINT ,
client_name TEXT,
contact_number TEXT,
business_time TEXT,
media_type Text,
data BYTEA,
route_map_url TEXT
);