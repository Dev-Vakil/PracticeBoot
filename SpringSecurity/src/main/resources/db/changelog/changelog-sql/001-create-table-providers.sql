-- liquibase formatted sql

-- changeset dev:1


CREATE TABLE providers (
    provider_id INT UNIQUE,
    provider_name VARCHAR(128),
    provider_code VARCHAR(30) UNIQUE,
    username VARCHAR(30),
    password VARCHAR(100),
    email VARCHAR(256)UNIQUE,
    is_active TINYINT,
    created_date DATETIME,
    modified_at DATETIME,
    PRIMARY KEY (provider_id)
)