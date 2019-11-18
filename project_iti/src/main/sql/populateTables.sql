DELETE FROM event;
DELETE FROM utilisateur;
DELETE FROM club;

INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (1,'Saturne','https://scontent-cdg2-1.xx.fbcdn.net/v/t31.0-8/p960x960/21640916_1657174690980107_4056707557393442670_o.jpg?_nc_cat=107&_nc_oc=AQmtFvKo2bu8Io52YWo6M506DGoIHquWAc0QOJJFRcvA4xzPlsV8xEssGCyyKuhlXdU&_nc_ht=scontent-cdg2-1.xx&oh=6de1c7ed2fb6440eabdbcc11b4a0045e&oe=5E47FE05');
INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (2,'Raid','http://www.jogging-plus.com/wp-content/uploads/2018/08/raid-hei.jpg');
INSERT INTO `club`(`club_id`,`name`,`lien`) VALUES (3,'Heir Force','https://scontent-cdg2-1.xx.fbcdn.net/v/t31.0-8/21686919_122546058488971_8361198402224485343_o.jpg?_nc_cat=104&_nc_oc=AQlijV4NX_-GdyvppSP7SjcShi4EDHqRTwCPvmMISFuqGXx6ALsn-dcvZbodbulvUFU&_nc_ht=scontent-cdg2-1.xx&oh=84393db2015cddfab0feb9e7fbd1c1b6&oe=5E44D55B');

INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (1,'iktro','sjkhgoiheiuz','iktro@gmail.com',true,3);
INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (2,'samos','dfvdfh57','samos@gmail.com',false,null);
INSERT INTO `utilisateur`(`utilisateur_id`,`pseudo`,`motdepasse`,`mail`,`president`,`club_id`) VALUES (3,'guerissologue','dvdifuvbb','guerissologue@gmail.com',false,null);

INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (1,'Afterwork',1,'2019-10-12','Afterwork à la Garderie','Le meilleur bar de tous les temps accueille tout le temps la meilleure asso.');
INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (2,'Week-end du Raid',2,'2020-04-12','Le week-end sportif de HEI','Si vous cherchez un moyen de cracher vos poumons pendant un week-end entier mais que la tuberculose vous fait peur, cet événement semble tout indiqué pour remédier à vos soucis mentaux.');
INSERT INTO `event`(`event_id`,`title`,`club_id`,`event_date`,`resume`,`details`) VALUES (3,'Lancer d avions en papier',3,'2019-12-25','Concours de lancer pour petits et grands','Je sais pas ce qui vous a pris de cliquer là-dessus, cet event est à chier, et je pèse mes mots.');
