
-- 数据库初始化表

CREATE TABLE `user`(
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(30) NOT NULL unique COMMENT '用户账号',
  `password` varchar(30) NOT NULL DEFAULT '' COMMENT '用户密码',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名字',
  `nickname` varchar(30) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `sex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0女 1 男',
  `birthday` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户生日',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '地址',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `personalized_signature` varchar(255)  NOT NULL DEFAULT '' COMMENT '个性签名',
  `head_path` varchar(64) NOT NULL DEFAULT '' COMMENT '用户头像路径url',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE = utf8_general_ci COMMENT '用户信息表';

CREATE TABLE `operator_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `ip` varchar(128) NOT NULL DEFAULT "" COMMENT 'ip',
  `operator` varchar(255) NOT NULL DEFAULT "" COMMENT '操作内容',
  `params` varchar(128) NOT NULL DEFAULT "" COMMENT '参数',
  `method` varchar(128) NOT NULL DEFAULT "" COMMENT '方法名',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '操作日志';





