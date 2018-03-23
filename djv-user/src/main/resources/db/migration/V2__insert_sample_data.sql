INSERT INTO ${tables.user.name}
  ( ${tables.user.columns.email}
  , ${tables.user.columns.password}
  , ${tables.user.columns.firstName}
  , ${tables.user.columns.lastName}
  , ${tables.user.columns.type}
  , ${tables.user.columns.banned}
  )
  VALUES
  ( 'test@email.com'
  , '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'
  , 'Testas'
  , 'Testauskas'
  , 'REGULAR'
  , 1
  ),
  ( 'admin@email.com'
  , '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'
  , 'Ad'
  , 'Min'
  , 'ADMIN'
  , 0
  );
