DROP TABLE IF EXISTS `t_test`;

CREATE TABLE `t_test` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `create_time` DATETIME   NULL     DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME   NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` INT(1)     NOT NULL DEFAULT 0,
  `name`        VARCHAR(255),
  `age`         INT(3)              DEFAULT 0,
  PRIMARY KEY (`id`)
);
