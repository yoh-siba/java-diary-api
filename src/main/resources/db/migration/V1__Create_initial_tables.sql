-- Create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create unique constraints for users
ALTER TABLE users ADD CONSTRAINT uk_users_username UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT uk_users_email UNIQUE (email);

-- Create diary_entries table
CREATE TABLE diary_entries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    date DATE NOT NULL,
    mood VARCHAR(20),
    weather VARCHAR(20),
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add foreign key for diary_entries
ALTER TABLE diary_entries ADD CONSTRAINT fk_diary_entries_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- Create tags table
CREATE TABLE tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add foreign key and unique constraint for tags
ALTER TABLE tags ADD CONSTRAINT fk_tags_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE tags ADD CONSTRAINT uk_tags_name_user UNIQUE (name, user_id);

-- Create diary_tags table (join table)
CREATE TABLE diary_tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diary_entry_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL
);

-- Add foreign keys and unique constraint for diary_tags
ALTER TABLE diary_tags ADD CONSTRAINT fk_diary_tags_diary_entry_id FOREIGN KEY (diary_entry_id) REFERENCES diary_entries(id) ON DELETE CASCADE;
ALTER TABLE diary_tags ADD CONSTRAINT fk_diary_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE;
ALTER TABLE diary_tags ADD CONSTRAINT uk_diary_tags_entry_tag UNIQUE (diary_entry_id, tag_id);

-- Create indexes for better performance
CREATE INDEX idx_diary_entries_user_id ON diary_entries(user_id);
CREATE INDEX idx_diary_entries_date ON diary_entries(date);
CREATE INDEX idx_diary_entries_mood ON diary_entries(mood);
CREATE INDEX idx_tags_user_id ON tags(user_id);
CREATE INDEX idx_diary_tags_diary_entry_id ON diary_tags(diary_entry_id);
CREATE INDEX idx_diary_tags_tag_id ON diary_tags(tag_id);