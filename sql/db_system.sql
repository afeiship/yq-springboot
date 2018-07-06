-- ----------------------------
-- Table structure for sys_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_user`;
CREATE TABLE `sys_admin_user` (
  `id` bigint(20) NOT NULL,
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(30) DEFAULT NULL COMMENT '电话',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户';
INSERT INTO `sys_admin_user` (`id`, `email`, `nickname`, `phone`, `version`) VALUES (1, NULL , '系统管理员', NULL, 0);

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) DEFAULT NULL COMMENT '参数类型',
  `code` varchar(20) DEFAULT NULL COMMENT '代码',
  `name` varchar(20) DEFAULT NULL COMMENT '展示名称',
  `parent_code` varchar(20) DEFAULT NULL COMMENT '上级代码',
  `value` varchar(10) DEFAULT NULL COMMENT '值',
  `is_default` bit(1) DEFAULT NULL COMMENT '是否默认选中',
  `extend_01` varchar(100) DEFAULT NULL COMMENT '扩展1',
  `extend_02` varchar(100) DEFAULT NULL COMMENT '扩展2',
  `priority` int(11) DEFAULT NULL COMMENT '默认排序',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数';
ALTER TABLE `sys_param` ADD UNIQUE UQE_SYS_PARAM_CODE(`code`);
ALTER TABLE `sys_param` ADD INDEX IDX_SYS_PARAM_PARENT_CODE(`parent_code`);

