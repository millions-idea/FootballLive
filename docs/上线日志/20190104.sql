BEGIN;
ALTER TABLE `tb_informations` ADD `forecast_result` varchar(32) DEFAULT NULL COMMENT '预测结果';
ALTER TABLE `tb_informations` ADD `forecast_grade` varchar(32) DEFAULT NULL COMMENT '预测比分';
ALTER TABLE `tb_informations` ADD `forecast_team_id` int(11) DEFAULT NULL COMMENT '预测胜利球队';

ALTER TABLE `tb_teams` ADD `edit_date` datetime DEFAULT NULL COMMENT '更新时间 ';
ALTER TABLE `tb_teams` ADD `cloud_id` int(11) DEFAULT NULL COMMENT '云平台';
ALTER TABLE `tb_teams` ADD UNIQUE KEY `uq_c_model` (`cloud_id`);

ALTER TABLE `tb_lives` ADD `edit_date` datetime DEFAULT NULL COMMENT '更新时间 ';

ALTER TABLE `tb_games` ADD `cloud_id`  int(11) DEFAULT '0';
ALTER TABLE `tb_games` ADD `edit_date` datetime DEFAULT NULL;
ALTER TABLE `tb_games` ADD UNIQUE KEY `uq_c_model` (`cloud_id`);

ALTER TABLE `tb_schedules` ADD `cloud_id` int(11) DEFAULT '0';
ALTER TABLE `tb_schedules` ADD `edit_date` datetime DEFAULT NULL;
ALTER TABLE `tb_schedules` ADD `master_red_chess` int(11) DEFAULT '0' COMMENT '主队红牌';
ALTER TABLE `tb_schedules` ADD `master_yellow_chess` int(11) DEFAULT '0' COMMENT '主队黄牌';
ALTER TABLE `tb_schedules` ADD `master_corner_kick` int(11) DEFAULT '0' COMMENT '主队角球';
ALTER TABLE `tb_schedules` ADD `target_red_chess` int(11) DEFAULT '0' COMMENT '客队红牌';
ALTER TABLE `tb_schedules` ADD `target_yellow_chess` int(11) DEFAULT '0' COMMENT '客队黄牌';
ALTER TABLE `tb_schedules` ADD `target_corner_kick` int(11) DEFAULT '0' COMMENT '客队角球';
ALTER TABLE `tb_schedules` ADD `nami_schedule_id` int(11) DEFAULT '0';
ALTER TABLE `tb_schedules` ADD UNIQUE KEY `uq_c_model` (`cloud_id`,`nami_schedule_id`);

GRANT ALL PRIVILEGES ON *.* TO 'root'@'172.17.198.107' IDENTIFIED BY '1qQp53iydbJ709Ue' WITH GRANT OPTION;
FLUSH PRIVILEGES;


GRANT ALL PRIVILEGES ON *.* TO 'root'@'172.17.198.108' IDENTIFIED BY '1qQp53iydbJ709Ue' WITH GRANT OPTION;
FLUSH PRIVILEGES;
COMMIT;