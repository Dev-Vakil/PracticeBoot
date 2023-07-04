-- liquibase formatted sql

-- changeset dev:6

INSERT INTO securitydemo.role (name)
VALUES ("USER");
INSERT INTO securitydemo.role (name)
VALUES ("ADMIN");
INSERT INTO securitydemo.role (name)
VALUES ("PAYER");