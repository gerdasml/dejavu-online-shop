CREATE TABLE cart (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  userId BIGINT NOT NULL,

  FOREIGN KEY (UserId) REFERENCES user(id)
);

--CREATE TABLE cartItem (

--)