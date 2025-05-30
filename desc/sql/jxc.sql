/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3306
 Source Schema         : jxc_shop

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 06/10/2025 23:39:43
*/
CREATE database if NOT EXISTS `goods_jxc_management_system` default character set utf8mb4;
use `goods_jxc_management_system`;

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `user_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '用户名',
    `title`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
    `content`     text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '笔记内容',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '修改时间',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  CHARACTER SET = latin1
  COLLATE = latin1_swedish_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note`
VALUES (1, 'admin', '测试', '<p>111222</p>', '2025-04-14 17:34:43', '2025-04-19 21:46:07', NULL);
INSERT INTO `note`
VALUES (2, 'admin', '制造业', '<p>我是第二个111</p>', '2025-03-03 07:10:29', '2025-04-14 17:34:08', NULL);
INSERT INTO `note`
VALUES (31, 'admin', 'tes', '<p>我是测试系统</p>', '2025-04-30 21:38:10', '2025-05-12 14:48:49', NULL);

-- ----------------------------
-- Table structure for depository
-- ----------------------------
DROP TABLE IF EXISTS `depository`;
CREATE TABLE `depository`
(
    `id`          bigint(11)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `number`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '仓库编号',
    `name`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '仓库名称',
    `head`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '仓库负责人',
    `store_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '仓库电话',
    `address`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
    `stock_total` bigint(50)                                              NULL DEFAULT NULL COMMENT '库存总容量',
    `surplus`     bigint(50)                                              NULL DEFAULT NULL COMMENT '剩余容量',
    `area`        varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '面积单位',
    `status`      int(1)                                                  NULL DEFAULT NULL COMMENT '0 正常 1 停用',
    `build_date`  datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of depository
-- ----------------------------
INSERT INTO `depository`
VALUES (1, '12sa', '虚拟仓库A', 'admin', '16423526791', '北京市/市辖区/东城区/', 2000, 2000, '立方米', 0,
        '2025-02-05 17:28:44', '');
INSERT INTO `depository`
VALUES (2, NULL, '虚拟仓库B', '老王', '15684457223', '天津市/市辖区/和平区/', 2000, 2000, '立方米', 0,
        '2025-03-22 22:44:35', '');
INSERT INTO `depository`
VALUES (4, NULL, '武汉仓1', '老孙', '1568546244', '湖北省/武汉市/市辖区/', 200, 200, '立方米', 0, '2025-04-28 20:25:43',
        '');
INSERT INTO `depository`
VALUES (5, NULL, '擎天', '老孙', '15685545221', '贵州省/黔东南苗族侗族自治州/天柱县/', 200, NULL, '', 0,
        '2025-05-12 10:53:14', '');

-- ----------------------------
-- Table structure for depository_in
-- ----------------------------
DROP TABLE IF EXISTS `depository_in`;
CREATE TABLE `depository_in`
(
    `id`            bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT 'id',
    `source_number` int(20)                                                NULL DEFAULT NULL COMMENT '来源 可有也可无',
    `in_id`         bigint(20)                                             NOT NULL COMMENT '入库编号',
    `depository`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库仓库名',
    `shop_name`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库商名',
    `shop_price`    decimal(10, 2)                                         NULL DEFAULT NULL COMMENT '入库商品单价',
    `shop_number`   bigint(50)                                             NULL DEFAULT NULL COMMENT '入库商品数量',
    `price_total`   decimal(10, 2)                                         NULL DEFAULT NULL COMMENT '入库商品总价',
    `specs`         varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位规格 [个斤盒]',
    `date`          datetime                                               NULL DEFAULT NULL COMMENT '入库日期',
    `in_user`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库人',
    `shop_supplier` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商名',
    `is_inspection` int(1)                                                 NULL DEFAULT 1 COMMENT '0已质检 1 未质检',
    `status`        int(1)                                                 NULL DEFAULT 1 COMMENT '0 已入库 1 未入库',
    `create_date`   datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of depository_in
-- ----------------------------
INSERT INTO `depository_in`
VALUES (1, NULL, 122322587, '北京海淀仓', '苹果', 7.00, 50, 325.00, '斤', '2025-05-12 11:37:46', 'admin', '北极星', 0,
        0, NULL);
INSERT INTO `depository_in`
VALUES (2, NULL, 123456789, '北京', '雪碧', 11.00, 122, 1342.00, '件', '2025-03-23 21:22:20', 'admin', NULL, 1, 1,
        NULL);
INSERT INTO `depository_in`
VALUES (3, NULL, 170498910, '北京', '阿莫西林胶囊', 8.20, 20, NULL, '个', '2025-05-12 11:37:12', 'admin', NULL, 0, 0,
        NULL);
INSERT INTO `depository_in`
VALUES (4, NULL, 811498807, '北京海淀仓', '华硕笔记本', 10000.00, 50, 120.00, '件', '2025-04-23 20:12:27', 'admin',
        '华硕厂商', 0, 0, NULL);
INSERT INTO `depository_in`
VALUES (5, NULL, 504912341, '武汉仓1', '鱼', 15.60, 230, 3588.00, '斤', NULL, NULL, NULL, 1, 1, '2025-05-13 16:13:50');
INSERT INTO `depository_in`
VALUES (10, NULL, 718006985, '虚拟仓库B', '华硕笔记本', 8865.00, 120, 120.00, '个', '2025-05-15 23:35:00', 'admin',
        '璐有限责任公司', 0, 0, NULL);
INSERT INTO `depository_in`
VALUES (11, NULL, 143649068, '虚拟仓库A', '苹果', 6.50, 300, 120.00, '斤', '2025-05-18 18:12:36', 'admin',
        '胡記发展贸易有限责任公司', 0, 0, NULL);
INSERT INTO `depository_in`
VALUES (12, NULL, 718006981, '虚拟仓库A', '鱼', 15.60, 230, 120.00, '斤', '2025-06-01 21:55:02', 'admin',
        '贾有限责任公司', 0, 0, NULL);
INSERT INTO `depository_in`
VALUES (13, NULL, 210663607, '虚拟仓库B', '苹果', 21.00, 50, 120.00, '件', '2025-06-03 14:29:22', 'admin',
        '嘉伦工程有限责任公司', 0, 0, NULL);

-- ----------------------------
-- Table structure for depository_out
-- ----------------------------
DROP TABLE IF EXISTS `depository_out`;
CREATE TABLE `depository_out`
(
    `id`             bigint(11)                                              NOT NULL AUTO_INCREMENT COMMENT '出库id',
    `source_number`  bigint(20)                                              NULL DEFAULT NULL COMMENT '来源 id',
    `out_id`         bigint(20)                                              NULL DEFAULT NULL COMMENT '出库编号',
    `depository`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库仓库',
    `shop_name`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '商品名称',
    `shop_price`     decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '出库价格',
    `shop_number`    bigint(50)                                              NULL DEFAULT NULL COMMENT '商品数量',
    `total_price`    decimal(60, 2)                                          NULL DEFAULT NULL COMMENT '商品总价',
    `specs`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '规格',
    `date`           datetime                                                NULL DEFAULT NULL COMMENT '出库时间',
    `out_user`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '出库用户',
    `shop_supplier`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '客户姓名',
    `status`         int(1)                                                  NULL DEFAULT NULL COMMENT '是否出库 0 出库 1不出库',
    `out_inspection` int(1)                                                  NULL DEFAULT 1 COMMENT '0 审核 1表示未审核',
    `create_date`    datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of depository_out
-- ----------------------------
INSERT INTO `depository_out`
VALUES (1, NULL, 123323, '虚拟仓库A', '苹果', 8.54, 30, 256.20, '个', '2025-04-27 20:19:55', '老王', '华宇', 0, 0,
        '2025-04-27 20:16:36');
INSERT INTO `depository_out`
VALUES (2, NULL, 123, '虚拟仓库B', '雪碧', 200.00, 30, 6000.00, '箱', '2025-04-27 20:14:09', NULL, NULL, 1, 0, NULL);
INSERT INTO `depository_out`
VALUES (3, 123456, 161997858, '虚拟仓库A', '苹果', 1.00, 5, 5.00, '箱', NULL, 'admin', '老杨', 1, 1,
        '2025-06-03 11:07:25');

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '序号 商品库存id',
    `shop`        varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '商品',
    `shop_type`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '商品类型',
    `quantity`    bigint(20)                                              NULL DEFAULT NULL COMMENT '库存量',
    `specs`       varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '规格 斤 、千克、个',
    `depository`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '仓库名',
    `address`     varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '修改时间',
    `remark`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '库存清单'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock`
VALUES (4, '雪碧', '饮品类', 100, '斤', '虚拟仓库A', '', '2025-03-23 21:23:33', '2025-04-28 20:26:45', '无');
INSERT INTO `stock`
VALUES (5, '华硕笔记本', '电器类', 20, '个', '虚拟仓库A', '', '2025-05-07 17:36:31', NULL, '');
INSERT INTO `stock`
VALUES (6, '苹果', '水果类', 62, '个', '虚拟仓库A', '', '2025-05-07 17:37:30', NULL, '');
INSERT INTO `stock`
VALUES (7, '鱼', '水产类', 50, '千克', '虚拟仓库A', '', '2025-05-07 17:45:02', NULL, '');
INSERT INTO `stock`
VALUES (8, '香蕉', '水果类', 23, '斤', '擎天', '', '2025-05-12 16:47:59', NULL, '');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '客户姓名',
    `phone`       varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '客户电话',
    `address`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '客户地址',
    `email`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '客户邮箱',
    `fax`         varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '传真',
    `branch`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '银行名',
    `branch_no`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '卡号',
    `build_date`  datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer`
VALUES (1, '张三', '15685565446', '北京海淀区', '1070348698@qq.com', '556688', '中信银行', '25468446x458455',
        '2025-01-19 19:30:02', NULL, NULL);
INSERT INTO `customer`
VALUES (3, '孙安琪', '2015024130', '天河区天河路278号', 'ansun218@mail.com', '17165678577', 'Visa', '3572647513660753',
        '2025-05-24 10:50:44', NULL, NULL);
INSERT INTO `customer`
VALUES (4, '邱安琪', '16879419430', '罗湖区蔡屋围深南东路541号', 'anqiqiu105@outlook.com', '18035438189', 'UnionPay',
        '4817496935500194', '2025-11-08 01:23:28', NULL, NULL);
INSERT INTO `customer`
VALUES (5, '史晓明', '101724612', '西城区复兴门内大街741号', 'xiaomingshi@gmail.com', '1043573841', 'MasterCard', NULL,
        '2025-07-13 14:11:56', NULL, NULL);
INSERT INTO `customer`
VALUES (7, '方安琪', '2038004755', '天河区天河路90号', 'fananqi@outlook.com', '15988881133', 'JCB', '4477357252652334',
        '2025-06-26 07:40:04', NULL, NULL);
INSERT INTO `customer`
VALUES (8, '田杰宏', '14314567223', '西城区复兴门内大街665号', 'jiehongtian@icloud.com', '1084121515', 'Visa',
        '379751807668208', '2025-07-21 23:57:19', NULL, NULL);
INSERT INTO `customer`
VALUES (9, '毛子韬', '2054161989', '白云区小坪东路207号', 'maozitao2025@icloud.com', '16425193461', 'American Express',
        '377830824899848', '2025-01-24 06:00:38', NULL, NULL);
INSERT INTO `customer`
VALUES (10, '武子韬', '106434415', '东城区东单王府井东街486号', 'wuz@gmail.com', NULL, 'MasterCard', '377417760293101',
        '2025-11-01 05:16:19', NULL, NULL);
INSERT INTO `customer`
VALUES (11, '邓震南', '76920499870', '东泰五街582号', 'dengz@hotmail.com', '18851937724', 'JCB', '346908265108123',
        '2025-04-29 12:05:04', NULL, NULL);

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`
(
    `id`           bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`         varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '商品名',
    `parent_id`    bigint(20)                                              NULL DEFAULT NULL COMMENT '商品科类',
    `shop_number`  bigint(25)                                              NULL DEFAULT NULL COMMENT '商品数量',
    `specs`        varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '规格',
    `market_price` decimal(50, 2)                                          NULL DEFAULT NULL COMMENT '市场价格',
    `build_date`   datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_date`  datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop`
VALUES (1, '苹果', 1, 123, '斤', 13.50, '2025-02-05 16:54:11', '2025-04-04 20:56:08', NULL);
INSERT INTO `shop`
VALUES (2, '鱼', 2, 277, '斤', 10.60, '2025-02-05 17:07:05', '2025-03-20 23:09:24', NULL);
INSERT INTO `shop`
VALUES (3, '华硕笔记本', 2, 125, '台', 8.50, '2025-02-05 17:07:05', '2025-05-05 14:54:23', NULL);
INSERT INTO `shop`
VALUES (4, '阿莫西林胶囊', 4, 687, '盒', 85.00, '2025-02-05 17:07:05', '2025-03-20 23:43:37', NULL);
INSERT INTO `shop`
VALUES (5, '雪碧', 5, 88, '瓶', 5.80, '2025-02-05 17:07:05', '2025-04-04 20:51:24', NULL);
INSERT INTO `shop`
VALUES (6, '香蕉', 5, NULL, NULL, 12.00, '2025-03-19 15:38:13', '2025-04-04 20:55:25', '');

-- ----------------------------
-- Table structure for shop_type
-- ----------------------------
DROP TABLE IF EXISTS `shop_type`;
CREATE TABLE `shop_type`
(
    `id`        int(11)                                                NOT NULL AUTO_INCREMENT COMMENT 'id',
    `class_id`  bigint(20)                                             NULL DEFAULT NULL COMMENT '商品种类编号',
    `shop_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品类型',
    `info`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_type
-- ----------------------------
INSERT INTO `shop_type`
VALUES (1, 1, '水果类', '一级');
INSERT INTO `shop_type`
VALUES (2, 2, '水产类', '一级');
INSERT INTO `shop_type`
VALUES (3, 3, '电器类', '一级');
INSERT INTO `shop_type`
VALUES (4, 3, '医药类', '一级');
INSERT INTO `shop_type`
VALUES (5, 4, '饮品类', '一级');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`
(
    `id`             bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `supplier_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '供应商名',
    `head`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '负责人',
    `phone`          varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '供应商电话',
    `address`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
    `shop_name`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '商品名称',
    `branch`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '银行',
    `branch_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '银行账号',
    `supplier_proxy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '供应商法人代表',
    `create_date`    datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `other`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier`
VALUES (1, '北京鱼业', '杨自行', '1256841265', '北京昌平区', '鱼', '招商银行', '558462364655315451', '杨',
        '2025-02-05 17:59:24', '2025-05-12 12:11:59', NULL);
INSERT INTO `supplier`
VALUES (2, '嘉伦工程有限责任公司', '彭杰宏', '76914906845', '坑美十五巷586号', 'Strawbekry', '中信银行',
        '6290307664140642', '彭杰宏', '2025-05-07 06:48:49', '2025-05-12 12:12:37', NULL);
INSERT INTO `supplier`
VALUES (3, '潘記有限责任公司', '陶致远', '15291801410', '成华区二仙桥东三路546号', 'Mango', ' 邮政银行',
        '6220943385717238', '陶致远', '2025-10-10 23:30:46', '2025-05-12 12:12:55', NULL);
INSERT INTO `supplier`
VALUES (4, '子异有限责任公司', '于宇宁', '205015838', '海珠区江南西路536号', 'hrange', '浦发银行', '6261597080930940',
        '于宇宁', '2025-12-22 06:02:01', '2025-05-12 12:13:06', NULL);
INSERT INTO `supplier`
VALUES (5, '贾有限责任公司', '梁子异', '13351713862', '浦东新区健祥路975号', 'ambi-Strawberry', 'MasterCard',
        '6200179181979748', '梁子异', '2025-02-03 18:24:01', NULL, NULL);
INSERT INTO `supplier`
VALUES (6, '睿物业代理有限责任公司', '尹子异', '18477850084', '罗湖区蔡屋围深南东路496号', 'fambutan', 'UnionPay',
        '6225194331513314', '尹子异', '2025-11-19 05:32:27', NULL, NULL);
INSERT INTO `supplier`
VALUES (7, '陆記发展贸易有限责任公司', '吕宇宁', '13493985903', '延庆区028县道865号', 'iMango', 'MasterCard',
        '6263980689074641', '吕宇宁', '2025-12-15 22:43:05', NULL, NULL);
INSERT INTO `supplier`
VALUES (8, '胡記发展贸易有限责任公司', '徐致远', '14723384265', '天河区天河路82号', 'Kiwi', 'UnionPay',
        '6273617269766562', '徐致远', '2025-02-02 01:29:25', NULL, NULL);
INSERT INTO `supplier`
VALUES (9, '子异有限责任公司', '贾安琪', '2084712952', '海珠区江南西路454号', 'Grape air', 'UnionPay',
        '6254519854021754', '贾安琪', '2025-05-19 18:53:54', NULL, NULL);
INSERT INTO `supplier`
VALUES (10, '璐有限责任公司', '苏子韬', '7551571770', '龙岗区布吉镇西环路411号', 'Gwape', 'Visa', '6206476415440933',
        '苏子韬', '2025-11-21 03:39:19', NULL, NULL);
INSERT INTO `supplier`
VALUES (11, '邹記顾问有限责任公司', '徐震南', '207964990', '海珠区江南西路118号', 'Ramlutan air', 'UnionPay',
        '6267092850431869', '徐震南', '2025-09-09 23:37:24', NULL, NULL);

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase`
(
    `id`              bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `number`          varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '采购编号',
    `purchase_user`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '采购人',
    `shop`            varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '采购商品',
    `shop_type`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '商品所属类',
    `supplier`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '采购供应商',
    `quantity`        bigint(20)                                              NULL DEFAULT NULL COMMENT '采购数量',
    `price`           decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '采购价格',
    `total_price`     decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '总价',
    `time`            datetime                                                NULL DEFAULT NULL COMMENT '采购时间',
    `status`          int(1)                                                  NULL DEFAULT NULL COMMENT '状态 0 完成 1进行中',
    `remark`          varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    `specs`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '单位规格  个斤盒',
    `is_destroy`      int(1)                                                  NULL DEFAULT NULL COMMENT '0存在 1退货，退货后不显示，可增加查询按钮',
    `images`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '图片地址',
    `value_attribute` int(11)                                                 NULL DEFAULT NULL COMMENT '增值比',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase
-- ----------------------------
INSERT INTO `purchase`
VALUES (7, '718006985', '辰东', '华硕笔记本', '电器类', '璐有限责任公司', 120, 8865.00, 120.00, '2025-05-12 16:59:47',
        1, '', '个', 1, NULL, NULL);
INSERT INTO `purchase`
VALUES (8, '718006981', '辰东', '鱼', '水产类', '贾有限责任公司', 230, 15.60, 120.00, '2025-05-13 16:11:58', 0, '',
        '斤', 1, NULL, NULL);
INSERT INTO `purchase`
VALUES (9, '210663607', 'test', '苹果', '水产类', '嘉伦工程有限责任公司', 50, 21.00, 120.00, '2025-06-01 21:57:09', 1,
        '', '件', 1, NULL, NULL);
INSERT INTO `purchase`
VALUES (10, '209088791', 'test', '苹果', '水果类', '北京鱼业', 12, 22.00, 120.00, '2025-06-01 21:57:31', 1, '', '斤', 0,
        NULL, NULL);
INSERT INTO `purchase`
VALUES (11, '182185694', '马化龙', '', '', '', NULL, NULL, 120.00, '2025-06-01 21:57:56', 1, '', '', 0, NULL, NULL);
INSERT INTO `purchase`
VALUES (12, '915089940', 'test', '雪碧', '饮品类', '子异有限责任公司', 25, 2.00, 120.00, '2025-06-01 21:59:43', 1, '',
        '个', 0, NULL, NULL);
INSERT INTO `purchase`
VALUES (13, '210753324', '张三', '', '', '', NULL, NULL, 120.00, '2025-06-01 22:00:04', 1, '', '', 0, NULL, NULL);

-- ----------------------------
-- Table structure for purchase_exit
-- ----------------------------
DROP TABLE IF EXISTS `purchase_exit`;
CREATE TABLE `purchase_exit`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `number`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '采购编号',
    `exit_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '退采编号',
    `num`         int(11)                                                 NULL DEFAULT NULL COMMENT '退采数量',
    `price`       decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '单价',
    `total_price` decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '总价',
    `time`        datetime                                                NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '日期',
    `reason`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
    `status`      int(1)                                                  NULL DEFAULT NULL COMMENT '状态 0 完成 1进行中  ',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    `specs`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '单位规格 个斤盒',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase_exit
-- ----------------------------
INSERT INTO `purchase_exit`
VALUES (1, ' 213341123', '12588', 30, 8.50, 25.50, '2025-04-30 23:41:31', '买多了', 1, '', NULL);
INSERT INTO `purchase_exit`
VALUES (2, '123456789', '12588', 30, 8.50, 25.50, '2025-04-30 23:41:38', '买多了', 1, NULL, NULL);
INSERT INTO `purchase_exit`
VALUES (3, ' 562231358', '123456', 20, 10.90, 120.00, '2025-04-30 23:41:43', '', 1, '', NULL);
INSERT INTO `purchase_exit`
VALUES (4, '179910234', '789581103', 230, 7.40, 120.00, '2025-05-18 18:11:25', 'aaaaa', 0, NULL, '个');
INSERT INTO `purchase_exit`
VALUES (5, '143649068', '436056446', 300, 6.50, 120.00, '2025-05-18 18:13:23', 'jxc1111111111111111111111111', 0, NULL,
        '斤');
INSERT INTO `purchase_exit`
VALUES (6, '718006985', '204514450', 120, 8865.00, 120.00, '2025-05-23 19:09:32', 'chusd1111111', 0, NULL, '个');
INSERT INTO `purchase_exit`
VALUES (7, '718006981', '612573700', 230, 15.60, 120.00, '2025-06-01 21:55:59', '买多了', 1, NULL, '斤');
INSERT INTO `purchase_exit`
VALUES (8, '210663607', '979599307', 50, 21.00, 120.00, '2025-06-03 14:31:21', '测试', 0, NULL, '件');

-- ----------------------------
-- Table structure for sale
-- ----------------------------
DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `sale_Number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '销售编号',
    `sale_user`   varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '销售人',
    `shop`        varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '销售商品',
    `shop_type`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '商品所属类',
    `supplier`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '销售客户，需要改成客户英文名',
    `num`         bigint(20)                                              NULL DEFAULT NULL COMMENT '数量',
    `specs`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '单位规格  个斤盒',
    `price`       decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '价格',
    `total_price` decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '总价',
    `time`        datetime                                                NULL DEFAULT CURRENT_TIMESTAMP COMMENT '销售时间',
    `status`      int(1)                                                  NULL DEFAULT NULL COMMENT '状态 0 完成 1进行中',
    `remark`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '销售表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sale
-- ----------------------------
INSERT INTO `sale`
VALUES (1, '123456', '王阳明', '苹果', '水果类', '老杨', 5, '箱', 1.00, 5.00, '2025-02-13 23:03:43', 1, NULL);
INSERT INTO `sale`
VALUES (6, '123456', 'yang', '鱼', '水产类', '孙安琪', 60, NULL, 3.50, 120.00, '2025-03-24 00:03:32', 1, '');
INSERT INTO `sale`
VALUES (8, '873479332', '老王', '鱼', '水产类', '方安琪', 52, NULL, 3.20, 166.40, '2025-05-04 22:09:20', 1, '');
INSERT INTO `sale`
VALUES (9, '106103464', '挖', '苹果', '水果类', '张三', 100, NULL, 3.50, 350.00, '2025-05-06 20:33:48', 1, '');
INSERT INTO `sale`
VALUES (11, '343538883', '老三', '华硕笔记本', '电器类', '孙安琪', 56, '个', 20.00, 1120.00, '2025-05-12 00:44:58', 1,
        '');
INSERT INTO `sale`
VALUES (12, '276558819', '马化腾', '香蕉', '水果类', '毛子韬', 12, '斤', 12.50, 150.00, '2025-05-12 16:39:47', 1, '');
INSERT INTO `sale`
VALUES (13, '137694665', '辰东', '雪碧', '饮品类', '罗晓明', 110, '个', 7.80, 858.00, '2025-05-12 16:50:09', 1, '');

-- ----------------------------
-- Table structure for sale_exit
-- ----------------------------
DROP TABLE IF EXISTS `sale_exit`;
CREATE TABLE `sale_exit`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `number`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '销售编号',
    `exit_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '退购编号',
    `num`         bigint(20)                                              NULL DEFAULT NULL COMMENT '退购数量',
    `price`       decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '价格',
    `total_price` decimal(10, 2)                                          NULL DEFAULT NULL COMMENT '总价',
    `time`        datetime                                                NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日期',
    `reason`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退购原因',
    `status`      int(1)                                                  NULL DEFAULT NULL COMMENT '状态 0 完成  1进行中',
    `remark`      varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '销售退货表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sale_exit
-- ----------------------------
INSERT INTO `sale_exit`
VALUES (1, '123456', '456', 202, 4.00, 7.00, '2025-02-13 23:19:36', '客户不想要了', 1, NULL);
INSERT INTO `sale_exit`
VALUES (2, '123', '456', 20, 4.00, NULL, '2025-03-24 00:15:40', '1', 1, NULL);
INSERT INTO `sale_exit`
VALUES (13, '', '267718632', 12, NULL, NULL, '2025-05-04 22:47:06', '', 1, '');
INSERT INTO `sale_exit`
VALUES (14, '123456', '373916142', 11, 2.00, 6.00, '2025-05-07 15:54:56', '', 1, '');

-- ----------------------------
-- Table structure for table_list
-- ----------------------------
DROP TABLE IF EXISTS `table_list`;
CREATE TABLE `table_list`
(
    `id`        bigint(20)                                              NOT NULL,
    `tableName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
    `md`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务模块',
    `describe`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `sort`      bigint(50)                                              NULL DEFAULT NULL COMMENT '表的顺序即业务-菜单显示顺序',
    `children`  bigint(50)                                              NULL DEFAULT NULL COMMENT '模块业务的子业务显示顺序',
    `remark`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_list
-- ----------------------------
INSERT INTO `table_list`
VALUES (1, 'customer', '基础信息管理模块', '客户表', 1, 1, NULL);
INSERT INTO `table_list`
VALUES (2, 'shop', '基础信息管理模块', '商品表', 1, 2, NULL);
INSERT INTO `table_list`
VALUES (3, 'supplier', '基础信息管理模块', '供应商表', 1, 3, NULL);
INSERT INTO `table_list`
VALUES (4, 'note', '备忘录', '笔记表', 2, 1, NULL);
INSERT INTO `table_list`
VALUES (5, 'purchase', '进销管理模块', '采购表', 3, 1, NULL);
INSERT INTO `table_list`
VALUES (6, 'purchase_exit', '进销管理模块', '采购退货表', 3, 2, NULL);
INSERT INTO `table_list`
VALUES (7, 'sale', '进销管理模块', '销售表', 3, 3, NULL);
INSERT INTO `table_list`
VALUES (8, 'sale_exit', '进销管理模块', '销售退货表', 3, 4, NULL);
INSERT INTO `table_list`
VALUES (9, 'depository', '仓库管理模块', '仓库表', 4, 2, NULL);
INSERT INTO `table_list`
VALUES (11, 'depository_in', '仓库管理模块', '入库清单表', 4, 3, NULL);
INSERT INTO `table_list`
VALUES (12, 'depository_out', '仓库管理模块', '出库清单表', 4, 4, NULL);
INSERT INTO `table_list`
VALUES (13, 'admin', '系统管理模块', '用户表', 5, 1, NULL);
INSERT INTO `table_list`
VALUES (14, 'role', '系统管理模块', '角色表', 5, 2, NULL);
INSERT INTO `table_list`
VALUES (15, 'dept', '系统管理模块', '部门表', 5, 3, NULL);
INSERT INTO `table_list`
VALUES (16, 'menu', '系统管理模块', '菜单表', 5, 4, NULL);
INSERT INTO `table_list`
VALUES (17, 'stock', '仓库管理模块', '库存清单列表', 4, 1, NULL);

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `dept`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '部门id或者名称',
    `user_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '账户',
    `true_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '真实姓名',
    `password`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
    `salt`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '盐值',
    `icon`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像路径',
    `status`      int(1)                                                  NULL DEFAULT 0 COMMENT '0正常 1停用',
    `e_mail`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '邮箱',
    `phone`       varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '电话',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `login_ip`    varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录ip',
    `login_date`  datetime                                                NULL DEFAULT NULL COMMENT '最后登录时间',
    `sex`         int(1)                                                  NULL DEFAULT 0 COMMENT '0男 1女 ',
    `remark`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin`
VALUES (1, '107', 'admin', '张三', '$2a$10$iFYzvKv03IKIT19PaBsSOefCNCjNpoC/GS67N9A0X2tFSjg/.E2XS', '', NULL, 0,
        '107348698@qq.com', '13521253828', '2025-01-08 15:05:52', NULL, '2025-01-08 15:05:00', 0, '12');
INSERT INTO `admin`
VALUES (2, '123', 'test', 'test', '$2a$10$NGdBl9UrrAGAjuNedDz2qOmjoi8OjxMbH7/Y3FUFTRf6iXwMoMu6i', NULL, NULL, 0,
        'hazishik8@gmail.com', '18321784059', '2025-04-27 19:40:08', NULL, NULL, 1, '11');
INSERT INTO `admin`
VALUES (15, '2', 'saleMan', '辰东', '$2a$10$S/go13Lz2DFMsqlDiro0Y.cO4.vtRZ4PNubvuVBZCTvcRm4pvYRq.', NULL, NULL, 0, '',
        '15685545557', NULL, NULL, NULL, 0, '');
INSERT INTO `admin`
VALUES (16, '10', 'wareHouse', '马化龙', '$2a$10$upiKjmC3tNDe3XDZMqvSWek79ZtSEHIpsLSk/I9hAJCAWdiccLiTC', NULL, NULL, 0,
        '', '15954263248', NULL, NULL, NULL, 0, '');

-- ----------------------------
-- Table structure for admin_dept_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_dept_relation`;
CREATE TABLE `admin_dept_relation`
(
    `id`      bigint(20) NOT NULL,
    `role_id` bigint(20) NOT NULL COMMENT '角色id',
    `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = latin1
  COLLATE = latin1_swedish_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_dept_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_relation`;
CREATE TABLE `admin_role_relation`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `admin_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
    `role_id`  bigint(20) NULL DEFAULT NULL COMMENT '角色id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 24
  CHARACTER SET = latin1
  COLLATE = latin1_swedish_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role_relation
-- ----------------------------
INSERT INTO `admin_role_relation`
VALUES (16, 1, 1);
INSERT INTO `admin_role_relation`
VALUES (18, 6, 1);
INSERT INTO `admin_role_relation`
VALUES (19, 5, 2);
INSERT INTO `admin_role_relation`
VALUES (21, 2, 3);
INSERT INTO `admin_role_relation`
VALUES (22, 15, 2);
INSERT INTO `admin_role_relation`
VALUES (23, 16, 4);

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`
(
    `id`          bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT 'id',
    `parent_id`   bigint(20)                                             NULL DEFAULT NULL COMMENT '父部门id',
    `dept_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
    `order_num`   varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示顺序',
    `leader`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
    `phone`       varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `status`      int(1)                                                 NULL DEFAULT NULL COMMENT '部门状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime                                               NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime                                               NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '部门表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept`
VALUES (2, 100, '深圳总公司', '1', '依', '16482618330', 0, '0', 'admin', '2025-01-08 15:35:55', '',
        '2025-05-07 12:05:30');
INSERT INTO `dept`
VALUES (3, 100, '长沙分公司', '2', '若依', '13016238720', 0, '0', 'admin', '2025-01-08 15:35:55', '',
        '2025-04-25 22:11:45');
INSERT INTO `dept`
VALUES (4, 101, '研发部门', '1', '若依', '16567564426', 0, '0', 'admin', '2025-01-08 15:35:55', '',
        '2025-04-27 20:34:24');
INSERT INTO `dept`
VALUES (5, 101, '市场部门', '2', '老三', '18489450107', 0, '0', 'admin', '2025-01-08 15:35:55', '',
        '2025-05-07 12:05:34');
INSERT INTO `dept`
VALUES (6, 101, '测试部门', '3', '若依', '19479515718', 0, '0', 'admin', '2025-01-08 15:35:55', '',
        '2025-05-05 18:42:33');
INSERT INTO `dept`
VALUES (7, 101, '财务部门', '4', '若依', '13121649314', 0, '0', 'admin', '2025-01-08 15:35:55', '', NULL);
INSERT INTO `dept`
VALUES (8, 101, '运维部门', '5', '若依', '19224141234', 0, '0', 'admin', '2025-01-08 15:35:55', '', NULL);
INSERT INTO `dept`
VALUES (9, 102, '市场部门', '1', '若依', '1033684022', 0, '0', 'admin', '2025-01-08 15:35:55', '', NULL);
INSERT INTO `dept`
VALUES (10, 102, '财务部门', '2', '若依', '19690068075', 0, '0', 'admin', '2025-01-08 15:35:55', '', NULL);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`
(
    `id`          int(10) UNSIGNED                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `operation`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '操作',
    `method`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
    `params`      varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '请求参数',
    `time`        bigint(20)                                              NULL DEFAULT NULL COMMENT '执行时长（毫秒）',
    `create_date` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 51
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log`
VALUES (1, 'name', '123', 'post', '123', 25, '2025-07-20 15:03:51');
INSERT INTO `log`
VALUES (10, 'admin', '系统', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 26,
        '2025-07-20 15:57:08');
INSERT INTO `log`
VALUES (11, 'admin', '系统', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 20,
        '2025-07-20 15:57:26');
INSERT INTO `log`
VALUES (12, 'admin', '系统', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 20,
        '2025-07-20 15:57:28');
INSERT INTO `log`
VALUES (13, 'admin', '系统管理', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 21,
        '2025-07-20 16:05:42');
INSERT INTO `log`
VALUES (14, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 315,
        '2025-07-20 16:27:41');
INSERT INTO `log`
VALUES (15, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[]', 22,
        '2025-07-20 16:27:42');
INSERT INTO `log`
VALUES (16, 'admin', '系统管理-角色', 'com.ygy.jxc.service.Impl.XtRoleServiceImpl.list()', '[\"\",5,1]', 33,
        '2025-07-20 16:27:43');
INSERT INTO `log`
VALUES (17, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[\"\",5,1]', 42,
        '2025-07-20 16:27:43');
INSERT INTO `log`
VALUES (18, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,1]', 18,
        '2025-07-20 16:27:45');
INSERT INTO `log`
VALUES (19, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,2]', 12,
        '2025-07-20 16:27:46');
INSERT INTO `log`
VALUES (20, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,1]', 38,
        '2025-07-20 16:27:49');
INSERT INTO `log`
VALUES (21, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,2]', 11,
        '2025-07-20 16:27:50');
INSERT INTO `log`
VALUES (22, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[\"\",5,1]', 14,
        '2025-07-20 16:27:51');
INSERT INTO `log`
VALUES (23, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,1]', 10,
        '2025-07-20 16:27:52');
INSERT INTO `log`
VALUES (24, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,3]', 8,
        '2025-07-20 16:27:53');
INSERT INTO `log`
VALUES (25, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,1]', 20,
        '2025-07-20 16:27:57');
INSERT INTO `log`
VALUES (26, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,4]', 7,
        '2025-07-20 16:27:59');
INSERT INTO `log`
VALUES (27, 'admin', '系统管理-角色', 'com.ygy.jxc.service.Impl.XtRoleServiceImpl.list()', '[\"\",5,1]', 7,
        '2025-07-20 16:28:05');
INSERT INTO `log`
VALUES (28, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,1]', 24,
        '2025-07-20 16:28:06');
INSERT INTO `log`
VALUES (29, 'admin', '系统管理-日志', 'com.ygy.jxc.service.Impl.XtLogServiceImpl.getList()', '[null,5,4]', 8,
        '2025-07-20 16:28:08');
INSERT INTO `log`
VALUES (30, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[\"\",5,1]', 284,
        '2025-07-20 16:29:04');
INSERT INTO `log`
VALUES (31, 'admin', '系统管理-角色', 'com.ygy.jxc.service.Impl.XtRoleServiceImpl.list()', '[\"\",5,1]', 22,
        '2025-07-20 16:29:14');
INSERT INTO `log`
VALUES (32, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[]', 20,
        '2025-07-20 16:29:16');
INSERT INTO `log`
VALUES (33, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 35,
        '2025-07-20 16:29:16');
INSERT INTO `log`
VALUES (34, 'test', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminAll()', '[]', 6,
        '2025-07-30 20:41:31');
INSERT INTO `log`
VALUES (35, 'test', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminAll()', '[]', 5,
        '2025-07-30 20:41:32');
INSERT INTO `log`
VALUES (36, 'test', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminAll()', '[]', 4,
        '2025-07-30 20:41:35');
INSERT INTO `log`
VALUES (37, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminAll()', '[]', 5,
        '2025-08-12 21:18:42');
INSERT INTO `log`
VALUES (38, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 12,
        '2025-08-12 21:18:46');
INSERT INTO `log`
VALUES (39, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[]', 60,
        '2025-08-12 21:18:46');
INSERT INTO `log`
VALUES (40, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[\"\",5,1]', 13,
        '2025-08-12 21:18:47');
INSERT INTO `log`
VALUES (41, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[\"\",5,1]', 54,
        '2025-08-13 13:59:46');
INSERT INTO `log`
VALUES (42, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 64,
        '2025-08-13 14:35:29');
INSERT INTO `log`
VALUES (43, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[]', 73,
        '2025-08-13 14:35:29');
INSERT INTO `log`
VALUES (44, 'admin', '系统管理-角色', 'com.ygy.jxc.service.Impl.XtRoleServiceImpl.list()', '[\"\",5,1]', 70,
        '2025-08-13 14:35:30');
INSERT INTO `log`
VALUES (45, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[\"\",5,1]', 50,
        '2025-08-13 14:35:31');
INSERT INTO `log`
VALUES (46, 'admin', '系统管理-角色', 'com.ygy.jxc.service.Impl.XtRoleServiceImpl.list()', '[\"\",5,1]', 36,
        '2025-08-13 14:35:33');
INSERT INTO `log`
VALUES (47, 'admin', '系统管理-部门', 'com.ygy.jxc.service.Impl.XtDeptServiceImpl.list()', '[]', 42,
        '2025-08-13 14:35:33');
INSERT INTO `log`
VALUES (48, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminList()', '[\"\",5,1]', 55,
        '2025-08-13 14:35:33');
INSERT INTO `log`
VALUES (49, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminAll()', '[]', 7,
        '2025-09-08 12:29:37');
INSERT INTO `log`
VALUES (50, 'admin', '系统管理-员工', 'com.ygy.jxc.service.Impl.XtAdminServiceImpl.getAdminAll()', '[]', 6,
        '2025-09-08 12:29:39');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称 label',
    `parent_id`   bigint(20)                                              NULL DEFAULT NULL COMMENT '父级ID',
    `level`       int(4)                                                  NULL DEFAULT NULL COMMENT '菜单级数',
    `sort`        int(4)                                                  NULL DEFAULT NULL COMMENT '菜单排序',
    `name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端名称',
    `icon`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端图标',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `hidden`      int(1)                                                  NULL DEFAULT NULL COMMENT '状态 0正常 1隐藏',
    `url`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址',
    `path`        varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'vue路径',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 25
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '后台菜单表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu`
VALUES (1, '基础信息管理', 0, 0, 1, 'jcmd', 'user', '2025-02-02 14:50:36', 0, NULL, '/');
INSERT INTO `menu`
VALUES (2, '客户资料', 1, 1, 0, 'customer', 'folder', '2025-02-02 14:51:50', 0, 'jcmd/customer', '/customer');
INSERT INTO `menu`
VALUES (3, '供应商资料', 1, 1, 3, 'supplier', 'folder', '2025-02-02 14:52:44', 0, 'jcmd/supplier', '/supplier');
INSERT INTO `menu`
VALUES (4, '商品资料', 1, 1, 1, 'shop', 'folder', '2025-02-02 14:53:51', 0, 'jcmd/shop', '/shop');
INSERT INTO `menu`
VALUES (5, '备忘录', 0, 0, 2, 'bjmd', 'notebook-1', '2025-02-02 16:54:07', 0, NULL, '/');
INSERT INTO `menu`
VALUES (6, '笔记', 5, 1, 0, 'note', 'notebook-1', '2025-02-02 16:55:18', 0, 'bjmd/note', '/note');
INSERT INTO `menu`
VALUES (7, '进销管理', 0, 0, 3, 'jxmd', 'sell', '2025-02-04 16:18:00', 0, NULL, NULL);
INSERT INTO `menu`
VALUES (8, '采购', 7, 1, 1, 'purchase', 'jxmd-purchase', '2025-02-04 16:19:22', 0, 'jxmd/purchase', '/purchase');
INSERT INTO `menu`
VALUES (9, '采购退货', 7, 1, 2, 'purchaseExit', 'jxmd-purchaseExit', '2025-02-04 16:20:16', 0, 'jxmd/purchaseExit',
        '/purchaseExit');
INSERT INTO `menu`
VALUES (10, '销售', 7, 1, 3, 'sale', 'jxmd-sale', '2025-02-07 16:22:38', 0, 'jxmd/sale', '/sale');
INSERT INTO `menu`
VALUES (11, '销售退货', 7, 1, 4, 'saleExit', 'jxmd-saleExit', '2025-02-07 16:23:14', 0, 'jxmd/saleExit', '/saleExit');
INSERT INTO `menu`
VALUES (12, '仓库管理', 0, 0, 4, 'ckmd', 's-order', '2025-02-07 16:29:13', 0, NULL, '');
INSERT INTO `menu`
VALUES (13, '库存列表', 12, 1, 1, 'stockList', 'ckmd-stock', '2025-02-07 16:29:51', 0, 'ckmd/stockList', '/stockList');
INSERT INTO `menu`
VALUES (14, '入库清单', 12, 3, 2, 'depositoryIn', 'ckmd-depositoryIn', '2025-02-07 16:30:13', 0, 'ckmd/depositoryIn',
        '/depositoryIn');
INSERT INTO `menu`
VALUES (15, '出库清单', 12, 4, 3, 'depositoryOut', 'ckmd-depositoryOut', '2025-02-07 16:30:53', 0, 'ckmd/depositoryOut',
        '/depositoryOut');
INSERT INTO `menu`
VALUES (16, '系统管理', 0, 0, 5, 'xtmd', 's-platform', '2025-02-07 16:29:13', 0, NULL, NULL);
INSERT INTO `menu`
VALUES (17, '员工管理', 16, 1, 0, 'adminList', 'setting', '2025-02-07 16:30:53', 0, 'xtmd/adminList', '/adminList');
INSERT INTO `menu`
VALUES (18, '角色管理', 16, 1, 1, 'roleLlist', 'xtmd-rolelist', '2025-02-07 16:30:53', 0, 'xtmd/roleList', '/roleList');
INSERT INTO `menu`
VALUES (19, '部门管理', 16, 1, 2, 'deptList', 'xtmd-deptList', '2025-02-07 16:30:53', 0, 'xtmd/deptList', '/deptList');
INSERT INTO `menu`
VALUES (20, '系统配置', 16, 1, 3, 'syscfg', 'setting', '2025-02-07 16:30:53', 0, 'xtmd/syscfg', '/syscfg');
INSERT INTO `menu`
VALUES (21, '仓库列表', 12, 2, 0, 'depository', 'setting', '2025-03-29 13:44:34', 0, 'ckmd/depositoryList',
        '/depository');
INSERT INTO `menu`
VALUES (22, '首页', 0, 0, 0, 'home', 's-home', '2025-03-31 16:10:48', 0, NULL, '/');
INSERT INTO `menu`
VALUES (23, '部门资料', 1, 1, 2, 'dept', 'folder', '2025-03-31 16:14:13', 0, 'jcmd/dept', '/dept');
INSERT INTO `menu`
VALUES (24, '系统日志', 16, 4, 4, 'log', NULL, '2025-07-20 14:43:00', NULL, 'xtmd/log', '/log');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `role_id`     bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色名称',
    `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
    `status`      int(1)                                                  NULL DEFAULT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag`    char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `build_user`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime                                                NULL DEFAULT NULL COMMENT '创建时间',
    `update_user` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime                                                NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role`
VALUES (1, 'admin', '拥有全部菜单', 0, '0', 'admin', '2025-04-25 22:17:15', 'admin', '2025-05-12 15:59:08',
        '超级管理员');
INSERT INTO `role`
VALUES (2, 'sale', '首页、基础信息、备忘录、进销模块', 0, '0', 'admin', '2025-05-06 13:44:04', 'admin',
        '2025-05-12 16:02:06', '销售管理员及其销售人员');
INSERT INTO `role`
VALUES (3, 'sys', '全部菜单', 0, '0', 'admin', '2025-05-12 15:58:57', 'admin', '2025-05-12 15:59:13', '系统管理员');
INSERT INTO `role`
VALUES (4, 'warehouse', '首页、基础信息、备忘录、仓库管理', 0, '0', 'admin', '2025-05-12 15:58:59', 'admin',
        '2025-05-12 16:02:57', '仓库管理员及其仓库人员');

-- ----------------------------
-- Table structure for role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_menu_relation`;
CREATE TABLE `role_menu_relation`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
    `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 264
  CHARACTER SET = latin1
  COLLATE = latin1_swedish_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu_relation
-- ----------------------------
INSERT INTO `role_menu_relation`
VALUES (173, 2, 22);
INSERT INTO `role_menu_relation`
VALUES (174, 2, 1);
INSERT INTO `role_menu_relation`
VALUES (175, 2, 2);
INSERT INTO `role_menu_relation`
VALUES (176, 2, 4);
INSERT INTO `role_menu_relation`
VALUES (177, 2, 23);
INSERT INTO `role_menu_relation`
VALUES (178, 2, 3);
INSERT INTO `role_menu_relation`
VALUES (179, 2, 5);
INSERT INTO `role_menu_relation`
VALUES (180, 2, 6);
INSERT INTO `role_menu_relation`
VALUES (181, 2, 7);
INSERT INTO `role_menu_relation`
VALUES (182, 2, 8);
INSERT INTO `role_menu_relation`
VALUES (183, 2, 9);
INSERT INTO `role_menu_relation`
VALUES (184, 2, 10);
INSERT INTO `role_menu_relation`
VALUES (185, 2, 11);
INSERT INTO `role_menu_relation`
VALUES (209, 3, 22);
INSERT INTO `role_menu_relation`
VALUES (210, 3, 2);
INSERT INTO `role_menu_relation`
VALUES (211, 3, 5);
INSERT INTO `role_menu_relation`
VALUES (212, 3, 6);
INSERT INTO `role_menu_relation`
VALUES (213, 3, 7);
INSERT INTO `role_menu_relation`
VALUES (214, 3, 8);
INSERT INTO `role_menu_relation`
VALUES (215, 3, 9);
INSERT INTO `role_menu_relation`
VALUES (216, 3, 10);
INSERT INTO `role_menu_relation`
VALUES (217, 3, 11);
INSERT INTO `role_menu_relation`
VALUES (218, 3, 12);
INSERT INTO `role_menu_relation`
VALUES (219, 3, 21);
INSERT INTO `role_menu_relation`
VALUES (220, 3, 13);
INSERT INTO `role_menu_relation`
VALUES (221, 3, 14);
INSERT INTO `role_menu_relation`
VALUES (222, 3, 15);
INSERT INTO `role_menu_relation`
VALUES (223, 3, 16);
INSERT INTO `role_menu_relation`
VALUES (224, 3, 17);
INSERT INTO `role_menu_relation`
VALUES (225, 3, 18);
INSERT INTO `role_menu_relation`
VALUES (226, 3, 19);
INSERT INTO `role_menu_relation`
VALUES (227, 3, 20);
INSERT INTO `role_menu_relation`
VALUES (228, 4, 22);
INSERT INTO `role_menu_relation`
VALUES (229, 4, 1);
INSERT INTO `role_menu_relation`
VALUES (230, 4, 2);
INSERT INTO `role_menu_relation`
VALUES (231, 4, 4);
INSERT INTO `role_menu_relation`
VALUES (232, 4, 23);
INSERT INTO `role_menu_relation`
VALUES (233, 4, 3);
INSERT INTO `role_menu_relation`
VALUES (234, 4, 5);
INSERT INTO `role_menu_relation`
VALUES (235, 4, 6);
INSERT INTO `role_menu_relation`
VALUES (236, 4, 12);
INSERT INTO `role_menu_relation`
VALUES (237, 4, 21);
INSERT INTO `role_menu_relation`
VALUES (238, 4, 13);
INSERT INTO `role_menu_relation`
VALUES (239, 4, 14);
INSERT INTO `role_menu_relation`
VALUES (240, 4, 15);
INSERT INTO `role_menu_relation`
VALUES (241, 1, 1);
INSERT INTO `role_menu_relation`
VALUES (242, 1, 2);
INSERT INTO `role_menu_relation`
VALUES (243, 1, 4);
INSERT INTO `role_menu_relation`
VALUES (244, 1, 23);
INSERT INTO `role_menu_relation`
VALUES (245, 1, 3);
INSERT INTO `role_menu_relation`
VALUES (246, 1, 5);
INSERT INTO `role_menu_relation`
VALUES (247, 1, 6);
INSERT INTO `role_menu_relation`
VALUES (248, 1, 7);
INSERT INTO `role_menu_relation`
VALUES (249, 1, 8);
INSERT INTO `role_menu_relation`
VALUES (250, 1, 9);
INSERT INTO `role_menu_relation`
VALUES (251, 1, 10);
INSERT INTO `role_menu_relation`
VALUES (252, 1, 11);
INSERT INTO `role_menu_relation`
VALUES (253, 1, 12);
INSERT INTO `role_menu_relation`
VALUES (254, 1, 21);
INSERT INTO `role_menu_relation`
VALUES (255, 1, 13);
INSERT INTO `role_menu_relation`
VALUES (256, 1, 14);
INSERT INTO `role_menu_relation`
VALUES (257, 1, 15);
INSERT INTO `role_menu_relation`
VALUES (258, 1, 16);
INSERT INTO `role_menu_relation`
VALUES (259, 1, 17);
INSERT INTO `role_menu_relation`
VALUES (260, 1, 18);
INSERT INTO `role_menu_relation`
VALUES (261, 1, 19);
INSERT INTO `role_menu_relation`
VALUES (262, 1, 20);
INSERT INTO `role_menu_relation`
VALUES (263, 1, 24);

SET FOREIGN_KEY_CHECKS = 1;
