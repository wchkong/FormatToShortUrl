

DROP TABLE IF EXISTS `short_urls`;
CREATE TABLE `short_urls`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `short_url` varchar(1024) DEFAULT NULL COMMENT '短链',
  `long_url` varchar(1024) DEFAULT NULL COMMENT '长链',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `count` int(11) NULL DEFAULT 0 COMMENT '访问次数',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4;



DROP TABLE IF EXISTS `url_record`;
CREATE TABLE `url_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `short_url` int(11) DEFAULT NULL COMMENT '短链',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP(0) '访问时间',
  `ip` varchar(15) DEFAULT NULL '访问IP',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4;

