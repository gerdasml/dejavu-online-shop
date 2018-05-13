create table importStatus (
  jobId VARCHAR(36) PRIMARY KEY,
  successCount INT NOT NULL,
  failureCount INT NOT NULL,
  failedItems ${types.text}
);