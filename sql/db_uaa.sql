-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `uaa_privilege`;
CREATE TABLE `uaa_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL COMMENT '权限代码',
  `name` varchar(60) DEFAULT NULL COMMENT '权限名称',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQE_PRIVILEGE_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';
ALTER TABLE `uaa_privilege` ADD UNIQUE UQE_UAA_PRIVILEGE_CODE(`code`);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('1', 'SYS_C', '系统-增', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('2', 'SYS_U', '系统-改', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('3', 'SYS_R', '系统-查', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('4', 'SYS_D', '系统-删', NULL, NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `uaa_resource`;
CREATE TABLE `uaa_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(1) DEFAULT NULL COMMENT '资源类型',
  `name` varchar(60) DEFAULT NULL COMMENT '名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单的url',
  `root` bit(1) DEFAULT NULL COMMENT '是否是根菜单',
  `leaf` bit(1) DEFAULT NULL COMMENT '是不是叶子菜单',
  `priority` int(2) DEFAULT NULL COMMENT '次序',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `permit_type` varchar(1) DEFAULT NULL COMMENT '可访问类型',
  `ms_client_id` varchar(20) DEFAULT NULL COMMENT '资源所有者',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='目录';

INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('1', 'M', '系统管理', '/admin/system-admin-users/index', '', '\0', '99', NULL, 'A', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001', 'M', '用户列表', '/admin/system-admin-users/index', '\0', '', '1', '1', 'A', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10002', 'M', '角色列表', '/admin/system-roles/index', '\0', '', '2', '1', 'A', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003', 'M', '资源列表', '/admin/system-resources/index', '\0', '', '3', '1', 'A', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000001', 'H', '管理员登录', '/adminUser/login', '\0', '\0', '0', NULL, 'N', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000002', 'H', '管理员登出', '/adminUser/logout', '\0', '\0', '0', NULL, 'N', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000003', 'H', '用户-列表', '/adminUser/page', '\0', '\0', '0', NULL, 'A', 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000004', 'H', '用户-新增', '/adminUser/add', '\0', '\0', '0', NULL, 'A', 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000005', 'H', '用户-编辑', '/adminUser/edit', '\0', '\0', '0', NULL, 'A', 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000006', 'H', '角色-列表', '/role/page', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000007', 'H', '角色-新增', '/role/add', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000008', 'H', '角色-编辑', '/role/edit', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000009', 'H', '角色-删除', '/role/delete', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000010', 'H', '资源-树结构', '/resource/tree', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000011', 'H', '资源-列表', '/resource/page', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000012', 'H', '资源-新增', '/resource/add', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000013', 'H', '资源-编辑', '/resource/edit', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `type`, `name`, `url`, `root`, `leaf`, `priority`, `parent_id`, `permit_type`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('3000014', 'H', '资源-权限编辑', '/resource/privilege/edit', '\0', '\0', '0', NULL, 'A', 'uaa', NULL, NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for sys_resource_privilege
-- ----------------------------
DROP TABLE IF EXISTS `uaa_resource_privilege`;
CREATE TABLE `uaa_resource_privilege` (
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  `privilege_id` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`resource_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('1', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10002', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000003', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000004', '1');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000005', '2');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000006', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000007', '1');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000008', '2');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000009', '4');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000010', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000011', '3');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000012', '1');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000013', '2');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('3000014', '2');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `uaa_role`;
CREATE TABLE `uaa_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(20) DEFAULT NULL COMMENT '角色代码',
  `name` varchar(60) DEFAULT NULL COMMENT '角色名称',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';
ALTER TABLE `uaa_role` ADD UNIQUE UQE_UAA_ROLE_CODE(`code`);

INSERT INTO `uaa_role` (`id`, `code`, `name`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, 'SYSTEM_ADMIN', '系统管理员', NULL, NULL, NULL, NULL, 0);


-- ----------------------------
-- Table structure for sys_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `uaa_role_privilege`;
CREATE TABLE `uaa_role_privilege` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `privilege_id` bigint(20) NOT NULL COMMENT '授权id',
  PRIMARY KEY (`role_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限';

INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '1');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '2');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '3');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '4');


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `uaa_user`;
CREATE TABLE `uaa_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` VARCHAR(60) NOT NULL COMMENT 'uid',
  `enabled` bit(1) DEFAULT NULL COMMENT '是否有效',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';
ALTER TABLE `uaa_user` ADD UNIQUE UQE_UAA_USER_UID(`uid`);

INSERT INTO `uaa_user` (`id`, `uid`, `enabled`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, '1806480EE000000', '', NULL, NULL, 1, '2018-5-17 22:58:22', 8);


-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `uaa_user_role`;
CREATE TABLE `uaa_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户的id',
  `role_id` bigint(20) NOT NULL COMMENT '角色的id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';
INSERT INTO `uaa_user_role` (`user_id`, `role_id`) VALUES (1, 1);

-- ----------------------------
-- Table structure for sys_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `uaa_user_auth`;
CREATE TABLE `uaa_user_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户的id',
  `type` varchar(10) DEFAULT NULL COMMENT '账号类型',
  `username` varchar(60) DEFAULT NULL COMMENT '用户名',
  `password` varchar(80) DEFAULT NULL COMMENT '密码',
  `verified` bit(1) DEFAULT NULL COMMENT '是否已验证',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录账号';
ALTER TABLE `uaa_user_auth` ADD UNIQUE UQE_UAA_USER_AUTH_USERNAME_TYPE(`username`, `type`);
ALTER TABLE `uaa_user_auth` ADD INDEX IDX_UAA_USER_AUTH_USER_ID(`user_id`);

-- ----------------------------
-- #密码123123
-- ----------------------------
INSERT INTO `uaa_user_auth` (`id`, `user_id`, `type`, `username`, `password`, `verified`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, 1, 'NORMAL', 'admin', '{bcrypt}$2a$10$cmZL/4WOW0Lqr1UmCDxa7O0aCX8PpuL79ZbrTX9oBxd3sKwtXhLW.', '', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for revinfo
-- ----------------------------
DROP TABLE IF EXISTS `revinfo`;
create table revinfo (
  rev int(11) not null auto_increment COMMENT '全局版本号',
  revtstmp bigint(20) DEFAULT NULL COMMENT '更新时间',
  primary key (rev)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局版本控制';

-- ----------------------------
-- Table structure for sys_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `uaa_oauth2_client`;
CREATE TABLE `uaa_oauth2_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(30) DEFAULT NULL COMMENT '客户端id',
  `client_secret` varchar(60) DEFAULT NULL COMMENT '客户端密码',
  `resource_ids` varchar(120) DEFAULT NULL COMMENT '当前客户端或其授权的用户可访问的其他资源id',
  `scope` varchar(30) DEFAULT NULL COMMENT '授权作用域',
  `auto_approve` varchar(30) DEFAULT NULL COMMENT '自动授权作用域',
  `authorized_grant_types` varchar(15) DEFAULT NULL COMMENT '授权方式，包括password, code等5种',
  `authorities` varchar(255) DEFAULT NULL COMMENT '权限',
  `additional_information` varchar(120) DEFAULT NULL COMMENT '扩展信息json格式',
  `web_server_redirect_uri` varchar(300) DEFAULT NULL COMMENT '重定向URI',
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'token有效时间',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT 'refresh token有效时间',

  `create_time` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
ALTER TABLE `uaa_oauth2_client` ADD UNIQUE UQE_UAA_OAUTH2_CLIENT_CLIENT_ID(`client_id`);

INSERT INTO `uaa_oauth2_client` (`id`, `client_id`, `client_secret`, `resource_ids`, `scope`, `auto_approve`, `authorized_grant_types`, `authorities`, `additional_information`, `web_server_redirect_uri`, `access_token_validity`, `refresh_token_validity`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, 'uaa', NULL, NULL, 'all', NULL, 'password', NULL, NULL, NULL, 7776000, 15552000, NULL, NULL, NULL, NULL, 0);

