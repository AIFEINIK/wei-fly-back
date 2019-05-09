DROP TABLE IF EXISTS `f_user`;
CREATE TABLE f_user(
  id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  user_name VARCHAR(50) NOT NULL COMMENT '用户名称',
  user_id VARCHAR(30) NOT NULL COMMENT '用户编号',
  passwd varchar(50) not null default '' comment '后台登录用户密码',
  sex TINYINT(2) NOT NULL COMMENT '性别(1:男，2:女)',
  phone VARCHAR(20) NOT NULL DEFAULT '' COMMENT '手机号',
  open_id VARCHAR(100) NOT NULL COMMENT 'open_id',
  session_key VARCHAR(100) NOT NULL COMMENT 'session_key',
  create_time DATETIME NOT NULL COMMENT '注册时间',
  update_time DATETIME NOT NULL COMMENT '更新时间',
  user_status TINYINT(6) NOT NULL DEFAULT 0 COMMENT '用户状态(0:正常， -1:已删除)',
  role_type TINYINT(10) NOT NULL COMMENT '角色类型',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS f_user_card;
CREATE TABLE f_user_card (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  update_time DATETIME NOT NULL COMMENT '更新时间',
  user_id VARCHAR(30) NOT NULL COMMENT '用户编号',
  card_code INT(11) NOT NULL COMMENT '会员卡表card_code',
  can_use_time INT(11) NOT NULL COMMENT '剩余可使用次数',
  active BIT(1) NOT NULL DEFAULT b'0' COMMENT '卡是否激活（0：未激活，1：已激活）',
  card_type tinyint(10) not null comment '卡类型（1:周卡，2:月卡，3:季度卡，4:年卡）',
  PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户会员卡关系表';

DROP TABLE IF EXISTS `f_card`;
CREATE TABLE f_card(
  id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  user_id VARCHAR(30) NOT NULL COMMENT '用户编号',
  card_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '会员卡名称',
  card_code VARCHAR(30) NOT NULL COMMENT '会员卡编号',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  update_time DATETIME NOT NULL COMMENT '更新时间',
  card_type TINYINT(10) NOT NULL COMMENT '卡类型(1:周卡，2:月卡，3:季度卡，4:年卡)',
  can_use_num INT NOT NULL COMMENT '剩余可使用次数',
  active BIT(1) NOT NULL DEFAULT b'0' COMMENT '卡是否激活',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='会员卡表';


DROP TABLE IF EXISTS `f_consume_record`;
CREATE TABLE f_consume_record(
  id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  user_id VARCHAR(30) NOT NULL COMMENT '用户编号',
  card_code VARCHAR(30) NOT NULL COMMENT '会员卡编号',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  consume_num INT NOT NULL COMMENT '消费次数',
  seat_num VARCHAR(5) NOT NULL COMMENT '座位号',
  seat_type TINYINT(10) NOT NULL COMMENT '座位类型',
  order_code VARCHAR(20) NOT NULL COMMENT '预约流水号',
  PRIMARY KEY(`id`),
  KEY `idx_user_id`(`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='消费记录表';


DROP TABLE IF EXISTS `f_seat`;
CREATE TABLE f_seat(
  id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  update_time DATETIME NOT NULL COMMENT '更新时间',
  num VARCHAR(10) NOT NULL COMMENT '座位号',
  title VARCHAR(20) NOT NULL DEFAULT '' COMMENT '小标题',
  locked BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否被预约（0:否，1:是）',
  locked_end_time VARCHAR(30) NOT NULL DEFAULT '' COMMENT '锁定结束时间（倒计时使用）',
  seat_type TINYINT(10) NOT NULL COMMENT '座位类型（1:单座，2:双座）',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='座位表';


DROP TABLE IF EXISTS `f_order`;
CREATE TABLE f_order(
  id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  order_code VARCHAR(20) NOT NULL COMMENT '预约流水号',
  card_code VARCHAR(30) NOT NULL COMMENT '会员卡编号',
  use_time DATETIME NOT NULL COMMENT '预约到店时间',
  user_id VARCHAR(30) NOT NULL COMMENT '用户编号',
  seat_id INT NOT NULL COMMENT 'f_seat表ID',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='预约表';


DROP TABLE IF EXISTS `f_role`;
CREATE TABLE IF NOT EXISTS `f_role` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_type` INT(6) NOT NULL DEFAULT '0' COMMENT '0:超级管理员, 1:普通用户，2:会员',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='角色表';


DROP TABLE IF EXISTS `f_user_role`;
CREATE TABLE IF NOT EXISTS `f_user_role` (
  user_id VARCHAR(30) NOT NULL COMMENT '用户编号',
  `role_id` INT NOT NULL COMMENT '角色表id'
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';


DROP TABLE IF EXISTS `f_unique_num`;
CREATE TABLE f_unique_num(
  num BIGINT NOT NULL COMMENT '全局唯一编号',
  biz_type TINYINT NOT NULL COMMENT '业务类型（1:预约订单）',
  PRIMARY KEY (`biz_type`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='全局唯一ID生成表';

UPDATE f_unique_num SET num = LAST_INSERT_ID(num + 1) WHERE biz_type = 1;SELECT LAST_INSERT_ID();

DROP FUNCTION IF EXISTS `seq`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `seq`(biz_type INT (20)) RETURNS BIGINT(20)
  BEGIN
    UPDATE f_unique_num SET num=LAST_INSERT_ID(num+1) WHERE biz_type=biz_type;
    RETURN LAST_INSERT_ID();
  END
;;
DELIMITER ;
DELIMITER ;;







