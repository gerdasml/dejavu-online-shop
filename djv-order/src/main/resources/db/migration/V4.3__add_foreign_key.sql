ALTER TABLE orderItem
ADD FOREIGN KEY (OrderId) REFERENCES purchase_order(id);