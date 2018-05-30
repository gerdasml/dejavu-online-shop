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


insert into `product` (`id`, `category`, `creationDate`, `description`, `identifier`, `mainImageUrl`, `name`, `price`, `skuCode`) values
  (1, 4, '2018-05-29 00:00:00',
   'Lengvas ir inovatyvus Powerslide riedučių modelis Kaze SC 110. Šiuose riedučiuose sumontuota naujoji Trinity sistema, dėl kurios svorio centras atsiduria žemiau, o pats riedutis tampa stabilesnis dėl trijuose taškuose tvirtinamo rėmo. Batas yra itin tvirtas, bet tuo pačiu metu ir lengvas, dėl to šis modelis tinka tiek važinėjimui mieste, tiek ilgesnėm distancijom.',
   'riedučiai', '/image/28', 'Powerslide Kaze SC 110',  299,    'SK198752'),
  (2, 4, '2018-05-29 00:00:00',
   'Seba FR2 80 sukurti žengti į SEBA freestyle riedučių pasaulį. Gaminant FRX buvo naudojama populiariojo FR1 bazė- bato kevalas ir rėmas, tad daugeliu charakteristikų šis modelis pasižymi FR1 savybėmis.',
   'riedučiai', '/image/31', 'SEBA FR2 80',             158.4,  'SK987652');

insert into `additional_image_url` (`productId`, `imageUrl`) values
  (1, '/image/29'),
  (1, '/image/30');

insert into `product_property` (`id`, `productId`, `category_property_Id`, `value`) values
  (1, 1, 8,  'Twincam Titalium freeride'),
  (2, 1, 9,  '8mm'),
  (3, 1, 10, '110mm 88A'),
  (4, 1, 11, 'Fitness'),
  (5, 2, 10, '80mm 85A'),
  (6, 2, 11, 'Freeride');



