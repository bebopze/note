
SET FOREIGN_KEY_CHECKS=0;


CREATE TABLE `storage` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品ID',
  `product_name` varchar(32) DEFAULT NULL COMMENT '商品name',
  `inventory` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '库存数量',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态 (1:正常；2-锁定；)',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁版本号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存表';


CREATE TABLE `account` (
  `id` bigint(20) unsigned NOT NULL COMMENT '账户ID',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `amount` decimal(12,2) unsigned NOT NULL COMMENT '账户金额',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁版本号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户表';


CREATE TABLE `tb_order` (
  `id` bigint(20) unsigned NOT NULL COMMENT '订单ID',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品ID',
  `product_name` varchar(64) DEFAULT NULL COMMENT '商品name',
  `product_price` decimal(12,2) unsigned NOT NULL COMMENT '商品单价',
  `product_num` int(11) unsigned NOT NULL COMMENT '商品数量',
  `product_total_price` decimal(12,2) unsigned NOT NULL COMMENT '商品总价',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '订单状态 (1-待付款;2-已付款;3-主动弃单;4-支付超时弃单;)',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁版本号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';


-- ----------------------------
-- Table structure for undo_log
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
