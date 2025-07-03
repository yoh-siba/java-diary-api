-- Insert sample users (password is 'password123' encoded with BCrypt)
INSERT INTO users (username, email, password, display_name) VALUES
('testuser', 'test@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Test User'),
('johndoe', 'john@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'John Doe');

-- Insert sample tags
INSERT INTO tags (name, user_id) VALUES
('work', 1),
('personal', 1),
('travel', 1),
('health', 1),
('hobby', 1),
('family', 2),
('work', 2),
('exercise', 2);

-- Insert sample diary entries
INSERT INTO diary_entries (user_id, title, content, date, mood, weather, is_public) VALUES
(1, 'First Day at Work', 'Today was my first day at the new job. Everyone was very welcoming and I learned a lot about the company culture.', '2024-01-15', 'EXCITED', 'SUNNY', false),
(1, 'Weekend Trip', 'Went on a wonderful weekend trip to the mountains. The weather was perfect and the views were breathtaking.', '2024-01-20', 'HAPPY', 'CLOUDY', true),
(1, 'Feeling Under the Weather', 'Not feeling great today. Stayed in bed most of the day and watched movies.', '2024-01-25', 'TIRED', 'RAINY', false),
(2, 'Family Dinner', 'Had a great family dinner tonight. We talked about old memories and shared lots of laughs.', '2024-01-16', 'HAPPY', 'SUNNY', false),
(2, 'Morning Jog', 'Started the day with a refreshing morning jog. Feeling energized and ready for the day ahead.', '2024-01-22', 'EXCITED', 'CLOUDY', false);

-- Insert diary-tag relationships
INSERT INTO diary_tags (diary_entry_id, tag_id) VALUES
(1, 1), -- First Day at Work -> work
(2, 3), -- Weekend Trip -> travel
(2, 2), -- Weekend Trip -> personal
(3, 4), -- Feeling Under the Weather -> health
(4, 6), -- Family Dinner -> family
(5, 8); -- Morning Jog -> exercise