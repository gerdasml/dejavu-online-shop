CREATE TABLE cart (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  userId INT NOT NULL,

  FOREIGN KEY (userId) REFERENCES user(id)
);

CREATE TABLE cartItem (
  cartId BIGINT NOT NULL,
  itemId BIGINT NOT NULL,

  FOREIGN KEY (cartId) REFERENCES cart(id),
  FOREIGN KEY (itemId) REFERENCES orderItem(id)
)