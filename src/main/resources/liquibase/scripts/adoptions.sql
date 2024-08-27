-- liquibase formatted sql

-- changeset pupuskjaer:3

create table adoptions(
id serial primary key,
probation_period timestamp,
result int,
animal_id bigint references animals(id),
client_id bigint references clients(id)
)
