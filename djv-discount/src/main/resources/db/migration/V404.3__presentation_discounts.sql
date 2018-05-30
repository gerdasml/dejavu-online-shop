insert into `discount` (`id`, `activeFrom`, `activeTo`, `type`, `value`) values
  (1, '2018-05-29 00:00:00', '2020-06-29 00:00:00', 'ABSOLUTE', 20),
  (2, '2018-05-29 00:00:00', '2020-06-29 00:00:00', 'PERCENTAGE', 20),
  (3, '2018-05-29 00:00:00', '2020-06-29 00:00:00', 'PERCENTAGE', 10);

insert into `categoryDiscount` (`id`, `categoryId`)values
  (3, 7);

insert into `productDiscount` (`id`, `productId`) values
  (1, 2),
  (2, 1);
