-- liquibase formatted sql

-- changeset dev:5


CREATE TABLE role_association (
    id INT UNIQUE AUTO_INCREMENT,
    provider_id INT,
    role_id int,    
    PRIMARY KEY (id),
    CONSTRAINT fk_providers FOREIGN KEY (provider_id)  
 	REFERENCES providers(provider_id),
 	CONSTRAINT fk_role FOREIGN KEY (id)  
 	REFERENCES role(id)
)