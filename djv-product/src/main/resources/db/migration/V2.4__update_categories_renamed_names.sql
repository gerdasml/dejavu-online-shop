ALTER TABLE `category`
  CHANGE `name` `identifier` VARCHAR(255);

ALTER TABLE `category`
   CHANGE `displayName` `name` VARCHAR(255);  