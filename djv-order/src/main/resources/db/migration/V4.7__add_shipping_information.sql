CREATE TABLE shippingInformation (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  recipientFirstName VARCHAR(255) NOT NULL,
  recipientLastName VARCHAR(255) NOT NULL,
  addressId INT NOT NULL,

  CONSTRAINT FK_shippingInformation_addressId FOREIGN KEY(addressId) REFERENCES address(id)
);

DROP TABLE order_address;

ALTER TABLE purchase_order
ADD shippingInformationId BIGINT;

ALTER TABLE purchase_order
ADD CONSTRAINT FK_purchase_order_shippingInformationId
FOREIGN KEY(shippingInformationId) REFERENCES shippingInformation(id);