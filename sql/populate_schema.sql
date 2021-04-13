INSERT INTO users (id, username, avatar_url)
VALUES 
('14cf7bf0-5845-4fc1-aef4-9dc3021c1b0f', 'sarah_edo','https://tylermcginnis.com/would-you-rather/sarah.jpg'),
('8fc10da1-7c2a-4abd-b147-ae32332213ae', 'tylermcginnis','https://tylermcginnis.com/would-you-rather/tyler.jpg'),
('4278a63b-d3a9-4924-a7e4-73b3f8ec7bde', 'dan_abramov','https://tylermcginnis.com/would-you-rather/dan.jpg');


INSERT INTO tweets (id, text, author_id, category, profanity_index)
VALUES 
('066fcfa3-ed26-4333-a689-a24fc6d3bad1', 'Shoutout to all the speakers I know for whom English is not a first language, but can STILL explain a concept well. It s hard enough to give a good talk in your mother tongue!', '14cf7bf0-5845-4fc1-aef4-9dc3021c1b0f', 'ham', 0.0);


INSERT INTO tweets (id, text, author_id, category, profanity_index)
VALUES 
('166fcfa3-ed26-4333-a689-a24fc6d3bad2', 'Another Tweet', '14cf7bf0-5845-4fc1-aef4-9dc3021c1b0f', 'spam', 0.12);



INSERT INTO tweets (id, text, author_id, replying_to, category, profanity_index)
VALUES 
('af8038a7-b5ce-4b5a-bc6e-a86ee06b89cb', 'I agree. I''m always really impressed when I see someone giving a talk in a language that''s not their own.', '8fc10da1-7c2a-4abd-b147-ae32332213ae', '066fcfa3-ed26-4333-a689-a24fc6d3bad1', 'ham', 0.0);

INSERT INTO tweets (id, text, author_id, replying_to, category, profanity_index)
VALUES
('56a3531e-0967-498e-9cee-4e7b33ec8a1c', 'It can be difficult at times.', '4278a63b-d3a9-4924-a7e4-73b3f8ec7bde', 'af8038a7-b5ce-4b5a-bc6e-a86ee06b89cb', 'unprocessed', 0.0);


INSERT INTO likes (tweet_id, user_id)
VALUES (
    '066fcfa3-ed26-4333-a689-a24fc6d3bad1', '8fc10da1-7c2a-4abd-b147-ae32332213ae'
)
