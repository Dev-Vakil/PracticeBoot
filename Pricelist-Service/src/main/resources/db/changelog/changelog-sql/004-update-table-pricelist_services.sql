-- liquibase formatted sql

-- changeset dev:4

ALTER TABLE pricelist_service RENAME TO service_pricelist;

ALTER TABLE service_pricelist
RENAME COLUMN pricelist_service_id TO service_pricelist_id;