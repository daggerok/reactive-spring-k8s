CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE IF EXISTS messages;
CREATE TABLE messages (
    id UUID NOT NULL DEFAULT uuid_generate_v1(),
    sender VARCHAR(36) NOT NULL,
    recipient VARCHAR(36),
    body TEXT,
    CONSTRAINT messages_pk PRIMARY KEY ( id )
);
