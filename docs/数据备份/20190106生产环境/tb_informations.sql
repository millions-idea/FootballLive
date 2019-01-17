/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-14 15:10:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_informations`
-- ----------------------------
DROP TABLE IF EXISTS `tb_informations`;
CREATE TABLE `tb_informations` (
  `isr_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL COMMENT '赛事id',
  `live_id` int(11) NOT NULL COMMENT '直播间id',
  `content` text NOT NULL COMMENT '情报内容',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除',
  `add_date` datetime DEFAULT NULL COMMENT '添加时间',
  `is_hot` int(11) DEFAULT '0',
  `forecast_result` varchar(32) DEFAULT NULL COMMENT '预测结果',
  `forecast_grade` varchar(32) DEFAULT NULL COMMENT '预测比分',
  `forecast_team_id` int(11) DEFAULT NULL COMMENT '预测胜利球队',
  PRIMARY KEY (`isr_id`),
  UNIQUE KEY `uq_model` (`game_id`,`live_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='情报信息表';

-- ----------------------------
-- Records of tb_informations
-- ----------------------------
INSERT INTO `tb_informations` VALUES ('1', '510857', '25', '<p>测试情报内容</p>', '', '2019-01-10 22:35:29', '0', '让胜 0.25', '2-1', '899840');
INSERT INTO `tb_informations` VALUES ('2', '510857', '34', '<p>中国-1</p>', '', '2019-01-11 20:14:38', '1', '中国-1', '', '899855');
INSERT INTO `tb_informations` VALUES ('3', '510857', '35', '<p>吉尔吉斯斯坦+1.5/2</p>', '', '2019-01-11 20:15:03', '1', '主受让+1.75', '', '899844');
INSERT INTO `tb_informations` VALUES ('4', '510857', '64', '<p>卡塔尔-1/1.5</p>', '', '2019-01-13 17:32:02', '1', '卡塔尔-1.25', '', '899863');
INSERT INTO `tb_informations` VALUES ('5', '510857', '65', '<p>日本-1/1.5</p>', '', '2019-01-13 17:32:37', '1', '日本-1.25', '', '899862');
INSERT INTO `tb_informations` VALUES ('6', '510857', '66', '<p>托特纳姆热刺-独赢</p>', '', '2019-01-13 17:33:05', '0', '独赢', '', '899098');
INSERT INTO `tb_informations` VALUES ('7', '510857', '67', '<p>伯恩茅斯+0.5/1</p>', '', '2019-01-13 17:33:08', '0', '伯恩茅斯+0.75', '', '899387');
INSERT INTO `tb_informations` VALUES ('8', '510857', '86', '<p>阿联酋-0.5/1</p>', '', '2019-01-14 14:13:08', '1', '阿联酋-0.75', '', '899846');
INSERT INTO `tb_informations` VALUES ('9', '510857', '87', '<p>曼城-2</p>', '', '2019-01-14 14:13:11', '1', '曼城-2', '', '899091');
INSERT INTO `tb_informations` VALUES ('10', '510857', '88', '<p>印度+0.5</p>', '', '2019-01-14 14:13:39', '1', '印度+0.5', '', '899852');
