-- liquibase formatted sql

-- changeset dev:3

INSERT INTO securitydemo.providers (provider_name,provider_code,username,password,email)
VALUES ("Mappa","1","DEV VAKIL","$2a$10$35jVKj0zphLeYTgHdROAHecX8VoJ3dekxsmAU5gQFYJ25j.BhzPg6","devvakil@gmail.com");