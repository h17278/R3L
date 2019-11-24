DROP TABLE `event`;
DROP TABLE `utilisateur`;
DROP TABLE `club`;

CREATE TABLE `club` (
  `club_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `lien` varchar(1000) NOT NULL,
  PRIMARY KEY (`club_id`)
);

CREATE TABLE `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `club_id` int(11) NOT NULL,
  `event_date` date NOT NULL,
  `bureau` varchar(3) NOT NULL,
  `image_link` varchar(1000) NOT NULL,
  `resume` varchar(100) NOT NULL,
  `details` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `club_id_fk` (`club_id`),
  CONSTRAINT `club_id_fk` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`)
);

CREATE TABLE `utilisateur` (
  `utilisateur_id` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(50) NOT NULL,
  `motdepasse` varchar(500) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `president` tinyint(1) NOT NULL,
  `club_id` int(11) NULL,
  PRIMARY KEY (`utilisateur_id`),
  KEY `club_id_fg` (`club_id`),
  CONSTRAINT `club_id_fg` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`)
);
