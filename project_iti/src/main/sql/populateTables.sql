DELETE FROM club;
DELETE FROM event;

INSERT INTO `club`(`club_id`,`name`) VALUES (1,'Saturne');
INSERT INTO `club`(`club_id`,`name`) VALUES (2,'Raid');
INSERT INTO `club`(`club_id`,`name`) VALUES (3,'Heir Force');

INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (1,'Afterwork',1,'2019-10-12','Afterwork à la Garderie','Le meilleur bar de tous les temps accueille tout le temps la meilleure asso.');
INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (2,'Week-end du Raid',2,'2020-04-12','Le week-end sportif de HEI','Si vous cherchez un moyen de cracher vos poumons pendant un week-end entier mais que la tuberculose vous fait peur, cet événement semble tout indiqué pour remédier à vos soucis mentaux.');
INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (3,'Lancer d avions en papier',3,'2019-12-25','Concours de lancer pour petits et grands','Je sais pas ce qui vous a pris de cliquer là-dessus, cet event est à chier, et je pèse mes mots.');
