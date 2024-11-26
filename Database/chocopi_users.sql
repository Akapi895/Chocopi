-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: chocopi
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `favor` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `role` enum('user','admin') NOT NULL,
  `totalBorrowed` int DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'pat','123123','Pham Anh Tuan','/com/chocopi/images/avatar/1.png',19,'0123456789','Fiction','john@example.com','admin',6),(2,'ndmvy','111','Nguyen Dieu Mai Vy','/com/chocopi/images/avatar/2.png',19,'0354109318','pat','maivybs2@gmail.com','user',6),(3,'tri','123','phantri','/com/chocopi/images/avatar/3.png',19,'123456789','maths	','tri@gmail.com','user',6),(4,'quangle','123123','Emily Jones','/com/chocopi/images/avatar/4.png',20,'5566778899','Romance','emily@example.com','admin',5),(5,'ndmaivy','12345','Nguyen Dieu Mai Vy','/com/chocopi/images/avatar/5.png',21,'0858234777','Math','email@gmail.com','user',2),(6,'tgk','12345','Tran Gia Khanh','/com/chocopi/images/avatar/6.png',21,'927395032','Physics','emailtgk@gmail.com','user',2),(7,'vanvu','12345','Vu Nhat Tuong Van','/com/chocopi/images/avatar/7.png',21,'928395032','Science','emailvv@gmail.com','user',2),(8,'pat1','1','Chocopi User',NULL,0,'1','Không có','1','user',0),(9,'mixi','2','Chocopi User',NULL,0,'2','Không có','2','user',0),(10,'mxxi','a','Chocopi User','/com/chocopi/images/avatar/10.png',0,'3','Không có','3','user',0),(11,'pat4','4','Nguoi dung 4','/com/chocopi/images/avatar/11.png',23,'1235432','bóng đá	','222@gm.com','user',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 16:41:32
