-- MySQL dump 10.14  Distrib 5.5.40-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ws
-- ------------------------------------------------------
-- Server version	5.5.40-MariaDB-1~lucid-log

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
-- Table structure for table `tb_client`
--

DROP TABLE IF EXISTS `tb_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `version` varchar(30) NOT NULL DEFAULT '' COMMENT '版本号',
  `name` varchar(100) NOT NULL DEFAULT '0' COMMENT '文件名称',
  `create_date` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='客户端版本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_client`
--

LOCK TABLES `tb_client` WRITE;
/*!40000 ALTER TABLE `tb_client` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_data`
--

DROP TABLE IF EXISTS `tb_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_data` (
  `data_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '文件编号',
  `user_id` int(10) NOT NULL COMMENT '用户编号 提交者',
  `data_key` varchar(100) NOT NULL COMMENT '数据编号',
  `name` varchar(2000) NOT NULL DEFAULT '' COMMENT '文件名字',
  `size` double DEFAULT NULL COMMENT '大小 单位：KB',
  `create_date` datetime NOT NULL COMMENT '提交时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `path` varchar(100) DEFAULT NULL COMMENT '文件路径',
  `state` int(1) DEFAULT '0' COMMENT '状态 0:未归档，1:已归档',
  `md5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`data_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4697 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_data`
--

LOCK TABLES `tb_data` WRITE;
/*!40000 ALTER TABLE `tb_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `create_date` datetime NOT NULL COMMENT '注册时间',
  `truename` varchar(45) DEFAULT NULL COMMENT '真实姓名',
  `state` int(1) DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-10-24 13:13:51
