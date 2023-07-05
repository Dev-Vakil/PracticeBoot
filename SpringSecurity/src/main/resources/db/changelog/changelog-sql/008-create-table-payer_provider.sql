-- liquibase formatted sql

-- changeset dev:8

CREATE TABLE payer_provider (
    payer_id INT AUTO_INCREMENT,
    provider_id INT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'INACTIVE',
    created_date DATETIME,
    modified_at DATETIME,
    UNIQUE(payer_id,provider_id),
    PRIMARY KEY (payer_id,provider_id),
    FOREIGN KEY (provider_id)  
 	REFERENCES providers(provider_id),
 	FOREIGN KEY (payer_id)  
 	REFERENCES payer(payer_id)
)