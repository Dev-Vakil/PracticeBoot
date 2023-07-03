-- liquibase formatted sql

-- changeset dev:9

ALTER TABLE role_association ADD COLUMN payer_id INT UNIQUE DEFAULT NULL, 
ADD FOREIGN KEY (payer_id) REFERENCES payer(payer_id) ON DELETE CASCADE;
