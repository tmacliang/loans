/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : lending_system

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-10-26 17:18:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `ACCOUNT_ID` bigint(19) unsigned NOT NULL COMMENT '账户ID ',
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `CUSTOMER_ID` bigint(64) unsigned NOT NULL COMMENT '客户ID',
  `LEVEL_NO` tinyint(4) NOT NULL COMMENT '层级',
  `LEVEL_1_ACT_ID` bigint(19) unsigned NOT NULL COMMENT '一级账户ID',
  `LEVEL_2_ACT_ID` bigint(19) unsigned DEFAULT NULL COMMENT '二级账户ID',
  `LEVEL_3_ACT_ID` bigint(19) unsigned DEFAULT NULL COMMENT '三级账户ID',
  `AUTO_STATUS_CODE` char(4) NOT NULL COMMENT '自动状态码',
  `AUTO_STATUS_SET_DATE` int(8) NOT NULL COMMENT '自动状态设置时间',
  `MANU_STATUS_CODE` char(4) NOT NULL COMMENT '手工状态码',
  `MANU_STATUS_SET_DATE` int(8) NOT NULL COMMENT '手工状态设置时间',
  `CURRENT_BALANCE` decimal(17,2) NOT NULL COMMENT '当前余额',
  `CREDIT_LIMIT` decimal(17,2) unsigned NOT NULL COMMENT '信用额度',
  `CURRENT_CYCLE_NO` int(11) unsigned NOT NULL COMMENT '当前账期号',
  `CYCLE_TRIGGER_SEQ` bigint(19) unsigned NOT NULL COMMENT '触发器序号',
  `OUTSTANDING_TXN_AMT` decimal(17,2) NOT NULL COMMENT '未入账金额',
  `CURR_DUE_DATE` int(8) unsigned DEFAULT NULL COMMENT '到期还款日',
  `PREFERRED_CYCLE_DAY` smallint(2) unsigned NOT NULL COMMENT '账期日',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `CURR_CYCLE_DATE` int(8) unsigned NOT NULL COMMENT '当前账期日',
  `PREV_CYCLE_DATE` int(8) NOT NULL COMMENT '上一次账期处理日',
  `TXN_FEE_RATE` decimal(9,5) NOT NULL DEFAULT '0.00000' COMMENT '个性化手续费率',
  `INTEREST_RATE` decimal(9,5) NOT NULL DEFAULT '0.00000' COMMENT '个性化利率或滞纳金费率',
  `DLQ_FEE_RATE` decimal(9,5) NOT NULL DEFAULT '0.00000' COMMENT '违约金费率',
  `INTEREST_FREE_DAYS` tinyint(2) NOT NULL DEFAULT '0' COMMENT '免息天数',
  `IN_DLQ_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '迟缴状态标识',
  `TXN_FEE_RATE_TYPE` char(4) NOT NULL COMMENT '个性化手续费标识',
  `WAIVE_INTEREST_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '利息免除标示(Y/N)',
  `WAIVE_INTEREST_START_DATE` int(8) NOT NULL COMMENT '利息免除开始日期',
  `WAIVE_INTEREST_END_DATE` int(8) NOT NULL COMMENT '利息免除结束日期',
  `WAIVE_TXN_FEE_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '手续费免除标示(Y/N)',
  `WAIVE_TXN_FEE_START_DATE` int(8) NOT NULL COMMENT '手续费免除开始日期',
  `WAIVE_TXN_FEE_END_DATE` int(8) NOT NULL COMMENT '手续费免除结束日期',
  `WAIVE_OTHER_FEE_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '其他费免除标示(Y/N)',
  `WAIVE_OTHER_FEE_START_DATE` int(8) NOT NULL COMMENT '其他费免除开始日期',
  `WAIVE_OTHER_FEE_END_DATE` int(8) NOT NULL COMMENT '其他费免除结束日期',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) unsigned NOT NULL COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `TOTAL_INTEREST_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '总利息',
  `TOTAL_TXN_FEE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '总交易分期首先费',
  `TOTAL_SERVICE_FEE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '总服务费',
  `TOTAL_DLQ_FEE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '总违约金',
  `TOTAL_OTHER_FEE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '其他费用',
  PRIMARY KEY (`ACCOUNT_ID`),
  KEY `IX_CUSTID_PRODCODE_MODCODE` (`CUSTOMER_ID`,`PRODUCT_ID`,`ACT_TYPE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户表';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('6763013902173185', 'QBAO', 'LIFE', '6763013902173184', '1', '6763013902173185', null, null, 'ACTP', '20171026', 'NORM', '20171026', '0.00', '10000.00', '1', '6763013903221761', '0.00', '20171121', '26', 'CNY', '20171126', '20171026', '0.01500', '0.00050', '0.02000', '26', 'N', 'NORM', 'N', '20171026', '20171026', 'N', '20171026', '20171026', 'Y', '20171026', '20171026', 'SYS', '2017-10-26 14:20:51', '0', 'SYS', '2017-10-26 14:20:51', '0.00', '0.00', '0.00', '0.00', '0.00');

-- ----------------------------
-- Table structure for `act_credit_data`
-- ----------------------------
DROP TABLE IF EXISTS `act_credit_data`;
CREATE TABLE `act_credit_data` (
  `ACCOUNT_ID` bigint(20) unsigned NOT NULL COMMENT '账户ID',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `CREDIT_LIMIT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '信用额度',
  `WHOLE_FLAG` char(1) NOT NULL COMMENT '总控信用额度标志',
  `TOTAL_POSTED_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '总入账金额',
  `OUTSTANDING_AUTH_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '未入账金额',
  `AVAILABLE_BALANCE` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '溢缴款余额',
  `LAST_LIMIT_CHANGE_DATE` int(8) NOT NULL COMMENT '最近信用额度调整时间',
  `TEMP_CREDIT_LIMIT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '临时信用额度',
  `TEMP_CREDIT_START_DATE` int(8) NOT NULL COMMENT '临时信用额度有效期起',
  `TEMP_CREDIT_END_DATE` int(8) NOT NULL COMMENT '临时信用额度有效期止',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL COMMENT '修改版本号',
  PRIMARY KEY (`ACCOUNT_ID`,`CURRENCY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户信用表';

-- ----------------------------
-- Records of act_credit_data
-- ----------------------------
INSERT INTO `act_credit_data` VALUES ('6763013902173185', 'CNY', '10000.00', 'Y', '0.00', '0.00', '0.00', '20171026', '0.00', '20171026', '20171026', 'SYS', '2017-10-26 14:20:51', 'SYS', '2017-10-26 14:20:51', '0');

-- ----------------------------
-- Table structure for `act_process_ctrl`
-- ----------------------------
DROP TABLE IF EXISTS `act_process_ctrl`;
CREATE TABLE `act_process_ctrl` (
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `CURRENCY_UNIT` char(4) NOT NULL DEFAULT '0' COMMENT '币种精度',
  `DEFAULT_TXN_FEE_RATE` decimal(9,5) NOT NULL DEFAULT '0.00000' COMMENT '缺省手续费率',
  `DEFAULT_INTEREST_RATE` decimal(9,5) NOT NULL DEFAULT '0.00000' COMMENT '缺省利率',
  `DEFAULT_DLQ_FEE_RATE` decimal(9,5) NOT NULL DEFAULT '0.00000' COMMENT '缺省违约金费率',
  `DEFAULT_INTEREST_FREE_DAYS` tinyint(2) NOT NULL DEFAULT '0' COMMENT '缺省免息期天数',
  `DEFAULT_CREDIT_LIMIT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '缺省信用额度',
  `DEFAULT_GRACE_DAYS` tinyint(2) NOT NULL DEFAULT '0' COMMENT '宽限期天数',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '更新用户',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '更新次数',
  PRIMARY KEY (`PRODUCT_ID`,`ACT_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户处理控制表';

-- ----------------------------
-- Records of act_process_ctrl
-- ----------------------------
INSERT INTO `act_process_ctrl` VALUES ('QBAO', 'LIFE', 'CNY', 'FEN', '0.01500', '0.00050', '0.02000', '26', '10000.00', '0', null, 'SYS', '2017-10-18 19:45:30', 'SYS', '2017-10-18 19:45:30', '0');

-- ----------------------------
-- Table structure for `bal_comp_profile`
-- ----------------------------
DROP TABLE IF EXISTS `bal_comp_profile`;
CREATE TABLE `bal_comp_profile` (
  `BCP_ID` char(4) NOT NULL COMMENT '余额成分ID',
  `BAL_TYPE` char(4) NOT NULL COMMENT '余额成分类型',
  `FLOW_TYPE` char(1) NOT NULL COMMENT '资金流向类型 C|D',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` char(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`BCP_ID`),
  UNIQUE KEY `UX_BCT_BCC` (`BAL_TYPE`,`BCP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='余额成分字典';

-- ----------------------------
-- Records of bal_comp_profile
-- ----------------------------
INSERT INTO `bal_comp_profile` VALUES ('CRED', 'CRED', 'C', '溢缴存/还款', 'SYS', '2017-09-19 14:19:09', '0', 'SYS', '2017-09-19 14:15:16');
INSERT INTO `bal_comp_profile` VALUES ('DEBT', 'ALL', 'C', '所有借记', 'SYS', '2017-10-18 17:55:44', '0', 'SYS', '2017-10-18 17:53:51');
INSERT INTO `bal_comp_profile` VALUES ('DLQF', 'FEE', 'D', '违约金手续费', 'SYS', '2017-09-19 14:18:44', '0', 'SYS', '2017-09-19 14:16:46');
INSERT INTO `bal_comp_profile` VALUES ('RETL', 'RETL', 'D', '消费', 'SYS', '2017-09-19 14:19:01', '0', 'SYS', '2017-09-19 14:12:03');
INSERT INTO `bal_comp_profile` VALUES ('RFEE', 'FEE', 'D', '消费手续费', 'SYS', '2017-09-19 14:18:53', '0', 'SYS', '2017-09-19 14:16:29');
INSERT INTO `bal_comp_profile` VALUES ('RINT', 'INST', 'D', '消费利息', 'SYS', '2017-09-19 14:18:58', '0', 'SYS', '2017-09-19 14:12:46');

-- ----------------------------
-- Table structure for `bal_comp_val`
-- ----------------------------
DROP TABLE IF EXISTS `bal_comp_val`;
CREATE TABLE `bal_comp_val` (
  `ACCOUNT_ID` bigint(19) NOT NULL COMMENT '账户ID',
  `CYCLE_NO` int(11) NOT NULL COMMENT '账期号',
  `BCP_ID` char(4) NOT NULL COMMENT '余额成分ID',
  `BAL_TYPE` char(4) NOT NULL COMMENT '余额成分类型',
  `FLOW_TYPE` char(1) NOT NULL COMMENT '资金流向类型 C|D',
  `CURRENCY_CODE` char(5) NOT NULL COMMENT '币种',
  `CTD_BALANCE` decimal(17,2) NOT NULL COMMENT '当前余额',
  `PVS_BALANCE` decimal(17,2) NOT NULL COMMENT '上期余额',
  `OLD_BALANCE` decimal(17,2) NOT NULL COMMENT '上期前余额',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  PRIMARY KEY (`ACCOUNT_ID`,`CYCLE_NO`,`BCP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='余额成分表';

-- ----------------------------
-- Records of bal_comp_val
-- ----------------------------
INSERT INTO `bal_comp_val` VALUES ('6763013902173185', '1', 'CRED', 'CRED', 'C', 'CNY', '10.00', '0.00', '0.00', 'SYS', '2017-10-26 15:29:28', 'SYS', '2017-10-26 15:29:28', '0');
INSERT INTO `bal_comp_val` VALUES ('6763013902173185', '1', 'RETL', 'RETL', 'D', 'CNY', '100.00', '0.00', '0.00', 'SYS', '2017-10-26 14:22:50', 'SYS', '2017-10-26 14:22:50', '0');

-- ----------------------------
-- Table structure for `bal_process_ctrl`
-- ----------------------------
DROP TABLE IF EXISTS `bal_process_ctrl`;
CREATE TABLE `bal_process_ctrl` (
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `BCP_ID` char(4) NOT NULL COMMENT '余额成分代码',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `WAIVE_INTEREST_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '免息标识',
  `INTEREST_TXN_CODE` char(4) DEFAULT NULL COMMENT '计息交易代码',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`PRODUCT_ID`,`ACT_TYPE_ID`,`BCP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品余额控制表';

-- ----------------------------
-- Records of bal_process_ctrl
-- ----------------------------
INSERT INTO `bal_process_ctrl` VALUES ('QBAO', 'LIFE', 'CRED', '溢缴存/还款', 'Y', null, 'SYS', '2017-10-17 15:24:59', '0', 'SYS', '2017-09-19 17:50:06');
INSERT INTO `bal_process_ctrl` VALUES ('QBAO', 'LIFE', 'DLQF', '违约金', 'Y', null, 'SYS', '2017-10-17 15:25:02', '0', 'SYS', '2017-09-19 17:50:26');
INSERT INTO `bal_process_ctrl` VALUES ('QBAO', 'LIFE', 'RETL', '消费', 'N', 'I010', 'SYS', '2017-09-19 17:59:44', '0', 'SYS', '2017-09-19 17:51:25');
INSERT INTO `bal_process_ctrl` VALUES ('QBAO', 'LIFE', 'RFEE', '消费手续费', 'Y', null, 'SYS', '2017-10-17 15:25:03', '0', 'SYS', '2017-09-19 17:51:49');
INSERT INTO `bal_process_ctrl` VALUES ('QBAO', 'LIFE', 'RINT', '消费利息', 'Y', null, 'SYS', '2017-10-17 15:25:05', '0', 'SYS', '2017-09-19 17:52:08');

-- ----------------------------
-- Table structure for `curr_process_ctrl`
-- ----------------------------
DROP TABLE IF EXISTS `curr_process_ctrl`;
CREATE TABLE `curr_process_ctrl` (
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '货币代码',
  `UNIT` char(5) NOT NULL COMMENT '转化单位',
  `POWER` int(2) NOT NULL DEFAULT '0' COMMENT '10的次幕|精度数',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建者',
  PRIMARY KEY (`CURRENCY_CODE`,`UNIT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货币单位控制表';

-- ----------------------------
-- Records of curr_process_ctrl
-- ----------------------------
INSERT INTO `curr_process_ctrl` VALUES ('CNY', 'CNY', '0', '人民币元', 'SYS', '2017-09-25 14:05:52', '0', 'SYS', '2017-09-25 14:05:33');
INSERT INTO `curr_process_ctrl` VALUES ('CNY', 'FEN', '-2', '人民币分', 'SYS', '2017-09-25 18:31:12', '0', 'SYS', '2017-09-21 17:25:57');

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `CUSTOMER_ID` bigint(20) unsigned NOT NULL COMMENT '客户ID',
  `MEMBER_ID` varchar(64) NOT NULL COMMENT '外部系统成员ID',
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `MEMBER_TYPE` varchar(5) NOT NULL COMMENT '外部系统成员类型（CUST个人）',
  `MOBILE_PHONE` varchar(18) NOT NULL COMMENT '开通手机号',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`CUSTOMER_ID`),
  UNIQUE KEY `UX_MID_PC_MC` (`MEMBER_ID`,`PRODUCT_ID`,`ACT_TYPE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('6763013902173184', '2017083112000000010006', 'QBAO', 'LIFE', 'CUST', '13718656984', 'SYS', '2017-10-26 14:20:51', '0', 'SYS', '2017-10-26 14:20:51');

-- ----------------------------
-- Table structure for `cycle_summary`
-- ----------------------------
DROP TABLE IF EXISTS `cycle_summary`;
CREATE TABLE `cycle_summary` (
  `ACCOUNT_ID` bigint(20) unsigned NOT NULL COMMENT '账号ID',
  `CYCLE_NO` int(10) unsigned NOT NULL COMMENT '账期号',
  `TOTAL_CREDITS` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '贷记交易总数',
  `TOTAL_DEBITS` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '借记交易总数',
  `TOTAL_TXNS` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '交易总数',
  `NEXT_TXN_SUMMARY_NO` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '下一个交易记录号',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `START_DATE` int(8) NOT NULL COMMENT '账期起始日',
  `END_DATE` int(8) NOT NULL COMMENT '账期结束日',
  `CYCLE_DATE` int(8) DEFAULT NULL COMMENT '账期处理日期',
  `OPEN_DUE_DATE` int(8) NOT NULL COMMENT '期初余额到期还款日',
  `OPEN_TOTAL_DUE` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '期初未还金额',
  `OPEN_BALANCE` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '期初余额',
  `CLOSE_BALANCE` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '期末余额',
  `TOTAL_PAYMENT_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '还款总额',
  `TOTAL_CYCLE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '账期内还款总额',
  `TOTAL_CREDIT_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '贷记交易总额',
  `TOTAL_DEBIT_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '借记交易总额',
  `TOTAL_GRACE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '宽限期内还款总额',
  `TOTAL_GRACE_CREDIT_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '宽限期内贷记交易总额',
  `TOTAL_GRACE_DEBIT_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '宽限期内借记交易总额',
  `MODIFIED_BY` char(64) NOT NULL,
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(10) unsigned NOT NULL COMMENT '修改版本号',
  `CREATED_BY` char(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ACCOUNT_ID`,`CYCLE_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账期表';

-- ----------------------------
-- Records of cycle_summary
-- ----------------------------
INSERT INTO `cycle_summary` VALUES ('6763013902173185', '1', '0', '0', '0', '3', 'CNY', '20171026', '20171126', null, '20171121', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'SYS', '2017-10-26 15:29:28', '2', 'SYS', '2017-10-26 14:20:52');

-- ----------------------------
-- Table structure for `iou_receipt`
-- ----------------------------
DROP TABLE IF EXISTS `iou_receipt`;
CREATE TABLE `iou_receipt` (
  `IOU_ID` bigint(20) NOT NULL COMMENT '借据ID',
  `PLAN_ID` char(5) NOT NULL COMMENT '分期计划ID',
  `ACCOUNT_ID` bigint(20) NOT NULL COMMENT '账户ID',
  `TXN_UUID` varchar(64) NOT NULL COMMENT '交易唯一号',
  `ORDER_NO` varchar(64) NOT NULL COMMENT '订单号',
  `CYCLE_NO` int(11) NOT NULL COMMENT '账期号',
  `COMMODITY_CODE` varchar(64) DEFAULT NULL COMMENT '商品编码',
  `MERCHANT_NAME` varchar(64) DEFAULT NULL COMMENT '商家名称',
  `STATUS_CODE` char(5) NOT NULL COMMENT '状态码',
  `PVS_STATUS_CODE` char(5) DEFAULT NULL COMMENT '前状态码',
  `ORIGINAL_IOU_AMT` decimal(17,2) NOT NULL COMMENT '原始交易金额',
  `ORIGINAL_IOU_TERMS` tinyint(4) NOT NULL COMMENT '原始分期期数',
  `IOU_AMT` decimal(17,2) NOT NULL COMMENT '借据本金',
  `IOU_TERMS` tinyint(4) NOT NULL COMMENT '借据分期期数',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `TXN_DATE` int(8) unsigned NOT NULL COMMENT '交易日期',
  `TXN_TIME` int(8) unsigned NOT NULL COMMENT '交易时间',
  `CURRENT_TERM_NO` tinyint(4) NOT NULL COMMENT '当前分期号',
  `POSTING_FEE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '实际入账手续费金额',
  `CURRENT_BALANCE` decimal(17,2) NOT NULL COMMENT '当前余额',
  `OUTSTANDING_TXN_AMT` decimal(17,2) NOT NULL COMMENT '未入账借据金额',
  `OUTSTANDING_TERMS` tinyint(2) NOT NULL COMMENT '未入账期数',
  `FIRST_TERM_AMT` decimal(17,2) NOT NULL COMMENT '首期还款本金',
  `FIRST_TERM_FEE_AMT` bigint(17) NOT NULL DEFAULT '0' COMMENT '首期分期手续费',
  `FIXED_TERM_AMT` decimal(17,2) NOT NULL COMMENT '除首末期外分期还款本金',
  `FIXED_TERM_FEE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '固定期分期手续费',
  `LAST_TERM_AMT` decimal(17,2) NOT NULL COMMENT '末期还款本金',
  `LAST_TERM_FEE_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '末期分期手续费',
  `TOTAL_PAYMENTS` int(11) NOT NULL COMMENT '总还款次数',
  `TOTAL_PAYMENT_AMT` decimal(17,2) NOT NULL COMMENT '总还款金额',
  `LAST_PAYMENT_DATE` int(8) DEFAULT NULL COMMENT '最后还款日期',
  `TOTAL_REVERSAL` int(11) NOT NULL COMMENT '总退款次数',
  `TOTAL_REVERSAL_AMT` decimal(10,0) NOT NULL COMMENT '总退款金额',
  `LAST_REVERSAL_DATE` int(8) DEFAULT NULL COMMENT '最后一次退款日期',
  `FEE_RATE` decimal(9,5) NOT NULL DEFAULT '0.00000' COMMENT '手续费率',
  `FEE_RATE_TYPE` char(4) NOT NULL COMMENT '交易手续费率类型',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`IOU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='借据记录表';

-- ----------------------------
-- Records of iou_receipt
-- ----------------------------
INSERT INTO `iou_receipt` VALUES ('6763045140825089', 'RIP01', '6763013902173185', '4008001000000002', '4008001000000002', '1', '10000000000', 'McDonald', 'BILL', 'ONCE', '100.00', '1', '100.00', '1', 'CNY', '20171026', '142249', '1', '0.00', '100.00', '0.00', '0', '100.00', '0', '0.00', '0.00', '0.00', '0.00', '0', '0.00', null, '0', '0', null, '0.00000', 'NORM', null, 'SYS', '2017-10-26 14:22:50', '0', 'SYS', '2017-10-26 14:22:50');

-- ----------------------------
-- Table structure for `payment_history`
-- ----------------------------
DROP TABLE IF EXISTS `payment_history`;
CREATE TABLE `payment_history` (
  `PAYMENT_NO` bigint(19) NOT NULL,
  `PAYMENT_ORDER_NO` varchar(64) NOT NULL COMMENT '还款参考号',
  `STATUS_CODE` char(4) NOT NULL COMMENT '还款状态（PEND/SUCC/FAIL/ERRO）',
  `CUSTOMER_ID` bigint(19) NOT NULL COMMENT '客户ID',
  `ACCOUNT_ID` bigint(19) NOT NULL COMMENT '账户ID',
  `IOU_ID` bigint(19) DEFAULT NULL COMMENT '借据ID',
  `TXN_ID` bigint(19) DEFAULT NULL COMMENT '交易ID',
  `PRODUCT_ID` char(4) NOT NULL COMMENT '产品ID',
  `PAYMENT_AMT` decimal(17,2) NOT NULL COMMENT '还款金额',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `PAYMENT_METHOD` char(5) NOT NULL COMMENT '还款方式',
  `REQUEST_DATE` int(8) NOT NULL COMMENT '请求日期',
  `REQUEST_TIME` int(8) NOT NULL COMMENT '请求时间',
  `RESPONSE_DATE` int(11) DEFAULT NULL COMMENT '响应日期',
  `RESPONSE_TIME` int(11) DEFAULT NULL COMMENT '响应时间',
  `COMPLETE_DATE` int(8) DEFAULT NULL COMMENT '还款完成日期',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间 ',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL COMMENT '修改版本号',
  PRIMARY KEY (`PAYMENT_NO`),
  UNIQUE KEY `UX_PAY_NO` (`PAYMENT_ORDER_NO`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment_history
-- ----------------------------
INSERT INTO `payment_history` VALUES ('6763315459523585', 'prepaymentOrderNo-20170101-001', 'SUCC', '6763013902173184', '6763013902173185', null, null, 'QBAO', '10.00', '0', 'PYMT', '20171026', '144001', '20171026', '152928', '20171026', 'SYS', '2017-10-26 14:40:01', 'SYS', '2017-10-26 15:29:28', '4');

-- ----------------------------
-- Table structure for `payment_process_ctrl`
-- ----------------------------
DROP TABLE IF EXISTS `payment_process_ctrl`;
CREATE TABLE `payment_process_ctrl` (
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `PAYMENT_METHOD` char(5) NOT NULL COMMENT '还款方式',
  `TXN_CODE` char(4) NOT NULL COMMENT '还款交易码',
  `INIT_STATUS_CODE` char(4) NOT NULL COMMENT '初始化状态码',
  `STATUS_TYPE` char(4) NOT NULL COMMENT '状态类型',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `CURRENCY_UNIT` char(4) NOT NULL COMMENT '货币单位',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`PRODUCT_ID`,`ACT_TYPE_ID`,`PAYMENT_METHOD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment_process_ctrl
-- ----------------------------
INSERT INTO `payment_process_ctrl` VALUES ('QBAO', 'LIFE', 'PYMT', 'P010', 'PEND', 'PYMT', 'CNY', 'FEN', 'SYS', '2017-10-20 11:25:58', '0', 'SYS', '2017-10-20 11:08:31');

-- ----------------------------
-- Table structure for `plan_process_ctrl`
-- ----------------------------
DROP TABLE IF EXISTS `plan_process_ctrl`;
CREATE TABLE `plan_process_ctrl` (
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `PLAN_ID` char(5) NOT NULL COMMENT '分期计划ID',
  `ACTIVE_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '启用标识',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `CURRENCY_UNIT` char(5) NOT NULL COMMENT '精度',
  `FIRST_TXN_CODE` char(4) NOT NULL COMMENT '首期交易代码',
  `FIXED_TXN_CODE` char(4) NOT NULL COMMENT '去除首末期的其他期交易代码',
  `LAST_TXN_CODE` char(4) NOT NULL COMMENT '末期交易代码',
  `MIN_INSTALLMENT_AMT` decimal(17,2) NOT NULL COMMENT '最小分期金额',
  `TXN_FEE_RATE` decimal(14,5) NOT NULL DEFAULT '0.00000' COMMENT '计划手续费利率',
  `REM_ADJUST_METHOD` char(4) NOT NULL COMMENT '除不尽剩余金额处理方法：首期/末期(FRST/LAST)',
  `FEE_PAY_METHOD` char(4) NOT NULL COMMENT '手续费收取方法（FULL 首期全额|TERM 每期收取）',
  `SERVER_FEE_CODE` char(4) DEFAULT NULL COMMENT '服务费交易代码',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`PRODUCT_ID`,`ACT_TYPE_ID`,`PLAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易计划控制表';

-- ----------------------------
-- Records of plan_process_ctrl
-- ----------------------------
INSERT INTO `plan_process_ctrl` VALUES ('QBAO', 'LIFE', 'RIP01', 'Y', 'CNY', 'FEN', 'R011', 'R011', 'R011', '100.00', '0.05000', 'FRST', 'FULL', null, '消费不分期', 'SYS', '2017-10-13 17:02:01', '0', 'SYS', '2017-09-20 14:52:02');
INSERT INTO `plan_process_ctrl` VALUES ('QBAO', 'LIFE', 'RIP03', 'Y', 'CNY', 'FEN', 'R010', 'R011', 'R011', '100.00', '0.06000', 'FRST', 'FULL', null, '消费分3期', 'SYS', '2017-10-13 17:13:38', '0', 'SYS', '2017-09-20 14:44:52');
INSERT INTO `plan_process_ctrl` VALUES ('QBAO', 'LIFE', 'RIP06', 'Y', 'CNY', 'FEN', 'R010', 'R011', 'R011', '100.00', '0.07000', 'FRST', 'FULL', null, '消费分6期', 'SYS', '2017-10-13 17:02:03', '0', 'SYS', '2017-09-20 14:45:20');
INSERT INTO `plan_process_ctrl` VALUES ('QBAO', 'LIFE', 'RIP09', 'Y', 'CNY', 'FEN', 'R010', 'R011', 'R011', '100.00', '0.08000', 'FRST', 'TERM', null, '消费分9期', 'SYS', '2017-10-13 17:04:15', '0', 'SYS', '2017-09-20 14:45:43');
INSERT INTO `plan_process_ctrl` VALUES ('QBAO', 'LIFE', 'RIP12', 'Y', 'CNY', 'FEN', 'R010', 'R011', 'R011', '100.00', '0.09000', 'FRST', 'TERM', 'S010', '消费分12期', 'SYS', '2017-10-13 17:04:14', '0', 'SYS', '2017-09-20 14:45:56');
INSERT INTO `plan_process_ctrl` VALUES ('QBAO', 'LIFE', 'RIP24', 'Y', 'CNY', 'FEN', 'R010', 'R011', 'R011', '100.00', '0.10000', 'FRST', 'TERM', 'S010', '消费分24期', 'SYS', '2017-10-13 14:47:19', '0', 'SYS', '2017-09-20 14:46:12');
INSERT INTO `plan_process_ctrl` VALUES ('QBAO', 'LIFE', 'RIP36', 'Y', 'CNY', 'FEN', 'R010', 'R011', 'R011', '100.00', '0.11000', 'FRST', 'TERM', 'S010', '消费分36期', 'SYS', '2017-10-13 14:47:20', '0', 'SYS', '2017-09-20 14:46:27');

-- ----------------------------
-- Table structure for `plan_profile`
-- ----------------------------
DROP TABLE IF EXISTS `plan_profile`;
CREATE TABLE `plan_profile` (
  `PLAN_ID` char(5) NOT NULL COMMENT '计划ID',
  `PLAN_TERMS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '计划总阶段数',
  `INIT_STATUS_CODE` char(4) NOT NULL COMMENT '计划初始状态码',
  `STATUS_TYPE` char(4) NOT NULL COMMENT '初始化状态类型',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`PLAN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分期计划字典';

-- ----------------------------
-- Records of plan_profile
-- ----------------------------
INSERT INTO `plan_profile` VALUES ('RIP01', '1', 'ONCE', 'IOU', '消费不分期', 'SYS', '2017-10-20 10:42:46', '0', 'SYS', '2017-09-20 14:51:35');
INSERT INTO `plan_profile` VALUES ('RIP03', '3', 'TERM', 'IOU', '消费分3期', 'SYS', '2017-10-20 10:42:47', '0', 'SYS', '2017-09-20 11:44:45');
INSERT INTO `plan_profile` VALUES ('RIP06', '6', 'TERM', 'IOU', '消费分6期', 'SYS', '2017-10-20 10:42:48', '0', 'SYS', '2017-09-20 11:46:58');
INSERT INTO `plan_profile` VALUES ('RIP09', '9', 'TERM', 'IOU', '消费分期9期', 'SYS', '2017-10-20 10:42:48', '0', 'SYS', '2017-10-13 17:09:47');
INSERT INTO `plan_profile` VALUES ('RIP12', '12', 'TERM', 'IOU', '消费分12期', 'SYS', '2017-10-20 10:42:48', '0', 'SYS', '2017-09-20 11:47:20');
INSERT INTO `plan_profile` VALUES ('RIP24', '24', 'TERM', 'IOU', '消费分24期', 'SYS', '2017-10-20 10:42:49', '0', 'SYS', '2017-09-20 11:47:40');
INSERT INTO `plan_profile` VALUES ('RIP36', '36', 'TERM', 'IOU', '消费分36期', 'SYS', '2017-10-20 10:42:49', '0', 'SYS', '2017-09-20 14:39:13');

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL COMMENT '修改版本号',
  `CREATED_BY` char(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`PRODUCT_ID`,`ACT_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('QBAO', 'LIFE', null, 'SYS', '2017-09-21 16:19:01', '0', 'SYS', '2017-07-20 14:05:40');

-- ----------------------------
-- Table structure for `status_code`
-- ----------------------------
DROP TABLE IF EXISTS `status_code`;
CREATE TABLE `status_code` (
  `STATUS_CODE` char(4) NOT NULL COMMENT '状态码',
  `STATUS_TYPE` char(4) NOT NULL COMMENT '状态类型',
  `NEXT_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '状态向后移动标识',
  `NEXT_CODE` char(4) NOT NULL COMMENT '下一状态码',
  `LAST_CODE` char(4) NOT NULL COMMENT '最后状态码',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`STATUS_CODE`,`STATUS_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='状态码表';

-- ----------------------------
-- Records of status_code
-- ----------------------------
INSERT INTO `status_code` VALUES ('BILL', 'IOU', 'N', 'BILL', 'BILL', '已经全部入账', 'SYS', '2017-10-16 19:03:48', '0', 'SYS', '2017-09-27 10:48:27');
INSERT INTO `status_code` VALUES ('ERRO', 'PYMT', 'N', 'ERRO', 'ERRO', '还款失败', 'SYS', '2017-10-20 11:11:57', '0', 'SYS', '2017-10-20 11:11:05');
INSERT INTO `status_code` VALUES ('ONCE', 'IOU', 'Y', 'BILL', 'BILL', '一次性入账', 'SYS', '2017-10-16 19:02:37', '0', 'SYS', '2017-10-16 19:01:55');
INSERT INTO `status_code` VALUES ('PEND', 'PYMT', 'Y', 'ERRO', 'SUCC', '还款等待', 'SYS', '2017-10-20 11:12:51', '0', 'SYS', '2017-10-20 10:36:24');
INSERT INTO `status_code` VALUES ('SUCC', 'PYMT', 'N', 'SUCC', 'SUCC', '还款成功', 'SYS', '2017-10-20 11:12:43', '0', 'SYS', '2017-10-20 11:12:43');
INSERT INTO `status_code` VALUES ('TERM', 'IOU', 'Y', 'TERM', 'BILL', '分期入账', 'SYS', '2017-10-16 19:03:29', '1', 'SYS', '2017-09-27 10:44:56');

-- ----------------------------
-- Table structure for `txn_process_ctrl`
-- ----------------------------
DROP TABLE IF EXISTS `txn_process_ctrl`;
CREATE TABLE `txn_process_ctrl` (
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `TXN_CODE` char(4) NOT NULL COMMENT '交易代码',
  `FEE_CODE` char(4) DEFAULT NULL COMMENT '手续费代码',
  `INIT_BCP_ID` char(4) NOT NULL COMMENT '初始化的余额成分ID',
  `BCP_ID` char(4) NOT NULL COMMENT '余额成分ID',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`PRODUCT_ID`,`ACT_TYPE_ID`,`TXN_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品交易控制表';

-- ----------------------------
-- Records of txn_process_ctrl
-- ----------------------------
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'D010', null, 'CRED', 'DLQF', '违约金交易控制', 'SYS', '2017-09-19 18:06:02', '0', 'SYS', '2017-09-19 18:03:46');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'D050', null, 'DLQF', 'CRED', '违约金冲正交易控制', 'SYS', '2017-09-19 18:08:41', '0', 'SYS', '2017-09-19 18:06:53');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'D051', null, 'DLQF', 'CRED', '手工减免违约金交易控制', 'SUS', '2017-09-19 18:18:15', '0', 'SYS', '2017-09-19 18:18:15');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'F010', null, 'CRED', 'RFEE', '手续费交易控制', 'SYS', '2017-09-20 11:10:15', '0', 'SYS', '2017-09-20 11:05:42');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'F050', null, 'RFEE', 'CRED', '手续费冲正交易控制', 'SYS', '2017-09-20 11:11:20', '0', 'SYS', '2017-09-20 11:11:20');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'F051', null, 'RFEE', 'CRED', '手工减免手续费交易控制', 'SYS', '2017-09-20 11:11:59', '0', 'SYS', '2017-09-20 11:11:59');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'I010', null, 'CRED', 'RINT', '消费利息交易控制', 'SYS', '2017-09-19 18:21:05', '0', 'SYS', '2017-09-19 18:21:05');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'I050', null, 'RINT', 'CRED', '消费利息冲正交易控制', 'SYS', '2017-09-19 18:42:46', '0', 'SYS', '2017-09-19 18:41:02');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'I051', null, 'RINT', 'CRED', '手工减免消费利息交易控制', 'SYS', '2017-09-19 18:42:41', '0', 'SYS', '2017-09-19 18:42:41');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'P010', null, 'DEBT', 'CRED', '还款交易控制', 'SYS', '2017-10-18 17:54:05', '0', 'SYS', '2017-09-20 11:13:50');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'P050', null, 'CRED', 'RETL', '还款冲正交易控制', 'SYS', '2017-09-20 11:15:19', '0', 'SYS', '2017-09-20 11:14:58');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'R010', 'F010', 'CRED', 'RETL', '消费（分期首付）交易控制', 'SYS', '2017-09-20 11:32:53', '0', 'SYS', '2017-09-20 11:24:22');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'R011', null, 'CRED', 'RETL', '消费（不分期）交易控制', 'SYS', '2017-10-16 19:07:41', '0', 'SYS', '2017-09-20 11:25:02');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'R050', 'F050', 'RETL', 'CRED', '消费冲正交易控制', 'SYS', '2017-09-20 11:33:03', '0', 'SYS', '2017-09-20 11:26:44');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'S010', null, 'CRED', 'RFEE', '服务费交易控制', 'SYS', '2017-09-20 11:27:42', '0', 'SYS', '2017-09-20 11:27:42');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'S050', null, 'RFEE', 'CRED', '服务费冲正交易控制', 'SYS', '2017-09-20 11:28:19', '0', 'SYS', '2017-09-20 11:28:19');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'S051', null, 'RFEE', 'CRED', '手工减免服务费交易控制', 'SYS', '2017-09-20 11:28:48', '0', 'SYS', '2017-09-20 11:28:48');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'X010', null, 'CRED', 'RETL', '溢缴款转移（内部账户转出）交易控制', 'SYS', '2017-09-20 11:30:51', '0', 'SYS', '2017-09-20 11:30:51');
INSERT INTO `txn_process_ctrl` VALUES ('QBAO', 'LIFE', 'X011', null, 'DEBT', 'CRED', '溢缴款转移（内部账户转入）交易控制', 'SYS', '2017-10-18 17:54:06', '0', 'SYS', '2017-09-20 11:32:29');

-- ----------------------------
-- Table structure for `txn_profile`
-- ----------------------------
DROP TABLE IF EXISTS `txn_profile`;
CREATE TABLE `txn_profile` (
  `TXN_CODE` char(4) NOT NULL COMMENT '交易代码',
  `TXN_TYPE` char(4) NOT NULL COMMENT '交易类型代码',
  `STATISTICS_CODE` char(4) NOT NULL COMMENT '统计代码',
  `FLOW_TYPE` char(1) NOT NULL COMMENT '资金流向（D借记/C贷记）',
  `REVERSAL_TXN_CODE` char(4) DEFAULT NULL COMMENT '冲正交易代码',
  `CUSTOMER_GEN_FLAG` char(1) NOT NULL DEFAULT 'N' COMMENT '客户发起交易标示（Y/N）',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  `CREATED_BY` char(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`TXN_CODE`,`TXN_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易类型字典';

-- ----------------------------
-- Records of txn_profile
-- ----------------------------
INSERT INTO `txn_profile` VALUES ('D010', 'FEE', 'DLQF', 'D', 'D050', 'N', '违约金', 'SYS', '2017-09-19 11:27:23', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('D050', 'FEE', 'DLQF', 'C', null, 'N', '违约金冲正', 'SYS', '2017-09-19 11:26:56', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('D051', 'FEE', 'DLQF', 'C', null, 'N', '手工减免违约金', 'SYS', '2017-09-19 11:26:58', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('F010', 'FEE', 'TXNF', 'D', 'F050', 'N', '手续费', 'SYS', '2017-09-20 11:09:54', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('F050', 'FEE', 'TXNF', 'C', null, 'N', '手续费冲正', 'SYS', '2017-09-20 11:09:55', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('F051', 'FEE', 'TXNF', 'C', null, 'N', '手工减免手续费', 'SYS', '2017-09-20 11:09:56', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('I010', 'INT', 'RINT', 'D', 'I050', 'N', '利息', 'SYS', '2017-09-19 11:27:30', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('I050', 'INT', 'RINT', 'C', null, 'N', '利息冲正', 'SYS', '2017-09-19 11:27:10', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('I051', 'INT', 'RINT', 'C', null, 'N', '手工减免利息', 'SYS', '2017-09-19 11:27:12', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('P010', 'PYMT', 'PYMT', 'C', 'P050', 'Y', '还款', 'SYS', '2017-09-27 18:29:19', '0', 'SYS', '2017-07-20 18:06:20');
INSERT INTO `txn_profile` VALUES ('P050', 'PYMT', 'PYMT', 'D', null, 'N', '还款冲正', 'SYS', '2017-09-19 11:27:15', '0', 'SYS', '2017-07-20 18:06:57');
INSERT INTO `txn_profile` VALUES ('R010', 'RETL', 'RETL', 'D', 'R050', 'Y', '消费（分期首付）', 'SYS', '2017-09-27 18:29:13', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('R011', 'RETL', 'RETL', 'D', 'R050', 'Y', '消费（非首期或不分期）', 'SYS', '2017-09-27 18:29:16', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('R050', 'RETL', 'RETL', 'C', null, 'N', '消费退款', 'SYS', '2017-09-20 15:01:33', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('S010', 'FEE', 'SFEE', 'D', 'S050', 'N', '服务费', 'SYS', '2017-09-19 11:27:28', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('S050', 'FEE', 'SFEE', 'C', null, 'N', '服务费冲正', 'SYS', '2017-09-19 11:27:06', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('S051', 'FEE', 'SFEE', 'C', null, 'N', '手工减免服务费', 'SYS', '2017-09-19 11:27:08', '0', 'SYS', '2017-07-20 14:06:49');
INSERT INTO `txn_profile` VALUES ('X010', 'CRED', 'XFR', 'D', null, 'N', '溢缴款转移（内部账户转出）', 'SYS', '2017-09-19 14:05:45', '0', 'SYS', '2017-09-19 14:05:53');
INSERT INTO `txn_profile` VALUES ('X011', 'CRED', 'XFR', 'C', null, 'N', '溢缴款转移（内部账户转入）', 'SYS', '2017-09-19 14:07:56', '0', 'SYS', '2017-09-19 14:07:49');

-- ----------------------------
-- Table structure for `txn_summary`
-- ----------------------------
DROP TABLE IF EXISTS `txn_summary`;
CREATE TABLE `txn_summary` (
  `ACCOUNT_ID` bigint(19) NOT NULL COMMENT '账户ID',
  `CYCLE_NO` int(11) NOT NULL COMMENT '账期号',
  `TXN_SUMMARY_NO` int(11) NOT NULL COMMENT '交易系列号',
  `TXN_ID` bigint(19) NOT NULL COMMENT '交易ID',
  `CUSTOMER_ID` bigint(19) NOT NULL COMMENT '客户ID',
  `IOU_ID` bigint(19) NOT NULL COMMENT '借据ID',
  `ORIGINAL_TXN_ID` bigint(19) NOT NULL DEFAULT '0' COMMENT '产生此交易的原始交易ID',
  `PRODUCT_ID` char(8) NOT NULL COMMENT '产品ID',
  `ACT_TYPE_ID` char(8) NOT NULL COMMENT '账户类型ID',
  `GEN_TXN_SUMMARY_NO` int(11) NOT NULL DEFAULT '0' COMMENT '产生此交易的原交易系列号',
  `TERM_NO` smallint(2) NOT NULL DEFAULT '0' COMMENT '分期号',
  `TXN_CODE` char(5) NOT NULL COMMENT '交易代码',
  `TXN_TYPE` char(5) NOT NULL COMMENT '交易类型',
  `TXN_UUID` varchar(64) DEFAULT NULL COMMENT '交易全局唯一标识ID',
  `ORDER_NO` varchar(64) DEFAULT NULL COMMENT '订单号',
  `CURRENCY_CODE` char(4) NOT NULL COMMENT '币种',
  `FLOW_TYPE` char(1) NOT NULL COMMENT '资金流向（C:credit/D:debit/N:non-money txn）',
  `TXN_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '原始交易金额',
  `POSTING_AMT` decimal(17,2) NOT NULL DEFAULT '0.00' COMMENT '实际入账金额',
  `OUTSTANDING_DEDUCT_AMT` decimal(17,2) NOT NULL COMMENT '未入账金额',
  `GEN_FEE_AMT` decimal(17,2) NOT NULL COMMENT '产生的费用',
  `REVERSAL_AMT` decimal(17,2) NOT NULL COMMENT '冲正金额',
  `CUSTOMER_GEN_FLAG` char(1) NOT NULL COMMENT '客户发起标识',
  `TXN_DATE` int(8) NOT NULL COMMENT '交易日期',
  `TXN_TIME` int(8) NOT NULL COMMENT '交易时间',
  `MERCHANT_NAME` varchar(64) DEFAULT NULL COMMENT '商户名称',
  `DESC_TEXT` varchar(255) DEFAULT NULL COMMENT '描述',
  `CREATED_BY` varchar(64) NOT NULL COMMENT '创建者',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `MODIFIED_BY` varchar(64) NOT NULL COMMENT '修改者',
  `MODIFIED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  `MODIFIED_NO` int(11) NOT NULL DEFAULT '0' COMMENT '修改版本号',
  PRIMARY KEY (`ACCOUNT_ID`,`CYCLE_NO`,`TXN_SUMMARY_NO`),
  UNIQUE KEY `UX_TXN_ID` (`TXN_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of txn_summary
-- ----------------------------
INSERT INTO `txn_summary` VALUES ('6763013902173185', '1', '1', '6763045148689409', '6763013902173184', '6763045140825089', '6763045148689409', 'QBAO', 'LIFE', '0', '1', 'R011', 'RETL', '4008001000000002', '4008001000000002', 'CNY', 'D', '100.00', '100.00', '0.00', '0.00', '0.00', 'Y', '20171026', '142249', 'McDonald', null, 'SYS', '2017-10-26 14:22:50', 'SYS', '2017-10-26 14:22:50', '0');
INSERT INTO `txn_summary` VALUES ('6763013902173185', '1', '3', '6764074916381696', '6763013902173184', '0', '0', 'QBAO', 'LIFE', '0', '0', 'P010', 'PYMT', 'prepaymentOrderNo-20170101-001', 'prepaymentOrderNo-20170101-001', 'CNY', 'C', '10.00', '10.00', '0.00', '0.00', '0.00', 'Y', '20171026', '152818', null, null, 'SYS', '2017-10-26 15:29:28', 'SYS', '2017-10-26 15:29:28', '0');
