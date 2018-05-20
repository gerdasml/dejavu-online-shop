insert into `category` (`id`, `iconName`, `identifier`, `name`, `parentCategory`) values
  (   1        , 'computer'         , 'electronics'                                                  , 'Electronics'           , null),
      (   2    , ''                 , 'electronics/computers'                                        , 'Computers'             , 1),
          (   3, ''                 , 'electronics/computers/laptops'                                , 'Laptops'               , 2),
          (   4, ''                 , 'electronics/computers/desktop-pcs'                            , 'Desktop PCs'           , 2),
          (   5, ''                 , 'electronics/computers/tablets'                                , 'Tablets'               , 2),
      (  11    , ''                 , 'electronics/phones'                                           , 'Phones'                , 1),
          (  12, ''                 , 'electronics/phones/smart-phones'                              , 'Smart-phones'          , 11),
          (  13, ''                 , 'electronics/phones/cellphones'                                , 'Cell phones'           , 11),
      (  21    , ''                 , 'electronics/cameras'                                          , 'Cameras'               , 1),
          (  22, ''                 , 'electronics/cameras/photo-cameras'                            , 'Photo cameras'         , 21),
          (  23, ''                 , 'electronics/cameras/video-cameras'                            , 'Video cameras'         , 21),
          (  24, ''                 , 'electronics/cameras/camera-accessories'                       , 'Camera accessories'    , 21),
      (  31    , ''                 , 'electronics/house-appliances'                                 , 'House appliances'      , 1),
          (  32, ''                 , 'electronics/house-appliances/fridges'                         , 'Fridges'               , 31),
          (  33, ''                 , 'electronics/house-appliances/ovens'                           , 'Ovens'                 , 31),
          (  34, ''                 , 'electronics/house-appliances/microwaves'                      , 'Shorts'                , 31),
          (  35, ''                 , 'electronics/house-appliances/dishwashers'                     , 'Dishwashers'           , 31),
          (  37, ''                 , 'electronics/house-appliances/washing-machines'                , 'Washing machines'      , 31),
          (  38, ''                 , 'electronics/house-appliances/driers'                          , 'Driers          '      , 31),
          (  39, ''                 , 'electronics/house-appliances/washing-machines'                , 'Washing machines'      , 31),
          (  40, ''                 , 'electronics/house-appliances/vacuum-cleaners'                 , 'Vacuum cleaners'       , 31),
  (  50        , 'spy'              , 'clothes'                                                      , 'Clothes'               , null),
      (  51    , ''                 , 'clothes/mans-clothing'                                        , 'Mans clothing'         , 50),
          (  52, ''                 , 'clothes/mans-clothing/mans-shirts'                            , 'Shirts'                , 51),
          (  53, ''                 , 'clothes/mans-clothing/mans-jumpers'                           , 'Jumpers'               , 51),
          (  54, ''                 , 'clothes/mans-clothing/mans-suits'                             , 'Suits'                 , 51),
          (  55, ''                 , 'clothes/mans-clothing/mans-boots'                             , 'Boots'                 , 51),
          (  56, ''                 , 'clothes/mans-clothing/mans-trousers'                          , 'Trousers'              , 51),
          (  57, ''                 , 'clothes/mans-clothing/mans-shorts'                            , 'Shorts'                , 51),
          (  58, ''                 , 'clothes/mans-clothing/mans-ties '                             , 'Ties'                  , 51),
      (  61    , ''                 , 'clothes/womans-clothing'                                      , 'Woman''s clothing'     , 50),
          (  62, ''                 , 'clothes/womans-clothing/womans-shirts'                        , 'Shirts'                , 61),
          (  63, ''                 , 'clothes/womans-clothing/womans-sweaters'                      , 'Sweaters'              , 61),
          (  65, ''                 , 'clothes/womans-clothing/womans-boots'                         , 'Boots'                 , 61),
          (  66, ''                 , 'clothes/womans-clothing/womans-trousers'                      , 'Trousers'              , 61),
          (  67, ''                 , 'clothes/womans-clothing/womans-skirts'                        , 'Skirts'                , 61),
          (  68, ''                 , 'clothes/womans-clothing/womans-dresses'                       , 'Dresses'               , 61),
      (  71    , ''                 , 'clothes/kids-clothing'                                        , 'Kids clothing'         , 50),
          (  72, ''                 , 'clothes/kids-clothing/boys-clothing'                          , 'Boys clothing'         , 71),
          (  73, ''                 , 'clothes/kids-clothing/girls-clothing'                         , 'Girls clothing'        , 71),
  ( 100        , 'bed'              , 'furniture'                                                    ,'Furniture'              , null),
      ( 101    , ''                 , 'furniture/kitchens-furniture'                                 , 'Kitchens furniture'    , 100),
          ( 102, ''                 , 'furniture/kitchens-furniture/kitchens-tables'                 , 'Tables'                , 101),
          ( 103, ''                 , 'furniture/kitchens-furniture/kitchens-chairs'                 , 'Chairs'                , 101),
          ( 104, ''                 , 'furniture/kitchens-furniture/kitchens-cabinets'               , 'Cabinets'              , 101),
      ( 111    , ''                 , 'furniture/bedrooms-furniture'                                 , 'Bedrooms furniture'    , 100),
          ( 112, ''                 , 'furniture/bedrooms-furniture/beds'                            , 'Beds'                  , 111),
          ( 113, ''                 , 'furniture/bedrooms-furniture/wardrobes'                       , 'Wardrobes'             , 111),
          ( 115, ''                 , 'furniture/bedrooms-furniture/bedside-tables'                  , 'Bedside tables'        , 111),
          ( 116, ''                 , 'furniture/bedrooms-furniture/bedrooms-lamps'                  , 'Lamps'                 , 111),
          ( 117, ''                 , 'furniture/bedrooms-furniture/mattresses'                      , 'Mattresses'            , 111),
          ( 118, ''                 , 'furniture/bedrooms-furniture/womans-dresses'                  , 'Dresses'               , 111),
      ( 121    , ''                 , 'furniture/living-room-furniture'                              , 'Living room furniture' , 100),
          ( 122, ''                 , 'furniture/living-room-furniture/sofas'                        , 'Sofas'                 , 121),
          ( 123, ''                 , 'furniture/living-room-furniture/coffee-tables'                , 'Coffee tables'         , 121),
          ( 124, ''                 , 'furniture/living-room-furniture/drawers'                      , 'Drawers'               , 121),
  ( 150        , 'leaf'             , 'garden'                                                       ,'Garden'                 , null),
  ( 200        , 'soccer'           , 'sports'                                                       ,'Sports equipment'       , null),
  ( 250        , 'car'              , 'vehicle-parts'                                                ,'Vehicle parts'          , null),
  ( 300        , 'book'             , 'books'                                                        ,'Books'                  , null),
  ( 350        , 'puzzle'           , 'toys'                                                         ,'Toys'                   , null),
  ( 400        , 'configure'        , 'tools'                                                        ,'Tools'                  , null);




insert into `product` (`id`, `category`, `creationDate`, `description`, `identifier`, `mainImageUrl`, `name`, `price`) values
      -- http://www.lg.com/us/laptops/lg-15Z980-RAAS9U1-ultra-slim-laptop
      (1, 3, '2000-01-01 00:00:00', 'LG gram 15.6” Ultra-Lightweight Touchscreen Laptop w/ Intel® Core™ i7 processor and Thunderbolt™ 3', 'electronics/computers/laptops/lg-gram-15-6', '/image/1', 'LG gram 15,6', 1999.99),
      -- https://whatis.techtarget.com/definition/MacBook-Air
      (2, 3, '2000-01-01 00:00:00', 'MacBook Air is a thin, lightweight laptop from Apple.  Because it is a full-sized notebook but only weighs three pounds, the laptop falls into a category that vendors are currently calling ''ultraportable.''
  The MacBook Air has an anodized aluminum casing that is 0.16 inches at its thinnest point and 0.76 inches at its thickest. Often described as the same size as a pad of paper, the laptop has just one USB port, an audio headphone jack and built-in speakers and microphone.  MacBook Air comes with built-in Bluetooth wireless technology for connecting and synching the laptop to a PDA, cell phone or wireless headset. It has an embedded iSight camera capable of taking still photos as well as recording video.
  The MacBook Air does not come with a DVD/CD drive. To install software from a disc, the consumer has two options: he can use the Remote Disk feature to wirelessly connect to another computer’s DVD/CD drive or purchase an external MacBook Air SuperDrive, which is sold separately. (SuperDrive is a multi-format CD/DVD read/write drive that plugs into the MacBook Air''s single USB port.)  The laptop uses Wi-Fi for network/Internet connections with 802.11n wireless networking capabilities. It can only use a wired Ethernet connection if the customer purchases an optional USB Ethernet adapter.
  According to Apple, the MacBook Air battery will last up to five hours, depending on what tasks the computer is performing. The battery itself has been somewhat controversial because it is enclosed inside the laptop’s aluminum casing and is not user-replaceable. If the battery reaches the point where it no longer takes a charge, the consumer has to return the computer to Apple for battery replacement.
  Apple is calling the MacBook Air an environmentally-friendly product not only because it consumes less power than other MacBooks, but because it has an aluminum body that can be recycled and an LCD display that is mercury- and arsenic-free. Apple also promotes the fact that the internal cables are polyvinyl chloride-free and consumer packaging is made from recycled materials.', 'electronics/computers/laptops/macbook-air-2017',
    '/image/5', 'MacBook Air 2017', 1599.99);

insert into additional_image_url (productId, imageUrl) values (1, '/image/2'),(1, '/image/3'),(1, '/image/4'),(2, '/image/6');

insert into category_property (id, categoryId, name) values
      -- https://www.amazon.com/LG-gram-Thin-Light-Laptop/dp/B077YSVKQK
      (   1, 3, 'Screen Size'),
      (   2, 3, 'Max Screen Resolution'),
      (   3, 3, 'Processor'),
      (   4, 3, 'RAM'),
      (   5, 3, 'Hard Drive'),
      (   6, 3, 'Card Description'),
      (   7, 3, 'Brand Name'),
      (   8, 3, 'Item model number'),
      (   9, 3, 'Operating System'),
      (  10, 3, 'Item Weight'),
      (  11, 3, 'Product Dimensions'),
      (  12, 3, 'Color'),
      (  13, 3, 'Processor Brand'),
      (  14, 3, 'Processor Count'),
      (  15, 3, 'Computer Memory Type'),
      (  16, 3, 'Flash Memory Size'),
      (  17, 3, 'Hard Drive Interface'),
      (  18, 3, 'Optical Drive Type'),
      (  19, 3, 'Batteries');

insert into product_property (id, productId, category_property_Id, value) values
      -- https://www.amazon.com/LG-gram-Thin-Light-Laptop/dp/B077YSVKQK
      ( 1, 1,  1, '15.6 inches'),
      ( 2, 1,  2, '1920 x 1080 pixels'),
      ( 3, 1,  3, '1.8 GHz Intel Core i7'),
      ( 4, 1,  4, '6 GB'),
      ( 5, 1,  5, '1000 GB 1TB (512GB x 2) SSD'),
      ( 6, 1,  6, 'Dedicated'),
      ( 7, 1,  7, 'LG'),
      ( 8, 1,  8, '15Z980-R.AAS9U1'),
      ( 9, 1,  9, 'Windows 10'),
      (10, 1, 10, '2.4 pounds (0.907Kg)'),
      (11, 1, 11, '14.1 x 9 x 0.7 inches'),
      (12, 1, 12, 'Dark Silver'),
      (13, 1, 13, 'Intel'),
      (14, 1, 14, '4'),
      (15, 1, 15, 'DDR4 SDRAM'),
      (16, 1, 16, '1000.0'),
      (17, 1, 17, 'Solid State'),
      (18, 1, 18, 'None'),
      (19, 1, 19, 'Lithium ion batteries required. (included) '),
      -- https://www.amazon.com/dp/B0774Z5VJN/ref=twister_B07C7JC4NC?_encoding=UTF8&psc=1
      (20, 2,  1, '13.3 inches'),
      (21, 2,  3, '1.8 GHz Core i5-2300'),
      (22, 2,  4, '8 GB DDR3 SDRAM'),
      (23, 2,  5, '128 GB flash_memory_solid_state'),
      (24, 2,  6, 'integrated_graphics'),
      (25, 2,  7, 'Apple'),
      (26, 2,  8, 'MQD42LL/A'),
      (27, 2,  9, 'Mac OS X'),
      (28, 2, 10, '2.96 pounds (1.343kg)'),
      (29, 2, 11, '13 x 1 x 9 inches'),
      (30, 2, 13, 'intel'),
      (31, 2, 14, '1'),
      (32, 2, 15, 'SODIMM'),
      (33, 2, 16, '256 GB');



insert into `image` (`id`, `filename`, `extension`, `uploadedOn`) values
    (1,'image-1','jpg',CURRENT_TIMESTAMP()),
    (2,'image-2','jpg',CURRENT_TIMESTAMP()),
    (3,'image-3','jpg',CURRENT_TIMESTAMP()),
    (4,'image-4','jpg',CURRENT_TIMESTAMP()),
    (5,'image-5','jpg',CURRENT_TIMESTAMP()),
    (6,'image-6','jpg',CURRENT_TIMESTAMP()),
    (7,'image-7','jpg',CURRENT_TIMESTAMP());
