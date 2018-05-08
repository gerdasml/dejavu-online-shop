insert into `category` (`id`, `iconName`, `identifier`, `name`, `parentCategory`) values
  (   1        , 'computer'  , 'Electronics'                  , 'Electronics'           , null),
      (   2    , ''  , 'Computers  '                  , 'Computers'             , 1),
          (   3, 'r'  , 'Laptops  '                    , 'Laptops'               , 2),
          (   4, 'computer'  , 'Desktop PCs '                 , 'Desktop PCs'           , 2),
          (   5, 'computer'  , 'Tablets'                      , 'Tablets'               , 2),
      (  11    , 'computer'  , 'Phones'                       , 'Phones'                , 1),
          (  12, 'computer'  , 'Smart-phones'                 , 'Smart-phones'          , 11),
          (  13, 'computer'  , 'Cell phones'                  , 'Cell phones'           , 11),
      (  21    , 'computer'  , 'Cameras'                      , 'Cameras'               , 1),
          (  22, 'computer'  , 'Photo cameras'                , 'Photo cameras'         , 21),
          (  23, 'computer'  , 'Video cameras'                , 'Video cameras'         , 21),
          (  24, 'computer'  , 'Camera accessories'           , 'Camera accessories'    , 21),
      (  31    , 'computer'  , 'House appliances'             , 'House appliances'      , 1),
          (  32, 'computer'  , 'Fridges'                      , 'Fridges'               , 31),
          (  33, 'computer'  , 'Ovens'                        , 'Ovens'                 , 31),
          (  34, 'computer'  , 'Microwaves'                   , 'Shorts'                , 31),
          (  35, 'computer'  , 'Dishwashers'                  , 'Dishwashers'           , 31),
          (  37, 'computer'  , 'Washing machines'             , 'Washing machines'      , 31),
          (  38, 'computer'  , 'Driers'                       , 'Driers          '      , 31),
          (  39, 'computer'  , 'Washing machines'             , 'Washing machines'      , 31),
          (  40, 'computer'  , 'Vacuum cleaners'              , 'Vacuum cleaners'       , 31),
  (  50        , 'computer'  , 'Clothes'                      , 'Clothes'               , null),
      (  51    , 'computer'  , 'Mans clothing'                , 'Mans clothing'         , 50),
          (  52, 'computer'  , 'Mans Shirts'                  , 'Shirts'                , 51),
          (  53, 'computer'  , 'Mans Jumpers'                 , 'Jumpers'               , 51),
          (  54, 'computer'  , 'Mans Suits'                   , 'Suits'                 , 51),
          (  55, 'computer'  , 'Mans Boots'                   , 'Boots'                 , 51),
          (  56, 'computer'  , 'Mans Trousers'                , 'Trousers'              , 51),
          (  57, 'computer'  , 'Mans Shorts'                  , 'Shorts'                , 51),
          (  58, 'computer'  , 'Mans Ties '                   , 'Ties'                  , 51),
      (  61    , 'computer'  , 'Woman''s clothing'            , 'Woman''s clothing'     , 50),
          (  62, 'computer'  , 'Woman''s Shirts'              , 'Shirts'                , 61),
          (  63, 'computer'  , 'Woman''s Sweaters'            , 'Sweaters'              , 61),
          (  65, 'computer'  , 'Woman''s Boots'               , 'Boots'                 , 61),
          (  66, 'computer'  , 'Woman''s Trousers'            , 'Trousers'              , 61),
          (  67, 'computer'  , 'Woman''s Skirts'              , 'Skirts'                , 61),
          (  68, 'computer'  , 'Woman''s Dresses'             , 'Dresses'               , 61),
      (  71    , 'computer'  , 'Kids clothing'                , 'Kids clothing'         , 50),
          (  72, 'computer'  , 'Boys clothing'                , 'Boys clothing'         , 71),
          (  73, 'computer'  , 'Girls clothing'               , 'Girls clothing'        , 71),
  ( 100        , 'computer'  , 'Furniture'                    ,'Furniture'              , null),
      ( 101    , 'computer'  , 'Kitchens furniture'           , 'Kitchens furniture'    , 100),
          ( 102, 'computer'  , 'Kitchens Tables'              , 'Tables'                , 101),
          ( 103, 'computer'  , 'Kitchens Chairs'              , 'Chairs'                , 101),
          ( 104, 'computer'  , 'Kitchens Cabinets'            , 'Cabinets'              , 101),
      ( 111    , 'computer'  , 'Bedrooms furniture'           , 'Bedrooms furniture'    , 100),
          ( 112, 'computer'  , 'Beds'                         , 'Beds'                  , 111),
          ( 113, 'computer'  , 'Wardrobes'                    , 'Wardrobes'             , 111),
          ( 115, 'computer'  , 'Bedside tables'               , 'Bedside tables'        , 111),
          ( 116, 'computer'  , 'Bedrooms Lamps'               , 'Lamps'                 , 111),
          ( 117, 'computer'  , 'Mattresses'                   , 'Mattresses'            , 111),
          ( 118, 'computer'  , 'Woman''s Dresses'             , 'Dresses'               , 111),
      ( 121    , 'computer'  , 'Living room furniture'        , 'Living room furniture' , 100),
          ( 122, 'computer'  , 'Sofas'                        , 'Sofas'                 , 121),
          ( 123, 'computer'  , 'Coffee tables'                , 'Coffee tables'         , 121),
          ( 124, 'computer'  , 'Drawers'                      , 'Drawers'               , 121),

      ( 150    , 'computer'  , 'Garden'                       ,'Garden'                , null),
      ( 200    , 'computer'  , 'Sports'                       ,'Sports equipment'      , null),
      ( 250    , 'computer'  , 'Vehicle parts'                ,'Vehicle parts'         , null),
      ( 300    , 'computer'  , 'Books  '                      ,'Books'                 , null),
      ( 350    , 'computer'  , 'Toys'                         ,'Toys'                  , null),
      ( 400    , 'computer'  , 'Tools  '                      ,'Tools'                 , null);




insert into `product` (`id`, `category`, `creationDate`, `description`, `identifier`, `mainImageUrl`, `name`, `price`) values
      -- http://www.lg.com/us/laptops/lg-15Z980-RAAS9U1-ultra-slim-laptop
      (1, 3, '2000-01-01 00:00:00', 'LG gram 15.6” Ultra-Lightweight Touchscreen Laptop w/ Intel® Core™ i7 processor and Thunderbolt™ 3', 'LG-15Z980-R.AAS9U1', '/image/1', 'LG gram 15,6', 1999.99),
      -- https://whatis.techtarget.com/definition/MacBook-Air
      (2, 3, '2000-01-01 00:00:00', 'MacBook Air is a thin, lightweight laptop from Apple.  Because it is a full-sized notebook but only weighs three pounds, the laptop falls into a category that vendors are currently calling ''ultraportable.''
  The MacBook Air has an anodized aluminum casing that is 0.16 inches at its thinnest point and 0.76 inches at its thickest. Often described as the same size as a pad of paper, the laptop has just one USB port, an audio headphone jack and built-in speakers and microphone.  MacBook Air comes with built-in Bluetooth wireless technology for connecting and synching the laptop to a PDA, cell phone or wireless headset. It has an embedded iSight camera capable of taking still photos as well as recording video.
  The MacBook Air does not come with a DVD/CD drive. To install software from a disc, the consumer has two options: he can use the Remote Disk feature to wirelessly connect to another computer’s DVD/CD drive or purchase an external MacBook Air SuperDrive, which is sold separately. (SuperDrive is a multi-format CD/DVD read/write drive that plugs into the MacBook Air''s single USB port.)  The laptop uses Wi-Fi for network/Internet connections with 802.11n wireless networking capabilities. It can only use a wired Ethernet connection if the customer purchases an optional USB Ethernet adapter.
  According to Apple, the MacBook Air battery will last up to five hours, depending on what tasks the computer is performing. The battery itself has been somewhat controversial because it is enclosed inside the laptop’s aluminum casing and is not user-replaceable. If the battery reaches the point where it no longer takes a charge, the consumer has to return the computer to Apple for battery replacement.
  Apple is calling the MacBook Air an environmentally-friendly product not only because it consumes less power than other MacBooks, but because it has an aluminum body that can be recycled and an LCD display that is mercury- and arsenic-free. Apple also promotes the fact that the internal cables are polyvinyl chloride-free and consumer packaging is made from recycled materials.', 'LG-15Z980-R.AAS9U1',
    '/image/5', 'MacBook Air 2017', 1599.99);

insert into additional_image_url (productId, imageUrl) values (1, '/image/2'),(1, '/image/3'),(1, '/image/4'),(2, '/image/6');

insert into product_property (productId, name, value) values
--https://www.amazon.com/LG-gram-Thin-Light-Laptop/dp/B077YSVKQK
      (1, 'Screen Size', '15.6 inches'),
      (1, 'Max Screen Resolution','1920 x 1080 pixels'),
      (1, 'Processor','1.8 GHz Intel Core i7'),
      (1, 'RAM','16 GB'),
      (1, 'Hard Drive','1000 GB 1TB (512GB x 2) SSD'),
      (1, 'Card Description','Dedicated'),
      (1, 'Brand Name','LG'),
      (1, 'Item model number','15Z980-R.AAS9U1'),
      (1, 'Operating System','Windows 10'),
      (1, 'Item Weight',' 	2.4 pounds (0.907Kg)'),
      (1, 'Product Dimensions', '14.1 x 9 x 0.7 inches'),
      (1, 'Color','Dark Silver'),
      (1, 'Processor Brand','Intel'),
      (1, 'Processor Count','4'),
      (1, 'Computer Memory Type','DDR4 SDRAM'),
      (1, 'Flash Memory Size','1000.0'),
      (1, 'Hard Drive Interface','Solid State'),
      (1, 'Optical Drive Type','None'),
      (1, 'Batteries','1 Lithium ion batteries required. (included) '),
      --https://www.amazon.com/dp/B0774Z5VJN/ref=twister_B07C7JC4NC?_encoding=UTF8&psc=1
      (2, 'Screen Size','13.3 inches'),
      (2, 'Processor','1.8 GHz Core i5-2300'),
      (2, 'RAM','8 GB DDR3 SDRAM'),
      (2, 'Hard Drive','128 GB flash_memory_solid_state'),
      (2, 'Graphics Coprocessor','integrated_graphics'),
      (2, 'Chipset Brand','intel'),
      (2, 'Wireless Type','802.11 A/C '),
      (2, 'Brand Name','Apple'),
      (2, 'Series','MQD42LL/A'),
      (2, 'Item model number','MQD42LL/A'),
      (2, 'Hardware Platform','Mac'),
      (2, 'Operating System','Mac OS X'),
      (2, 'Item Weight','2.96 pounds'),
      (2, 'Product Dimensions','13 x 1 x 9 inches'),
      (2, 'Item Dimensions L x W x H','13 x 1 x 9 inches'),
      (2, 'Processor Brand','Apple'),
      (2, 'Processor Count','1'),
      (2, 'Computer Memory Type','SODIMM'),
      (2, 'Flash Memory Size','256 GB');

insert into `image` (`id`, `filename`, `extension`, `uploadedOn`) values
    (1,'image-1','jpg',CURRENT_TIMESTAMP()),
    (3,'image-2','jpg',CURRENT_TIMESTAMP()),
    (4,'image-3','jpg',CURRENT_TIMESTAMP()),
    (2,'image-4','jpg',CURRENT_TIMESTAMP()),
    (5,'image-5','jpg',CURRENT_TIMESTAMP()),
    (6,'image-6','jpg',CURRENT_TIMESTAMP()),
    (7,'image-7','jpg',CURRENT_TIMESTAMP());
    