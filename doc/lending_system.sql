/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : lending_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-20 11:37:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
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
  PRIMARY KEY (`ACCOUNT_ID`),
  KEY `IX_CUSTID_PRODCODE_MODCODE` (`CUSTOMER_ID`,`PRODUCT_ID`,`ACT_TYPE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户表';

-- ----------------------------
-- Table structure for act_credit_data
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
-- Table structure for act_process_ctrl
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
-- Table structure for bal_comp_profile
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
-- Table structure for bal_comp_val
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
-- Table structure for bal_process_ctrl
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
-- Table structure for curr_process_ctrl
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
-- Table structure for customer
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
-- Table structure for cycle_summary
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
-- Table structure for iou_receipt
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
-- Table structure for payment_history
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
-- Table structure for payment_process_ctrl
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
-- Table structure for plan_process_ctrl
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
-- Table structure for plan_profile
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
-- Table structure for product
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
-- Table structure for status_code
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
-- Table structure for txn_process_ctrl
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
-- Table structure for txn_profile
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
-- Table structure for txn_summary
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
SET FOREIGN_KEY_CHECKS=1;
