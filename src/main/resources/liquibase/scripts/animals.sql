-- liquibase formatted sql

-- changeset 1999aRf:2

CREATE TABLE animals(
id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  species TEXT NOT NULL,
  shelter_id BIGINT references shelters(id)
);