insert into `address` (`id`, `street`, `city`, `country`, `zipCode`) values
  (5, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011'),
  (6, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011'),
  (7, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011'),
  (8, 'Naugarduko g., 24', 'Vilnius', 'Lithuania', 'LT-03011');

insert into `shippingInformation` (`id`, `recipientFirstName`, `recipientLastName`, `addressId`) values
  (1, 'Vartotojas', 'Pirmauskas', 5),
  (2, 'Vartotojas', 'Pirmauskas', 6),
  (3, 'Vartotojas', 'Antrauskas', 7),
  (4, 'Vartotojas', 'Trečiauskas', 8);

insert into `purchase_order` (`id`,`status`,`reviewShown`,`creationDate`,`userId`,`shippingInformationId`,`reviewId`, `lastModified`) values
  (1, 'PROCESSING'  , false, '2018-05-29 17:45:41', 1, 1, null, '2018-05-29 17:45:41'),
  (2, 'DELIVERED'   , false, '2018-05-29 17:47:10', 1, 2, null, '2018-05-29 17:47:10'),
  (3, 'SENT'        , false, '2018-05-29 17:48:58', 2, 3, null, '2018-05-29 17:48:59'),
  (4, 'CREATED'     , false, '2018-05-29 17:50:28', 3, 4, null, '2018-05-29 17:50:28');


insert into `orderItem` (`id`, `amount`, `orderId`, `name`, `description`, `price`, `mainImageUrl`, `productId`) values
  (3,	1,	1,	'Mindless Voodoo Cayuga II',
  'Mindless Cayuga Longboard- kokybės, greičio, komforto derinys. Tai lenta, su kuria pasijusite tarsi skriedami banglente jūros bangomis. Neužmirškite apsaugų- ši lenta įsibėgėja labai greitai. Išpjovos važiuoklei ir ratams gerokai pažemino šio asfalto bolido svorio centrą, tad ja galima užtikrintai ir tiksliai manevruoti posūkiuose (carving) bei stabiliai laikytis lekiant dideliu greičiu nuokalne (downhill).',
  195.00,	'/image/5',	null),
  (4,	1,	2,	'Powerslide standard Man M',	'Powerslide kompanijos trijų dalių apsaugų komplektas riedutininkams. Anatominės formos dvigubo tankio EVA kempinė su plastikinėmis plokštelėmis. Fiksacija - elastingi Velcro dirželiai ir ergonomiška medvilnės "rankovė". Saugumo klasė A+++',
  29.00,	'/image/27',	null),
  (5,	4,	2,	'Wicked SUS Rustproof',	'Wicked kompanijos SUS Rustproof guoliai pagaminti iš aukščiausios kokybės medžiagų. Freespin technologija mažina trintį guolyje, taip pagerindama riedėjimo kokybę bei guolio tarnavimo laiką. Šie guoliukai yra puikus pasirinkimas tiems kas mėgsta riedėti visus metus, guoliukai pritaikyti visoms oro sąlygoms. Uždengta guolio viena pusė sukurta patogiam išvalymui ir sutepimui. Sukurti riedučiams, tačiau tinka ir riedlentėms, paspirtukams. Komplekte - 16vnt.',
  73.90,	'/image/33',	null),
  (6,	2,	3,	'SEBA FR1 80 Grey',	'SEBA FR1 80 slalomo freestyle gatvės riedučiai- vienas populiariausių modelių pasaulyje. Keičiamos šoninės apsauginės-abrazyvinės plokštelės, apsaugančios batą nuo subraižymų mokinantis slydimus. Patentuotas auliuko 4 pozicijų nustatymas.',
  176.22,	'/image/20',	null),
  (7,	2,	3,	'Ennui City Brace',	'Riešų apsaugos Ennui Allround Wrist Brace. Riešas įtvirtinamas dviem anatomiškai išformuotais aliumininiais įtvarais apsisaugojimui nuo traumos krintant. Neopreninė įmautė leidžia rankai maksimaliai kvėpuoti ir maloniai priglunda prie plaštakos. Tvirtinama dviem Velcro dirželiais su papildoma fiksacijos apsauga.',
  39.00,	'/image/28',	null),
  (8,	1,	4,	'Mindless Voodoo Lakota DT',	'Mindless Maverick DT (drop through) Longboard- kokybės, greičio, komforto derinys. Ištisinė Kanados klevo (drop through) formos lenta paruošta važiavimui. Tai lenta, su kuria pasijusite tarsi čiuoždami banglente jūros bangomis.  Su ja galima užtikrintai ir tiksliai manevruoti posūkiuose (carving) bei stabiliai laikytis lekiant dideliu greičiu nuokalne (downhill). Neužmirškite apsaugų- ši lenta įsibėgėja labai greitai!',
  171.27,	'/image/8',	null);

