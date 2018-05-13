create table importStatus (
  id VARCHAR(36) PRIMARY KEY,
  successCount INT NOT NULL,
  failureCount INT NOT NULL,
  failedItems ${types.text}
);