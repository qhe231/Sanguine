insert into user_authentication (userName, hashedPassword, salt, hashNum) values
  ('Cricket', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1),
  ('TreeBoy', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678', 'qwertyuiop', 1);

insert into user_info values
  (1, 'Chirpings of a Lonely Insect', 'Sally', 'Green', '1989-06-17', '', 'I like collecting bugs.'),
  (2, 'The Forest', 'John', 'Magnus', '1992-01-03', '', 'Nothing to say here.');

insert into articles_and_comments (datePosted, title, content, parentId, userBelongedId) values
  ('2020-01-20 18:35:00', 'How About Them Apples', 'I like all kinds of apples. My favourite are Granny Smith, but honestly I can''t pick a least favourite, I love them all so much. Apples are just the best. Don''t you agree? What''s your favourite kind of apple?', null, 2),
  ('2020-01-20 18:40:02', 'Apples?', 'Wow dude, you like apples to just... an unhealthy degree. Have you considered therapy?', 1, 1),
  ('2020-01-21 00:03:23', 'Something', 'I wanted to post something but couldn''t think of what so this is really just filler.', null, 1),
  ('2020-01-22 10:03:12', 'Apples!', 'That''s not very nice.', 2, 2),
  ('2020-01-22 11:59:59', 'Sorry', 'You''re right, that was mean of me.', 4, 1),
  ('2020-01-22 11:59:59', 'Back on Topic', 'I actually prefer oranges.', 1, 1);

  insert into user_info (userId, blogName, firstName, lastName, dateOfBirth, avatarURL, profile)