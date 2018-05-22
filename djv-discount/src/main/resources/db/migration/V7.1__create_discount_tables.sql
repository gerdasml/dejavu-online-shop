CREATE TABLE `discount` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `activeFrom` TIMESTAMP NOT NULL,
  `activeTo` TIMESTAMP NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `value` DECIMAL(10,2)
);

CREATE TABLE `categoryDiscount` (
  `id` BIGINT PRIMARY KEY,
  `categoryId` BIGINT NOT NULL,
  CONSTRAINT FK_categoryDiscount_category FOREIGN KEY (`categoryId`) REFERENCES `category`(`id`),
  CONSTRAINT FK_categoryDiscount_discount FOREIGN KEY (`id`) REFERENCES `discount`(`id`)
);

CREATE TABLE `productDiscount` (
  `id` BIGINT PRIMARY KEY,
  `productId` BIGINT NOT NULL,
  CONSTRAINT FK_productDiscount_product FOREIGN KEY (`productId`) REFERENCES `product`(`id`),
  CONSTRAINT FK_productDiscount_discount FOREIGN KEY (`id`) REFERENCES `discount`(`id`)
);
