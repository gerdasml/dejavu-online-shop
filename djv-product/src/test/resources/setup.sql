CREATE TABLE product (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(80000),
  creationDate TIMESTAMP,
  category INT,
  price DECIMAL,
);