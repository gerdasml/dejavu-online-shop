CREATE TABLE ${tables.product.name} (
  ${tables.product.columns.id} BIGINT AUTO_INCREMENT PRIMARY KEY,
  ${tables.product.columns.name} VARCHAR(255) NOT NULL,
  ${tables.product.columns.description} VARCHAR(80000),
  ${tables.product.columns.creationDate} TIMESTAMP,
  ${tables.product.columns.category} BIGINT,
  ${tables.product.columns.price} DECIMAL,
);