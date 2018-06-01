create table importStatus (
  jobId ${types.uuid} PRIMARY KEY,
  status VARCHAR(10) NOT NULL,
  successCount INT NOT NULL,
  failureCount INT NOT NULL,
  total INT NOT NULL,
  startTime TIMESTAMP NOT NULL,
  failedItems ${types.text}
);