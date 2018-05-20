
CREATE TABLE `category_property` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `categoryId` BIGINT NOT NULL,
  CONSTRAINT `FK_productProperty_category` FOREIGN KEY (categoryId) REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE `product_property` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `value` VARCHAR(255) NOT NULL,
  `category_property_Id` BIGINT NOT NULL,
  `productId` BIGINT NOT NULL,
  CONSTRAINT `FK_productProperty_categoryProperty` FOREIGN KEY (category_property_Id) REFERENCES category_property(id) ON DELETE CASCADE
);

CREATE TABLE `additional_image_url` (
  `imageUrl` VARCHAR(255) NOT NULL,
  `productId` BIGINT NOT NULL,
  CONSTRAINT `PK_additionalImagesUrls` PRIMARY KEY (`imageUrl`, `productId`),
  CONSTRAINT `FK_additionalImagesUrls_Product` FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);