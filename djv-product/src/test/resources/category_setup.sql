CREATE TABLE category (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) UNIQUE NOT NULL,
  iconName VARCHAR(255),
  displayName VARCHAR(255),
  parentCategory BIGINT,
  FOREIGN KEY (parentCategory) REFERENCES category(id)
);