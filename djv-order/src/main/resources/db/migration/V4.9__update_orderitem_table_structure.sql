ALTER TABLE orderItem
DROP productId;

ALTER TABLE orderItem
ADD name VARCHAR(255) NOT NULL;

ALTER TABLE orderItem
ADD description ${types.text};

ALTER TABLE orderItem
ADD price DECIMAL(10,2);

ALTER TABLE orderItem
ADD mainImageUrl VARCHAR(255);

ALTER TABLE orderItem
ADD productId VARCHAR(255);