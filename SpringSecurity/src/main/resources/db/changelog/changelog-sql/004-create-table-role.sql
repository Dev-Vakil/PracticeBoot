-- liquibase formatted sql

-- changeset dev:4


CREATE TABLE role (
    id INT UNIQUE AUTO_INCREMENT,
    name varchar(15) UNIQUE,    
    PRIMARY KEY (id)
)
