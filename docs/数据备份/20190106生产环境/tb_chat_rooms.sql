/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-06 19:53:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_chat_rooms`
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_rooms`;
CREATE TABLE `tb_chat_rooms` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `live_id` int(11) NOT NULL COMMENT '直播间id',
  `chat_room_id` varchar(32) DEFAULT NULL COMMENT '聊天室 id',
  `frequency` double NOT NULL COMMENT '发言频率',
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `uq_room` (`live_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='聊天室信息表';

-- ----------------------------
-- Records of tb_chat_rooms
-- ----------------------------
INSERT INTO `tb_chat_rooms` VALUES ('1', '1', '1551712440', '10');
INSERT INTO `tb_chat_rooms` VALUES ('2', '2', '1551854654', '10');
