ALTER TABLE `product`
  ADD COLUMN `identifier` VARCHAR(255);

ALTER TABLE `product`
  ADD COLUMN `mainImageUrl` VARCHAR(255);

CREATE TABLE `product_property` (
  `name` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  `productId` BIGINT NOT NULL,
  CONSTRAINT `FK_productProperty_property` FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE,
  CONSTRAINT `PK_productProperty` PRIMARY KEY (`productId`, `name`)
);

CREATE TABLE `additional_image_url` (
  `imageUrl` VARCHAR(255) NOT NULL,
  `productId` BIGINT NOT NULL,
  CONSTRAINT `PK_additionalImagesUrls` PRIMARY KEY (`imageUrl`, `productId`),
  CONSTRAINT `FK_additionalImagesUrls_Product` FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);