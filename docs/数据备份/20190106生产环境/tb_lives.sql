/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-01-14 15:11:34
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
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='直播间信息表';

-- ----------------------------
-- Records of tb_lives
-- ----------------------------
INSERT INTO `tb_lives` VALUES ('1', '墨尔本城 VS 新城堡联队', '2019-01-06 14:00:00', '616', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj616.m3u8', '0', '2019-01-06 15:34:55', '2019-01-06 15:36:21', null);
INSERT INTO `tb_lives` VALUES ('2', '广东 VS 中国香港', '2019-01-06 15:30:00', '627', '1', '0', '0', 'http://play.512ck.cn/zuqiu/sgb627.m3u8', '0', '2019-01-06 15:34:55', '2019-01-06 15:51:00', null);
INSERT INTO `tb_lives` VALUES ('7', '中国 VS 吉尔吉斯斯坦', '2019-01-07 19:00:00', '1362', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb1867.m3u8', '0', '2019-01-07 18:55:06', '2019-01-07 18:57:41', null);
INSERT INTO `tb_lives` VALUES ('9', '中国 VS 吉尔吉斯斯坦', '2019-01-07 19:00:00', '1867', '1', '0', '0', '#', '0', '2019-01-07 19:57:06', null, null);
INSERT INTO `tb_lives` VALUES ('10', '韩国 VS 菲律宾', '2019-01-07 21:30:00', '1892', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb1892.m3u8', '0', '2019-01-07 21:25:09', null, null);
INSERT INTO `tb_lives` VALUES ('11', '多特蒙德 VS 杜塞尔多夫', '2019-01-07 23:00:00', '1218', '1', '0', '0', 'http://play.512ck.cn/zuqiu/qhyy1899.m3u8', '0', '2019-01-07 22:55:09', '2019-01-07 22:56:34', null);
INSERT INTO `tb_lives` VALUES ('13', '伊朗 VS 也门', '2019-01-07 23:59:00', '1907', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb1907.m3u8', '0', '2019-01-07 23:40:05', null, null);
INSERT INTO `tb_lives` VALUES ('14', '西悉尼流浪者 VS 惠灵顿凤凰', '2019-01-08 16:50:00', '1290', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj2067.m3u8', '0', '2019-01-08 16:43:38', null, null);
INSERT INTO `tb_lives` VALUES ('15', '伊拉克 VS 越南', '2019-01-08 21:30:00', '2096', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb2096.m3u8', '0', '2019-01-08 20:54:12', null, null);
INSERT INTO `tb_lives` VALUES ('16', '沙特阿拉伯 VS 朝鲜', '2019-01-08 23:59:00', '2126', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb2126.m3u8', '0', '2019-01-08 23:44:47', null, null);
INSERT INTO `tb_lives` VALUES ('17', '南特 VS 蒙彼利埃', '2019-01-09 02:00:00', '4141', '1', '0', '0', 'http://play.512ck.cn/zuqiu/fj4141.m3u8', '0', '2019-01-09 01:30:00', null, null);
INSERT INTO `tb_lives` VALUES ('18', '阿德莱德联 VS 墨尔本胜利', '2019-01-09 16:50:00', '4256', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj4256.m3u8', '0', '2019-01-09 16:33:26', null, null);
INSERT INTO `tb_lives` VALUES ('19', '日本 VS 土库曼斯坦', '2019-01-09 19:00:00', '4259', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb4259.m3u8', '0', '2019-01-09 18:24:20', null, null);
INSERT INTO `tb_lives` VALUES ('20', '珀斯光荣 VS 悉尼FC', '2019-01-09 19:00:00', '4260', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj4260.m3u8', '0', '2019-01-09 18:47:57', null, null);
INSERT INTO `tb_lives` VALUES ('21', '乌兹别克 VS 阿曼', '2019-01-09 21:30:00', '4271', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb4271.m3u8', '0', '2019-01-09 21:11:22', null, null);
INSERT INTO `tb_lives` VALUES ('22', '卡塔尔 VS 黎巴嫩', '2019-01-09 23:59:00', '4300', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb4300.m3u8', '0', '2019-01-09 22:52:32', null, null);
INSERT INTO `tb_lives` VALUES ('23', '悉尼女足 VS 西悉尼流浪者女足', '2019-01-10 16:30:00', '73111', '1', '0', '0', 'http://play.512ck.cn/zuqiu/anl73111.m3u8', '0', '2019-01-10 16:34:44', null, null);
INSERT INTO `tb_lives` VALUES ('24', '巴林 VS 泰国', '2019-01-10 19:00:00', '73121', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb73121.m3u8', '0', '2019-01-10 18:35:47', null, null);
INSERT INTO `tb_lives` VALUES ('25', '约旦 VS 叙利亚', '2019-01-10 21:30:00', '73131', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb73131.m3u8', '0', '2019-01-10 20:51:26', null, null);
INSERT INTO `tb_lives` VALUES ('26', '阿联酋 VS 印度', '2019-01-10 23:59:00', '73152', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb73152.m3u8', '0', '2019-01-10 23:26:46', null, null);
INSERT INTO `tb_lives` VALUES ('27', '毕尔巴鄂竞技 VS 塞维利亚', '2019-01-11 02:30:00', '4191', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xb506833.m3u8', '0', '2019-01-11 02:01:49', '2019-01-11 02:01:50', null);
INSERT INTO `tb_lives` VALUES ('29', '毕尔巴鄂竞技 VS 塞维利亚', '2019-01-11 02:30:00', '506833', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xb506833.m3u8', '0', '2019-01-11 02:37:34', null, null);
INSERT INTO `tb_lives` VALUES ('30', '欧斯特青年队 VS 格雷米奥青年队', '2019-01-11 07:30:00', '506847', '1', '0', '0', 'http://play.512ck.cn/zuqiu/sqb506847.m3u8', '0', '2019-01-11 09:20:42', null, null);
INSERT INTO `tb_lives` VALUES ('31', '墨尔本城女足 VS 布里斯本狮吼女足', '2019-01-11 14:20:00', '506850', '1', '0', '0', 'http://play.512ck.cn/zuqiu/anl506850.m3u8', '0', '2019-01-11 14:17:37', null, null);
INSERT INTO `tb_lives` VALUES ('32', '墨尔本城 VS 布里斯班狮吼', '2019-01-11 16:50:00', '506852', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj506852.m3u8', '0', '2019-01-11 16:52:31', null, null);
INSERT INTO `tb_lives` VALUES ('33', '巴勒斯坦 VS 澳大利亚', '2019-01-11 19:00:00', '506853', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb506853.m3u8', '0', '2019-01-11 18:42:31', null, null);
INSERT INTO `tb_lives` VALUES ('34', '菲律宾 VS 中国', '2019-01-11 21:30:00', '506884', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb506884.m3u8', '0', '2019-01-11 20:10:43', '2019-01-11 20:27:49', null);
INSERT INTO `tb_lives` VALUES ('35', '吉尔吉斯斯坦 VS 韩国', '2019-01-11 23:59:00', '506910', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb506910.m3u8', '0', '2019-01-11 20:12:27', '2019-01-11 23:26:24', null);
INSERT INTO `tb_lives` VALUES ('39', '卡昂 VS 里尔', '2019-01-12 02:00:00', '882872', '1', '0', '0', 'http://play.512ck.cn/zuqiu/fj882872.m3u8', '0', '2019-01-12 00:54:57', null, null);
INSERT INTO `tb_lives` VALUES ('40', '里昂 VS 兰斯', '2019-01-12 03:45:00', '882904', '1', '0', '0', 'http://play.512ck.cn/zuqiu/fj882904.m3u8', '0', '2019-01-12 00:58:02', null, null);
INSERT INTO `tb_lives` VALUES ('41', '巴列卡诺 VS 塞尔塔', '2019-01-12 04:00:00', '882909', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj882909.m3u8', '0', '2019-01-12 01:00:35', null, null);
INSERT INTO `tb_lives` VALUES ('42', '惠灵顿凤凰 VS 中部海岸海员', '2019-01-12 14:35:00', '882925', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj882925.m3u8', '0', '2019-01-12 14:03:45', null, null);
INSERT INTO `tb_lives` VALUES ('43', '墨尔本胜利 VS 新城堡联队', '2019-01-12 16:50:00', '882931', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj882931.m3u8', '0', '2019-01-12 16:26:49', null, null);
INSERT INTO `tb_lives` VALUES ('44', '越南 VS 伊朗', '2019-01-12 19:00:00', '882958', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb882958.m3u8', '0', '2019-01-12 18:30:09', null, null);
INSERT INTO `tb_lives` VALUES ('45', '莱加内斯 VS 韦斯卡', '2019-01-12 20:00:00', '882968', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj882968.m3u8', '0', '2019-01-12 19:17:31', null, null);
INSERT INTO `tb_lives` VALUES ('46', '西汉姆联 VS 阿森纳', '2019-01-12 20:30:00', '882984', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc882984.m3u8', '0', '2019-01-12 19:49:25', null, null);
INSERT INTO `tb_lives` VALUES ('47', '也门 VS 伊拉克', '2019-01-12 21:30:00', '883043', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb883043.m3u8', '0', '2019-01-12 21:26:46', null, null);
INSERT INTO `tb_lives` VALUES ('48', '布莱顿 VS 利物浦', '2019-01-12 23:00:00', '883110', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc883110.m3u8', '0', '2019-01-12 22:36:32', null, null);
INSERT INTO `tb_lives` VALUES ('49', '莱切斯特城 VS 南安普敦', '2019-01-12 23:00:00', '883114', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc883114.m3u8', '0', '2019-01-12 22:49:21', null, null);
INSERT INTO `tb_lives` VALUES ('50', '水晶宫 VS 沃特福德', '2019-01-12 23:00:00', '883113', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc883113.m3u8', '0', '2019-01-12 22:51:05', null, null);
INSERT INTO `tb_lives` VALUES ('51', '卡迪夫城 VS 哈德斯菲尔德', '2019-01-12 23:00:00', '883112', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc883112.m3u8', '0', '2019-01-12 22:52:33', null, null);
INSERT INTO `tb_lives` VALUES ('52', '伯恩利 VS 富勒姆', '2019-01-12 23:00:00', '883111', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc883111.m3u8', '0', '2019-01-12 22:55:27', null, null);
INSERT INTO `tb_lives` VALUES ('53', '巴伦西亚 VS 瓦拉多利德', '2019-01-12 23:15:00', '883282', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj883282.m3u8', '0', '2019-01-12 23:05:43', null, null);
INSERT INTO `tb_lives` VALUES ('54', '黎巴嫩 VS 沙特阿拉伯', '2019-01-12 23:59:00', '883290', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb883290.m3u8', '0', '2019-01-12 23:30:00', null, null);
INSERT INTO `tb_lives` VALUES ('55', '亚眠 VS 巴黎圣日尔曼', '2019-01-12 23:59:00', '883289', '1', '0', '0', 'http://play.512ck.cn/zuqiu/fj883289.m3u8', '0', '2019-01-12 23:58:49', null, null);
INSERT INTO `tb_lives` VALUES ('56', '桑普多利亚 VS AC米兰', '2019-01-13 01:00:00', '1874971', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yb1874971.m3u8', '0', '2019-01-13 01:02:30', null, null);
INSERT INTO `tb_lives` VALUES ('57', '米尔沃尔 VS 布莱克本', '2019-01-13 01:30:00', '1874985', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yg1874985.m3u8', '0', '2019-01-13 01:12:09', null, null);
INSERT INTO `tb_lives` VALUES ('58', '赫罗纳 VS 阿拉维斯', '2019-01-13 01:30:00', '1874984', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj1874984.m3u8', '0', '2019-01-13 01:16:50', null, null);
INSERT INTO `tb_lives` VALUES ('59', '博洛尼亚 VS 尤文图斯', '2019-01-13 03:45:00', '1875033', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yb1875033.m3u8', '0', '2019-01-13 02:02:24', '2019-01-13 02:06:50', null);
INSERT INTO `tb_lives` VALUES ('60', '比利亚雷亚尔 VS 赫塔菲', '2019-01-13 03:45:00', '1875032', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj1875032.m3u8', '0', '2019-01-13 02:04:20', null, null);
INSERT INTO `tb_lives` VALUES ('62', '切尔西 VS 纽卡斯尔联', '2019-01-13 01:30:00', '1874983', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc1874983.m3u8', '0', '2019-01-13 02:17:16', null, null);
INSERT INTO `tb_lives` VALUES ('63', '悉尼FC VS 阿德莱德联', '2019-01-13 15:00:00', '1875069', '1', '0', '0', 'http://play.512ck.cn/zuqiu/azj1875069.m3u8', '0', '2019-01-13 14:38:28', null, null);
INSERT INTO `tb_lives` VALUES ('64', '朝鲜 VS 卡塔尔', '2019-01-13 19:00:00', '1875084', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb1875084.m3u8', '0', '2019-01-13 17:27:30', '2019-01-13 19:13:13', null);
INSERT INTO `tb_lives` VALUES ('65', '阿曼 VS 日本', '2019-01-13 21:30:00', '1875151', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yzb1875151.m3u8', '0', '2019-01-13 17:29:09', '2019-01-13 20:58:59', '899861,899862');
INSERT INTO `tb_lives` VALUES ('66', '托特纳姆热刺 VS 曼彻斯特联', '2019-01-14 00:30:00', '2969537', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc2969537.m3u8', '0', '2019-01-13 17:29:42', '2019-01-14 00:21:00', '899098,899092');
INSERT INTO `tb_lives` VALUES ('67', '埃弗顿 VS 伯恩茅斯', '2019-01-13 22:15:00', '1875198', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yc1875198.m3u8', '0', '2019-01-13 17:31:39', '2019-01-13 21:55:53', '899096,899387');
INSERT INTO `tb_lives` VALUES ('68', '马德里竞技 VS 莱万特', '2019-01-13 19:00:00', '1255', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj1875082.m3u8', '0', '2019-01-13 18:27:43', null, null);
INSERT INTO `tb_lives` VALUES ('70', '马德里竞技 VS 莱万特', '2019-01-13 19:00:00', '1875082', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj1875082.m3u8', '0', '2019-01-13 19:03:02', null, null);
INSERT INTO `tb_lives` VALUES ('76', '南特 VS 雷恩', '2019-01-13 22:00:00', '1875170', '1', '0', '0', 'http://play.512ck.cn/zuqiu/fj1875170.m3u8', '0', '2019-01-13 21:21:52', null, '899312,899300');
INSERT INTO `tb_lives` VALUES ('77', '都灵 VS 佛罗伦萨', '2019-01-13 22:00:00', '1875173', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yb1875173.m3u8', '0', '2019-01-13 21:34:57', null, '899585,899236');
INSERT INTO `tb_lives` VALUES ('79', '毕尔巴鄂竞技 VS 塞维利亚', '2019-01-13 23:15:00', '1875215', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj1875215.m3u8', '0', '2019-01-13 23:19:37', null, '899156,899150');
INSERT INTO `tb_lives` VALUES ('80', '图卢兹 VS 斯特拉斯堡', '2019-01-13 23:59:00', '1875224', '1', '0', '0', 'http://play.512ck.cn/zuqiu/fj1875224.m3u8', '0', '2019-01-13 23:21:05', null, '899273,899309');
INSERT INTO `tb_lives` VALUES ('83', '巴塞罗那 VS 埃瓦尔', '2019-01-14 01:30:00', '2969560', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj2969560.m3u8', '0', '2019-01-14 00:23:29', null, '899148,899193');
INSERT INTO `tb_lives` VALUES ('84', '皇家贝蒂斯 VS 皇家马德里', '2019-01-14 03:45:00', '2969578', '1', '0', '0', 'http://play.512ck.cn/zuqiu/xj2969578.m3u8', '0', '2019-01-14 00:24:39', null, '899160,899146');
INSERT INTO `tb_lives` VALUES ('85', '国际米兰 VS 贝内文托', '2019-01-14 01:00:00', '2969551', '1', '0', '0', 'http://play.512ck.cn/zuqiu/yb2969551.m3u8', '0', '2019-01-14 00:29:39', null, '899213,900310');
INSERT INTO `tb_lives` VALUES ('86', '阿联酋 VS 泰国', '2019-01-14 23:59:00', '2969625', '0', '0', '0', '#', '0', '2019-01-14 14:08:11', null, null);
INSERT INTO `tb_lives` VALUES ('87', '曼彻斯特城 VS 狼队', '2019-01-15 04:00:00', '3712740', '0', '0', '0', '#', '0', '2019-01-14 14:08:32', null, null);
INSERT INTO `tb_lives` VALUES ('88', '印度 VS 巴林', '2019-01-14 23:59:00', '2969624', '0', '0', '0', '#', '0', '2019-01-14 14:13:37', null, null);
