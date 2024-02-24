CREATE TABLE users (
    id serial PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(2048) NOT NULL,
    role VARCHAR(32) NOT NULL,
    
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    email VARCHAR(64),

    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    xp FLOAT NOT NULL DEFAULT 0,
    level INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE rewards (
    id SERIAL PRIMARY KEY,
    value INTEGER NOT NULL,
    reward_reason VARCHAR(128) NOT NULL,
    reward_description VARCHAR(1024) DEFAULT 'default reason',
    is_visible BOOLEAN NOT NULL DEFAULT FALSE,
    received_at TIMESTAMP
);