CREATE TABLE order_address (
  orderId BIGINT NOT NULL,
  addressId INT NOT NULL,

  FOREIGN KEY (orderId) REFERENCES purchase_order(id),
  FOREIGN KEY (addressId) REFERENCES address(id)
);