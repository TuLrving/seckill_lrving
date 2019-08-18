-- 创建数据库
CREATE DATABASE miaosha;

-- 使用数据库
USE miaosha;

-- 创建用户表
CREATE TABLE `miaosha_user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID，手机号码',
  `nick_name` varchar(255) NOT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt) + salt)',
  `salt` varchar(128) DEFAULT NULL,
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建商品表
CREATE TABLE `goods`(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext COMMENT '商品的详情介绍',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- 向商品表插入两条数据
INSERT INTO `goods` VALUES
(1,'iphone XsMAx','苹果最新款 XsMax 64GB 银色 联通一栋电信4G手机','/img/iphoneXsMax.png','iPhone XS Max 64GB、256GB和512GB对应售价分别是9599元、10999元和12799元;
可以看到，从256GB开始，iPhone XS/XS Max这两款手机价格齐刷刷超过了售价过W的大关，苹果iPhone在产品售价上依然比高更高，当初iPhone X 256GB售价9688元只是开胃菜，这次新iPhone的备货很充足，目前如果需要的话去预订，两款机型官网显示的都是1-2周内完成配货。你会买其中之一吗?需要注意的是，定位中端的iPhone XR，要等到下个月才能预订',10000.00,10000),
(2,'华为P30Pro','HUAWEI P30 Pro 麒麟980 超感光徕卡四摄 屏内指纹 曲面屏 双景录像 8GB+128GB 全网通版（天空之境）','/img/P30Pro.png','HUAWEI P30 Pro 麒麟980 超感光徕卡四摄 屏内指纹 曲面屏 双景录像 8GB+128GB 全网通版（天空之境）,麒麟980芯片|6.47英寸OLED曲面屏|4000万超感光徕卡四摄|4200毫安电池强劲续航',5600.25,10000);


-- 创建秒杀商品表
CREATE TABLE `miaosha_goods`(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `miaosha_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- 往秒杀商品表插入两条数据
INSERT INTO `miaosha_goods` VALUES
(1,1,0.01,10000,'2019-6-21 15:18:00','2019-6-23 16:18:01'),
(2,2,0.01,10000,'2019-6-21 15:18:00','2019-6-23 16:18:01');

-- 创建订单表
CREATE TABLE `order_info`(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址ID',
  `goods_name` varchar (16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal (10,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1 pc,2 android,3 ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态,0 新建未支付 1 已支付 2 已发货 3已收货 4 已退款 5 已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- 创建秒杀订单表
CREATE TABLE `miaosha_order`(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
















