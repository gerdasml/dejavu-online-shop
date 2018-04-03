CREATE TABLE ${tables.category.name} (
  ${tables.category.columns.id} BIGINT AUTO_INCREMENT PRIMARY KEY,
  ${tables.category.columns.name} VARCHAR(255) NOT NULL,
  ${tables.category.columns.iconName} VARCHAR(255),
  ${tables.category.columns.displayName} VARCHAR(255),
  ${tables.category.columns.parentCategory} BIGINT,
  FOREIGN KEY (${tables.category.columns.parentCategory}) REFERENCES ${tables.category.name}(${tables.category.columns.id})
);