CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(2048) NOT NULL,
    user_role VARCHAR(32) NOT NULL,

    first_name VARCHAR(64),
    last_name VARCHAR(64),
    email VARCHAR(64),

    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),

    xp FLOAT NOT NULL DEFAULT 0,
    level INTEGER NOT NULL DEFAULT 0,
    score FLOAT DEFAULT NULL,
    scores_count INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS rewards (
    id SERIAL PRIMARY KEY,
    value BIGINT NOT NULL,
    reason VARCHAR(64) NOT NULL,
    description VARCHAR(1024) DEFAULT 'default reason',

    rewarded_user_id SERIAL references users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),

    valid BOOLEAN NOT NULL DEFAULT TRUE,
    is_visible BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS reward_reasons (
  id SERIAL PRIMARY KEY,
  reason VARCHAR(64) NOT NULL,
  string_name VARCHAR(86) NOT NULL,
  description VARCHAR(256),
  type VARCHAR NOT NULL CONSTRAINT reward_type CHECK (type='REWARD' OR type='FINE') DEFAULT 'REWARD',
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

INSERT INTO reward_reasons (reason, string_name, description, type, enabled, created_at, updated_at)
VALUES
    ('TEST_REWARD', 'test reward', 'reward for api testing', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('TIMEZONE_TEST_REWARD', 'timezone test reward', 'reward for timestamp testing', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('ACTIVITY', 'activity reward', 'reward for user`s activity', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('VISIT', 'visit reward', 'reward for user`s visiting', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('QUEST', 'quest reward', 'reward for completing a quest', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('TASK', 'task reward', 'reward for completing a task', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('BUG_REPORT', 'bug report reward', 'bug bounty', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('BUG_REPORT_CONFIRMED', 'bug report confirmed reward', 'confirmed bug bounty', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('DONATION', 'donation reward', 'reward for donat', 'REWARD', DEFAULT, DEFAULT, DEFAULT),
    ('OTHER', 'other reward', 'custom (extra) reward', 'REWARD', DEFAULT, DEFAULT, DEFAULT),

    ('TEST_FINE', 'test fine', 'fine for api testing', 'FINE', DEFAULT, DEFAULT, DEFAULT),
    ('INACTIVITY', 'inactivity fine', 'fine for user`s inactivity', 'FINE', DEFAULT, DEFAULT, DEFAULT),
    ('BUG_ABUSE', 'bug abuse fine', 'fine for bug abuse', 'FINE', DEFAULT, DEFAULT, DEFAULT),
    ('MULTI_ACC', 'multi acc fine', 'fine for using multiple accounts', 'FINE', DEFAULT, DEFAULT, DEFAULT),
    ('OTHER_FINE', 'other fine', 'custom (extra) fine', 'FINE', DEFAULT, DEFAULT, DEFAULT);