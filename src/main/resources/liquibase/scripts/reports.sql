-- liquibase formatted sql

-- changeset pupuskjaer:4

CREATE TABLE reports(
id SERIAL PRIMARY KEY,
date timestamp,
photo BYTEA,
diet text,
wellbeing text,
behavior_changes text,
is_reviewed boolean,
adoption_id BIGINT references adoptions(id)
)