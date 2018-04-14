CREATE TABLE ${tables.user.name} (
  ${tables.user.columns.id} INT AUTO_INCREMENT PRIMARY KEY,
  ${tables.user.columns.email} VARCHAR(255) UNIQUE NOT NULL,
  ${tables.user.columns.password} VARCHAR(255) NOT NULL,
  ${tables.user.columns.firstName} VARCHAR(255),
  ${tables.user.columns.lastName} VARCHAR(255),
  ${tables.user.columns.type} VARCHAR(10) NOT NULL,
  ${tables.user.columns.banned} ${types.bool} NOT NULL
);
