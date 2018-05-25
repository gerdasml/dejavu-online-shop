CREATE TABLE shippingInformation (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  recipientFirstName VARCHAR(255) NOT NULL,
  recipientLastName VARCHAR(255) NOT NULL
);

CREATE TABLE shippingInformation_address (
  shippingInformationId BIGINT,
  addressId INT,

  CONSTRAINT FK_shippingInformation_address_shippingInformationId FOREIGN KEY (shippingInformationId) REFERENCES shippingInformation(id),
  CONSTRAINT FK_shippingInformation_address_addressId FOREIGN KEY (addressId) REFERENCES address(id)
);

DROP TABLE order_address;

CREATE TABLE order_shippingInformation (
  orderId BIGINT NOT NULL,
  shippingInformationId BIGINT NOT NULL,

  CONSTRAINT FK_order_shippingInformation_orderId FOREIGN KEY (orderId) REFERENCES purchase_order(id),
  CONSTRAINT FK_order_shippingInformation_shippingInformationId FOREIGN KEY (shippingInformationId) REFERENCES shippingInformation(id)
);