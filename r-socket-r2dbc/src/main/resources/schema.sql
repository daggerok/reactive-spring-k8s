CREATE SCHEMA IF NOT EXISTS public;
DROP TABLE IF EXISTS messages;
CREATE TABLE messages (
    id UUID NOT NULL DEFAULT RANDOM_UUID(),
    sender VARCHAR(36) NOT NULL,
    recipient VARCHAR(36),
    body VARCHAR,
    CONSTRAINT messages_pk PRIMARY KEY ( id )
);
