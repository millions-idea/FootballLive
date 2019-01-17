BEGIN;
ALTER TABLE `tb_lives` ADD UNIQUE KEY `uq_model` (`schedule_id`);
COMMIT;