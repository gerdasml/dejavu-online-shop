INSERT INTO address
(
  street, country, city, zipCode
)
VALUES
  (
      'Giedriu g. 27',
      'Lithuania',
      'Vilkaviskis',
      '05732'
  ),
  (
      'Didlaukio g.59, 412A',
      'Estonia',
      'Talin',
      '098765'
  );

UPDATE user
SET phone='863629548', address_id='1' WHERE id = 1;

UPDATE user
SET phone='861280831', address_id='2' WHERE id = 2;
