-- MySQL dump 10.13  Distrib 8.0.19, for Linux (x86_64)
--
-- Host: 120.120.16.51    Database: uretim
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bantsefleri`
--

DROP TABLE IF EXISTS `bantsefleri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bantsefleri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sira` int(11) NOT NULL DEFAULT '0',
  `sefAdi` varchar(50) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donem`
--

DROP TABLE IF EXISTS `donem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sira` int(11) DEFAULT NULL,
  `donemBasla` char(50) DEFAULT NULL,
  `donemBit` char(50) DEFAULT NULL,
  `donemModeli` char(50) DEFAULT NULL,
  `aktif` int(11) DEFAULT NULL,
  `sure` float DEFAULT NULL,
  `renk` char(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hedeflertablosu`
--

DROP TABLE IF EXISTS `hedeflertablosu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hedeflertablosu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modelAdi` text NOT NULL,
  `dikimHedef` int(11) NOT NULL,
  `koliHedef` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=527 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `iletisim`
--

DROP TABLE IF EXISTS `iletisim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iletisim` (
  `ad` varchar(50) DEFAULT NULL,
  `soyad` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `sehir` varchar(50) DEFAULT NULL,
  `yorum` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mail_listesi`
--

DROP TABLE IF EXISTS `mail_listesi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mail_listesi` (
  `mailSahibi` text NOT NULL,
  `mailAdresi` text NOT NULL,
  `sifre` varchar(15) NOT NULL DEFAULT 'yok',
  `aktifKullanici` varchar(15) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mail_listesi3`
--

DROP TABLE IF EXISTS `mail_listesi3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mail_listesi3` (
  `mailSahibi` text NOT NULL,
  `mailAdresi` text NOT NULL,
  `sifre` varchar(15) NOT NULL DEFAULT 'yok',
  `aktifKullanici` varchar(15) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `saatliktakip`
--

DROP TABLE IF EXISTS `saatliktakip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saatliktakip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bantAd` char(50) DEFAULT 'YOK',
  `bantSef` char(50) DEFAULT 'YOK',
  `donem` char(50) DEFAULT NULL,
  `tarih` date DEFAULT NULL,
  `model` varchar(50) NOT NULL DEFAULT 'YOK',
  `duyuru` text,
  `dikimAdet` int(11) DEFAULT '0',
  `koliAdet` int(11) DEFAULT '0',
  `dikimHedef` int(11) DEFAULT '0',
  `koliHedef` int(11) DEFAULT '0',
  `operatorAdedi` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `model` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=12135 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `saatliktakip_depo`
--

DROP TABLE IF EXISTS `saatliktakip_depo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saatliktakip_depo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bantAd` char(50) DEFAULT 'YOK',
  `bantSef` char(50) DEFAULT 'YOK',
  `donem` char(50) DEFAULT NULL,
  `tarih` date DEFAULT NULL,
  `model` varchar(50) NOT NULL DEFAULT 'YOK',
  `duyuru` text,
  `dikimAdet` int(11) DEFAULT '0',
  `koliAdet` int(11) DEFAULT '0',
  `dikimHedef` int(11) DEFAULT '0',
  `koliHedef` int(11) DEFAULT '0',
  `operatorAdedi` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `model` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=85812 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-21 12:13:49
