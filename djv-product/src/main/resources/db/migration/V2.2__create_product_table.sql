CREATE TABLE ${tables.product.name} (
  ${tables.product.columns.id} BIGINT AUTO_INCREMENT PRIMARY KEY,
  ${tables.product.columns.name} VARCHAR(255) NOT NULL,
  ${tables.product.columns.description} ${types.text},
  ${tables.product.columns.creationDate} TIMESTAMP,
  ${tables.product.columns.category} BIGINT,
  ${tables.product.columns.price} DECIMAL(10,2),
  CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (${tables.product.columns.category}) REFERENCES ${tables.category.name}(${tables.category.columns.id}) ON DELETE CASCADE
);