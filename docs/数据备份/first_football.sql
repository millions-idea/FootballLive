/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50640
Source Host           : 193.112.151.148:3306
Source Database       : football

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-12-13 14:08:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_advertisings
-- ----------------------------
DROP TABLE IF EXISTS `tb_advertisings`;
CREATE TABLE `tb_advertisings` (
  `ad_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '类型(0=图片广告,1=播放器广告)',
  `source_url` varchar(255) NOT NULL COMMENT '源地址',
  `target_url` varchar(255) NOT NULL COMMENT '跳转链接',
  PRIMARY KEY (`ad_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告信息表';

-- ----------------------------
-- Records of tb_advertisings
-- ----------------------------

-- ----------------------------
-- Table structure for tb_chat_rooms
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_rooms`;
CREATE TABLE `tb_chat_rooms` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `live_id` int(11) NOT NULL COMMENT '直播间id',
  `chat_room_id` varchar(32) DEFAULT NULL COMMENT '聊天室 id',
  `frequency` double NOT NULL COMMENT '发言频率',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天室信息表';

-- ----------------------------
-- Records of tb_chat_rooms
-- ----------------------------

-- ----------------------------
-- Table structure for tb_chat_room_historys
-- ----------------------------
DROP TABLE IF EXISTS `tb_chat_room_historys`;
CREATE TABLE `tb_chat_room_historys` (
  `history_id` int(11) NOT NULL,
  `chat_room_id` int(11) DEFAULT NULL COMMENT '聊天室id',
  `content` text COMMENT '聊天内容',
  `add_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `account` varchar(255) DEFAULT NULL COMMENT '云信账户',
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天历史信息表';

-- ----------------------------
-- Records of tb_chat_room_historys
-- ----------------------------

-- ----------------------------
-- Table structure for tb_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary`;
CREATE TABLE `tb_dictionary` (
  `dictionary_id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) DEFAULT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

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
INSERT INTO `tb_dictionary` VALUES ('10', 'banner.image1', '#');
INSERT INTO `tb_dictionary` VALUES ('11', 'banner.image2', '#');
INSERT INTO `tb_dictionary` VALUES ('12', 'banner.image3', '#');
INSERT INTO `tb_dictionary` VALUES ('13', 'bootstrap.image1', '#');
INSERT INTO `tb_dictionary` VALUES ('14', 'bootstrap.image2', '#');
INSERT INTO `tb_dictionary` VALUES ('15', 'bootstrap.image3', '#');
INSERT INTO `tb_dictionary` VALUES ('16', 'home.text.ad', '#');
INSERT INTO `tb_dictionary` VALUES ('17', 'contact', '#');

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
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='赛事信息表';

-- ----------------------------
-- Records of tb_games
-- ----------------------------

-- ----------------------------
-- Table structure for tb_informations
-- ----------------------------
DROP TABLE IF EXISTS `tb_informations`;
CREATE TABLE `tb_informations` (
  `isr_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL COMMENT '赛事id',
  `live_id` int(11) NOT NULL COMMENT '直播间id',
  `content` text NOT NULL COMMENT '情报内容',
  PRIMARY KEY (`isr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='情报信息表';

-- ----------------------------
-- Records of tb_informations
-- ----------------------------

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
  PRIMARY KEY (`live_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播间信息表';

-- ----------------------------
-- Records of tb_lives
-- ----------------------------

-- ----------------------------
-- Table structure for tb_live_categorys
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_categorys`;
CREATE TABLE `tb_live_categorys` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(32) NOT NULL COMMENT '直播分类名称',
  `category_background_image_url` varchar(255) NOT NULL COMMENT '直播分类背景图',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播分类信息表';

-- ----------------------------
-- Records of tb_live_categorys
-- ----------------------------

-- ----------------------------
-- Table structure for tb_live_collects
-- ----------------------------
DROP TABLE IF EXISTS `tb_live_collects`;
CREATE TABLE `tb_live_collects` (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `live_id` int(11) DEFAULT NULL COMMENT '直播间id',
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播间收藏表';

-- ----------------------------
-- Records of tb_live_collects
-- ----------------------------

-- ----------------------------
-- Table structure for tb_permission_groups
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission_groups`;
CREATE TABLE `tb_permission_groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_group_name` varchar(32) NOT NULL COMMENT '权限分组名称',
  `permission_group_code` varchar(32) NOT NULL COMMENT '权限分组代码',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限分组表';

-- ----------------------------
-- Records of tb_permission_groups
-- ----------------------------

-- ----------------------------
-- Table structure for tb_permission_maps
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission_maps`;
CREATE TABLE `tb_permission_maps` (
  `map_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_group_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限分组id',
  `access_code` varchar(255) DEFAULT NULL COMMENT '访问代码(以某个控制器的名称命名, 如果此列不为空, 那么后面的URL则不起作用, 粗粒度控制)',
  `access_url` varchar(255) DEFAULT NULL COMMENT '访问URL(详细地址)',
  PRIMARY KEY (`map_id`),
  UNIQUE KEY `uq_item` (`map_id`,`permission_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限路径映射表';

-- ----------------------------
-- Records of tb_permission_maps
-- ----------------------------

-- ----------------------------
-- Table structure for tb_permission_relations
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission_relations`;
CREATE TABLE `tb_permission_relations` (
  `relation_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_group_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限分组id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限关系表';

-- ----------------------------
-- Records of tb_permission_relations
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播消息表';

-- ----------------------------
-- Records of tb_publish_messages
-- ----------------------------

-- ----------------------------
-- Table structure for tb_publish_message_markeds
-- ----------------------------
DROP TABLE IF EXISTS `tb_publish_message_markeds`;
CREATE TABLE `tb_publish_message_markeds` (
  `marked_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_id` int(11) NOT NULL COMMENT '消息id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '阅读回执(0=未阅读,1=已阅读)',
  `add_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `edit_date` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`marked_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推送消息阅读签收表';

-- ----------------------------
-- Records of tb_publish_message_markeds
-- ----------------------------

-- ----------------------------
-- Table structure for tb_schedules
-- ----------------------------
DROP TABLE IF EXISTS `tb_schedules`;
CREATE TABLE `tb_schedules` (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL COMMENT '赛事id',
  `team_id` int(11) NOT NULL COMMENT '球队id',
  `game_date` datetime NOT NULL COMMENT '开始比赛时间',
  `game_duration` double NOT NULL COMMENT '比赛时长',
  `status` int(11) NOT NULL COMMENT '比赛状态(0=未开始, 1=正在直播, 2=已结束)',
  PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='赛程信息表';

-- ----------------------------
-- Records of tb_schedules
-- ----------------------------

-- ----------------------------
-- Table structure for tb_system_logs
-- ----------------------------
DROP TABLE IF EXISTS `tb_system_logs`;
CREATE TABLE `tb_system_logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `section` varchar(255) NOT NULL COMMENT '节点名称',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `add_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of tb_system_logs
-- ----------------------------

-- ----------------------------
-- Table structure for tb_teams
-- ----------------------------
DROP TABLE IF EXISTS `tb_teams`;
CREATE TABLE `tb_teams` (
  `team_id` int(11) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(32) NOT NULL COMMENT '球队名称',
  `team_icon` varchar(255) NOT NULL COMMENT '球队logo',
  `game_id` int(11) DEFAULT NULL COMMENT '隶属赛事',
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='球队信息表';

-- ----------------------------
-- Records of tb_teams
-- ----------------------------

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(15) NOT NULL COMMENT '用户编码(字母开头+数字,最短5位,最长15位)',
  `phone` varchar(11) NOT NULL COMMENT '手机号(用户名)',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(18) DEFAULT NULL COMMENT '昵称(默认=用户编码)',
  `signature` varchar(60) DEFAULT NULL COMMENT '签名',
  `add_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `add_imei` varchar(18) DEFAULT NULL COMMENT '注册设备码',
  `edit_date` datetime DEFAULT NULL COMMENT '最后一次编辑时间',
  `cloud_accid` varchar(32) DEFAULT NULL COMMENT '网易云通信ID(只允许字母、数字、半角下划线_、@、半角点以及半角-组成，不区分大小写，会统一小写处理)',
  `cloud_token` varchar(128) DEFAULT NULL COMMENT '网易云通信ID可以指定登录token值，最大长度128字符，\r\n并更新，如果未指定，会自动生成token，并在\r\n创建成功后返回',
  `edit_count` int(11) DEFAULT '1' COMMENT '剩余修改次数',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除(黑名单)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES ('12', 'im299392', '15253133391', '6098e249f7d964396e6b47a02cadeb24', null, 'im299392', null, null, null, null, null, null, '1', null, null);
INSERT INTO `tb_users` VALUES ('13', 'mm1234569999', '17854141391', 'b0c8296f94396edde33a00ed69c890a4', null, '小辣椒7', '遗憾有很多，不差你一个', null, null, '2018-12-12 15:51:45', '17854141391', '84b59b518936c0fab943afccf96cb2d4', '0', null, null);
INSERT INTO `tb_users` VALUES ('14', 'im321728', '17854141392', 'a7824c095eea2ce9cc3b15991a9a5c26', null, '汤姆猫', '我要捉老鼠', null, null, '2018-12-11 20:52:49', '17854141392', '442f9e4932941c100fc2657524931795', '1', null, null);
INSERT INTO `tb_users` VALUES ('15', 'im227776', '17854141393', '9c17d431d43fc71c59587a3672762797', null, 'im227776', null, null, null, '2018-12-11 20:54:46', '17854141393', '4998bcd5bc7d505e1daa6b92fdb5851d', '1', null, null);
INSERT INTO `tb_users` VALUES ('16', 'im409280', '17854141394', '34002be2c5df02e57c66a99731b5a386', null, 'im409280', null, null, null, null, '17854141394', 'fccf42db877ed8a85c62b5c08deecde8', '1', null, null);
INSERT INTO `tb_users` VALUES ('17', 'im431488', '17854141395', '69e4695920258f8b119ca2ec106653d5', null, 'im431488', null, null, null, null, '17854141395', 'ec65708ee05e9eb7b5951b40583ba044', '1', null, null);
INSERT INTO `tb_users` VALUES ('18', 'im406272', '17854141396', 'fcf22f0c015b45406bbc6fdf5dc982cd', null, 'im406272', null, null, null, null, '17854141396', 'be236f0e99ca2ccb40030433f3328377', '1', null, null);
INSERT INTO `tb_users` VALUES ('19', 'im387968', '17854141397', 'ff8c756fec57366526a4df3875792671', null, 'im387968', null, null, null, null, '17854141397', '8a2f0412721229508780c28d6deb7ad4', '1', null, null);
INSERT INTO `tb_users` VALUES ('20', 'im731584', '17854141398', 'c4073b40c22890d05451bd9ab1c07db0', null, 'im731584', null, null, null, null, '17854141398', 'fe638ce777fbe48ec451e9c400e2ae98', '1', null, null);
INSERT INTO `tb_users` VALUES ('21', 'im186432', '17854141310', '39e633bc313bf4954e26f2d39b42fb0a', null, 'im186432', null, null, null, null, '17854141310', '2e6057b1926bd9948abd95a2c01986da', '1', null, null);
INSERT INTO `tb_users` VALUES ('22', 'im749440', '17854141311', 'ace9c867a674c78bb23c13544f8e11ad', null, 'im749440', null, null, null, null, '17854141311', 'ba55aada8d9cef3674b3f8f074b01ad8', '1', null, null);
INSERT INTO `tb_users` VALUES ('23', 'im617728', '17854141312', '579374d412f82912cd0c80634b56c4b9', null, 'im617728', null, null, null, null, '17854141312', '45ae4a580f44f2c868e18c25d17ad27e', '1', null, null);
INSERT INTO `tb_users` VALUES ('24', 'dongfeng123456', '17854141313', '129e7c7decfc1beb508066163d3abecb', 'images/head-default.png', '难忘今宵', '难忘今宵 ^_^', '2018-12-07 04:00:56', null, '2018-12-07 04:00:56', '17854141313', '1036af72825fa43408d8e0e4086a9f52', '1', null, null);
INSERT INTO `tb_users` VALUES ('25', 'im888960', '17854141314', '26f283915662d31d8992c7e6fd30bc5d', null, 'im888960', null, '2018-12-11 20:31:07', null, null, '17854141314', '47b66b94d0462dbb4f47d02f353e7dfd', '1', null, null);
INSERT INTO `tb_users` VALUES ('27', 'dongfeng123456', '17854141315', 'a057bc7a2701136a47ec852c5d00e3fc', 'images/head-default.png', '难忘今宵', '难忘今宵 ^_^', '2018-12-11 20:35:33', null, null, '17854141315', '6cdd6d16ee6da3a6be43de685e1de155', '1', null, null);
INSERT INTO `tb_users` VALUES ('28', 'im10752', '17854141316', '96b195b9cb6a3c0c194c59bbedc6cd2e', null, '测试', null, '2018-12-11 20:37:42', null, '2018-12-11 20:41:23', '17854141316', '0d6bca8a5b69534bc8bea7ca8b10b52e', '1', null, null);
INSERT INTO `tb_users` VALUES ('29', 'im680768', '17679115321', '18b9bfa243e8fe813be7ee044765988b', null, 'im680768', null, '2018-12-12 00:23:15', null, null, '17679115321', '4acebb538e496b6bf0b0cacb4cb0855b', '1', null, null);

-- ----------------------------
-- Table structure for tb_user_feedback
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_feedback`;
CREATE TABLE `tb_user_feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_avatar` varchar(255) NOT NULL COMMENT '用户头像',
  `user_nick` varchar(255) NOT NULL COMMENT '用户昵称',
  `user_phone` varchar(255) NOT NULL COMMENT '用户手机号',
  `add_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户反馈信息表';

-- ----------------------------
-- Records of tb_user_feedback
-- ----------------------------
