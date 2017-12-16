CREATE TABLE `t_comment` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
`update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
`delete_flag` int(1) NULL DEFAULT 0,
`cid` bigint(20) NULL DEFAULT NULL,
`text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`uid` bigint(20) NULL DEFAULT NULL,
`pid` bigint(20) NULL DEFAULT NULL,
PRIMARY KEY (`id`) 
)
ENGINE = InnoDB
AUTO_INCREMENT = 109
AVG_ROW_LENGTH = 0
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
KEY_BLOCK_SIZE = 0
MAX_ROWS = 0
MIN_ROWS = 0
ROW_FORMAT = Compact;

CREATE TABLE `t_user` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
`update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
`delete_flag` int(1) NULL DEFAULT 0,
`uid` bigint(20) NOT NULL DEFAULT 0,
`name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`avatar_id` int(11) NULL DEFAULT 0,
`last_comment` datetime NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最后一次评论的时间',
PRIMARY KEY (`id`, `uid`) 
)
ENGINE = InnoDB
AUTO_INCREMENT = 14
AVG_ROW_LENGTH = 0
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
KEY_BLOCK_SIZE = 0
MAX_ROWS = 0
MIN_ROWS = 0
ROW_FORMAT = Compact;

