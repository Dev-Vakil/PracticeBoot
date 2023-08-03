-- liquibase formatted sql

-- changeset dev:2

CREATE TABLE pricelist_service (
    pricelist_service_id INT UNIQUE AUTO_INCREMENT,
    service_code VARCHAR(64) NOT NULL,
    service_description VARCHAR(256),
    price INT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    pricelist_id INT NOT NULL,
    rejection_reason VARCHAR(256),
    created_at DATETIME,
    updated_at DATETIME,      
    PRIMARY KEY (pricelist_service_id),
    CONSTRAINT fk_pricelist FOREIGN KEY (pricelist_id)  
 	REFERENCES pricelist(pricelist_id)
)