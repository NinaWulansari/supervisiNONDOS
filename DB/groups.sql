/*
 Navicat Premium Data Transfer

 Source Server         : 38
 Source Server Type    : MySQL
 Source Server Version : 50171
 Source Host           : 192.168.0.38:3306
 Source Schema         : intranet_dev

 Target Server Type    : MySQL
 Target Server Version : 50171
 File Encoding         : 65001

 Date: 23/10/2018 10:03:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of groups
-- ----------------------------
INSERT INTO `groups` VALUES (1, 'IT Administrator', 'Superman', '2011-07-18 00:00:00', '2011-07-18 00:00:00');
INSERT INTO `groups` VALUES (2, 'DEALER', 'DEALER', '2012-04-24 17:45:26', '2012-04-24 17:45:26');
INSERT INTO `groups` VALUES (3, 'MARKETING', 'MARKETING', '2012-06-29 14:49:59', '2013-05-02 13:39:54');
INSERT INTO `groups` VALUES (4, 'ADMIN GUDANG', 'ADMIN GUDANG JATAKE DAN CIMANGGIS DAN JATIASIH', '2012-06-29 14:50:33', '2018-05-18 11:43:46');
INSERT INTO `groups` VALUES (5, 'INDENT', 'INDENT', '2013-04-22 17:16:58', '2013-04-22 17:16:58');
INSERT INTO `groups` VALUES (6, 'CUSTOMER CARE', 'CUSTOMER CARE', '2013-08-23 17:18:53', '2013-08-23 17:18:53');
INSERT INTO `groups` VALUES (7, 'SUPPORT', 'SUPPORT', '2013-09-19 16:44:00', '2013-12-26 15:12:35');
INSERT INTO `groups` VALUES (8, 'NonMkt', 'NonMkt', '2013-10-04 10:08:30', '2013-10-04 10:08:30');
INSERT INTO `groups` VALUES (9, 'WKM', 'wahana kalyanamitra mahardhika', '2013-10-29 09:44:45', '2013-10-29 09:44:45');
INSERT INTO `groups` VALUES (10, 'Sales Admin', 'SALES ADMIN', '2014-04-04 15:14:59', '2014-04-04 15:14:59');
INSERT INTO `groups` VALUES (11, 'Logistik', 'Logistik', '2014-04-04 15:25:35', '2014-04-04 15:25:35');
INSERT INTO `groups` VALUES (12, 'SA', 'SA', '2014-07-02 15:04:22', '2016-07-14 16:18:49');
INSERT INTO `groups` VALUES (13, 'Promotion', 'Promotion', '2014-07-02 15:55:35', '2014-07-02 15:55:35');
INSERT INTO `groups` VALUES (14, 'Head SA', 'Head SA', '2014-07-23 13:53:44', '2014-07-23 13:53:44');
INSERT INTO `groups` VALUES (15, 'HEAD WARI', 'HEAD WARI', '2014-08-15 10:18:44', '2014-08-15 10:18:44');
INSERT INTO `groups` VALUES (16, 'Secretary', 'Secretary', '2014-08-22 15:21:28', '2014-08-22 15:21:28');
INSERT INTO `groups` VALUES (17, 'Faktur', 'Faktur', '2014-08-22 15:21:41', '2014-08-22 15:21:41');
INSERT INTO `groups` VALUES (18, 'Distplan', 'Distplan', '2014-08-25 15:07:49', '2014-08-25 15:07:49');
INSERT INTO `groups` VALUES (19, 'Cimanggis', 'Admin Gudang Cimanggis', '2014-09-03 13:59:28', '2018-05-18 11:21:55');
INSERT INTO `groups` VALUES (20, 'Jatake', 'Admin Gudang Jatake', '2014-09-03 13:59:46', '2014-09-03 13:59:46');
INSERT INTO `groups` VALUES (21, 'Finance', 'Finance', '2014-09-04 15:16:43', '2014-09-04 15:16:43');
INSERT INTO `groups` VALUES (22, 'BTL', 'Biro The Line', '2014-10-22 15:55:14', '2014-10-22 15:55:14');
INSERT INTO `groups` VALUES (23, 'Training HCQD', 'Training HCQD', '2014-10-31 14:20:17', '2015-08-07 10:32:14');
INSERT INTO `groups` VALUES (25, 'BTL Admin', 'BTL Admin', '2014-12-29 15:09:08', '2014-12-29 15:09:08');
INSERT INTO `groups` VALUES (26, 'BTL FIN/TAX/ACC', 'BTL FIN/TAX/ACC', '2014-12-29 15:10:50', '2014-12-29 15:10:50');
INSERT INTO `groups` VALUES (27, 'FIN_KWITANSI', 'Finance Kwitansi', '2015-01-28 14:00:23', '2015-01-28 14:00:23');
INSERT INTO `groups` VALUES (28, 'EO', 'EO Roadshow', '2015-02-27 15:42:51', '2015-02-27 15:42:51');
INSERT INTO `groups` VALUES (29, 'Devi Access', 'Devi Access', '2015-03-18 14:06:56', '2015-03-18 14:06:56');
INSERT INTO `groups` VALUES (30, 'Pembayaran SO', 'Pembayaran SO', '2015-03-24 16:11:40', '2015-03-24 16:11:40');
INSERT INTO `groups` VALUES (31, 'bonus system', 'bonus system', '2015-08-06 14:55:34', '2015-08-06 14:55:34');
INSERT INTO `groups` VALUES (32, 'PDCA', 'PDCA', '2015-10-02 13:24:56', '2015-10-02 13:24:56');
INSERT INTO `groups` VALUES (33, 'Compliance', 'Compliance', '2016-03-18 13:43:05', '2016-03-18 13:43:05');
INSERT INTO `groups` VALUES (34, 'Data Polda', 'Report Data Polda', '2016-03-28 14:27:24', '2016-03-28 14:27:24');
INSERT INTO `groups` VALUES (35, 'Legal', 'Legal', '2016-05-03 15:08:45', '2016-05-03 15:08:45');
INSERT INTO `groups` VALUES (36, 'Accounting', 'Accounting', '2016-05-10 15:47:22', '2016-05-10 15:47:22');
INSERT INTO `groups` VALUES (37, 'ADMIN SURVEY', 'admin survey survey online', '2016-12-01 14:15:29', '2016-12-01 14:15:29');
INSERT INTO `groups` VALUES (38, 'INTERVIEWER', 'interviewer survey online', '2016-12-01 14:15:58', '2016-12-01 14:15:58');
INSERT INTO `groups` VALUES (39, 'Kode Pos', 'Kode Pos', '2017-01-23 15:23:35', '2017-01-23 15:23:35');
INSERT INTO `groups` VALUES (40, 'Biro Jasa', 'Input tanda terima faktur', '2017-04-12 11:21:00', '2017-04-12 11:22:06');
INSERT INTO `groups` VALUES (41, 'Daftar Harga Beli Dealer', 'Daftar Harga Beli Dealer', '2017-04-27 15:13:34', '2017-04-27 15:13:34');
INSERT INTO `groups` VALUES (42, 'Admin Biro Jasa', '', '2017-05-02 17:04:38', '2017-05-02 17:04:38');
INSERT INTO `groups` VALUES (43, 'scoopy', 'scoopy', '2018-01-08 15:47:02', '2018-01-08 15:47:02');
INSERT INTO `groups` VALUES (44, 'PRJ', 'PRJ', '2018-05-15 09:30:02', '2018-05-15 09:30:02');
INSERT INTO `groups` VALUES (45, 'Jatiasih', 'Admin Gudang Jatiasih', '2018-05-18 11:15:29', '2018-05-18 11:21:16');

SET FOREIGN_KEY_CHECKS = 1;
