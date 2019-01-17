/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50640
Source Host           : 193.112.151.148:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-12-22 12:47:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_users`;
CREATE TABLE `tb_admin_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0=正常,1=禁用,2=删除)',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型(1=管理员,2=超级管理员)',
  `add_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `edit_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '摘要',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_admin` (`user_id`,`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='管理员用户表';

-- ----------------------------
-- Records of tb_admin_users
-- ----------------------------
INSERT INTO `tb_admin_users` VALUES ('1', '15253133391', 'afcee72fc58f055cff4201a436e541f4', '0', '2', '2018-12-17 14:49:38', '2018-12-17 14:49:38', null);
INSERT INTO `tb_admin_users` VALUES ('2', '17854141391', '0a435b885263c0096c83c835c91cd74f', '0', '1', '2018-12-17 15:12:49', '2018-12-17 15:12:49', null);
INSERT INTO `tb_admin_users` VALUES ('10', '12345678909', '776302b05d6df5f0fe60b49af3389ff8', '0', '1', '2018-12-17 21:02:48', '2018-12-17 21:02:45', null);
INSERT INTO `tb_admin_users` VALUES ('11', '17686891672', 'ed5d167766d5378557a3d09493c23a8d', '0', '2', '2018-12-17 21:13:28', '2018-12-17 21:13:25', null);
INSERT INTO `tb_admin_users` VALUES ('12', '17686891673', '361c73489f6a2d79e65e985bb5ec637b', '0', '1', '2018-12-17 21:27:26', '2018-12-17 21:27:23', null);
INSERT INTO `tb_admin_users` VALUES ('13', '18765505992', '5c2945b74b87769e1f111228ea1d0bc6', '0', '1', '2018-12-18 00:46:04', '2018-12-18 00:46:01', null);

-- ----------------------------
-- Table structure for tb_advertisings
-- ----------------------------
DROP TABLE IF EXISTS `tb_advertisings`;
CREATE TABLE `tb_advertisings` (
  `ad_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '类型(0=图片广告,1=播放器广告)',
  `source_url` varchar(255) NOT NULL COMMENT '源地址',
  `target_url` varchar(255) NOT NULL COMMENT '跳转链接',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`ad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='广告信息表';

-- ----------------------------
-- Records of tb_advertisings
-- ----------------------------
INSERT INTO `tb_advertisings` VALUES ('1', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '');
INSERT INTO `tb_advertisings` VALUES ('2', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('3', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('4', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('5', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('6', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('7', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('8', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('9', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('10', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('11', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('12', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('13', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('14', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('15', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('16', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('17', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('18', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('19', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('20', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('21', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('22', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('23', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('24', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('25', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('26', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('27', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('28', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('29', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('30', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('31', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');
INSERT INTO `tb_advertisings` VALUES ('32', '0', 'http://vjs.zencdn.net/v/oceans.mp4', 'https://www.baidu.com/', '\0');

-- ----------------------------
-- Table structure for tb_chat_rooms
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_rooms`;
CREATE TABLE `tb_chat_rooms` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `live_id` int(11) NOT NULL COMMENT '直播间id',
  `chat_room_id` varchar(32) DEFAULT NULL COMMENT '聊天室 id',
  `frequency` double NOT NULL COMMENT '发言频率',
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `uq_room` (`live_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='聊天室信息表';

-- ----------------------------
-- Records of tb_chat_rooms
-- ----------------------------
INSERT INTO `tb_chat_rooms` VALUES ('5', '17', '1531054732', '10');
INSERT INTO `tb_chat_rooms` VALUES ('6', '18', '1531116384', '10');
INSERT INTO `tb_chat_rooms` VALUES ('7', '19', '1531783986', '10');

-- ----------------------------
-- Table structure for tb_chat_room_user_relations
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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='聊天室用户关系表';

-- ----------------------------
-- Records of tb_chat_room_user_relations
-- ----------------------------
INSERT INTO `tb_chat_room_user_relations` VALUES ('13', '17', '5', '1', '0', null);
INSERT INTO `tb_chat_room_user_relations` VALUES ('15', '17', '5', '12', '0', '2018-12-21 00:34:07');
INSERT INTO `tb_chat_room_user_relations` VALUES ('24', '13', '5', '13', '0', '2018-12-21 17:54:26');
INSERT INTO `tb_chat_room_user_relations` VALUES ('32', '32', '5', '32', '0', '2018-12-21 01:21:07');
INSERT INTO `tb_chat_room_user_relations` VALUES ('39', '39', '5', '39', '0', '2018-12-21 02:29:01');
INSERT INTO `tb_chat_room_user_relations` VALUES ('51', '18', '6', '1', '0', null);
INSERT INTO `tb_chat_room_user_relations` VALUES ('52', '39', '6', '39', '0', null);
INSERT INTO `tb_chat_room_user_relations` VALUES ('54', '40', '5', '40', '0', null);
INSERT INTO `tb_chat_room_user_relations` VALUES ('61', '41', '5', '41', '0', '2018-12-21 20:17:24');
INSERT INTO `tb_chat_room_user_relations` VALUES ('69', '19', '7', '1', '0', null);
INSERT INTO `tb_chat_room_user_relations` VALUES ('70', '42', '5', '42', '0', null);

-- ----------------------------
-- Table structure for tb_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary`;
CREATE TABLE `tb_dictionary` (
  `dictionary_id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) DEFAULT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of tb_dictionary
-- ----------------------------
INSERT INTO `tb_dictionary` VALUES ('1', 'finance.pays.channel.internal', '站内交易');
INSERT INTO `tb_dictionary` VALUES ('2', 'finance.pays.channel.alipay', '支付宝');
INSERT INTO `tb_dictionary` VALUES ('3', 'finance.pays.product.sms', '短信');
INSERT INTO `tb_dictionary` VALUES ('4', 'finance.pays.product.currency', '通用货币');
INSERT INTO `tb_dictionary` VALUES ('5', 'finance.pays.product.roomCard', '房卡');
INSERT INTO `tb_dictionary` VALUES ('6', 'finance.pays.trade.recharge', '充值');
INSERT INTO `tb_dictionary` VALUES ('7', 'finance.pays.trade.withdraw', '提现');
INSERT INTO `tb_dictionary` VALUES ('8', 'finance.pays.trade.deduction', '扣费');
INSERT INTO `tb_dictionary` VALUES ('9', 'finance.pays.trade.consume', '消费');
INSERT INTO `tb_dictionary` VALUES ('10', 'banner.image1', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/c6c27743-f752-4572-8684-9a5563782cc2.png');
INSERT INTO `tb_dictionary` VALUES ('11', 'banner.image2', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/5b1e2692-863c-4948-83f0-fd0a15aae80c.png');
INSERT INTO `tb_dictionary` VALUES ('12', 'banner.image3', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/a4221d9d-7283-42af-9e97-1caca1b24274.png');
INSERT INTO `tb_dictionary` VALUES ('13', 'bootstrap.image1', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/aa73fa80-9bcd-43b5-8421-4a30c2a41eb1.png');
INSERT INTO `tb_dictionary` VALUES ('14', 'bootstrap.image2', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/8ee35a68-6b42-4a29-9514-3df9f00a4260.png');
INSERT INTO `tb_dictionary` VALUES ('15', 'bootstrap.image3', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/a67d9771-508a-47e6-88d2-7340f770244f.png');
INSERT INTO `tb_dictionary` VALUES ('16', 'home.text.ad', '奇偶发方法都是京东山东杰卡斯');
INSERT INTO `tb_dictionary` VALUES ('17', 'contact', '<p><img src=\"https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/76390387-52da-439d-86c1-9fbe148040d4.png\" style=\"max-width:100%;\">撒大声地<br></p>');
INSERT INTO `tb_dictionary` VALUES ('18', 'webapp.image.ad.url', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/f73d77d2-1b10-4a1b-a05b-7b1c9b8dff22.png');
INSERT INTO `tb_dictionary` VALUES ('19', 'webapp.image.ad.target.url', '这是测试的地址');
INSERT INTO `tb_dictionary` VALUES ('20', 'version', '1.0.0');
INSERT INTO `tb_dictionary` VALUES ('21', 'iosDownload', '6');
INSERT INTO `tb_dictionary` VALUES ('22', 'androidDownload', '8');
INSERT INTO `tb_dictionary` VALUES ('23', 'banner.image1.targetUrl', 'http://www.baidu.com/');
INSERT INTO `tb_dictionary` VALUES ('24', 'banner.image2.targetUrl', 'http://www.taobao.com/');
INSERT INTO `tb_dictionary` VALUES ('25', 'banner.image3.targetUrl', 'http://www.tencent.cn/');

-- ----------------------------
-- Table structure for tb_exceptions
-- ----------------------------
DROP TABLE IF EXISTS `tb_exceptions`;
CREATE TABLE `tb_exceptions` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `body` text NOT NULL COMMENT '日志内容',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上报时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常日志表';

-- ----------------------------
-- Records of tb_exceptions
-- ----------------------------

-- ----------------------------
-- Table structure for tb_games
-- ----------------------------
DROP TABLE IF EXISTS `tb_games`;
CREATE TABLE `tb_games` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_name` varchar(64) NOT NULL COMMENT '赛事名称',
  `game_icon` varchar(255) NOT NULL COMMENT '赛事logo',
  `category_id` int(11) NOT NULL COMMENT '直播分类',
  `is_delete` int(11) DEFAULT '0' COMMENT '删除',
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='赛事信息表';

-- ----------------------------
-- Records of tb_games
-- ----------------------------
INSERT INTO `tb_games` VALUES ('1', '中超赛事', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', '1', '0');
INSERT INTO `tb_games` VALUES ('2', '测试', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', '2', '0');
INSERT INTO `tb_games` VALUES ('3', '东部决赛', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', '2', '0');
INSERT INTO `tb_games` VALUES ('4', '快拳', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/88445e90-1d89-4d7c-bb6e-50222bc3837e.png', '2', '1');
INSERT INTO `tb_games` VALUES ('5', '山东', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/cd08708e-f783-4d82-972f-f9117801c89d.png', '2', '0');
INSERT INTO `tb_games` VALUES ('6', '湖人', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/648e3d92-3ad4-4611-a6c0-3bcc90209d69.png', '2', '0');
INSERT INTO `tb_games` VALUES ('7', '环境', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/e3bb2e84-8c9f-4b09-8ca0-0766ad05482a.png', '2', '0');
INSERT INTO `tb_games` VALUES ('8', '湖人', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/d9246c2e-5d9b-433d-8f0c-05e41e165a3b.png', '2', '0');
INSERT INTO `tb_games` VALUES ('10', '海商法', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/f1f28237-6cbe-4e8e-b318-f238497732c6.png', '2', '0');
INSERT INTO `tb_games` VALUES ('14', '环境第三', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/1ae7a7f7-fcb1-49e5-b0fb-30bf519162c5.png', '1', '0');

-- ----------------------------
-- Table structure for tb_informations
-- ----------------------------
DROP TABLE IF EXISTS `tb_informations`;
CREATE TABLE `tb_informations` (
  `isr_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL COMMENT '赛事id',
  `live_id` int(11) NOT NULL COMMENT '直播间id',
  `content` text NOT NULL COMMENT '情报内容',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除',
  `add_date` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`isr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='情报信息表';

-- ----------------------------
-- Records of tb_informations
-- ----------------------------
INSERT INTO `tb_informations` VALUES ('1', '1', '17', '这是测试的情报内容啊啊啊', '\0', '2018-12-18 20:52:53');
INSERT INTO `tb_informations` VALUES ('2', '2', '18', '测试啊啊啊啊啊', '\0', '2018-12-18 20:52:55');

-- ----------------------------
-- Table structure for tb_lives
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
  PRIMARY KEY (`live_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='直播间信息表';

-- ----------------------------
-- Records of tb_lives
-- ----------------------------
INSERT INTO `tb_lives` VALUES ('17', '专用直播间', '2018-12-20 23:53:44', '1', '0', '0', '2', 'http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4', '1', '2018-12-20 23:55:16');
INSERT INTO `tb_lives` VALUES ('18', '第二个直播间', '2018-12-21 01:51:50', '1', '1', '0', '0', 'http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4', '1', '2018-12-21 01:52:02');
INSERT INTO `tb_lives` VALUES ('19', '测试赛', '2018-12-21 20:44:22', '15', '1', '0', '0', '###', '1', '2018-12-21 20:44:28');

-- ----------------------------
-- Table structure for tb_live_categorys
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_categorys`;
CREATE TABLE `tb_live_categorys` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(32) NOT NULL COMMENT '直播分类名称',
  `category_background_image_url` varchar(255) NOT NULL COMMENT '直播分类背景图',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_delete` int(11) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='直播分类信息表';

-- ----------------------------
-- Records of tb_live_categorys
-- ----------------------------
INSERT INTO `tb_live_categorys` VALUES ('1', '足球赛事', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/QQ%E6%88%AA%E5%9B%BE20181215130436.png', '0', '0');
INSERT INTO `tb_live_categorys` VALUES ('2', '篮球赛事', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/QQ%E6%88%AA%E5%9B%BE20181215141027.png', '1', '0');
INSERT INTO `tb_live_categorys` VALUES ('3', '斯塔克', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/QQ%E6%88%AA%E5%9B%BE20181215141027.png', '2', '0');
INSERT INTO `tb_live_categorys` VALUES ('4', '乒乓球', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/QQ%E6%88%AA%E5%9B%BE20181215141027.png', '3', '0');
INSERT INTO `tb_live_categorys` VALUES ('5', '羽毛球', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/QQ%E6%88%AA%E5%9B%BE20181215141027.png', '6', '0');
INSERT INTO `tb_live_categorys` VALUES ('6', '网球', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/QQ%E6%88%AA%E5%9B%BE20181215141027.png', '5', '0');
INSERT INTO `tb_live_categorys` VALUES ('7', '电竞', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/QQ%E6%88%AA%E5%9B%BE20181215141027.png', '4', '0');
INSERT INTO `tb_live_categorys` VALUES ('8', '联盟', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/50adebe3-8ab5-47d0-add4-508a6979ead5.png', '6', '0');

-- ----------------------------
-- Table structure for tb_live_collects
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_collects`;
CREATE TABLE `tb_live_collects` (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `live_id` int(11) DEFAULT NULL COMMENT '直播间id',
  `is_cancel` int(11) DEFAULT '0' COMMENT '是否取消',
  `add_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
  `edit_date` datetime DEFAULT NULL COMMENT '取消关注时间',
  PRIMARY KEY (`collect_id`),
  UNIQUE KEY `uq_user` (`user_id`,`live_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='直播间收藏表';

-- ----------------------------
-- Records of tb_live_collects
-- ----------------------------
INSERT INTO `tb_live_collects` VALUES ('22', '13', '1', '0', '2018-12-20 07:06:29', null);
INSERT INTO `tb_live_collects` VALUES ('23', '13', '2', '0', '2018-12-20 21:48:48', '2018-12-20 21:48:50');
INSERT INTO `tb_live_collects` VALUES ('25', '13', '15', '0', '2018-12-20 23:23:07', null);
INSERT INTO `tb_live_collects` VALUES ('26', '40', '17', '0', '2018-12-21 02:30:56', null);
INSERT INTO `tb_live_collects` VALUES ('27', '41', '17', '0', '2018-12-21 20:17:00', null);

-- ----------------------------
-- Table structure for tb_live_historys
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_historys`;
CREATE TABLE `tb_live_historys` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `live_id` int(11) NOT NULL COMMENT '直播id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `active_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '活动时间',
  PRIMARY KEY (`history_id`),
  UNIQUE KEY `uq_model` (`live_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COMMENT='观看历史表';

-- ----------------------------
-- Records of tb_live_historys
-- ----------------------------
INSERT INTO `tb_live_historys` VALUES ('8', '3', '13', '2018-12-20 23:25:27');
INSERT INTO `tb_live_historys` VALUES ('9', '14', '13', '2018-12-20 18:41:22');
INSERT INTO `tb_live_historys` VALUES ('10', '5', '13', '2018-12-20 23:24:37');
INSERT INTO `tb_live_historys` VALUES ('12', '1', '13', '2018-12-20 23:25:14');
INSERT INTO `tb_live_historys` VALUES ('19', '2', '13', '2018-12-20 23:25:11');
INSERT INTO `tb_live_historys` VALUES ('31', '16', '13', '2018-12-20 23:23:36');
INSERT INTO `tb_live_historys` VALUES ('33', '8', '13', '2018-12-20 21:37:07');
INSERT INTO `tb_live_historys` VALUES ('35', '7', '13', '2018-12-20 23:24:44');
INSERT INTO `tb_live_historys` VALUES ('37', '15', '13', '2018-12-20 23:24:55');
INSERT INTO `tb_live_historys` VALUES ('45', '3', '12', '2018-12-20 21:53:15');
INSERT INTO `tb_live_historys` VALUES ('46', '2', '12', '2018-12-20 21:53:21');
INSERT INTO `tb_live_historys` VALUES ('47', '1', '12', '2018-12-20 21:53:24');
INSERT INTO `tb_live_historys` VALUES ('50', '5', '12', '2018-12-20 21:53:27');
INSERT INTO `tb_live_historys` VALUES ('52', '6', '12', '2018-12-20 21:53:31');
INSERT INTO `tb_live_historys` VALUES ('54', '7', '12', '2018-12-20 21:53:34');
INSERT INTO `tb_live_historys` VALUES ('55', '6', '13', '2018-12-20 23:24:40');
INSERT INTO `tb_live_historys` VALUES ('56', '10', '12', '2018-12-20 21:53:37');
INSERT INTO `tb_live_historys` VALUES ('57', '16', '12', '2018-12-20 21:53:40');
INSERT INTO `tb_live_historys` VALUES ('59', '15', '12', '2018-12-20 23:22:10');
INSERT INTO `tb_live_historys` VALUES ('60', '10', '13', '2018-12-20 23:24:51');
INSERT INTO `tb_live_historys` VALUES ('61', '9', '13', '2018-12-20 23:24:47');
INSERT INTO `tb_live_historys` VALUES ('96', '17', '12', '2018-12-21 00:34:07');
INSERT INTO `tb_live_historys` VALUES ('107', '17', '13', '2018-12-21 17:54:28');
INSERT INTO `tb_live_historys` VALUES ('115', '17', '32', '2018-12-21 01:21:07');
INSERT INTO `tb_live_historys` VALUES ('122', '17', '39', '2018-12-21 02:29:01');
INSERT INTO `tb_live_historys` VALUES ('134', '18', '39', '2018-12-21 01:52:31');
INSERT INTO `tb_live_historys` VALUES ('136', '17', '40', '2018-12-21 02:28:32');
INSERT INTO `tb_live_historys` VALUES ('143', '17', '41', '2018-12-21 20:17:24');
INSERT INTO `tb_live_historys` VALUES ('151', '17', '42', '2018-12-21 21:30:29');

-- ----------------------------
-- Table structure for tb_permission_groups
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission_groups`;
CREATE TABLE `tb_permission_groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_group_name` varchar(32) NOT NULL COMMENT '权限分组名称',
  `permission_group_code` varchar(32) NOT NULL COMMENT '权限分组代码',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='权限分组表';

-- ----------------------------
-- Records of tb_permission_groups
-- ----------------------------
INSERT INTO `tb_permission_groups` VALUES ('1', '超级管理员', 'ROLE_ADMIN');
INSERT INTO `tb_permission_groups` VALUES ('2', '管理员', 'ROLE_STAFF');

-- ----------------------------
-- Table structure for tb_permission_maps
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission_maps`;
CREATE TABLE `tb_permission_maps` (
  `map_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_group_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限分组id',
  `permission_group_code` varchar(32) NOT NULL,
  `access_code` varchar(255) DEFAULT NULL COMMENT '访问代码(以某个控制器的名称命名, 如果此列不为空, 那么后面的URL则不起作用, 粗粒度控制)',
  `access_url` varchar(255) DEFAULT NULL COMMENT '访问URL(详细地址)',
  PRIMARY KEY (`map_id`),
  UNIQUE KEY `uq_item` (`map_id`,`permission_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='权限路径映射表';

-- ----------------------------
-- Records of tb_permission_maps
-- ----------------------------
INSERT INTO `tb_permission_maps` VALUES ('1', '1', 'ROLE_ADMIN', null, 'management/index');
INSERT INTO `tb_permission_maps` VALUES ('2', '2', 'ROLE_STAFF', null, ' ');

-- ----------------------------
-- Table structure for tb_permission_relations
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission_relations`;
CREATE TABLE `tb_permission_relations` (
  `relation_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_group_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限分组id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户权限关系表';

-- ----------------------------
-- Records of tb_permission_relations
-- ----------------------------
INSERT INTO `tb_permission_relations` VALUES ('1', '1', '1');
INSERT INTO `tb_permission_relations` VALUES ('2', '2', '2');
INSERT INTO `tb_permission_relations` VALUES ('3', '1', '10');
INSERT INTO `tb_permission_relations` VALUES ('4', '1', '11');
INSERT INTO `tb_permission_relations` VALUES ('5', '1', '12');
INSERT INTO `tb_permission_relations` VALUES ('6', '1', '13');

-- ----------------------------
-- Table structure for tb_publish_messages
-- ----------------------------
DROP TABLE IF EXISTS `tb_publish_messages`;
CREATE TABLE `tb_publish_messages` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL COMMENT '消息',
  `add_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型(0=跳转链接,1=文字内容)',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='直播消息表';

-- ----------------------------
-- Records of tb_publish_messages
-- ----------------------------
INSERT INTO `tb_publish_messages` VALUES ('4', 'fdsfsdfas', '2018-12-19 01:53:18', '0');
INSERT INTO `tb_publish_messages` VALUES ('6', '测试啊是', '2018-12-19 01:55:34', '0');
INSERT INTO `tb_publish_messages` VALUES ('7', '发生地方发生', '2018-12-19 01:59:59', '0');
INSERT INTO `tb_publish_messages` VALUES ('8', '测试发送内容', '2018-12-20 01:31:20', '0');
INSERT INTO `tb_publish_messages` VALUES ('9', '1312312', '2018-12-20 01:31:47', '0');
INSERT INTO `tb_publish_messages` VALUES ('10', 'sdasadsasad', '2018-12-20 01:32:50', '0');
INSERT INTO `tb_publish_messages` VALUES ('11', '测试内容', '2018-12-20 01:34:44', '0');
INSERT INTO `tb_publish_messages` VALUES ('12', 'wqesadasd', '2018-12-20 01:36:06', '0');
INSERT INTO `tb_publish_messages` VALUES ('13', 'http://www.baidu.com/', '2018-12-20 01:40:48', '1');
INSERT INTO `tb_publish_messages` VALUES ('14', 'http://www.baidu.com/', '2018-12-20 01:41:05', '1');
INSERT INTO `tb_publish_messages` VALUES ('15', 'eeeee', '2018-12-20 01:56:46', '0');
INSERT INTO `tb_publish_messages` VALUES ('16', 'test', '2018-12-20 02:03:09', '0');
INSERT INTO `tb_publish_messages` VALUES ('17', '测试推送啊啊啊啊啊啊啊', '2018-12-20 06:36:08', '0');
INSERT INTO `tb_publish_messages` VALUES ('18', '测试啊啊啊啊啊啊啊啊啊啊啊', '2018-12-20 06:52:44', '0');
INSERT INTO `tb_publish_messages` VALUES ('19', '测试推送啊', '2018-12-20 06:57:28', '0');

-- ----------------------------
-- Table structure for tb_publish_message_relations
-- ----------------------------
DROP TABLE IF EXISTS `tb_publish_message_relations`;
CREATE TABLE `tb_publish_message_relations` (
  `relation_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_id` int(11) NOT NULL COMMENT '消息id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `is_read` int(11) DEFAULT '0' COMMENT '是否已阅读',
  `read_date` datetime DEFAULT NULL COMMENT '阅读时间',
  `add_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='推送消息用户关系表';

-- ----------------------------
-- Records of tb_publish_message_relations
-- ----------------------------
INSERT INTO `tb_publish_message_relations` VALUES ('1', '4', '12', '1', '2018-12-21 00:02:54', '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('2', '4', '13', '1', '2018-12-20 02:07:30', '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('3', '4', '14', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('4', '4', '15', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('5', '4', '16', null, null, '2018-12-19 02:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('6', '4', '17', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('7', '4', '18', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('8', '4', '19', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('9', '4', '20', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('10', '4', '21', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('11', '4', '22', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('12', '4', '23', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('13', '4', '24', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('14', '4', '25', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('15', '4', '27', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('16', '4', '28', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('17', '4', '29', null, null, '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('18', '6', '12', '1', '2018-12-21 00:02:56', '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('19', '7', '13', '1', '2018-12-20 02:22:29', '2018-12-19 06:26:40');
INSERT INTO `tb_publish_message_relations` VALUES ('20', '8', '13', '1', '2018-12-20 02:34:53', '2018-12-12 02:32:52');
INSERT INTO `tb_publish_message_relations` VALUES ('21', '9', '13', '1', '2018-12-20 02:35:11', '2018-12-28 02:32:57');
INSERT INTO `tb_publish_message_relations` VALUES ('22', '10', '13', '1', '2018-12-20 02:35:02', '2018-12-18 02:33:01');
INSERT INTO `tb_publish_message_relations` VALUES ('23', '11', '13', '1', '2018-12-20 02:35:00', '2018-12-18 02:31:42');
INSERT INTO `tb_publish_message_relations` VALUES ('24', '12', '13', '1', '2018-12-20 04:31:24', '2018-12-04 02:33:05');
INSERT INTO `tb_publish_message_relations` VALUES ('25', '13', '13', '1', '2018-12-20 02:35:06', '2018-12-23 02:33:08');
INSERT INTO `tb_publish_message_relations` VALUES ('26', '14', '13', '1', '2018-12-20 02:27:09', '2018-12-31 02:33:12');
INSERT INTO `tb_publish_message_relations` VALUES ('27', '15', '13', '1', '2018-12-20 02:27:06', '2018-12-25 02:33:16');
INSERT INTO `tb_publish_message_relations` VALUES ('28', '16', '13', '1', '2018-12-20 02:35:04', '2018-12-20 02:33:20');
INSERT INTO `tb_publish_message_relations` VALUES ('29', '17', '12', '1', '2018-12-20 23:15:27', null);
INSERT INTO `tb_publish_message_relations` VALUES ('30', '17', '13', '1', '2018-12-20 09:16:07', null);
INSERT INTO `tb_publish_message_relations` VALUES ('31', '17', '14', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('32', '17', '15', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('33', '17', '16', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('34', '17', '17', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('35', '17', '18', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('36', '17', '19', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('37', '17', '20', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('38', '17', '21', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('39', '17', '22', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('40', '17', '23', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('41', '17', '24', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('42', '17', '25', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('43', '17', '27', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('44', '17', '28', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('45', '17', '29', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('46', '18', '12', '1', '2018-12-21 00:02:50', null);
INSERT INTO `tb_publish_message_relations` VALUES ('47', '18', '13', '1', '2018-12-20 09:16:05', null);
INSERT INTO `tb_publish_message_relations` VALUES ('48', '18', '14', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('49', '18', '15', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('50', '18', '16', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('51', '18', '17', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('52', '18', '18', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('53', '18', '19', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('54', '18', '20', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('55', '18', '21', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('56', '18', '22', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('57', '18', '23', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('58', '18', '24', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('59', '18', '25', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('60', '18', '27', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('61', '18', '28', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('62', '18', '29', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('63', '19', '12', '1', '2018-12-21 00:02:52', null);
INSERT INTO `tb_publish_message_relations` VALUES ('64', '19', '13', '1', '2018-12-20 21:50:46', null);
INSERT INTO `tb_publish_message_relations` VALUES ('65', '19', '14', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('66', '19', '15', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('67', '19', '16', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('68', '19', '17', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('69', '19', '18', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('70', '19', '19', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('71', '19', '20', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('72', '19', '21', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('73', '19', '22', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('74', '19', '23', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('75', '19', '24', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('76', '19', '25', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('77', '19', '27', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('78', '19', '28', null, null, null);
INSERT INTO `tb_publish_message_relations` VALUES ('79', '19', '29', null, null, null);

-- ----------------------------
-- Table structure for tb_schedules
-- ----------------------------
DROP TABLE IF EXISTS `tb_schedules`;
CREATE TABLE `tb_schedules` (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL COMMENT '赛事id',
  `team_id` varchar(32) NOT NULL COMMENT '球队id(支持多个)',
  `game_date` datetime NOT NULL COMMENT '开始比赛时间',
  `game_duration` double NOT NULL COMMENT '比赛时长',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '比赛状态(0=未开始, 1=正在直播, 2=已结束)',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '删除(0=正常 1=删除)',
  `schedule_result` varchar(32) DEFAULT NULL COMMENT '比赛结果(让胜\\让平\\让负...)',
  `schedule_grade` varchar(32) DEFAULT NULL COMMENT '比赛成绩(-1.5/2或2-1这种格式)',
  `win_team_id` int(11) DEFAULT NULL COMMENT '胜利方球队id',
  PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='赛程信息表';

-- ----------------------------
-- Records of tb_schedules
-- ----------------------------
INSERT INTO `tb_schedules` VALUES ('1', '1', '1,2', '2018-12-18 13:13:00', '120', '1', '0', '让胜', '2-1', '4');
INSERT INTO `tb_schedules` VALUES ('2', '2', '3,4', '2018-12-18 19:00:03', '120', '1', '0', null, '-1.5/2', '3');
INSERT INTO `tb_schedules` VALUES ('15', '3', '2,1', '2018-12-21 20:47:25', '120', '1', '1', '让胜', '2-1', '3');
INSERT INTO `tb_schedules` VALUES ('16', '3', '3,2', '2018-12-21 21:08:00', '120', '1', '0', null, null, null);
INSERT INTO `tb_schedules` VALUES ('17', '3', '2,1', '2018-12-22 00:00:00', '120', '0', '0', null, null, null);

-- ----------------------------
-- Table structure for tb_system_logs
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_logs`;
CREATE TABLE `tb_system_logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '操作人',
  `section` varchar(255) NOT NULL COMMENT '节点名称',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `add_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of tb_system_logs
-- ----------------------------
INSERT INTO `tb_system_logs` VALUES ('1', '0', '测试', '测试', '2018-12-18 22:13:26');
INSERT INTO `tb_system_logs` VALUES ('2', '1', 'User', '管理员登陆', '2018-12-21 22:50:10');
INSERT INTO `tb_system_logs` VALUES ('3', '1', 'User', '管理员登陆', '2018-12-21 22:50:18');
INSERT INTO `tb_system_logs` VALUES ('4', '1', 'SystemLog', '查看系统日志', '2018-12-21 22:50:22');
INSERT INTO `tb_system_logs` VALUES ('5', '1', 'SystemLog', '查看系统日志', '2018-12-21 22:52:45');
INSERT INTO `tb_system_logs` VALUES ('6', '1', 'User', '管理员登陆', '2018-12-21 22:53:17');
INSERT INTO `tb_system_logs` VALUES ('7', '1', 'User', '管理员登陆', '2018-12-21 22:54:49');
INSERT INTO `tb_system_logs` VALUES ('8', '1', 'User', '管理员登陆', '2018-12-21 23:25:50');
INSERT INTO `tb_system_logs` VALUES ('9', '1', 'SystemLog', '查看系统日志', '2018-12-21 23:25:53');
INSERT INTO `tb_system_logs` VALUES ('10', '1', 'SystemLog', '查看系统日志', '2018-12-21 23:26:56');
INSERT INTO `tb_system_logs` VALUES ('11', '1', 'Competition', '查看赛事列表', '2018-12-21 23:27:15');
INSERT INTO `tb_system_logs` VALUES ('12', '1', 'SystemLog', '查看系统日志', '2018-12-21 23:27:19');
INSERT INTO `tb_system_logs` VALUES ('13', '1', 'SystemLog', '查看系统日志', '2018-12-21 23:27:28');
INSERT INTO `tb_system_logs` VALUES ('14', '1', 'User', '管理员登陆', '2018-12-21 23:27:42');
INSERT INTO `tb_system_logs` VALUES ('15', '1', 'SystemLog', '查看系统日志', '2018-12-21 23:27:44');

-- ----------------------------
-- Table structure for tb_teams
-- ----------------------------
DROP TABLE IF EXISTS `tb_teams`;
CREATE TABLE `tb_teams` (
  `team_id` int(11) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(32) NOT NULL COMMENT '球队名称',
  `team_icon` varchar(255) NOT NULL COMMENT '球队logo',
  `game_id` int(11) DEFAULT NULL COMMENT '隶属赛事',
  `is_delete` int(11) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='球队信息表';

-- ----------------------------
-- Records of tb_teams
-- ----------------------------
INSERT INTO `tb_teams` VALUES ('1', '山东鲁能', 'images/luneng.png', '1', '0');
INSERT INTO `tb_teams` VALUES ('2', '北京国安', 'images/guoan.png', '1', '0');
INSERT INTO `tb_teams` VALUES ('3', '广州恒大', 'images/luneng.png', '2', '0');
INSERT INTO `tb_teams` VALUES ('4', '不知道的', 'images/luneng.png', '2', '0');
INSERT INTO `tb_teams` VALUES ('5', '土耳其', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/98c174cb-76db-4fc5-a779-b07aab0a59f9.png', '10', '0');
INSERT INTO `tb_teams` VALUES ('6', '电商', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/upload/ccaf2514-807d-422b-98c7-c272a84f7222.png', '2', '1');

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL COMMENT '手机号(用户名)',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(18) DEFAULT NULL COMMENT '昵称(默认=用户编码)',
  `signature` varchar(60) DEFAULT NULL COMMENT '签名',
  `add_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `edit_date` datetime DEFAULT NULL COMMENT '最后一次编辑时间',
  `cloud_accid` varchar(32) DEFAULT NULL COMMENT '网易云通信ID(只允许字母、数字、半角下划线_、@、半角点以及半角-组成，不区分大小写，会统一小写处理)',
  `cloud_token` varchar(128) DEFAULT NULL COMMENT '网易云通信ID可以指定登录token值，最大长度128字符，\r\n并更新，如果未指定，会自动生成token，并在\r\n创建成功后返回',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除(黑名单)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ip` varchar(15) DEFAULT NULL COMMENT '注册ip',
  `type` int(11) DEFAULT '0' COMMENT '用户类型(0 用户 1 管理员2超级管理员)',
  `black_time` datetime DEFAULT NULL COMMENT '拉黑时间',
  `black_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES ('1', '10000000000', '#', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', '管理员', null, '2018-12-20 23:59:00', '2018-12-12 03:54:04', null, null, null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('12', '15253133391', '6098e249f7d964396e6b47a02cadeb24', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im299392', null, '2018-12-12 03:54:04', '2018-12-21 00:34:48', null, '39ed0a2ca6d9c02afd4e19ff4ffb2be3', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('13', '17854141391', 'b0c8296f94396edde33a00ed69c890a4', 'https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/face/8598bb77-b5f8-4c0c-b800-24d4265e653b.png', '小辣椒', '遗憾有很多，不差你一个', '2018-12-12 03:54:04', '2018-12-21 01:18:36', '17854141391', '5f5e1b71803533c70158667459e8c83c', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('14', '17854141392', 'a7824c095eea2ce9cc3b15991a9a5c26', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', '汤姆猫', '我要捉老鼠', '2018-12-12 03:54:04', '2018-12-11 20:52:49', '17854141392', '442f9e4932941c100fc2657524931795', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('15', '17854141393', '9c17d431d43fc71c59587a3672762797', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im227776', null, '2018-12-12 03:54:04', '2018-12-11 20:54:46', '17854141393', '4998bcd5bc7d505e1daa6b92fdb5851d', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('16', '17854141394', '34002be2c5df02e57c66a99731b5a386', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im409280', null, '2018-12-12 03:54:04', '2018-12-11 03:53:24', '17854141394', 'fccf42db877ed8a85c62b5c08deecde8', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('17', '17854141395', '69e4695920258f8b119ca2ec106653d5', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im431488', null, '2018-12-12 03:54:04', '2018-12-20 03:53:28', '17854141395', 'ec65708ee05e9eb7b5951b40583ba044', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('18', '17854141396', 'fcf22f0c015b45406bbc6fdf5dc982cd', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im406272', null, '2018-12-12 03:54:04', '2018-12-12 03:53:31', '17854141396', 'be236f0e99ca2ccb40030433f3328377', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('19', '17854141397', 'ff8c756fec57366526a4df3875792671', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im387968', null, '2018-12-12 03:54:04', '2018-12-04 03:53:36', '17854141397', '8a2f0412721229508780c28d6deb7ad4', '1', null, null, '0', '2018-12-21 05:58:16', '   ');
INSERT INTO `tb_users` VALUES ('20', '17854141398', 'c4073b40c22890d05451bd9ab1c07db0', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im731584', null, '2018-12-12 03:54:04', '2018-12-09 03:53:40', '17854141398', 'fe638ce777fbe48ec451e9c400e2ae98', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('21', '17854141310', '39e633bc313bf4954e26f2d39b42fb0a', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im186432', null, '2018-12-12 03:54:04', '2018-12-03 03:53:45', '17854141310', '2e6057b1926bd9948abd95a2c01986da', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('22', '17854141311', 'ace9c867a674c78bb23c13544f8e11ad', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im749440', null, '2018-12-12 03:54:04', '2018-12-17 03:53:49', '17854141311', 'ba55aada8d9cef3674b3f8f074b01ad8', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('23', '17854141312', '579374d412f82912cd0c80634b56c4b9', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im617728', null, '2018-12-12 03:54:04', '2018-12-12 03:53:53', '17854141312', '45ae4a580f44f2c868e18c25d17ad27e', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('24', '17854141313', '129e7c7decfc1beb508066163d3abecb', 'images/head-default.png', '难忘今宵', '难忘今宵 ^_^', '2018-12-07 04:00:56', '2018-12-07 04:00:56', '17854141313', '1036af72825fa43408d8e0e4086a9f52', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('25', '17854141314', '26f283915662d31d8992c7e6fd30bc5d', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im888960', null, '2018-12-11 20:31:07', '2018-12-04 03:53:57', '17854141314', '47b66b94d0462dbb4f47d02f353e7dfd', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('27', '17854141315', 'a057bc7a2701136a47ec852c5d00e3fc', 'images/head-default.png', '难忘今宵', '难忘今宵 ^_^', '2018-12-11 20:35:33', '2018-12-21 03:54:01', '17854141315', '6cdd6d16ee6da3a6be43de685e1de155', null, null, null, '0', null, null);
INSERT INTO `tb_users` VALUES ('28', '17854141316', '96b195b9cb6a3c0c194c59bbedc6cd2e', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', '测试', null, '2018-12-11 20:37:42', '2018-12-11 20:41:23', '17854141316', '0d6bca8a5b69534bc8bea7ca8b10b52e', '0', null, null, '0', '2018-12-21 04:35:13', '');
INSERT INTO `tb_users` VALUES ('29', '17679115321', '18b9bfa243e8fe813be7ee044765988b', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im680768', null, '2018-12-12 00:23:15', '2018-12-12 03:54:04', '17679115321', '4acebb538e496b6bf0b0cacb4cb0855b', '1', '嘿嘿嘿', null, '0', '2018-12-20 05:44:02', '水水水水是');
INSERT INTO `tb_users` VALUES ('32', '17686891672', 'b804eaf238e05302e91ff502ab959cc5', null, '176******72', null, '2018-12-21 00:55:14', '2018-12-21 00:58:03', '17686891672', '59401ca790be809af6aa42e4499f0af9', null, null, '223.104.187.92', '0', null, null);
INSERT INTO `tb_users` VALUES ('36', '13505488591', '0759db38206fce86b0fe02e00b3d93b6', null, '135******91', null, '2018-12-21 01:26:21', '2018-12-12 03:54:04', '13505488591', 'fc93e950bf3bf9e81bd4721eee189094', null, null, '223.104.187.92', '0', null, null);
INSERT INTO `tb_users` VALUES ('37', '13505488592', 'a1a1818052f49a24e0bb95499c0afe9a', null, '135******92', null, '2018-12-21 01:28:47', '2018-12-12 03:54:04', '13505488592', 'f8cdc2c596a0c0339d0b6f1c22f12334', null, null, '223.104.187.92', '0', null, null);
INSERT INTO `tb_users` VALUES ('39', '13505488593', '88f5a414c18e38a067ef9690db646c2b', null, '135******93', null, '2018-12-21 01:32:52', '2018-12-21 01:36:15', '13505488593', '5cb048c2f1b8beacdcde8d22544a527b', null, null, '223.104.187.92', '0', null, null);
INSERT INTO `tb_users` VALUES ('40', '13505488594', '736fd7aec7c617763a2e311bf245f35e', null, '135******94', null, '2018-12-21 02:28:11', '2018-12-12 03:54:04', '13505488594', 'ba6ab777b7a4d9b224c4f90f1112ee84', null, null, '119.190.111.93', '0', null, null);
INSERT INTO `tb_users` VALUES ('41', '17891926782', 'a6685f5ab2559f2379345e0b35b24b00', null, 'Leo', null, '2018-12-21 17:54:38', null, '17891926782', 'fab48b726df0530566eb0f21e09f37ba', null, null, '103.113.62.10', '0', null, null);
INSERT INTO `tb_users` VALUES ('42', '13165211125', '8808edffadc263e5945f873583f403f8', null, '131******25', null, '2018-12-21 21:30:15', null, '13165211125', '66001e3d71bc38848b9c133123f0ae29', null, null, '119.185.205.232', '0', null, null);

-- ----------------------------
-- Table structure for tb_user_feedback
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_feedback`;
CREATE TABLE `tb_user_feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `user_nick` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `content` varchar(255) DEFAULT NULL COMMENT '反馈内容',
  `user_phone` varchar(255) DEFAULT NULL COMMENT '用户手机号',
  `add_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户反馈信息表';

-- ----------------------------
-- Records of tb_user_feedback
-- ----------------------------
INSERT INTO `tb_user_feedback` VALUES ('1', '12', 'https://youxun-1251694474.cos.ap-beijing.myqcloud.com/face/QQ%E6%88%AA%E5%9B%BE20181215130436.png', 'im299392', '测试反馈', '15253133391', '2018-12-20 05:25:01');
INSERT INTO `tb_user_feedback` VALUES ('2', '1', null, null, 'test', null, '2018-12-20 07:59:16');
INSERT INTO `tb_user_feedback` VALUES ('3', '12', '#', '#', 'CCCCCC', '15253133391', '2018-12-20 22:32:24');
INSERT INTO `tb_user_feedback` VALUES ('4', '32', null, '176******72', null, '17686891672', '2018-12-21 01:02:19');
INSERT INTO `tb_user_feedback` VALUES ('5', '32', null, '176******72', null, '17686891672', '2018-12-21 01:02:22');
INSERT INTO `tb_user_feedback` VALUES ('6', '32', null, '176******72', null, '17686891672', '2018-12-21 01:04:53');
