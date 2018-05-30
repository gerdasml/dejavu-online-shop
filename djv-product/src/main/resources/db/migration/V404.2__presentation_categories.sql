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

insert into `image` (`id`, `filename`, `extension`, `uploadedOn`) values
  (1,'image-1','jpg',CURRENT_TIMESTAMP()),
  (2,'image-2','jpg',CURRENT_TIMESTAMP()),
  (3,'image-3','jpg',CURRENT_TIMESTAMP()),
  (4,'image-4','jpg',CURRENT_TIMESTAMP()),
  (5,'image-5','jpg',CURRENT_TIMESTAMP()),
  (6,'image-6','jpg',CURRENT_TIMESTAMP()),
  (7,'image-7','jpg',CURRENT_TIMESTAMP()),
  (8,'image-8','jpg',CURRENT_TIMESTAMP()),
  (9,'image-9','jpg',CURRENT_TIMESTAMP()),
  (10,'image-10','jpg',CURRENT_TIMESTAMP()),
  (11,'image-11','jpg',CURRENT_TIMESTAMP()),
  (12,'image-12','jpg',CURRENT_TIMESTAMP()),
  (13,'image-13','jpg',CURRENT_TIMESTAMP()),
  (14,'image-14','jpg',CURRENT_TIMESTAMP()),
  (15,'image-15','jpg',CURRENT_TIMESTAMP()),
  (16,'image-16','jpg',CURRENT_TIMESTAMP()),
  (17,'image-17','jpg',CURRENT_TIMESTAMP()),
  (18,'image-18','jpg',CURRENT_TIMESTAMP()),
  (19,'image-19','jpg',CURRENT_TIMESTAMP()),
  (20,'image-20','jpg',CURRENT_TIMESTAMP()),
  (21,'image-21','jpg',CURRENT_TIMESTAMP()),
  (22,'image-22','jpg',CURRENT_TIMESTAMP()),
  (23,'image-23','jpg',CURRENT_TIMESTAMP()),
  (24,'image-24','jpg',CURRENT_TIMESTAMP()),
  (25,'image-25','jpg',CURRENT_TIMESTAMP()),
  (26,'image-26','jpg',CURRENT_TIMESTAMP()),
  (27,'image-27','jpg',CURRENT_TIMESTAMP()),
  (28,'image-28','jpg',CURRENT_TIMESTAMP()),
  (29,'image-29','jpg',CURRENT_TIMESTAMP()),
  (30,'image-30','jpg',CURRENT_TIMESTAMP()),
  (31,'image-31','jpg',CURRENT_TIMESTAMP()),
  (32,'image-32','jpg',CURRENT_TIMESTAMP()),
  (33,'image-33','jpg',CURRENT_TIMESTAMP()),
  (34,'image-34','jpg',CURRENT_TIMESTAMP()),
  (35,'image-35','jpg',CURRENT_TIMESTAMP()),
  (36,'image-36','jpg',CURRENT_TIMESTAMP()),
  (37,'image-37','jpg',CURRENT_TIMESTAMP()),
  (38,'image-38','jpg',CURRENT_TIMESTAMP()),
  (39,'image-39','jpg',CURRENT_TIMESTAMP()),
  (40,'image-40','jpg',CURRENT_TIMESTAMP()),
  (41,'image-41','jpg',CURRENT_TIMESTAMP()),
  (42,'image-42','jpg',CURRENT_TIMESTAMP()),
  (43,'image-43','jpg',CURRENT_TIMESTAMP());



insert into `product` (`id`, `category`, `creationDate`, `description`, `identifier`, `mainImageUrl`, `name`, `price`, `skuCode`) values
  (1, 4, '2018-05-29 00:00:00',
   'Lengvas ir inovatyvus Powerslide riedučių modelis Kaze SC 110. Šiuose riedučiuose sumontuota naujoji Trinity sistema, dėl kurios svorio centras atsiduria žemiau, o pats riedutis tampa stabilesnis dėl trijuose taškuose tvirtinamo rėmo. Batas yra itin tvirtas, bet tuo pačiu metu ir lengvas, dėl to šis modelis tinka tiek važinėjimui mieste, tiek ilgesnėm distancijom.',
   'riedučiai/powerslide-kaze-sc-110-1', '/image/28', 'Powerslide Kaze SC 110',  299,    'SK198752'),
  (2, 4, '2018-05-29 00:00:00',
   'Seba FR2 80 sukurti žengti į SEBA freestyle riedučių pasaulį. Gaminant FRX buvo naudojama populiariojo FR1 bazė- bato kevalas ir rėmas, tad daugeliu charakteristikų šis modelis pasižymi FR1 savybėmis.',
   'riedučiai/seba-fr2-80-2', '/image/31', 'SEBA FR2 80',             158.4,  'SK987652');

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

insert into productIdGen (genName, genValue) values
  ('productGen', 9);



