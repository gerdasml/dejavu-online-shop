CREATE TABLE `product` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `description` ${types.text},
  `creationDate` TIMESTAMP,
  `category` BIGINT,
  `price` DECIMAL(10,2),
  `identifier` VARCHAR(255),
  `mainImageUrl` VARCHAR(255),
  CONSTRAINT FK_product_category FOREIGN KEY (`category`) REFERENCES`category`(`id`) ON DELETE CASCADE
);