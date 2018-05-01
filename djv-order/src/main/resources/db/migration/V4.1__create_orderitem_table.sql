CREATE TABLE orderItem (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  amount INT NOT NULL,
  productId BIGINT NOT NULL,
  orderId BIGINT,

  FOREIGN KEY (productId) REFERENCES product(id)
);
