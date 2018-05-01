ALTER TABLE orderItem
ADD FOREIGN KEY (orderId) REFERENCES purchase_order(id);