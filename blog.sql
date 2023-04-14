/*
Navicat MySQL Data Transfer

Source Server         : lsc
Source Server Version : 50735
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50735
File Encoding         : 65001

Date: 2023-04-07 09:50:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `entity_type` int(11) DEFAULT '0' COMMENT '实体类型',
  `entity_id` int(11) DEFAULT '0' COMMENT '实体id',
  `target_id` int(11) DEFAULT '0' COMMENT '目标id',
  `content` varchar(255) DEFAULT '' COMMENT '评论内容',
  `statu` tinyint(4) DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('1', '1', '1', '14', '1', '鸡哥好帅', '0', '2023-03-20 17:25:34');
INSERT INTO `t_comment` VALUES ('2', '2', '2', '1', '1', '鸡哥肯定很帅啦1', '0', '2023-03-20 17:30:25');
INSERT INTO `t_comment` VALUES ('3', '1', '2', '1', '2', '鸡哥我老公', '0', '2023-03-20 17:33:21');
INSERT INTO `t_comment` VALUES ('4', '6', '1', '14', '1', '鸡哥一起打球啊', '0', '2023-03-20 17:36:36');
INSERT INTO `t_comment` VALUES ('5', '8', '1', '14', '1', '鸽鸽好美', '0', '2023-03-20 19:40:11');
INSERT INTO `t_comment` VALUES ('6', '3', '2', '5', '8', '你什么意思，小黑子', '0', '2023-03-20 19:51:27');
INSERT INTO `t_comment` VALUES ('7', '1', '2', '4', '6', '你太蔡了', '1', '2023-03-22 14:37:23');
INSERT INTO `t_comment` VALUES ('8', '12', '2', '4', '1', '你太美了', '1', '2023-03-22 15:00:00');
INSERT INTO `t_comment` VALUES ('9', '1', '2', '4', '12', '你更美', '1', '2023-03-22 15:02:19');
INSERT INTO `t_comment` VALUES ('10', '12', '1', '13', '1', '食不食油饼', '1', '2023-03-22 15:09:19');
INSERT INTO `t_comment` VALUES ('11', '1', '2', '10', '12', '不食', '1', '2023-03-22 15:11:12');
INSERT INTO `t_comment` VALUES ('12', '1', '1', '2', '2', '树枝666', '1', '2023-03-22 15:12:54');
INSERT INTO `t_comment` VALUES ('13', '1', '1', '7', '7', '小黑子是不是人啊！！！！！！！！！！！！', '1', '2023-03-22 16:37:38');
INSERT INTO `t_comment` VALUES ('14', '5', '1', '14', '1', '鲲鲲我女神啊，爱了爱了。', '1', '2023-03-22 16:39:34');
INSERT INTO `t_comment` VALUES ('15', '9', '2', '14', '5', '兼职石油兵', '1', '2023-03-22 16:41:04');
INSERT INTO `t_comment` VALUES ('16', '12', '2', '14', '9', '你七师也有兵', '1', '2023-03-22 16:41:39');
INSERT INTO `t_comment` VALUES ('17', '9', '1', '3', '3', '兼职石油兵！！\r\n', '1', '2023-03-22 16:42:25');
INSERT INTO `t_comment` VALUES ('18', '12', '2', '17', '9', '你七师也有兵', '1', '2023-03-22 16:42:43');
INSERT INTO `t_comment` VALUES ('19', '11', '2', '17', '12', '你九师也有兵', '1', '2023-03-22 16:43:42');
INSERT INTO `t_comment` VALUES ('20', '8', '1', '2', '2', '没树枝！！！', '1', '2023-03-22 16:45:27');
INSERT INTO `t_comment` VALUES ('21', '10', '2', '14', '9', '你九师有兵', '1', '2023-03-27 16:54:50');
INSERT INTO `t_comment` VALUES ('22', '7', '1', '4', '4', '我家咯咯', '1', '2023-04-06 12:23:19');
INSERT INTO `t_comment` VALUES ('23', '7', '1', '9', '9', '太过粪了', '1', '2023-04-06 12:25:40');

-- ----------------------------
-- Table structure for t_invitation
-- ----------------------------
DROP TABLE IF EXISTS `t_invitation`;
CREATE TABLE `t_invitation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` text COMMENT '内容',
  `statu` tinyint(4) DEFAULT '0' COMMENT '帖子状态',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `score` decimal(6,4) DEFAULT '0.0000' COMMENT '评分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='帖子表';

-- ----------------------------
-- Records of t_invitation
-- ----------------------------
INSERT INTO `t_invitation` VALUES ('1', '你实在是太美', '你太美baby', '1', '1', '2023-03-07 14:48:30', '0.0000');
INSERT INTO `t_invitation` VALUES ('2', '树枝666', '小黑子真虾头', '1', '2', '2023-03-15 14:49:20', '0.0000');
INSERT INTO `t_invitation` VALUES ('3', '油饼食不食', '不食不食', '1', '3', '2023-03-14 15:27:08', '0.0000');
INSERT INTO `t_invitation` VALUES ('4', '你们会毁了他的前途', '呜呜呜呜呜呜', '1', '4', '2023-03-02 15:37:36', '0.0000');
INSERT INTO `t_invitation` VALUES ('5', '再看一眼就会爆炸', '哦baby', '1', '5', '2023-03-11 16:20:06', '0.0000');
INSERT INTO `t_invitation` VALUES ('6', '在靠近点就会融化', '哦baby', '1', '6', '2023-03-12 16:20:47', '0.0000');
INSERT INTO `t_invitation` VALUES ('7', '道歉会不会', '你们·会毁了它的前途', '1', '7', '2023-03-13 16:21:58', '0.0000');
INSERT INTO `t_invitation` VALUES ('8', '哇，真的是你丫', '贞德史尼鸭', '1', '8', '2023-03-08 16:22:32', '0.0000');
INSERT INTO `t_invitation` VALUES ('9', '玩烂梗', '道歉会不会', '1', '9', '2023-03-09 16:41:45', '0.0000');
INSERT INTO `t_invitation` VALUES ('10', 'ikun不惹事也不怕事', '我是ikun你记住', '1', '10', '2023-03-05 16:42:30', '0.0000');
INSERT INTO `t_invitation` VALUES ('11', 'amagi', 'ikun尖叫', '1', '11', '2023-03-06 16:43:03', '0.0000');
INSERT INTO `t_invitation` VALUES ('12', '纯路人，真没必要', '我是纯鹿人', '1', '12', '2023-03-07 16:44:04', '0.0000');
INSERT INTO `t_invitation` VALUES ('13', '鸡哥爱上苏珊', '#### 我是一名鸡哥，我爱上了姑娘苏珊', '1', '1', '2023-03-17 11:02:29', '0.0000');
INSERT INTO `t_invitation` VALUES ('14', '鸡哥简介', '####鸡哥唱跳rap两年半，喜欢食油饼和荔枝，喜欢姑娘苏珊\n[![aa](https://pic1.zhimg.com/v2-c139344e88a6e7ea71508c641ab3d465_r.jpg?source=1940ef5c \"aa\")](https://pic1.zhimg.com/v2-c139344e88a6e7ea71508c641ab3d465_r.jpg?source=1940ef5c \"aa\")\n', '1', '1', '2023-03-17 11:09:32', '0.0000');

-- ----------------------------
-- Table structure for t_invitation_data
-- ----------------------------
DROP TABLE IF EXISTS `t_invitation_data`;
CREATE TABLE `t_invitation_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '帖子详情id',
  `tid` int(11) DEFAULT NULL COMMENT '帖子id',
  `pv` int(11) DEFAULT '0' COMMENT '浏览量',
  `likes` int(11) DEFAULT '0' COMMENT '点赞量',
  `comments` int(11) DEFAULT '0' COMMENT '评论量',
  `collect` int(11) DEFAULT '0' COMMENT '收藏量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='帖子详情表';

-- ----------------------------
-- Records of t_invitation_data
-- ----------------------------
INSERT INTO `t_invitation_data` VALUES ('1', '1', '12', '33', '42', '100');
INSERT INTO `t_invitation_data` VALUES ('2', '2', '15', '66', '88', '111');
INSERT INTO `t_invitation_data` VALUES ('3', '3', '22', '12', '56', '66');
INSERT INTO `t_invitation_data` VALUES ('4', '4', '13', '22', '97', '78');
INSERT INTO `t_invitation_data` VALUES ('5', '5', '12', '44', '63', '86');
INSERT INTO `t_invitation_data` VALUES ('6', '6', '45', '23', '53', '25');
INSERT INTO `t_invitation_data` VALUES ('7', '7', '13', '33', '75', '24');
INSERT INTO `t_invitation_data` VALUES ('8', '8', '34', '54', '24', '43');
INSERT INTO `t_invitation_data` VALUES ('9', '9', '32', '64', '23', '43');
INSERT INTO `t_invitation_data` VALUES ('10', '10', '34', '56', '45', '46');
INSERT INTO `t_invitation_data` VALUES ('11', '11', '65', '23', '16', '25');
INSERT INTO `t_invitation_data` VALUES ('12', '12', '75', '35', '76', '75');

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` int(11) DEFAULT '0' COMMENT '消息类型，点赞，回复，关注',
  `status` tinyint(4) DEFAULT '0' COMMENT '消息是否已读，0未读',
  `uid` int(255) DEFAULT '0' COMMENT '执行这个操作人id',
  `target_id` int(11) DEFAULT '0' COMMENT '消息的接收者，也可以是评论id',
  `entity_id` int(11) DEFAULT '0' COMMENT '实体id，存储喜欢的帖子id',
  `create_time` datetime DEFAULT NULL COMMENT '消息时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES ('3', '1', '0', '10', '3', '3', '2023-04-04 11:20:24');
INSERT INTO `t_message` VALUES ('4', '2', '1', '10', '2', '0', '2023-04-04 11:20:42');
INSERT INTO `t_message` VALUES ('5', '1', '0', '10', '8', '8', '2023-04-04 11:22:05');
INSERT INTO `t_message` VALUES ('6', '1', '0', '10', '5', '5', '2023-04-04 11:22:19');
INSERT INTO `t_message` VALUES ('7', '1', '1', '10', '1', '14', '2023-04-04 11:24:49');
INSERT INTO `t_message` VALUES ('8', '1', '1', '10', '1', '13', '2023-04-04 11:24:53');
INSERT INTO `t_message` VALUES ('9', '1', '1', '10', '1', '1', '2023-04-04 11:25:00');
INSERT INTO `t_message` VALUES ('10', '2', '1', '10', '1', '0', '2023-04-04 12:08:26');
INSERT INTO `t_message` VALUES ('12', '2', '0', '1', '3', '0', '2023-04-04 13:14:26');
INSERT INTO `t_message` VALUES ('14', '2', '1', '1', '5', '0', '2023-04-04 14:55:17');
INSERT INTO `t_message` VALUES ('15', '2', '1', '1', '7', '0', '2023-04-04 14:55:31');
INSERT INTO `t_message` VALUES ('16', '2', '0', '1', '4', '0', '2023-04-04 14:55:54');
INSERT INTO `t_message` VALUES ('17', '2', '0', '2', '1', '0', '2023-04-04 14:57:42');
INSERT INTO `t_message` VALUES ('18', '1', '0', '2', '1', '14', '2023-04-04 14:58:17');
INSERT INTO `t_message` VALUES ('19', '2', '0', '2', '8', '0', '2023-04-04 15:39:46');
INSERT INTO `t_message` VALUES ('20', '2', '0', '3', '1', '0', '2023-04-04 15:40:23');
INSERT INTO `t_message` VALUES ('21', '2', '1', '3', '5', '0', '2023-04-04 15:40:49');
INSERT INTO `t_message` VALUES ('22', '2', '0', '3', '10', '0', '2023-04-04 15:40:57');
INSERT INTO `t_message` VALUES ('23', '2', '0', '3', '4', '0', '2023-04-04 15:41:04');
INSERT INTO `t_message` VALUES ('24', '2', '0', '4', '1', '0', '2023-04-04 15:41:39');
INSERT INTO `t_message` VALUES ('25', '2', '0', '4', '3', '0', '2023-04-04 15:42:04');
INSERT INTO `t_message` VALUES ('26', '2', '0', '4', '10', '0', '2023-04-04 15:42:10');
INSERT INTO `t_message` VALUES ('27', '2', '0', '5', '1', '0', '2023-04-04 15:44:18');
INSERT INTO `t_message` VALUES ('28', '1', '0', '5', '1', '14', '2023-04-04 15:44:24');
INSERT INTO `t_message` VALUES ('29', '2', '0', '5', '10', '0', '2023-04-04 17:06:55');
INSERT INTO `t_message` VALUES ('30', '1', '0', '6', '1', '14', '2023-04-04 19:48:59');
INSERT INTO `t_message` VALUES ('31', '2', '0', '6', '1', '0', '2023-04-04 19:49:04');
INSERT INTO `t_message` VALUES ('32', '2', '0', '6', '2', '0', '2023-04-04 19:49:29');
INSERT INTO `t_message` VALUES ('33', '1', '0', '6', '2', '2', '2023-04-04 19:49:36');
INSERT INTO `t_message` VALUES ('34', '1', '0', '6', '3', '3', '2023-04-04 19:49:40');
INSERT INTO `t_message` VALUES ('35', '1', '0', '7', '1', '14', '2023-04-04 21:34:21');
INSERT INTO `t_message` VALUES ('36', '2', '0', '7', '1', '0', '2023-04-04 21:34:25');
INSERT INTO `t_message` VALUES ('37', '1', '0', '7', '3', '3', '2023-04-06 12:03:44');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) DEFAULT '' COMMENT '用户名',
  `pwd` varchar(255) DEFAULT '' COMMENT '密码',
  `sex` tinyint(255) DEFAULT '1' COMMENT '性别',
  `mobile` varchar(255) DEFAULT '' COMMENT '手机号',
  `email` varchar(255) DEFAULT '' COMMENT '邮箱',
  `statu` tinyint(255) DEFAULT '1' COMMENT '状态',
  `head_url` varchar(255) DEFAULT '' COMMENT '头像',
  `activate_code` varchar(255) DEFAULT '' COMMENT '激活码',
  `score` int(255) DEFAULT '1' COMMENT '分数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '鸡哥', '$2a$10$.h40zBRr50xNoIXYCI0V1O5XMtleZZgRLG.SRa2Jsf/0BbPkoDBCG', '1', '', '2804610365@qq.com', '1', 'https://inews.gtimg.com/newsapp_bt/0/9680744094/641', '557efcbc436140bfa4fe640882b8f1f8', '1', '2023-03-08 15:28:59');
INSERT INTO `t_user` VALUES ('2', '荔枝', '$2a$10$CylOrUNRBamoZ8pBxuPFL.QE0G/c6LeROqz8ALBA3WfephwasWimC', '1', '', 'lizhi@qq.com', '1', 'https://inews.gtimg.com/newsapp_bt/0/13297855094/1000', '6a03737b7b7d457ca238721b9b350747', '1', '2023-03-10 11:20:57');
INSERT INTO `t_user` VALUES ('3', '苏珊', '$2a$10$bWPefP9ZiZ..Ik5mX9J/.OXBz1EpHbM3uTkw77HD72LykIRNZC4M.', '1', '', 'sushan@qq.com', '1', 'https://img1.baidu.com/it/u=1850986054,3191233909&fm=253&fmt=auto&app=138&f=JPEG?w=309&h=323', 'c0609a0e150442ce8d8b8f195c65e9c8', '1', '2023-03-10 11:22:11');
INSERT INTO `t_user` VALUES ('4', '蔡徐坤', '$2a$10$9.A90DZK4cC0CGcJJ5mFyun6ar/rZCSP3F9zu//1O1NY2Ywke0iya', '1', '', 'cxk@qq.com', '1', 'https://inews.gtimg.com/newsapp_bt/0/14945844602/1000', 'ef3d5bfe6d814259bb25f11e926cc9dc', '1', '2023-03-10 11:23:01');
INSERT INTO `t_user` VALUES ('5', '纯鹿人', '$2a$10$iQn2AIcl.jgmqq8bGXToJeUsuwXwOeJt1xHDcucxvuM9pmkZe/xLm', '1', '', 'chunluren@qq.com', '1', 'https://img2.baidu.com/it/u=2559989587,265632244&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=530', 'd7ddab5ed6154009af56c922e11887f6', '1', '2023-03-10 11:24:56');
INSERT INTO `t_user` VALUES ('6', '贞德史尼鸭', '$2a$10$76C3CgZGzAdjVSMl59TRHeKaP9dp6HUJdM6tvP6C0gUi9JV/6pp1y', '1', '', 'zdsny@qq.com', '1', 'https://pic3.zhimg.com/v2-fcdaa5056430236d4000303d4a4f982a_r.jpg', '9c49704e9ae643c6820659d93bb5b754', '1', '2023-03-10 11:26:58');
INSERT INTO `t_user` VALUES ('7', '狮豹者', '$2a$10$oQ0ZqtSmKfegNGA1LzBnSupmuzK78cCVuMQ/UE0CCcvufkZEI7TS2', '1', '', 'sbz@qq.com', '1', 'http://localhost:8001/images/e2276cb00329f8bb4cff93ef9cf2464d.jpg', '4e59df4f222641c48ce033047bbc0a6d', '1', '2023-03-10 11:27:48');
INSERT INTO `t_user` VALUES ('8', '豪啸马', '$2a$10$qH9oJvD5Lp0erCfB8.g7HOTaB87avznzZSbA441W.XcvnDxCvUCEy', '1', '', 'hxm@qq.com', '1', 'https://pic4.zhimg.com/v2-f9003f80f713ecff32284c43a81a2aaf_r.jpg', '02ea74e0e88c4e60807a0d6f25e3a822', '1', '2023-03-10 11:29:51');
INSERT INTO `t_user` VALUES ('9', '梅狸猫', '$2a$10$EKeUKy4H0gx7DRRlDIkrrO3xaLUNgCypeVkItfbR1Yr8Wmz2pRND.', '1', '', 'mlm@qq.com', '1', 'https://pic1.zhimg.com/v2-255d410edb7c19b743f0b3113ee22484_r.jpg', '44b8cfd4b27149448baf2a09f65e6716', '1', '2023-03-10 11:32:05');
INSERT INTO `t_user` VALUES ('10', 'amagi', '$2a$10$aFX9ZY.uhL1t9uEu3CCK/e0hLE6a7ffEfQ5UbsgRM1zf9R.V2lhoq', '1', '', 'amagi@qq.com', '1', 'https://pic4.zhimg.com/v2-bd8152a93aa3f0940235b1442ac14087_b.jpg', 'bc308df2ba3045598e8df51d91944af3', '1', '2023-03-10 11:33:06');
INSERT INTO `t_user` VALUES ('11', '枣豹鹰', '$2a$10$pPBJ2yJpnBZQw6h9JjyvYeTzhgyQzhrtTL585oq7Xx9uul5ks9NBK', '1', '', 'zby@qq.com', '1', 'https://pic3.zhimg.com/v2-01c251f30308deb07133c22147844cea_r.jpg', '29d5a24bc30f46bcacf3161fd8d0f5fe', '1', '2023-03-10 11:34:42');
INSERT INTO `t_user` VALUES ('12', '沃佳鸽鸽', '$2a$10$64GZ8.g85IkzADEAFq4mGOX2Wkd/2C0o1D/RvBRmPdEijyXQLtMK6', '1', '', 'wjgg@qq.com', '1', 'https://img1.baidu.com/it/u=1547556428,981072124&fm=253&fmt=auto&app=138&f=JPEG?w=874&h=500', '9320439537764954aa9d181f17ffb900', '1', '2023-03-10 11:35:33');
