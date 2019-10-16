
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

CREATE TABLE `book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `book_name` varchar(64) NOT NULL DEFAULT '' COMMENT '书名',
  `book_image_url` varchar(64) NOT NULL DEFAULT '' COMMENT '书本图片url',
  `book_describe` varchar (128) NOT NULL DEFAULT '' COMMENT '书本描述',
  `category_id` int(11) NOT NULL DEFAULT 0 COMMENT '类别id，数据字典',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '所属用户的id',
  `kind` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0自己的 1换的 2赠的 3借的',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '书表';

CREATE TABLE `desire`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `desire_title` varchar(64) NOT NULL DEFAULT '' COMMENT '意愿标题',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '意愿内容',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '所属用户的id',
  `kind` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0换 1借 2赠 3送',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '意愿表';


CREATE TABLE `friend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
  `friend_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户朋友id',
  `friend_group_id` int(11) NOT NULL DEFAULT 0 COMMENT '朋友所属分组id',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '好友表';

CREATE TABLE `friend_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
  `name` varchar (64) NOT NULL DEFAULT '' COMMENT '分组名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '好友分组表';

CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `text` varchar (1024) NOT NULL DEFAULT 0 COMMENT '内容',
  `from_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '发送者id',
  `to_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '接收者id',
  `message_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0文本 1 图片',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '消息表';



