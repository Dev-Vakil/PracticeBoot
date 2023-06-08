-- liquibase formatted sql

-- changeset liquibase:1


CREATE TABLE user (
    id INT,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role ENUM('USER','ADMIN'),
    PRIMARY KEY (id)
)

