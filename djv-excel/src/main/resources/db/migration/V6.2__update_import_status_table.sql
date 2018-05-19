alter table importStatus
  drop column failedItems;

create table failedImportItem (
  statusId VARCHAR(36),
  failedItem ${types.text},
  CONSTRAINT `PK_failedImportItem` PRIMARY KEY (`statusId`, `failedItem`),
  CONSTRAINT `FK_failedImportItem_importStatus` FOREIGN KEY (statusId) REFERENCES importStatus(jobId) ON DELETE CASCADE
);
