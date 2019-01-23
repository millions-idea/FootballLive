/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-19 21:40:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_lives`
-- ----------------------------
DROP TABLE IF EXISTS `tb_lives`;
CREATE TABLE `tb_lives` (
  `live_id` int(11) NOT NULL AUTO_INCREMENT,
  `live_title` varchar(255) NOT NULL COMMENT '直播间标题',
  `live_date` datetime NOT NULL COMMENT '直播时间',
  `schedule_id` int(11) NOT NULL COMMENT '赛程id',
  `status` int(11) NOT NULL COMMENT '状态(0=正常,1=删除)',
  `share_count` int(11) NOT NULL DEFAULT '0' COMMENT '分享次数',
  `collect_count` int(11) NOT NULL DEFAULT '0' COMMENT '收藏次数',
  `source_url` varchar(255) NOT NULL COMMENT '播放链接',
  `ad_id` int(11) NOT NULL DEFAULT '0' COMMENT '广告id',
  `add_date` datetime DEFAULT NULL COMMENT '添加时间',
  `edit_date` datetime DEFAULT NULL COMMENT '更新时间 ',
  `team_id_list` varchar(32) DEFAULT NULL COMMENT '球队id集合',
  PRIMARY KEY (`live_id`),
  UNIQUE KEY `uq_model` (`schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=701889 DEFAULT CHARSET=utf8 COMMENT='直播间信息表';

-- ----------------------------
-- Records of tb_lives
-- ----------------------------
INSERT INTO `tb_lives` VALUES ('1', '西悉尼流浪者 VS 阿德莱德联', '2019-01-18 16:50:00', '9', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj9.m3u8', '0', '2019-01-18 16:31:11', null, '144159,144160');
INSERT INTO `tb_lives` VALUES ('2', '霍芬海姆 VS 拜仁慕尼黑', '2019-01-19 03:30:00', '201', '1', '0', '0', 'http://play.512ck.cn/zuqiu/dj201.m3u8', '0', '2019-01-19 01:01:36', null, '999954,999955');
INSERT INTO `tb_lives` VALUES ('3', '里尔 VS 亚眠', '2019-01-19 03:45:00', '211', '1', '0', '0', 'http://play.512ck.cn/zuqiu/fj211.m3u8', '0', '2019-01-19 01:01:57', null, '999984,999985');
INSERT INTO `tb_lives` VALUES ('4', '利物浦 VS 水晶宫', '2019-01-19 23:00:00', '1013', '0', '0', '0', '#', '0', '2019-01-19 14:02:17', null, null);
INSERT INTO `tb_lives` VALUES ('5', '纽卡斯尔联 VS 卡迪夫城', '2019-01-19 23:00:00', '1015', '0', '0', '0', '#', '0', '2019-01-19 14:03:21', null, null);
INSERT INTO `tb_lives` VALUES ('6', '伯恩茅斯 VS 西汉姆联', '2019-01-19 23:00:00', '1012', '0', '0', '0', '#', '0', '2019-01-19 14:03:38', null, null);
INSERT INTO `tb_lives` VALUES ('7', '曼彻斯特联 VS 布莱顿', '2019-01-19 23:00:00', '1014', '1', '0', '0', '#', '0', '2019-01-19 14:03:59', null, null);
INSERT INTO `tb_lives` VALUES ('8', '沃特福德 VS 伯恩利', '2019-01-19 23:00:00', '1017', '0', '0', '0', '#', '0', '2019-01-19 14:04:47', null, null);
INSERT INTO `tb_lives` VALUES ('9', '南安普敦 VS 埃弗顿', '2019-01-19 23:00:00', '1016', '0', '0', '0', '#', '0', '2019-01-19 14:05:18', null, null);
INSERT INTO `tb_lives` VALUES ('701888', '马泰拉 VS ASD西库拉', '2019-01-20 23:30:00', '531532', '0', '0', '0', '#', '0', '2019-01-19 17:03:29', null, '');
