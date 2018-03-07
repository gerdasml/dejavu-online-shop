INSERT INTO user (email, password, firstName, lastName, userType)
VALUES ('test@email.com', '5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8', 'Testas', 'Testauskas', 'REGULAR');

INSERT INTO userToken (userId, accessToken)
VALUES (1, '1a36ffdd-48e5-4951-9d6a-420836e33a4c');