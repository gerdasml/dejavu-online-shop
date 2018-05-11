
CREATE TABLE `product_property` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `categoryId` BIGINT NOT NULL,
  CONSTRAINT `FK_productProperty_category` FOREIGN KEY (categoryId) REFERENCES category(id) ON DELETE CASCADE,
  CONSTRAINT `PK_productProperty` PRIMARY KEY (`id`)
);

CREATE TABLE `product_property_value` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `value` VARCHAR(255) NOT NULL,
  `product_property_Id` BIGINT NOT NULL,
  `productId` BIGINT NOT NULL,
  CONSTRAINT `FK_productPropertyValue_productProperty` FOREIGN KEY (product_property_Id) REFERENCES product_property(id) ON DELETE CASCADE,
  CONSTRAINT `PK_productProperty` PRIMARY KEY (`id`)
);

CREATE TABLE `additional_image_url` (
  `imageUrl` VARCHAR(255) NOT NULL,
  `productId` BIGINT NOT NULL,
  CONSTRAINT `PK_additionalImagesUrls` PRIMARY KEY (`imageUrl`, `productId`),
  CONSTRAINT `FK_additionalImagesUrls_Product` FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);