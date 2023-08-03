-- liquibase formatted sql

-- changeset dev:3

CREATE TABLE available_pricelist_services (
    id INT UNIQUE AUTO_INCREMENT,
    service_code VARCHAR(64),
    service_description VARCHAR(256),
    default_price INT,
    payer_id INT,
    created_at DATETIME,
    updated_at DATETIME,      
    PRIMARY KEY (id)    
)