-- liquibase formatted sql

-- changeset dev:6

INSERT INTO securitydemo.role (id,name)
VALUES (1,"USER");
INSERT INTO securitydemo.role (id,name)
VALUES (2,"ADMIN");
INSERT INTO securitydemo.role (id,name)
VALUES (3,"PAYER");