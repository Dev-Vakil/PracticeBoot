-- liquibase formatted sql

-- changeset dev:4


CREATE TABLE role (
    id INT UNIQUE,
    name varchar(15) UNIQUE,    
    PRIMARY KEY (id)
)
