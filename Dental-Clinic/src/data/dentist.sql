CREATE DATABASE  IF NOT EXISTS `dentist` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `dentist`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: dentist
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patients` (
  `patientId` int(11) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (NULL,'Cathryn','Butler','Cathryn.Butler@gmail.com','131 Neptune Court - Hall (2952 Nevada)','+1 (995) 555-2432'),(NULL,'David','Coffey','Coffey.David@gmail.com','110 Schermerhorn Street - Greer (5615 Oklahoma)','+1 (898) 482-3496'),(NULL,'Cathryn','Butler','Cathryn.Butler@gmail.com','131 Neptune Court - Hall (2952 Nevada)','+1 (995) 555-2432'),(NULL,'John','Kerry','Kerry.Rich@gmail.com','149 Roosevelt Court - Kidder (6603 Northern Mariana Islands)','+1 (853) 457-3218'),(NULL,'Grace','Velasquez','Grace.Velasquez@gmail.com','175 Kings Hwy - Loveland (8520 South Carolina)','+1 (909) 411-3328');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proceduretypes`
--

DROP TABLE IF EXISTS `proceduretypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proceduretypes` (
  `procedureTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`procedureTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proceduretypes`
--

LOCK TABLES `proceduretypes` WRITE;
/*!40000 ALTER TABLE `proceduretypes` DISABLE KEYS */;
INSERT INTO `proceduretypes` VALUES (1,'Crowns      ','Core/Post (if needed)       ',230),(2,'Crowns      ','Core/Post (if needed)       ',230),(3,'Crowns      ','Porcelain crown (fused to metal)        ',570),(4,'Crowns      ','Zirconia crown      ',650),(5,'Dental Hygiene  ','Dentist (per visit)        ',55),(6,'Dental Hygiene  ','Hygienist (per visit)      ',55),(7,'Dental Implants     ','Consultation        ',65),(8,'Dental Implants     ','Implant crown from      ',1100),(9,'Dental Implants     ','Single implant fixture      ',1200),(10,'Fillings        ','Gold or ceramic restoration/inlay/onlay     ',425),(11,'Nervous Patient Consultation','Consultation with Dr Nuck Chorris  ',110),(12,'Oral Surgery        ','Consultation        ',150),(13,'Oral Surgery        ','Surgical extraction with specialist (Your health insurer may partially cover this treatment)     ',370),(14,'Orthodontics      ','Fixed braces (adults)       ',3200),(15,'Orthodontics      ','Fixed braces (kids)     ',2850),(16,'Prescription','Without exam',25),(17,'Re-treatment Root Canal','Incisors and canines        ',410),(18,'Re-treatment Root Canal','Molar       ',520),(19,'Re-treatment Root Canal','Pre-molar       ',460),(20,'Root Canal Treatment  ','Front tooth     ',330),(21,'Root Canal Treatment  ','Molar       ',475),(22,'Root Canal Treatment  ','Pre-molar       ',425),(23,'Routine Exam','(Exam, diagnosis and treatment plan)',40),(24,'Teeth Whitening     ','Enlighten Whitening       ',720),(25,'Teeth Whitening     ','Smiles At Home Whitening Kit        ',260),(26,'Teeth Whitening     ','Smiles At Home Whitening Kit Refills        ',85),(27,'Teeth Whitening     ','Zoom Whitening        ',475),(28,'Tooth Extraction        ','Routine extraction      ',85),(29,'Tooth Extraction        ','Surgical extraction     ',160),(30,'Veneers     ','Single veneer       ',530),(31,'X-Rays      ','Small       ',15),(32,'X-Rays','Ceph X-Rays     ',50),(33,'X-Rays','Large (OPG)     ',35),(34,'X-Rays','Referral X-rays     ',55);
/*!40000 ALTER TABLE `proceduretypes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-08 20:20:27
