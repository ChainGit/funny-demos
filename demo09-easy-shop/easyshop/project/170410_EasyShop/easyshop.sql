/*
Navicat MySQL Data Transfer

Source Server         : VirtualBox-PC
Source Server Version : 50633
Source Host           : 192.168.56.2:3306
Source Database       : easyshop_test

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2017-05-13 17:29:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `goods_trade`
-- ----------------------------
DROP TABLE IF EXISTS `goods_trade`;
CREATE TABLE `goods_trade` (
  `good_trade_id` int(8) NOT NULL AUTO_INCREMENT,
  `good_id` int(8) NOT NULL,
  `good_trade_quantity` int(8) NOT NULL DEFAULT '0',
  `user_trade_id` int(8) NOT NULL,
  PRIMARY KEY (`good_trade_id`),
  KEY `goods_trade_good_id_fk` (`good_id`),
  KEY `goods_trade_user_trade_id_fk` (`user_trade_id`),
  CONSTRAINT `goods_trade_good_id_fk` FOREIGN KEY (`good_id`) REFERENCES `store_goods_id` (`good_id`),
  CONSTRAINT `goods_trade_user_trade_id_fk` FOREIGN KEY (`user_trade_id`) REFERENCES `users_trade` (`user_trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_trade
-- ----------------------------
INSERT INTO `goods_trade` VALUES ('1', '80000', '5', '2');
INSERT INTO `goods_trade` VALUES ('3', '80001', '8', '3');
INSERT INTO `goods_trade` VALUES ('4', '80003', '1', '2');
INSERT INTO `goods_trade` VALUES ('5', '80004', '2', '2');
INSERT INTO `goods_trade` VALUES ('6', '80000', '3', '4');
INSERT INTO `goods_trade` VALUES ('7', '80005', '4', '6');
INSERT INTO `goods_trade` VALUES ('8', '80006', '1', '5');
INSERT INTO `goods_trade` VALUES ('9', '80012', '5', '7');
INSERT INTO `goods_trade` VALUES ('10', '80009', '5', '7');
INSERT INTO `goods_trade` VALUES ('11', '80003', '2', '8');
INSERT INTO `goods_trade` VALUES ('12', '80005', '2', '8');
INSERT INTO `goods_trade` VALUES ('13', '80006', '2', '9');
INSERT INTO `goods_trade` VALUES ('14', '80008', '2', '9');
INSERT INTO `goods_trade` VALUES ('15', '80001', '2', '10');
INSERT INTO `goods_trade` VALUES ('16', '80000', '1', '10');
INSERT INTO `goods_trade` VALUES ('17', '80002', '1', '10');
INSERT INTO `goods_trade` VALUES ('18', '80001', '1', '11');
INSERT INTO `goods_trade` VALUES ('19', '80000', '2', '11');
INSERT INTO `goods_trade` VALUES ('20', '80002', '2', '12');
INSERT INTO `goods_trade` VALUES ('21', '80004', '1', '13');
INSERT INTO `goods_trade` VALUES ('22', '80001', '1', '14');
INSERT INTO `goods_trade` VALUES ('23', '80002', '1', '15');
INSERT INTO `goods_trade` VALUES ('24', '80002', '1', '16');
INSERT INTO `goods_trade` VALUES ('25', '80004', '1', '16');
INSERT INTO `goods_trade` VALUES ('26', '80007', '1', '16');
INSERT INTO `goods_trade` VALUES ('27', '80019', '1', '17');

-- ----------------------------
-- Table structure for `store_goods_base`
-- ----------------------------
DROP TABLE IF EXISTS `store_goods_base`;
CREATE TABLE `store_goods_base` (
  `good_id` int(8) NOT NULL AUTO_INCREMENT,
  `good_title` varchar(30) NOT NULL,
  `good_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`good_id`),
  KEY `store_goods_base_good_id_fk` (`good_id`),
  CONSTRAINT `store_goods_base_good_id_fk` FOREIGN KEY (`good_id`) REFERENCES `store_goods_id` (`good_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80020 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store_goods_base
-- ----------------------------
INSERT INTO `store_goods_base` VALUES ('80000', 'Java从入门到精通', '50.50');
INSERT INTO `store_goods_base` VALUES ('80001', '蒙牛牛奶', '60.50');
INSERT INTO `store_goods_base` VALUES ('80002', '伊利牛奶', '71.82');
INSERT INTO `store_goods_base` VALUES ('80003', '男式衣服', '120.99');
INSERT INTO `store_goods_base` VALUES ('80004', '女式裙子', '138.90');
INSERT INTO `store_goods_base` VALUES ('80005', '男式洗发露', '27.50');
INSERT INTO `store_goods_base` VALUES ('80006', '女式洗发露', '19.80');
INSERT INTO `store_goods_base` VALUES ('80007', '陶瓷杯子', '10.80');
INSERT INTO `store_goods_base` VALUES ('80008', '丹阳眼镜', '123.99');
INSERT INTO `store_goods_base` VALUES ('80009', '好视力眼镜', '100.32');
INSERT INTO `store_goods_base` VALUES ('80010', '玻璃杯子', '23.43');
INSERT INTO `store_goods_base` VALUES ('80011', '公牛插排', '43.92');
INSERT INTO `store_goods_base` VALUES ('80012', '山寨插排', '12.34');
INSERT INTO `store_goods_base` VALUES ('80013', 'C++从入门到精通', '60.23');
INSERT INTO `store_goods_base` VALUES ('80014', '山东苹果', '2.23');
INSERT INTO `store_goods_base` VALUES ('80015', '浙江荔枝', '23.43');
INSERT INTO `store_goods_base` VALUES ('80016', '牛皮糖', '4.79');
INSERT INTO `store_goods_base` VALUES ('80018', '小鸭电风扇', '23.68');
INSERT INTO `store_goods_base` VALUES ('80019', '多芬沐浴露', '25.00');

-- ----------------------------
-- Table structure for `store_goods_id`
-- ----------------------------
DROP TABLE IF EXISTS `store_goods_id`;
CREATE TABLE `store_goods_id` (
  `good_id` int(8) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`good_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80020 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store_goods_id
-- ----------------------------
INSERT INTO `store_goods_id` VALUES ('80000');
INSERT INTO `store_goods_id` VALUES ('80001');
INSERT INTO `store_goods_id` VALUES ('80002');
INSERT INTO `store_goods_id` VALUES ('80003');
INSERT INTO `store_goods_id` VALUES ('80004');
INSERT INTO `store_goods_id` VALUES ('80005');
INSERT INTO `store_goods_id` VALUES ('80006');
INSERT INTO `store_goods_id` VALUES ('80007');
INSERT INTO `store_goods_id` VALUES ('80008');
INSERT INTO `store_goods_id` VALUES ('80009');
INSERT INTO `store_goods_id` VALUES ('80010');
INSERT INTO `store_goods_id` VALUES ('80011');
INSERT INTO `store_goods_id` VALUES ('80012');
INSERT INTO `store_goods_id` VALUES ('80013');
INSERT INTO `store_goods_id` VALUES ('80014');
INSERT INTO `store_goods_id` VALUES ('80015');
INSERT INTO `store_goods_id` VALUES ('80016');
INSERT INTO `store_goods_id` VALUES ('80018');
INSERT INTO `store_goods_id` VALUES ('80019');

-- ----------------------------
-- Table structure for `store_goods_remark`
-- ----------------------------
DROP TABLE IF EXISTS `store_goods_remark`;
CREATE TABLE `store_goods_remark` (
  `good_remark_id` int(10) NOT NULL AUTO_INCREMENT,
  `good_id` int(8) NOT NULL,
  `good_remark` text NOT NULL,
  `good_remark_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `good_remark_user_id` int(10) NOT NULL,
  PRIMARY KEY (`good_remark_id`),
  KEY `store_goods_remark_good_id_fk` (`good_id`) USING BTREE,
  KEY `store_goods_remark_user_id_fk` (`good_remark_user_id`) USING BTREE,
  CONSTRAINT `store_goods_remark_good_id_fk` FOREIGN KEY (`good_id`) REFERENCES `store_goods_id` (`good_id`),
  CONSTRAINT `store_goods_remark_user_id_fk` FOREIGN KEY (`good_remark_user_id`) REFERENCES `users_base` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store_goods_remark
-- ----------------------------
INSERT INTO `store_goods_remark` VALUES ('1', '80000', '好', '2017-05-07 16:17:27', '1001');
INSERT INTO `store_goods_remark` VALUES ('2', '80000', '嗯', '2017-05-07 16:17:38', '1001');
INSERT INTO `store_goods_remark` VALUES ('3', '80001', '不错', '2017-05-07 16:17:49', '1001');
INSERT INTO `store_goods_remark` VALUES ('4', '80001', '好东西', '2017-05-07 16:18:45', '1000');
INSERT INTO `store_goods_remark` VALUES ('6', '80003', '真不错', '2017-05-07 16:19:03', '1002');
INSERT INTO `store_goods_remark` VALUES ('7', '80000', '非常棒', '2017-05-07 18:01:29', '1001');
INSERT INTO `store_goods_remark` VALUES ('8', '80000', '真他妈好', '2017-05-07 18:01:47', '1002');
INSERT INTO `store_goods_remark` VALUES ('10', '80000', '牛逼啊', '2017-05-07 18:02:05', '1001');
INSERT INTO `store_goods_remark` VALUES ('11', '80000', '这是一本好书', '2017-05-10 22:54:30', '1001');
INSERT INTO `store_goods_remark` VALUES ('12', '80000', '读一本好书', '2017-05-10 22:56:45', '1001');
INSERT INTO `store_goods_remark` VALUES ('13', '80000', '非常推荐', '2017-05-10 22:57:27', '1001');
INSERT INTO `store_goods_remark` VALUES ('14', '80000', '真的牛逼啊', '2017-05-10 22:57:39', '1001');
INSERT INTO `store_goods_remark` VALUES ('15', '80000', '好书好书好书', '2017-05-10 22:58:59', '1002');
INSERT INTO `store_goods_remark` VALUES ('16', '80000', '太牛逼的书', '2017-05-10 23:02:20', '1001');
INSERT INTO `store_goods_remark` VALUES ('17', '80000', 'NICENICE', '2017-05-10 23:02:38', '1002');
INSERT INTO `store_goods_remark` VALUES ('18', '80000', 'hao!', '2017-05-10 23:02:55', '1001');
INSERT INTO `store_goods_remark` VALUES ('19', '80000', 'niubi', '2017-05-10 23:04:39', '1001');
INSERT INTO `store_goods_remark` VALUES ('20', '80000', 'bang', '2017-05-10 23:04:50', '1001');
INSERT INTO `store_goods_remark` VALUES ('21', '80000', 'niuniu', '2017-05-10 23:05:23', '1002');
INSERT INTO `store_goods_remark` VALUES ('22', '80000', '123', '2017-05-10 23:07:07', '1001');
INSERT INTO `store_goods_remark` VALUES ('23', '80000', '111', '2017-05-10 23:07:10', '1001');
INSERT INTO `store_goods_remark` VALUES ('24', '80000', '111111', '2017-05-10 23:07:16', '1001');
INSERT INTO `store_goods_remark` VALUES ('25', '80000', '222', '2017-05-10 23:09:29', '1001');
INSERT INTO `store_goods_remark` VALUES ('26', '80000', '4444', '2017-05-10 23:09:31', '1001');
INSERT INTO `store_goods_remark` VALUES ('27', '80000', '44444444', '2017-05-10 23:09:34', '1001');
INSERT INTO `store_goods_remark` VALUES ('28', '80000', '5555555', '2017-05-10 23:09:42', '1001');
INSERT INTO `store_goods_remark` VALUES ('29', '80000', '1213123', '2017-05-10 23:16:59', '1001');
INSERT INTO `store_goods_remark` VALUES ('30', '80000', '123123123', '2017-05-10 23:17:01', '1001');
INSERT INTO `store_goods_remark` VALUES ('31', '80000', '123123123', '2017-05-10 23:17:07', '1001');
INSERT INTO `store_goods_remark` VALUES ('32', '80000', '123123123123', '2017-05-10 23:17:09', '1001');
INSERT INTO `store_goods_remark` VALUES ('33', '80000', '123123', '2017-05-10 23:17:12', '1001');
INSERT INTO `store_goods_remark` VALUES ('34', '80000', '123123123', '2017-05-10 23:17:26', '1002');
INSERT INTO `store_goods_remark` VALUES ('35', '80000', '123123123', '2017-05-10 23:17:29', '1002');
INSERT INTO `store_goods_remark` VALUES ('36', '80000', 'feqweq', '2017-05-10 23:17:34', '1002');
INSERT INTO `store_goods_remark` VALUES ('37', '80000', 'sadasd', '2017-05-10 23:36:22', '1001');
INSERT INTO `store_goods_remark` VALUES ('38', '80000', '123123', '2017-05-10 23:36:25', '1001');
INSERT INTO `store_goods_remark` VALUES ('39', '80000', '234234324', '2017-05-10 23:36:27', '1001');
INSERT INTO `store_goods_remark` VALUES ('40', '80000', '234234324324', '2017-05-10 23:36:29', '1001');
INSERT INTO `store_goods_remark` VALUES ('41', '80007', '21133123', '2017-05-10 23:36:41', '1001');
INSERT INTO `store_goods_remark` VALUES ('42', '80007', '123123123123', '2017-05-10 23:36:48', '1001');
INSERT INTO `store_goods_remark` VALUES ('43', '80007', '123123123', '2017-05-10 23:36:50', '1001');
INSERT INTO `store_goods_remark` VALUES ('44', '80007', '123123123', '2017-05-10 23:36:52', '1001');
INSERT INTO `store_goods_remark` VALUES ('45', '80001', 'wwqeqweqwe', '2017-05-10 23:38:05', '1001');
INSERT INTO `store_goods_remark` VALUES ('46', '80001', '123123123', '2017-05-10 23:38:08', '1001');
INSERT INTO `store_goods_remark` VALUES ('47', '80002', 'sadasdasd', '2017-05-10 23:39:02', '1001');
INSERT INTO `store_goods_remark` VALUES ('48', '80002', '123123', '2017-05-10 23:39:12', '1002');
INSERT INTO `store_goods_remark` VALUES ('49', '80012', '123123123', '2017-05-10 23:39:23', '1002');
INSERT INTO `store_goods_remark` VALUES ('50', '80011', '123123123', '2017-05-10 23:40:10', '1001');
INSERT INTO `store_goods_remark` VALUES ('51', '80000', 'dsfsdfdf', '2017-05-10 23:43:13', '1002');
INSERT INTO `store_goods_remark` VALUES ('52', '80012', '阿萨飒飒', '2017-05-11 11:04:35', '1001');
INSERT INTO `store_goods_remark` VALUES ('53', '80000', '围墙围起为', '2017-05-11 13:57:20', '1001');
INSERT INTO `store_goods_remark` VALUES ('54', '80004', 'low', '2017-05-13 17:19:21', '1006');

-- ----------------------------
-- Table structure for `store_goods_trade`
-- ----------------------------
DROP TABLE IF EXISTS `store_goods_trade`;
CREATE TABLE `store_goods_trade` (
  `good_id` int(8) NOT NULL AUTO_INCREMENT,
  `good_sales_amount` int(5) NOT NULL DEFAULT '0',
  `good_store_number` int(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`good_id`),
  KEY `store_goods_trade_good_id_fk` (`good_id`),
  CONSTRAINT `store_goods_trade_good_id_fk` FOREIGN KEY (`good_id`) REFERENCES `store_goods_id` (`good_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80020 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store_goods_trade
-- ----------------------------
INSERT INTO `store_goods_trade` VALUES ('80000', '6', '12');
INSERT INTO `store_goods_trade` VALUES ('80001', '8', '11');
INSERT INTO `store_goods_trade` VALUES ('80002', '10', '0');
INSERT INTO `store_goods_trade` VALUES ('80003', '5', '32');
INSERT INTO `store_goods_trade` VALUES ('80004', '6', '43');
INSERT INTO `store_goods_trade` VALUES ('80005', '7', '21');
INSERT INTO `store_goods_trade` VALUES ('80006', '5', '32');
INSERT INTO `store_goods_trade` VALUES ('80007', '4', '22');
INSERT INTO `store_goods_trade` VALUES ('80008', '4', '8');
INSERT INTO `store_goods_trade` VALUES ('80009', '2', '11');
INSERT INTO `store_goods_trade` VALUES ('80010', '2', '23');
INSERT INTO `store_goods_trade` VALUES ('80011', '2', '44');
INSERT INTO `store_goods_trade` VALUES ('80012', '3', '32');
INSERT INTO `store_goods_trade` VALUES ('80013', '2', '23');
INSERT INTO `store_goods_trade` VALUES ('80014', '2', '45');
INSERT INTO `store_goods_trade` VALUES ('80015', '0', '34');
INSERT INTO `store_goods_trade` VALUES ('80016', '0', '45');
INSERT INTO `store_goods_trade` VALUES ('80018', '0', '30');
INSERT INTO `store_goods_trade` VALUES ('80019', '1', '9');

-- ----------------------------
-- Table structure for `users_account`
-- ----------------------------
DROP TABLE IF EXISTS `users_account`;
CREATE TABLE `users_account` (
  `account_id` int(10) NOT NULL AUTO_INCREMENT,
  `account_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11009 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_account
-- ----------------------------
INSERT INTO `users_account` VALUES ('11000', '0.00');
INSERT INTO `users_account` VALUES ('11001', '726.99');
INSERT INTO `users_account` VALUES ('11002', '23432.34');
INSERT INTO `users_account` VALUES ('11006', '702.00');
INSERT INTO `users_account` VALUES ('11007', '778.48');
INSERT INTO `users_account` VALUES ('11008', '1209.00');

-- ----------------------------
-- Table structure for `users_base`
-- ----------------------------
DROP TABLE IF EXISTS `users_base`;
CREATE TABLE `users_base` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` char(10) NOT NULL,
  `user_pass` char(10) NOT NULL,
  `account_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `users_base_account_id_fk` (`account_id`),
  CONSTRAINT `users_base_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `users_account` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1008 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_base
-- ----------------------------
INSERT INTO `users_base` VALUES ('1000', 'admin', 'admin', '11000');
INSERT INTO `users_base` VALUES ('1001', 'may', '123', '11001');
INSERT INTO `users_base` VALUES ('1002', 'tom', '123', '11002');
INSERT INTO `users_base` VALUES ('1005', 'chain', '123', '11006');
INSERT INTO `users_base` VALUES ('1006', 'lee', '123', '11007');
INSERT INTO `users_base` VALUES ('1007', 'lee1', '1234', '11008');

-- ----------------------------
-- Table structure for `users_cart`
-- ----------------------------
DROP TABLE IF EXISTS `users_cart`;
CREATE TABLE `users_cart` (
  `user_cart_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `good_id` int(8) NOT NULL,
  `goods_amount` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_cart_id`),
  KEY `users_cart_user_id_fk` (`user_id`) USING BTREE,
  KEY `users_cart_good_id_fk` (`good_id`) USING BTREE,
  CONSTRAINT `users_cart_good_id_fk` FOREIGN KEY (`good_id`) REFERENCES `store_goods_id` (`good_id`),
  CONSTRAINT `users_cart_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users_base` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_cart
-- ----------------------------
INSERT INTO `users_cart` VALUES ('59', '1002', '80001', '1');
INSERT INTO `users_cart` VALUES ('60', '1002', '80002', '1');
INSERT INTO `users_cart` VALUES ('103', '1001', '80008', '1');
INSERT INTO `users_cart` VALUES ('106', '1001', '80006', '1');
INSERT INTO `users_cart` VALUES ('112', '1007', '80000', '1');
INSERT INTO `users_cart` VALUES ('113', '1007', '80001', '2');
INSERT INTO `users_cart` VALUES ('115', '1007', '80014', '4');
INSERT INTO `users_cart` VALUES ('116', '1007', '80004', '2');
INSERT INTO `users_cart` VALUES ('117', '1006', '80002', '1');
INSERT INTO `users_cart` VALUES ('118', '1006', '80001', '1');

-- ----------------------------
-- Table structure for `users_trade`
-- ----------------------------
DROP TABLE IF EXISTS `users_trade`;
CREATE TABLE `users_trade` (
  `user_trade_id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `user_trade_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_trade_id`),
  KEY `users_trade_user_id_fk` (`user_id`),
  CONSTRAINT `users_trade_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users_base` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_trade
-- ----------------------------
INSERT INTO `users_trade` VALUES ('2', '1001', '2017-05-07 11:38:06');
INSERT INTO `users_trade` VALUES ('3', '1002', '2017-05-07 11:38:46');
INSERT INTO `users_trade` VALUES ('4', '1001', '2017-05-07 12:14:58');
INSERT INTO `users_trade` VALUES ('5', '1001', '2017-05-07 18:13:12');
INSERT INTO `users_trade` VALUES ('6', '1001', '2017-05-07 18:13:17');
INSERT INTO `users_trade` VALUES ('7', '1001', '2017-05-07 18:13:24');
INSERT INTO `users_trade` VALUES ('8', '1001', '2017-05-13 16:57:06');
INSERT INTO `users_trade` VALUES ('9', '1001', '2017-05-13 16:57:18');
INSERT INTO `users_trade` VALUES ('10', '1001', '2017-05-13 17:01:40');
INSERT INTO `users_trade` VALUES ('11', '1001', '2017-05-13 17:03:06');
INSERT INTO `users_trade` VALUES ('12', '1001', '2017-05-13 17:03:40');
INSERT INTO `users_trade` VALUES ('13', '1001', '2017-05-13 17:07:32');
INSERT INTO `users_trade` VALUES ('14', '1001', '2017-05-13 17:08:43');
INSERT INTO `users_trade` VALUES ('15', '1001', '2017-05-13 17:11:01');
INSERT INTO `users_trade` VALUES ('16', '1006', '2017-05-13 17:21:09');
INSERT INTO `users_trade` VALUES ('17', '1007', '2017-05-13 17:28:11');
