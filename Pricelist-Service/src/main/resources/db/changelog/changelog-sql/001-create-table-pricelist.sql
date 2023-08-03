-- liquibase formatted sql

-- changeset dev:1

CREATE TABLE pricelist (
    pricelist_id INT UNIQUE AUTO_INCREMENT,
    provider_id INT NOT NULL,
    payer_id INT NOT NULL,
    status ENUM('NEW', 'ACTIVE', 'DISABLE') NOT NULL,
    is_deleted BOOLEAN,
    upload_date DATETIME,
    uploaded_by VARCHAR(64) NOT NULL,
    last_updated_date DATETIME,    
    PRIMARY KEY (pricelist_id),
    CONSTRAINT un_provider_payer 
    UNIQUE (provider_id, payer_id)
)