CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(2048) NOT NULL,
    user_role VARCHAR(32) NOT NULL,

    first_name VARCHAR(64),
    last_name VARCHAR(64),
    email VARCHAR(64),

    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,

    xp FLOAT NOT NULL DEFAULT 0,
    level INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS rewards (
    id SERIAL PRIMARY KEY,
    value BIGINT NOT NULL,
    reward_reason VARCHAR(64) NOT NULL,
    reward_description VARCHAR(1024) DEFAULT 'default reason',

    rewarded_user_id SERIAL references users(id),
    received_at TIMESTAMP WITH TIME ZONE,

    valid BOOLEAN NOT NULL DEFAULT TRUE,
    is_visible BOOLEAN NOT NULL DEFAULT FALSE
);