BEGIN;
ALTER TABLE `tb_lives` ADD `team_id_list` varchar(32) DEFAULT NULL COMMENT '球队id集合';
COMMIT;