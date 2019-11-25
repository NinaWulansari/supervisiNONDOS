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

 Date: 23/10/2018 09:21:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for supervisi_form_checklists
-- ----------------------------
DROP TABLE IF EXISTS `supervisi_form_checklists`;
CREATE TABLE `supervisi_form_checklists`  () ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of supervisi_form_checklists
-- ----------------------------
INSERT INTO `supervisi_form_checklists` VALUES (1, NULL, 'Approval Layout New VinCi', 'Approval Layout New VinCi', 1, '1', '0', '1', 1, 32, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (2, NULL, 'Eksterior', 'Eksterior', 2, '1', '0', '1', 33, 38, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (11, 1, 'Eksterior', 'Eksterior', 1, '0', '0', '1', 2, 7, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (12, 1, 'Interior', 'Interior', 2, '0', '0', '1', 8, 31, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (13, 2, 'Fascia', 'Fascia', 1, '0', '0', '1', 34, 35, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (14, 2, 'Pylon', 'Pylon', 2, '0', '0', '1', 36, 37, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (15, 11, 'Sesuai dengan gambar yang tampak dengan yang sudah diapprove bersama', 'Sesuai dengan gambar yang tampak dengan yang sudah diapprove bersama', 1, '0', '0', '1', 3, 4, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (16, 11, 'Gambar eksterior yang sudah diapproved, dicetak, ditempel di ruang Kacab', 'Gambar eksterior yang sudah diapproved, dicetak, ditempel di ruang Kacab', 2, '0', '0', '1', 5, 6, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (17, 12, 'Penempatan interior sesuai dengan gambar interior yang sudah diapproed bersama, kecuali :', 'Penempatan interior sesuai dengan gambar interior yang sudah diapproed bersama, kecuali :', 1, '0', '0', '1', 9, 28, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (18, 12, 'Gambar interior yang sudah diapproved, dicetak, ditempel di ruang Kacab', 'Gambar interior yang sudah diapproved, dicetak, ditempel di ruang Kacab', 2, '0', '0', '1', 29, 30, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (19, 17, 'a. meja negosiasi', 'a. meja negosiasi', 1, '0', '0', '1', 10, 11, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (20, 17, 'b. display motor', 'b. display motor', 2, '0', '0', '1', 12, 13, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (21, 17, 'c. magazine rack', 'c. magazine rack', 3, '0', '0', '1', 14, 15, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (22, 17, 'd. rak brosur', 'd. rak brosur', 4, '0', '0', '1', 16, 17, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (23, 17, 'f. mannequin', 'e. mannequin', 6, '0', '0', '1', 20, 21, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (24, 17, 'g. HRT dan HRT Banner', 'f. HRT dan HRT Banner', 7, '0', '0', '1', 22, 23, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (25, 17, 'h. SA desk', 'g. SA desk', 8, '0', '0', '1', 24, 25, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (26, 17, 'i. Rak helm', 'h. Rak helm', 9, '0', '0', '1', 26, 27, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');
INSERT INTO `supervisi_form_checklists` VALUES (43, 17, 'e. spect stand', 'i. spect stand', 5, '0', '0', '1', 18, 19, '2018-10-12 16:21:53', 1, '2018-10-12 16:21:53', 1, 'DOS');

SET FOREIGN_KEY_CHECKS = 1;
