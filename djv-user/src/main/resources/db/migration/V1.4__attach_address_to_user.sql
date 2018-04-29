ALTER TABLE user
ADD phone VARCHAR(20);

ALTER TABLE user
ADD address_id INT;

ALTER TABLE user
ADD FOREIGN KEY (address_id) REFERENCES address(id);