CREATE TABLE ${tables.user.name} (
  ${tables.user.columns.id} INT AUTO_INCREMENT PRIMARY KEY,
  ${tables.user.columns.email} VARCHAR(255) UNIQUE NOT NULL,
  ${tables.user.columns.password} VARCHAR(255) NOT NULL,
  ${tables.user.columns.firstName} VARCHAR(255),
  ${tables.user.columns.lastName} VARCHAR(255),
  ${tables.user.columns.type} VARCHAR(10) NOT NULL
);

CREATE TABLE ${tables.userToken.name} (
  ${tables.userToken.columns.id} INT PRIMARY KEY,
  ${tables.userToken.columns.token} UUID UNIQUE,

  FOREIGN KEY (${tables.userToken.columns.id}) REFERENCES user(${tables.user.columns.id})
);