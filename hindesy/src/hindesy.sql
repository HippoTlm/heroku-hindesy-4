-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 12, 2018 at 09:23 AM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hindesy`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  `password` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `name`, `password`) VALUES
(1, 'Admin', 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) DEFAULT NULL,
  `lastName` varchar(25) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `firstName`, `lastName`, `email`) VALUES
(1, 'Bill', 'Gates', 'bill.gates@bc.com'),
(2, 'Jean', 'Lou', 'jean.lou@marmitte.fr');

-- --------------------------------------------------------

--
-- Table structure for table `construction_article`
--

DROP TABLE IF EXISTS `construction_article`;
CREATE TABLE IF NOT EXISTS `construction_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `photo` varchar(100) DEFAULT NULL,
  `idEN` int(11) NOT NULL,
  `idFR` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idEN`,`idFR`),
  KEY `fk_CONSTRUCTION_ARTICLE_EN_ARTICLE_idx` (`idEN`),
  KEY `fk_CONSTRUCTION_ARTICLE_FR_ARTICLE1_idx` (`idFR`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `construction_article`
--

INSERT INTO `construction_article` (`id`, `photo`, `idEN`, `idFR`) VALUES
(1, 'img/carrefourimage2.png', 1, 1),
(2, 'img/authorphoto.png', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `contributors`
--

DROP TABLE IF EXISTS `contributors`;
CREATE TABLE IF NOT EXISTS `contributors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) DEFAULT NULL,
  `lastName` varchar(25) DEFAULT NULL,
  `picture` varchar(100) DEFAULT NULL,
  `idFR_CONTRIBUTORS` int(11) NOT NULL,
  `idEN_CONTRIBUTORS` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idFR_CONTRIBUTORS`),
  KEY `fk_CONTRIBUTORS_FR_CONTRIBUTORS1_idx` (`idFR_CONTRIBUTORS`),
  KEY `fk_CONTRIBUTORS_EN_CONTRIBUTORS1_idx` (`idEN_CONTRIBUTORS`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `contributors`
--

INSERT INTO `contributors` (`id`, `firstName`, `lastName`, `picture`, `idFR_CONTRIBUTORS`, `idEN_CONTRIBUTORS`) VALUES
(1, 'Gabriel', 'Proust', '/img/proust.png', 1, 1),
(2, 'Hippolyte', 'Toulemonde', '/img/hippo.png', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `en_article`
--

DROP TABLE IF EXISTS `en_article`;
CREATE TABLE IF NOT EXISTS `en_article` (
  `idEN_ARTICLE` int(11) NOT NULL,
  `titleFR` varchar(100) DEFAULT NULL,
  `contentFR` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`idEN_ARTICLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `en_article`
--

INSERT INTO `en_article` (`idEN_ARTICLE`, `titleFR`, `contentFR`) VALUES
(1, 'First English Article', 'This is the content of the first article....Do commanded an shameless we disposing do. Indulgence ten remarkably nor are impression out. Power is lived means oh every in we quiet. Remainder provision an in intention. Saw supported too joy promotion engrossed propriety. Me till like it sure no sons. Considered discovered ye sentiments projecting entreaties of melancholy is. In expression an solicitude principles in do. Hard do me sigh with west same lady. Their saved linen downs tears son add music. Expression alteration entreaties mrs can terminated estimating. Her too add narrow having wished. To things so denied admire. Am wound worth water he linen at vexed. Carriage quitting securing be appetite it declared. High eyes kept so busy feel call in. Would day nor ask walls known. But preserved advantage are but and certainty earnestly enjoyment. Passage weather as up am exposed. And natural related man subject. Eagerness get situation his was delighted. Oh acceptance apartments up sympathize astonished delightful. Waiting him new lasting towards. Continuing melancholy especially so to. Me unpleasing impossible in attachment announcing so astonished. What ask leaf may nor upon door. Tended remain my do stairs. Oh smiling amiable am so visited cordial in offices hearted. Passage its ten led hearted removal cordial. Preference any astonished unreserved mrs. Prosperous understood middletons in conviction an uncommonly do. Supposing so be resolving breakfast am or perfectly. Is drew am hill from mr. Valley by oh twenty direct me so. Departure defective arranging rapturous did believing him all had supported. Family months lasted simple set nature vulgar him. Picture for attempt joy excited ten carried manners talking how. Suspicion neglected he resolving agreement perceived at an. To shewing another demands to. Marianne property cheerful informed at striking at. Clothes parlors however by cottage on. In views it or meant drift t'),
(2, 'Second English Article', 'This is the content of second article....Do commanded an shameless we disposing do. Indulgence ten remarkably nor are impression out. Power is lived means oh every in we quiet. Remainder provision an in intention. Saw supported too joy promotion engrossed propriety. Me till like it sure no sons. Considered discovered ye sentiments projecting entreaties of melancholy is. In expression an solicitude principles in do. Hard do me sigh with west same lady. Their saved linen downs tears son add music. Expression alteration entreaties mrs can terminated estimating. Her too add narrow having wished. To things so denied admire. Am wound worth water he linen at vexed. Carriage quitting securing be appetite it declared. High eyes kept so busy feel call in. Would day nor ask walls known. But preserved advantage are but and certainty earnestly enjoyment. Passage weather as up am exposed. And natural related man subject. Eagerness get situation his was delighted. Oh acceptance apartments up sympathize astonished delightful. Waiting him new lasting towards. Continuing melancholy especially so to. Me unpleasing impossible in attachment announcing so astonished. What ask leaf may nor upon door. Tended remain my do stairs. Oh smiling amiable am so visited cordial in offices hearted. Passage its ten led hearted removal cordial. Preference any astonished unreserved mrs. Prosperous understood middletons in conviction an uncommonly do. Supposing so be resolving breakfast am or perfectly. Is drew am hill from mr. Valley by oh twenty direct me so. Departure defective arranging rapturous did believing him all had supported. Family months lasted simple set nature vulgar him. Picture for attempt joy excited ten carried manners talking how. Suspicion neglected he resolving agreement perceived at an. To shewing another demands to. Marianne property cheerful informed at striking at. Clothes parlors however by cottage on. In views it or meant drift t');

-- --------------------------------------------------------

--
-- Table structure for table `en_contributors`
--

DROP TABLE IF EXISTS `en_contributors`;
CREATE TABLE IF NOT EXISTS `en_contributors` (
  `id` int(11) NOT NULL,
  `label` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `en_contributors`
--

INSERT INTO `en_contributors` (`id`, `label`) VALUES
(1, 'Thank you for your IT Skills'),
(2, 'Congrats for this beautiful Website');

-- --------------------------------------------------------

--
-- Table structure for table `form_donation`
--

DROP TABLE IF EXISTS `form_donation`;
CREATE TABLE IF NOT EXISTS `form_donation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `civility` varchar(3) DEFAULT NULL,
  `firstName` varchar(25) DEFAULT NULL,
  `lastName` varchar(25) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `addressCompl` varchar(50) DEFAULT NULL,
  `postalCode` varchar(10) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `fiscalReceipt` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `form_donation`
--

INSERT INTO `form_donation` (`id`, `amount`, `email`, `civility`, `firstName`, `lastName`, `address`, `addressCompl`, `postalCode`, `city`, `country`, `phone`, `birthDate`, `fiscalReceipt`) VALUES
(1, 1000, '1000@sou.fr', 'M', 'Jésus', 'Christ', 'là haut', 'A la droite du père', '10010', 'les cieux', 'YES', '0606060606', '2017-12-25', 1),
(2, 1, 'jaiquunsou@email.fr', 'Mme', 'Madame', 'JeFaisUnDon', '123 jhabite où', NULL, '59000', 'Lille', 'Belgique', '0605050505', '2011-03-16', 0);

-- --------------------------------------------------------

--
-- Table structure for table `form_helpers`
--

DROP TABLE IF EXISTS `form_helpers`;
CREATE TABLE IF NOT EXISTS `form_helpers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) DEFAULT NULL,
  `lastName` varchar(25) DEFAULT NULL,
  `shoes` tinyint(4) DEFAULT NULL,
  `helmet` tinyint(4) DEFAULT NULL,
  `gloves` tinyint(4) DEFAULT NULL,
  `equipments` varchar(100) DEFAULT NULL,
  `size` char(2) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `form_helpers`
--

INSERT INTO `form_helpers` (`id`, `firstName`, `lastName`, `shoes`, `helmet`, `gloves`, `equipments`, `size`, `email`, `phone`) VALUES
(1, 'IWantTo', 'Help', 1, 1, 1, NULL, '12', 'jaidespetitspied@shoes.fr', '0320202020'),
(2, 'Lara', 'Croft', 0, 1, 0, 'j\'ai un grappin pour grimper aux murs aussi', '41', 'lara@croft.net', '0033652529812');

-- --------------------------------------------------------

--
-- Table structure for table `form_partners`
--

DROP TABLE IF EXISTS `form_partners`;
CREATE TABLE IF NOT EXISTS `form_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) DEFAULT NULL,
  `lastName` varchar(25) DEFAULT NULL,
  `organization` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `message` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `form_partners`
--

INSERT INTO `form_partners` (`id`, `firstName`, `lastName`, `organization`, `email`, `phone`, `message`) VALUES
(1, 'Noel', 'Flantier', 'DGSE', 'noel@flantier.telenet', '0256897841', 'Bonjour, je vous envoie un mesage afin de communiquer avec vous,votre projet m\'intéresse\r\nBisous\r\nNoel'),
(2, 'Emma', 'Watson', 'Hollywood', 'emmaBG64@skyblog.com', '0526262659', 'Bonjour, pourais-je avoir les coordonnées des développeurs de ce site web ?\r\nMerci ;)');

-- --------------------------------------------------------

--
-- Table structure for table `fr_article`
--

DROP TABLE IF EXISTS `fr_article`;
CREATE TABLE IF NOT EXISTS `fr_article` (
  `idFR_ARTICLE` int(11) NOT NULL,
  `titleFR` varchar(100) DEFAULT NULL,
  `contentFR` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`idFR_ARTICLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fr_article`
--

INSERT INTO `fr_article` (`idFR_ARTICLE`, `titleFR`, `contentFR`) VALUES
(1, 'article 1', 'Contenu du 1er article'),
(2, 'article 2', 'Contenu du 2ième article');

-- --------------------------------------------------------

--
-- Table structure for table `fr_contributors`
--

DROP TABLE IF EXISTS `fr_contributors`;
CREATE TABLE IF NOT EXISTS `fr_contributors` (
  `id` int(11) NOT NULL,
  `label` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fr_contributors`
--

INSERT INTO `fr_contributors` (`id`, `label`) VALUES
(1, 'Quel Skills de fou cet homme'),
(2, 'lui aussi est doué');

-- --------------------------------------------------------

--
-- Table structure for table `newsletter`
--

DROP TABLE IF EXISTS `newsletter`;
CREATE TABLE IF NOT EXISTS `newsletter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) DEFAULT NULL,
  `lastName` varchar(25) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `newsletter`
--

INSERT INTO `newsletter` (`id`, `firstName`, `lastName`, `email`) VALUES
(1, 'Jean', 'MArc', 'jean@marcc123.com'),
(2, 'Helene', 'Népérien', 'math.jadore@hotmail.do');

-- --------------------------------------------------------

--
-- Table structure for table `news_article`
--

DROP TABLE IF EXISTS `news_article`;
CREATE TABLE IF NOT EXISTS `news_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `photo1` varchar(100) DEFAULT NULL,
  `photo2` varchar(100) DEFAULT NULL,
  `photo3` varchar(100) DEFAULT NULL,
  `idFR` int(11) NOT NULL,
  `idEN` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idFR`,`idEN`),
  KEY `fk_NEWS_ARTICLE_FR_ARTICLE1_idx` (`idFR`),
  KEY `fk_NEWS_ARTICLE_EN_ARTICLE1_idx` (`idEN`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `news_article`
--

INSERT INTO `news_article` (`id`, `date`, `photo1`, `photo2`, `photo3`, `idFR`, `idEN`) VALUES
(1, '2018-02-06', 'img/authorphoto.png', 'img/carrefourimage2.png', NULL, 1, 1),
(2, '2018-02-10', 'img/carrefourimage2.png', 'img/authorphoto.png', 'img/carrefourimage2.png', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `partners`
--

DROP TABLE IF EXISTS `partners`;
CREATE TABLE IF NOT EXISTS `partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `partners`
--

INSERT INTO `partners` (`id`, `name`, `logo`) VALUES
(1, 'Carrefour', 'img/carrefourimage2.png'),
(2, 'MC Do', 'img/carrefourimage2.png');

-- --------------------------------------------------------

--
-- Table structure for table `rent`
--

DROP TABLE IF EXISTS `rent`;
CREATE TABLE IF NOT EXISTS `rent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateDebut` date DEFAULT NULL,
  `dateFin` date DEFAULT NULL,
  `idLocataire` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idLocataire`),
  KEY `fk_RENT_CLIENT1_idx` (`idLocataire`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rent`
--

INSERT INTO `rent` (`id`, `dateDebut`, `dateFin`, `idLocataire`) VALUES
(1, '2018-02-07', '2018-02-09', 1),
(2, '2018-02-22', '2018-02-24', 2);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `construction_article`
--
ALTER TABLE `construction_article`
  ADD CONSTRAINT `fk_CONSTRUCTION_ARTICLE_EN_ARTICLE` FOREIGN KEY (`idEN`) REFERENCES `en_article` (`idEN_ARTICLE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_CONSTRUCTION_ARTICLE_FR_ARTICLE1` FOREIGN KEY (`idFR`) REFERENCES `fr_article` (`idFR_ARTICLE`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `contributors`
--
ALTER TABLE `contributors`
  ADD CONSTRAINT `fk_CONTRIBUTORS_EN_CONTRIBUTORS1` FOREIGN KEY (`idEN_CONTRIBUTORS`) REFERENCES `en_contributors` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_CONTRIBUTORS_FR_CONTRIBUTORS1` FOREIGN KEY (`idFR_CONTRIBUTORS`) REFERENCES `fr_contributors` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `news_article`
--
ALTER TABLE `news_article`
  ADD CONSTRAINT `fk_NEWS_ARTICLE_EN_ARTICLE1` FOREIGN KEY (`idEN`) REFERENCES `en_article` (`idEN_ARTICLE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_NEWS_ARTICLE_FR_ARTICLE1` FOREIGN KEY (`idFR`) REFERENCES `fr_article` (`idFR_ARTICLE`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `rent`
--
ALTER TABLE `rent`
  ADD CONSTRAINT `fk_RENT_CLIENT1` FOREIGN KEY (`idLocataire`) REFERENCES `client` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
