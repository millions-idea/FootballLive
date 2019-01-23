/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-19 21:46:54
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='情报信息表';

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
INSERT INTO `tb_informations` VALUES ('9', '510814', '87', '<p>曼城-2</p>', '', '2019-01-14 14:13:11', '1', '曼城-2', '', '899091');
INSERT INTO `tb_informations` VALUES ('10', '510857', '88', '<p>印度+0.5</p>', '', '2019-01-14 14:13:39', '1', '印度+0.5', '', '899852');
INSERT INTO `tb_informations` VALUES ('11', '510857', '92', '<p>巴勒斯坦+0/0.5</p>', '', '2019-01-15 14:18:04', '1', '巴+0.25', '', '899838');
INSERT INTO `tb_informations` VALUES ('12', '510857', '93', '<p>澳大利亚-1/1.5</p>', '', '2019-01-15 14:18:12', '1', '澳-1.25', '', '899869');
INSERT INTO `tb_informations` VALUES ('13', '510857', '100', '<p>中国独赢</p>', '', '2019-01-16 16:58:26', '1', '中国独赢', '', '899855');
INSERT INTO `tb_informations` VALUES ('14', '510857', '101', '<p>伊朗独赢</p>', '', '2019-01-16 16:58:29', '0', '伊朗独赢', '', '899750');
INSERT INTO `tb_informations` VALUES ('15', '510857', '102', '<p>吉尔吉斯斯坦独赢</p>', '', '2019-01-16 16:58:32', '1', '主场独赢', '', '899844');
INSERT INTO `tb_informations` VALUES ('16', '510857', '103', '<p>越南独赢</p>', '', '2019-01-16 16:58:35', '0', '越南独赢', '', '899842');
INSERT INTO `tb_informations` VALUES ('17', '510857', '115', '<p>土库曼斯坦+1/1.5</p>', '', '2019-01-17 16:48:19', '0', '土+1/1.5', '', '899788');
INSERT INTO `tb_informations` VALUES ('18', '510857', '116', '<p>日本-0.5/1</p>', '', '2019-01-17 16:48:21', '1', '日本-0.5/1', '', '899862');
INSERT INTO `tb_informations` VALUES ('19', '510857', '117', '<p>黎巴嫩-1/1.5</p>', '', '2019-01-17 16:48:27', '0', '黎巴嫩-1.25', '', '899856');
INSERT INTO `tb_informations` VALUES ('20', '510857', '118', '<p>沙特阿拉伯 让 0</p>', '', '2019-01-17 16:48:30', '1', '主让0', '', '899850');
INSERT INTO `tb_informations` VALUES ('21', '115904', '5', '<p>卡迪夫城+0.5</p>', '', '2019-01-19 14:11:09', '0', '卡迪夫城+0.5', '', '450942');
INSERT INTO `tb_informations` VALUES ('22', '115904', '6', '<p>西汉姆联+0/0.5</p>', '', '2019-01-19 14:11:13', '1', '西+0.25', '', '607043');
INSERT INTO `tb_informations` VALUES ('23', '115904', '8', '<p>沃特福德-0.5/1<br></p>', '', '2019-01-19 14:11:16', '1', '沃-0.75', '', '554307');
INSERT INTO `tb_informations` VALUES ('24', '115904', '9', '<p>南安普敦 让 0&nbsp;</p>', '', '2019-01-19 14:11:20', '0', '南安普敦-0', '', '115392');
INSERT INTO `tb_informations` VALUES ('25', '979346', '4', '<p>利物浦-2</p>', '', '2019-01-19 16:33:10', '1', '利物浦-2', '', '978822');
