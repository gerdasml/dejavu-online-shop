CREATE TABLE `category` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `identifier` VARCHAR(255) NOT NULL,
  `iconName` VARCHAR(255),
  `name` VARCHAR(255),
  `parentCategory` BIGINT,
  CONSTRAINT FK_category_parentCategory FOREIGN KEY (`parentCategory`) REFERENCES `category`(`id`) ON DELETE CASCADE
);