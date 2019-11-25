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

 Date: 02/11/2018 17:59:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for supervisi_headers
-- ----------------------------
DROP TABLE IF EXISTS `supervisi_headers`;
CREATE TABLE `supervisi_headers`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `latitude` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `longitude` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `no_dlr` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tgl_supervisi` date NULL DEFAULT NULL,
  `main_item` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `item` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pi` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ca` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `duration` int(11) NULL DEFAULT NULL,
  `deadline` date NULL DEFAULT NULL,
  `new_deadline` date NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `modified` datetime NULL DEFAULT NULL,
  `modi_by` bigint(20) NULL DEFAULT NULL,
  `created` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `status_supervisi` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status_pica` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status_approve` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of supervisi_headers
-- ----------------------------
INSERT INTO `supervisi_headers` VALUES ('000bfb99-2380-11e8-8dd0-005056880752', '-6.266667', '106.6543378', '156', '2018-10-25', '1', '11', '1', NULL, 'good', NULL, NULL, NULL, NULL, NULL, '115090318225Y', NULL, NULL, '2018-03-09 09:55:29', 1, '1', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('0012d126-1582-11e8-8dd0-005056880752', '-6.266667', '106.6543378', '156', '2018-10-25', '1', '12', '1', NULL, 'good', NULL, NULL, NULL, NULL, NULL, '186190218556Y', NULL, NULL, '2018-02-19 14:34:30', 1, '1', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('001abc74-1165-11e8-8dd0-005056880752', '-6.266667', '106.6543378', '156', '2018-10-26', '2', '13', '1', NULL, 'good', NULL, NULL, NULL, NULL, NULL, '206140218224T', NULL, NULL, '2018-02-14 08:56:50', 1, '1', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('001df29e-c888-11e7-8dd0-005056880752', '-6.266667', '106.6543378', '156', '2018-10-26', '2', '14', '2', NULL, 'rusak bocor gabisa digunakan', '', NULL, '0000-00-00', NULL, NULL, '087131117227Y', NULL, NULL, '2017-11-13 15:33:35', 1, '1', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('00312c8e-4315-11e8-8dd0-005056880752', '-6.3117399', '106.7532613', '062', '2018-10-26', '1', '11', '3', NULL, 'belum ada masih kosong', 'Diganti', NULL, '2018-11-10', '2018-11-24', 'gapapa pingin aja', '062180418358Y', '2018-11-02 10:30:04', NULL, '2018-04-18 14:30:15', 653, '0', 'Reschedule', NULL, '');
INSERT INTO `supervisi_headers` VALUES ('00312c8e-4315-11e8-8dd0-0050568h9ik0', '-6.3117399', '106.7532613', '062', '2018-10-26', '1', '12', '2', NULL, 'rusak gabisa digunakan', 'aye bisa hihi', NULL, '2018-11-10', '2018-11-17', 'hmmmmmm', '15630101816145', '2018-11-01 19:01:39', NULL, '0000-00-00 00:00:00', 653, '0', 'Done', NULL, 'dah selesai');
INSERT INTO `supervisi_headers` VALUES ('003459c3-956b-11e8-b414-005056880752', '-6.294859', '106.7110453', '151', '2018-10-26', '2', '13', '4', NULL, 'gatau gajelas', NULL, NULL, NULL, NULL, NULL, '151010818227T', NULL, NULL, '2018-08-01 09:12:34', 1, '0', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('003527cc-faae-11e7-8dd0-005056880752', '-6.2547384', '106.6224332', '068', '2018-10-26', '1', '11', '1', NULL, 'good', NULL, NULL, NULL, NULL, NULL, '068160118453Y', NULL, NULL, '2018-01-16 11:11:31', 1, '0', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('00382da9-ce0b-11e7-8dd0-005056880752', '-6.152248604105162', '106.59506493888979', '065', '2018-10-26', '2', '13', '2', NULL, 'rusak gabisa digunakan', NULL, NULL, NULL, NULL, NULL, '065201117217Y', NULL, NULL, '2017-11-20 15:53:56', 1, '0', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('003e6bfe-d423-11e7-8dd0-005056880752', '-6.0698726', '106.6485595', '085', '2018-10-26', '1', '11', '1', NULL, 'good', NULL, NULL, NULL, NULL, NULL, '08528111716Y', NULL, NULL, '2017-11-28 10:00:52', 1, '0', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('00403671-c009-11e8-b414-005056880752', '-6.1550414', '106.8366755', '998', '2018-10-26', '2', '13', '1', NULL, 'good', NULL, NULL, NULL, NULL, NULL, '998240918224Y', NULL, NULL, '2018-09-24 14:49:40', 1, '0', 'Planned', NULL, NULL);
INSERT INTO `supervisi_headers` VALUES ('1784d702-de7b-11e8-87f1-005056b25997', '37.421998333333335', '-122.08400000000002', '156', '2018-11-02', '1', '6', '1', NULL, NULL, 'cobates', NULL, '2018-12-01', '2018-12-12', 'ruwet', '156021118161', '2018-11-02 18:28:53', NULL, '2018-11-02 16:26:17', 2, NULL, 'Done', NULL, 'hhiihi');
INSERT INTO `supervisi_headers` VALUES ('72a4b3d6-dcb5-11e8-87f1-005056b25997', '37.421998333333335', '-122.08400000000002', '156', '2018-10-31', '1', '6', '1', NULL, NULL, 'huhuhuhu', NULL, '2018-10-30', '2018-10-30', '', '156311018161', '2018-11-01 16:15:32', NULL, '2018-10-31 10:18:59', 1, NULL, 'Planned', NULL, 'hih');
INSERT INTO `supervisi_headers` VALUES ('e9324e90-dbee-11e8-87f1-005056b25997', '37.421998333333335', '-122.08400000000002', '156', '2018-10-30', '1', '6', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '156301018161', NULL, NULL, '2018-10-30 10:37:48', 1, '0', 'Planned', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
