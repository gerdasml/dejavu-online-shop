insert into `category` (`id`, `iconName`, `identifier`, `name`, `parentCategory`) values
  (   1        , 'microchip', 'riedlentės'              , 'Riedlentės'      , null),
    (   2      , ''         , 'riedlentės/longboards'   , 'Longboards'      , 1),
    (   3      , ''         , 'riedlentės/skateboards'  , 'Skateboards'     , 1),
  (   4        , 'r circle' , 'riedučiai'               , 'Riedučiai'       , null),
  (   5        , 'shield'   , 'apsaugos'                , 'Apsaugos'        , null),
  (   6        , 'settings' , 'atsarginės-dalys'        , 'Atsarginės dalys', null),
    (   7      , ''         , 'atsarginės-dalys/ratai'  , 'Ratai'           , 6),
    (   8      , ''         , 'atsarginės-dalys/guoliai', 'Guoliai'         , 6);


insert into category_property (id, categoryId, name) values
  (   1, 1, 'Ilgis'),
  (   2, 1, 'Plotis'),
  (   3, 1, 'Ratai'),
  (   4, 1, 'Guoliai'),
  (   5, 2, 'Maksimalus Svoris'),
  (   6, 3, 'Spalva'),
  (   7, 3, 'Ašys'),
  (   8, 4, 'Guoliai'),
  (   9, 4, 'Ašys'),
  (  10, 4, 'Ratai'),
  (  11, 4, 'Paskirtis'),
  (  12, 5, 'Gamintojas'),
  (  13, 5, 'Dydis'),
  (  14, 7, 'Dydis'),
  (  15, 7, 'Storis'),
  (  16, 7, 'Kietumas'),
  (  17, 7, 'Skirta');
