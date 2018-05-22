CREATE TABLE review (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  rating INT NOT NULL,
  comment ${types.text}
);

ALTER TABLE purchase_order
ADD reviewId BIGINT;

ALTER TABLE purchase_order
ADD FOREIGN KEY (reviewId) REFERENCES review(id);
