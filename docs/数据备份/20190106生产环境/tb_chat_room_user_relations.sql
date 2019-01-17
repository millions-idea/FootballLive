/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-06 19:53:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_chat_room_user_relations`
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_room_user_relations`;
CREATE TABLE `tb_chat_room_user_relations` (
  `relation_id` int(11) NOT NULL AUTO_INCREMENT,
  `live_id` int(11) NOT NULL COMMENT '直播间id',
  `room_id` int(11) NOT NULL COMMENT '房间id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `is_black_list` int(11) DEFAULT '0' COMMENT '是否拉黑',
  `edit_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`relation_id`),
  UNIQUE KEY `uq_user` (`room_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='聊天室用户关系表';

-- ----------------------------
-- Records of tb_chat_room_user_relations
-- ----------------------------
INSERT INTO `tb_chat_room_user_relations` VALUES ('1', '1', '1', '1', '0', '2019-01-06 15:36:21');
INSERT INTO `tb_chat_room_user_relations` VALUES ('2', '2', '2', '1', '0', '2019-01-06 15:51:00');
