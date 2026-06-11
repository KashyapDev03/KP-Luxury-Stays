CREATE DATABASE  IF NOT EXISTS `kp_luxestays` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kp_luxestays`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: kp_luxestays
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`admin_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('kashyapnayyar@gmail.com','123');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `cityid` int NOT NULL AUTO_INCREMENT,
  `cityname` varchar(50) NOT NULL,
  `statename` varchar(45) NOT NULL,
  `countryname` varchar(45) NOT NULL,
  `citydesc` varchar(500) NOT NULL,
  `cityphoto` varchar(1000) NOT NULL,
  PRIMARY KEY (`cityid`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (16,'Amritsar','Punjab','India','Historic city famous for Golden Temple, local food, luxury stays, and peaceful family accommodations.','myUploads/Amritsar.png'),(22,'Delhi','Delhi','India','Capital city offering modern luxury residences, famous tourist attractions, and premium hospitality experiences.','myUploads/Delhi1.png'),(27,'Goa','Goa','India','Famous beach destination with sea-facing villas, swimming pools, nightlife, and luxury vacation stays.','myUploads/Goa.png'),(28,'Manali','Himachal Pradesh','India','Beautiful hill station surrounded by mountains, snowfall, cozy cottages, and scenic valley views.','myUploads/Manali.png'),(29,'Shimla','Himachal Pradesh','India','Charming mountain destination with luxury cottages, cool weather, pine forests, and family vacation stays.','myUploads/Shimla.png'),(31,'Mussoorie','Uttarakhand','India','Scenic hill station with mountain resorts, beautiful viewpoints, and relaxing stays for nature lovers.','myUploads/Mussoorie.png');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owner` (
  `name` varchar(100) NOT NULL,
  `email` varchar(75) NOT NULL,
  `password` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `contact` varchar(100) NOT NULL,
  `brandname` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` VALUES ('Kashyap','kashyap@gmail.com','123','Amritsar','7894561230','KP Stays','Active'),('Mark','mark@gmail.com','123','27','7897419630','Mark Bnb','Active'),('Samuel','samuel@gmail.com','123','28','7894568520','Samuel Stays','Active');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ownerproperties`
--

DROP TABLE IF EXISTS `ownerproperties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ownerproperties` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner_email` varchar(50) NOT NULL,
  `property_type` varchar(45) DEFAULT NULL,
  `property_name` varchar(45) DEFAULT NULL,
  `propertyid` int NOT NULL,
  `cityid` int NOT NULL,
  `price` varchar(45) NOT NULL,
  `offer_price` varchar(45) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `address` varchar(500) NOT NULL,
  `photo` varchar(1000) NOT NULL,
  `status` varchar(45) NOT NULL,
  `latitude` varchar(200) NOT NULL,
  `longitude` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `owner_email_idx` (`owner_email`),
  KEY `cityid_idx` (`cityid`),
  KEY `propertyid_idx` (`propertyid`),
  CONSTRAINT `fk_cityid` FOREIGN KEY (`cityid`) REFERENCES `city` (`cityid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_owner_email` FOREIGN KEY (`owner_email`) REFERENCES `owner` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_propertyid` FOREIGN KEY (`propertyid`) REFERENCES `property` (`propertyid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ownerproperties`
--

LOCK TABLES `ownerproperties` WRITE;
/*!40000 ALTER TABLE `ownerproperties` DISABLE KEYS */;
INSERT INTO `ownerproperties` VALUES (22,'samuel@gmail.com','15','Samuel Cottage',15,28,'4500','4200','Cozy mountain Airbnb surrounded by snowy peaks, pine forests, and breathtaking valley views, perfect for peaceful vacations and relaxing weekend getaways in Manali.','Near Hadimba Temple Road, Old Manali, Manali, Himachal Pradesh – 175131','myUploads/Cottage1_Manali.png','Active','32.2461','77.1887'),(23,'kashyap@gmail.com','7','City Lights Apartment',7,22,'11500','9500','Enjoy a modern and comfortable apartment with stylish interiors, spacious rooms, and all essential amenities. Perfect for a relaxing stay near popular attractions, shopping, and dining areas.','45 Connaught Place, Block A, New Delhi, Delhi 110001, India','myUploads/Apartment1_front.png','Active','28.6315','77.2167'),(26,'mark@gmail.com','14','Mark Villa',14,27,'16500','15000','Experience luxury and serenity at Ocean Breeze Villa, featuring elegant interiors, spacious rooms, and breathtaking sea views. Perfect for relaxing family vacations and premium coastal stays.','Ocean Breeze Villa, Palm Beach Road, Near Candolim Beach, North Goa, Goa 403515, India','myUploads/Mark Villa Goa profile photo.png','Active','15.5170','73.7626'),(27,'kashyap@gmail.com','16','Grand Tranquil Resort',16,16,'19500','18000','Escape to Grand Tranquil Resort, a premium luxury getaway near the Amritsar highway featuring elegant rooms, lush green lawns, a peaceful ambiance, and modern amenities for a perfect relaxing stay.','Grand Tranquil Resort, NH-3 Grand Trunk Road, Near Jandiala Guru, Amritsar, Punjab 143115, India','myUploads/Amritsar Resort.png','Active','31.6264','74.9508'),(28,'mark@gmail.com','16','Mark Cottage ',16,29,'8500','7500','Enjoy a peaceful mountain escape at Snow Crest Retreat, offering luxurious rooms, breathtaking valley views, cozy interiors, and a relaxing atmosphere in the heart of Shimla.','Snow Crest Retreat, Kufri-Chail Road, Near Green Valley, Shimla, Himachal Pradesh 171012, India','myUploads/Mark Shimla.png','Active','31.1048','77.1734'),(29,'mark@gmail.com','16','The Cedar Valley Retreat',16,31,'22500','20000','Experience luxury and serenity at The Cedar Valley Retreat, a premium Mussoorie getaway featuring elegant wooden interiors, panoramic Himalayan views, cozy fireplaces, and peaceful nature surroundings.','The Cedar Valley Retreat, Mall Road Extension, Near Gun Hill Point, Mussoorie, Uttarakhand 248179, India','myUploads/Mussoorie Property 1.png','Active','30.4589','78.0643'),(30,'kashyap@gmail.com','18','KP Royal Villa',18,22,'18500','15000','Experience luxury and elegance in this premium villa located in the heart of Delhi. Featuring spacious bedrooms, a private pool, modern interiors, high-speed Wi-Fi, and a peaceful ambiance, this villa is perfect for family vacations, parties, and relaxing weekend stays.','89 Palm Residency, Vasant Kunj, New Delhi, Delhi 110070, India','myUploads/KP Villa Delhi.png','Active','28.5245','68');
/*!40000 ALTER TABLE `ownerproperties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `owner_email` varchar(50) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `owner_property_id` int NOT NULL,
  `total_price` int NOT NULL,
  `payment_type` varchar(50) NOT NULL,
  `address` varchar(500) NOT NULL,
  `start_date` varchar(50) NOT NULL,
  `end_date` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (70,'kashyap@gmail.com','kashyap@gmail.com',27,74880,'cash','Grand Tranquil Resort, NH-3 Grand Trunk Road, Near Jandiala Guru, Amritsar, Punjab 143115, India','05/27/2026','05/31/2026'),(71,'kashyap@gmail.com','kashyap@gmail.com',30,62400,'cash','89 Palm Residency, Vasant Kunj, New Delhi, Delhi 110070, India','05/27/2026','05/31/2026'),(72,'kashyap@gmail.com','kashyap@gmail.com',30,62400,'online','89 Palm Residency, Vasant Kunj, New Delhi, Delhi 110070, India','06/10/2026','06/14/2026'),(73,'kashyap@gmail.com','kashyap@gmail.com',30,62400,'cash','89 Palm Residency, Vasant Kunj, New Delhi, Delhi 110070, India','06/10/2026','06/14/2026');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_detail`
--

DROP TABLE IF EXISTS `payment_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_date` varchar(50) NOT NULL,
  `end_date` varchar(50) NOT NULL,
  `payment_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_payment_id_idx` (`payment_id`),
  CONSTRAINT `fk_payment_id` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_detail`
--

LOCK TABLES `payment_detail` WRITE;
/*!40000 ALTER TABLE `payment_detail` DISABLE KEYS */;
INSERT INTO `payment_detail` VALUES (189,'05/27/2026','05/28/2026',70),(190,'05/28/2026','05/29/2026',70),(191,'05/29/2026','05/30/2026',70),(192,'05/30/2026','05/31/2026',70),(193,'05/27/2026','05/28/2026',71),(194,'05/28/2026','05/29/2026',71),(195,'05/29/2026','05/30/2026',71),(196,'05/30/2026','05/31/2026',71),(197,'06/10/2026','06/11/2026',72),(198,'06/11/2026','06/12/2026',72),(199,'06/12/2026','06/13/2026',72),(200,'06/13/2026','06/14/2026',72),(201,'06/10/2026','06/11/2026',73),(202,'06/11/2026','06/12/2026',73),(203,'06/12/2026','06/13/2026',73),(204,'06/13/2026','06/14/2026',73);
/*!40000 ALTER TABLE `payment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propertiesphoto`
--

DROP TABLE IF EXISTS `propertiesphoto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propertiesphoto` (
  `photoid` int NOT NULL AUTO_INCREMENT,
  `photo` varchar(100) NOT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`photoid`),
  KEY `id_idx` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `ownerproperties` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propertiesphoto`
--

LOCK TABLES `propertiesphoto` WRITE;
/*!40000 ALTER TABLE `propertiesphoto` DISABLE KEYS */;
INSERT INTO `propertiesphoto` VALUES (19,'myUploads/Cottage1_Manali.png',22),(21,'myUploads/Cottage1_Manali_Bathrrom.jpg',22),(25,'myUploads/a6b8e771-4dd4-461b-9461-3438a44b42f6.png',22),(26,'myUploads/7c7de9eb-a397-483c-9e55-68fad935b5ce.png',22),(35,'myUploads/Cottage1_3.png',22),(48,'myUploads/Apartment1_front.png',23),(49,'myUploads/Apartment1_6.png',23),(50,'myUploads/Apartment1_5.png',23),(51,'myUploads/Apartment1_4.png',23),(52,'myUploads/Apartment1_3.png',23),(53,'myUploads/Apartment1_2.png',23),(54,'myUploads/Apartment1.png',23),(68,'myUploads/Mark Villa Goa profile photo.png',26),(69,'myUploads/Mark Villa Goa room.png',26),(70,'myUploads/Mark Villa Goa kitchen.png',26),(72,'myUploads/Mark Villa Goa Lawn.png',26),(76,'myUploads/Mark Villa Goa 2.png',26),(77,'myUploads/Amritsar Resort.png',27),(78,'myUploads/Amritsar Resort 2.png',27),(79,'myUploads/Amritsar Resort 1.png',27),(80,'myUploads/Amritsar Resort 4.png',27),(85,'myUploads/Amritsar Resort 3.png',27),(89,'myUploads/Amritsar Resort 5.png',27),(90,'myUploads/Amritsar Resort 6.png',27),(91,'myUploads/Mark Shimla 1.png',28),(94,'myUploads/Mark Shimla.png',28),(95,'myUploads/Mark Shimla 2.png',28),(96,'myUploads/Mark Shimla 3.png',28),(97,'myUploads/Mark Shimla 4.png',28),(98,'myUploads/Mark Shimla 5.png',28),(99,'myUploads/Mussoorie Property 1.png',29),(100,'myUploads/Mussoorie Property 2.png',29),(101,'myUploads/Mussoorie Property 3.png',29),(102,'myUploads/Mussoorie Property 4.png',29),(103,'myUploads/Mussoorie Property 5.png',29),(104,'myUploads/Mussoorie Property 7.png',29),(105,'myUploads/Mussoorie Property 6.png',29),(112,'myUploads/KP Villa Delhi.png',30),(113,'myUploads/KP Villa Room2.png',30),(114,'myUploads/KP Villa Bathroom.png',30),(115,'myUploads/KP Villa Living 2.png',30),(116,'myUploads/KP Villa Kitchen.png',30),(117,'myUploads/KP Villa Bedroom.png',30),(118,'myUploads/KP Villa Living Room.png',30);
/*!40000 ALTER TABLE `propertiesphoto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property` (
  `propertyid` int NOT NULL AUTO_INCREMENT,
  `propertyname` varchar(50) NOT NULL,
  `propertydesc` varchar(100) NOT NULL,
  `propertyphoto` varchar(1000) NOT NULL,
  PRIMARY KEY (`propertyid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property`
--

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
INSERT INTO `property` VALUES (7,'Luxury Apartments','Here you can rent the property as per your requirment','myUploads/Apartments.jpg'),(8,' Luxury Kothi','Here you can organize house parties with you friend and family','myUploads/Kothi.jpg'),(13,'KP Royal Residency','Luxury stay with modern interiors, private parking, and peaceful environment near city center.','myUploads/KP Royal Residency.png'),(14,'Ocean Breeze Villa','Beautiful villa with swimming pool and sea-facing balcony perfect for family vacations.','myUploads/Ocean Breeze Villa.png'),(15,'Cottage','Cozy mountain cottage with scenic views, fireplace, and relaxing atmosphere.','myUploads/Mountain View Cottage.png'),(16,'Resort','Nature-friendly resort surrounded by greenery and ideal for weekend getaways.','myUploads/Green Valley Resort.png'),(18,'Luxury Villa','Here you can get the holy view of Shri Darbar Sahib','myUploads/Mark Villa Goa profile photo.png');
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uname` varchar(50) NOT NULL,
  `uemail` varchar(50) NOT NULL,
  `upassword` varchar(50) NOT NULL,
  `ucontact` varchar(50) NOT NULL,
  `uaddress` varchar(100) NOT NULL,
  PRIMARY KEY (`uemail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('Akshita','aksh@gmail.com','123','7894512310','123, Loharka Road'),('Kashyap','kashyap@gmail.com','123','7894561230','123, asr'),('Samwell Tarly','sam@gmail.com','123','852444500','123, ABC');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-11 11:06:52
