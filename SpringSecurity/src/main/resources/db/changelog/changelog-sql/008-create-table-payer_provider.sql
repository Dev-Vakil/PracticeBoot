-- liquibase formatted sql

-- changeset dev:8

CREATE TABLE payer_provider (
    payer_id INT UNIQUE AUTO_INCREMENT,
    provider_id INT UNIQUE,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'INACTIVE',
    created_date DATETIME,
    modified_at DATETIME,
    PRIMARY KEY (payer_id,provider_id),
    FOREIGN KEY (provider_id)  
 	REFERENCES providers(provider_id),
 	FOREIGN KEY (payer_id)  
 	REFERENCES payer(payer_id)
)