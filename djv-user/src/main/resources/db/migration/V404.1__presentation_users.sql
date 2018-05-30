insert into `address` (`id`, `street`, `city`, `country`, `zipCode`) values
  (1, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011'),
  (2, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011'),
  (3, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011'),
  (4, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011');

insert into `user` (`id`, `email`, `password`, `firstName`, `lastName`, `type`, `banned`, `address_id`) values
  (1, 'Vartotojas1@test.lt',  '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Vartotojas', 'Pirmauskas',   'REGULAR',  0, 1),
  (2, 'Vartotojas2@test.lt',  '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Vartotojas', 'Antrauskas',   'REGULAR',  0, 2),
  (3, 'Vartotojas3@test.lt',  '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Vartotojas', 'Trečiauskas',  'REGULAR',  1, 3),
  (4, 'admin@email.com',      '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Adas',       'Minas',        'ADMIN',    0, 4);