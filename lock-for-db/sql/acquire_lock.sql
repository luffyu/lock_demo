CREATE TABLE `acquire_lock` (
  `id` int(11) unsigned NOT NULL COMMENT '自增id',
  `method_name` varchar(64) NOT NULL COMMENT '方法名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_method` (`method_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
