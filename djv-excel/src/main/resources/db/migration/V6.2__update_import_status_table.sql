alter table importStatus
  drop column failedItems;

create table failedImportItem (
  Id BIGINT AUTO_INCREMENT PRIMARY KEY,
  statusId ${types.uuid},
  failedItem ${types.text},
  CONSTRAINT `FK_failedImportItem_importStatus` FOREIGN KEY (statusId) REFERENCES importStatus(jobId) ON DELETE CASCADE
);
