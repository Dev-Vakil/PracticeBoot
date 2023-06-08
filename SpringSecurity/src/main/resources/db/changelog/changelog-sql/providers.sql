-- liquibase formatted sql

-- changeset liquibase:2


CREATE TABLE provider (
    provider_id INT UNIQUE,
    provider_name VARCHAR(128),
    provider_code VARCHAR(30),
    username VARCHAR(30),
    password VARCHAR(30),
    email VARCHAR(256)UNIQUE,
    is_active TINYINT,
    created_date DATETIME,
    modified_at DATETIME,
    PRIMARY KEY (provider_id)
)