-- liquibase formatted sql

-- changeset dev:2

ALTER TABLE providers DROP COLUMN username,
ADD COLUMN username VARCHAR(30) UNIQUE AFTER provider_code;