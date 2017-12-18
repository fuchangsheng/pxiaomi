CREATE DATABASE pxiaomi;
SHOW WARNINGS;
USE pxiaomi;

/**
 * Table structure for tb_user_info
 */
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `id` VARCHAR(45)  NOT NULL COMMENT '用户ID',
  `name` VARCHAR(45) NOT NULL COMMENT '用户名',
  `password` VARCHAR(45) NOT NULL COMMENT '用户密码',
  `portrait` VARCHAR(200) DEFAULT NULL COMMENT '用户头像',
  `age` int(11) DEFAULT NULL COMMENT '用户姓名',
  `gender` int(11) DEFAULT 0 COMMENT '性别,1男,2女,0未知',
  `mobile` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(45) DEFAULT NULL COMMENT '用户邮箱',
  `type` int(11) DEFAULT 0 COMMENT '用户类型, 0-普通,1-管理员',
  `start` VARCHAR(45) DEFAULT NULL COMMENT '用户评分',
  `smsCode` VARCHAR(45) DEFAULT NULL COMMENT '验证码', 
  `loginState` int(11) DEFAULT 0 COMMENT '登陆状态，0-no， 1-yes',
  `loginTime` DATETIME DEFAULT NULL COMMENT '注册日期',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/**
 * Table structure of tb_bank_info
 */
DROP TABLE IF EXISTS `tb_bank_info`;
CREATE TABLE `tb_bank_info` (
  `id` VARCHAR(45)  NOT NULL COMMENT '银行卡ID',
  `userId` VARCHAR(45) NOT NULL COMMENT '用户ID',
  `name` VARCHAR(45) NOT NULL COMMENT '银行名称',
  `accountNo` VARCHAR(45) NOT NULL COMMENT '银行账户',
  `bankDeposit`VARCHAR(45) NOT NULL COMMENT '开户行名称',
  `bankAddress` VARCHAR(45) NOT NULL COMMENT '开户行地址',
  `type` int(11) DEFAULT 0 COMMENT '银行卡类型',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bank_user` FOREIGN KEY (`userId`) REFERENCES `tb_user_info`(`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行信息表';

/**
 * Table structure for tb_business_info
 */
DROP TABLE IF EXISTS `tb_business_info`;
CREATE TABLE `tb_business_info` (
  `id` VARCHAR(45) NOT NULL COMMENT '商家ID',
  `name` VARCHAR(45) NOT NULL COMMENT '商家名称',
  `mobile` VARCHAR(45) DEFAULT NULL COMMENT '商家电话',
  `telephone` VARCHAR(45) DEFAULT NULL COMMENT '移动电话',
  `email` VARCHAR(45) DEFAULT NULL COMMENT '商户邮箱',
  `type` int(11) DEFAULT 0 COMMENT '商家类型',
  `provice` VARCHAR(45) DEFAULT NULL COMMENT '商家所在省份',
  `city` VARCHAR(45) DEFAULT NULL COMMENT '商家所在城市',
  `district` VARCHAR(45) DEFAULT NULL COMMENT '商家所在地区',
  `town` VARCHAR(45) DEFAULT NULL COMMENT '商家所在镇/街道',
  `address` VARCHAR(45) DEFAULT NULL COMMENT '商家地址',
  `postcode` VARCHAR(45) DEFAULT NULL COMMENT '商家的邮编',
  `logitude` VARCHAR(45) DEFAULT NULL COMMENT  '商家位置的经度',
  `latitude` VARCHAR(45) DEFAULT NULL COMMENT '商家位置的纬度',
  `start` VARCHAR(45) DEFAULT NULL COMMENT '用户对商家的评价',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '商户信息表';

/**
 * Table structure of tb_tax_info
 */
DROP TABLE IF EXISTS `tb_tax_info`;
CREATE TABLE `tb_tax_info` (
  `id` VARCHAR(45) NOT NULL COMMENT '税务ID',
  `userId` VARCHAR(45) NOT NULL COMMENT '用户ID',
  `bankDeposit` VARCHAR(45) NOT NULL COMMENT '开户行名称',
  `accountNo` VARCHAR(45) NOT NULL COMMENT '银行账户',
  `title` VARCHAR(45) NOT NULL COMMENT '发票抬头',
  `taxNo` VARCHAR(45) NOT NULL COMMENT '税号',
  `address` VARCHAR(45) NOT NULL COMMENT '地址',
  `mobile` VARCHAR(45) NOT NULL COMMENT '电话',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_tax_user` FOREIGN KEY(`userId`) REFERENCES `tb_user_info`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='税务信息表';


DROP TABLE IF EXISTS `tb_comment_info`;
CREATE TABLE `tb_comment_info`(
  `id` VARCHAR(45) NOT NULL COMMENT '评分表ID',
  `userId` VARCHAR(45) NOT NULL COMMENT '用户ID',
  `businessId` VARCHAR(45) NOT NULL COMMENT '商户ID',
  `userScore` VARCHAR(11) DEFAULT NULL COMMENT '用户得分',
  `userComment` VARCHAR(200) DEFAULT NULL COMMENT '商户对用户的评论',
  `businessScore` VARCHAR(11) DEFAULT NULL COMMENT '商户得分',
  `businessComment`VARCHAR(200) DEFAULT NULL COMMENT '用户对商户的评论',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`userId`) REFERENCES `tb_user_info`(`id`),
  CONSTRAINT `fk_comment_business` FOREIGN KEY (`businessId`) REFERENCES `tb_business_info`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '评分表';



DROP TABLE  IF EXISTS `tb_bill_info`;
CREATE TABLE `tb_bill_info`(
  `id` VARCHAR(45) NOT NULL COMMENT '发票ID',
  `userId` VARCHAR(45) NOT NULL COMMENT '用户ID',
  `taxId` VARCHAR(45) NOT NULL COMMENT '税务ID',
  `businessId` VARCHAR(45) NOT NULL COMMENT '商户ID',
  `amount` VARCHAR(45) DEFAULT NULL COMMENT '发票金额',
  `type` int(11) DEFAULT 0 COMMENT '发票类型',
  `content` int(11) DEFAULT NULL COMMENT '发票内容',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bill_user` FOREIGN KEY (`userId`) REFERENCES `tb_user_info`(`id`),
  CONSTRAINT `fk_bill_tax` FOREIGN KEY (`taxId`) REFERENCES `tb_tax_info`(`id`),
  CONSTRAINT `fk_bill_business` FOREIGN KEY (`businessId`) REFERENCES `tb_business_info`(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '发票表';


DROP TABLE IF EXISTS `tb_bill_items_info`;
CREATE TABLE `tb_bill_items_info`(
  `id` VARCHAR(45) NOT NULL COMMENT '发票内容ID',
  `billId` VARCHAR(45) NOT NULL COMMENT '发票ID',
  `amount` VARCHAR(45) DEFAULT NULL COMMENT '金额',
  `content` int(11) DEFAULT NULL COMMENT '内容',
  `rate` float(6,4) DEFAULT NULL COMMENT '税率',
  `type` VARCHAR(45) DEFAULT NULL COMMENT '发票内容的类型',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_items_bill` FOREIGN KEY (`billId`) REFERENCES `tb_bill_info`(`id`) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '发票内容表';

DROP TABLE IF EXISTS `tb_smsCode_info`;
CREATE TABLE `tb_smsCode_info`(
  `id` VARCHAR(45) NOT NULL COMMENT '验证码ID',
  `mobile` VARCHAR(22) DEFAULT NULL COMMENT '手机号',
  `smsCode` VARCHAR(22) DEFAULT NULL COMMENT '验证码',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '验证码表';

DROP TABLE IF EXISTS `tb_wechat_user_info`;
CREATE TABLE `tb_wechat_user_info`(
  `id` VARCHAR(200) NOT NULL COMMENT '微信openId',
  `userId` VARCHAR(45) UNIQUE DEFAULT NULL COMMENT '用户ID',
  `nickname` VARCHAR(45) DEFAULT NULL COMMENT '微信昵称',
  `headimgurl` VARCHAR(300) DEFAULT NULL COMMENT '用户头像地址',
  `sex` int(11) DEFAULT NULL COMMENT '0-UNKNOWN 1-MALE 2-FEMALE',
  `country` VARCHAR(40) DEFAULT NULL COMMENT '国家',
  `province` VARCHAR(40) DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(40) DEFAULT NULL COMMENT '城市',
  `privilege` VARCHAR(200) DEFAULT NULL COMMENT '微信特权',
  `unionid` VARCHAR(200) DEFAULT NULL COMMENT '联合ID',
  `createTime` DATETIME DEFAULT NULL COMMENT '创建日期',
  `updateTime` DATETIME DEFAULT NULL COMMENT 'update日期', 
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '记录状态 0:有效 1:非法 2:删除',
  PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '微信用户信息表';