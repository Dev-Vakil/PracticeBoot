-- liquibase formatted sql

-- changeset dev:7

CREATE TABLE payer (
    payer_id INT UNIQUE,
    payer_name VARCHAR(128),
    payer_code VARCHAR(30) UNIQUE,    
    password VARCHAR(128),
    email VARCHAR(256)UNIQUE,
    is_active TINYINT,
    created_date DATETIME,
    modified_at DATETIME,
    PRIMARY KEY (payer_id)
)