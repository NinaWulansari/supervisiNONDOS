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

 Date: 23/10/2018 10:04:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for groups_users
-- ----------------------------
DROP TABLE IF EXISTS `groups_users`;
CREATE TABLE `groups_users`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_id` int(10) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `groups_users_i`(`group_id`, `user_id`) USING BTREE,
  INDEX `FK_groups_users_to_users`(`user_id`) USING BTREE,
  INDEX `FK_groups_users_to_groups`(`group_id`) USING BTREE,
  CONSTRAINT `groups_users_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `groups_users_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 624 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = 'InnoDB free: 7168 kB; (`group_id`) REFER `intranet/groups`(`' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of groups_users
-- ----------------------------
INSERT INTO `groups_users` VALUES (1, 1, 1);
INSERT INTO `groups_users` VALUES (169, 1, 666);
INSERT INTO `groups_users` VALUES (12, 2, 8);
INSERT INTO `groups_users` VALUES (76, 2, 149);
INSERT INTO `groups_users` VALUES (14, 2, 510);
INSERT INTO `groups_users` VALUES (17, 2, 511);
INSERT INTO `groups_users` VALUES (18, 2, 513);
INSERT INTO `groups_users` VALUES (19, 2, 514);
INSERT INTO `groups_users` VALUES (20, 2, 515);
INSERT INTO `groups_users` VALUES (21, 2, 516);
INSERT INTO `groups_users` VALUES (22, 2, 517);
INSERT INTO `groups_users` VALUES (23, 2, 518);
INSERT INTO `groups_users` VALUES (24, 2, 519);
INSERT INTO `groups_users` VALUES (25, 2, 520);
INSERT INTO `groups_users` VALUES (26, 2, 521);
INSERT INTO `groups_users` VALUES (27, 2, 522);
INSERT INTO `groups_users` VALUES (28, 2, 523);
INSERT INTO `groups_users` VALUES (29, 2, 524);
INSERT INTO `groups_users` VALUES (30, 2, 525);
INSERT INTO `groups_users` VALUES (31, 2, 526);
INSERT INTO `groups_users` VALUES (32, 2, 527);
INSERT INTO `groups_users` VALUES (33, 2, 528);
INSERT INTO `groups_users` VALUES (34, 2, 529);
INSERT INTO `groups_users` VALUES (35, 2, 530);
INSERT INTO `groups_users` VALUES (36, 2, 531);
INSERT INTO `groups_users` VALUES (37, 2, 532);
INSERT INTO `groups_users` VALUES (38, 2, 533);
INSERT INTO `groups_users` VALUES (39, 2, 534);
INSERT INTO `groups_users` VALUES (40, 2, 535);
INSERT INTO `groups_users` VALUES (41, 2, 538);
INSERT INTO `groups_users` VALUES (42, 2, 539);
INSERT INTO `groups_users` VALUES (43, 2, 540);
INSERT INTO `groups_users` VALUES (44, 2, 541);
INSERT INTO `groups_users` VALUES (45, 2, 542);
INSERT INTO `groups_users` VALUES (46, 2, 543);
INSERT INTO `groups_users` VALUES (47, 2, 544);
INSERT INTO `groups_users` VALUES (48, 2, 545);
INSERT INTO `groups_users` VALUES (49, 2, 546);
INSERT INTO `groups_users` VALUES (50, 2, 547);
INSERT INTO `groups_users` VALUES (51, 2, 548);
INSERT INTO `groups_users` VALUES (52, 2, 549);
INSERT INTO `groups_users` VALUES (53, 2, 550);
INSERT INTO `groups_users` VALUES (54, 2, 551);
INSERT INTO `groups_users` VALUES (55, 2, 552);
INSERT INTO `groups_users` VALUES (56, 2, 553);
INSERT INTO `groups_users` VALUES (57, 2, 554);
INSERT INTO `groups_users` VALUES (58, 2, 555);
INSERT INTO `groups_users` VALUES (59, 2, 556);
INSERT INTO `groups_users` VALUES (60, 2, 557);
INSERT INTO `groups_users` VALUES (61, 2, 558);
INSERT INTO `groups_users` VALUES (62, 2, 559);
INSERT INTO `groups_users` VALUES (63, 2, 560);
INSERT INTO `groups_users` VALUES (64, 2, 561);
INSERT INTO `groups_users` VALUES (65, 2, 562);
INSERT INTO `groups_users` VALUES (66, 2, 563);
INSERT INTO `groups_users` VALUES (67, 2, 564);
INSERT INTO `groups_users` VALUES (68, 2, 565);
INSERT INTO `groups_users` VALUES (69, 2, 566);
INSERT INTO `groups_users` VALUES (70, 2, 567);
INSERT INTO `groups_users` VALUES (71, 2, 568);
INSERT INTO `groups_users` VALUES (72, 2, 569);
INSERT INTO `groups_users` VALUES (73, 2, 570);
INSERT INTO `groups_users` VALUES (74, 2, 571);
INSERT INTO `groups_users` VALUES (75, 2, 572);
INSERT INTO `groups_users` VALUES (77, 2, 574);
INSERT INTO `groups_users` VALUES (78, 2, 575);
INSERT INTO `groups_users` VALUES (79, 2, 576);
INSERT INTO `groups_users` VALUES (80, 2, 577);
INSERT INTO `groups_users` VALUES (81, 2, 578);
INSERT INTO `groups_users` VALUES (82, 2, 579);
INSERT INTO `groups_users` VALUES (83, 2, 580);
INSERT INTO `groups_users` VALUES (84, 2, 581);
INSERT INTO `groups_users` VALUES (85, 2, 582);
INSERT INTO `groups_users` VALUES (86, 2, 583);
INSERT INTO `groups_users` VALUES (87, 2, 584);
INSERT INTO `groups_users` VALUES (88, 2, 585);
INSERT INTO `groups_users` VALUES (89, 2, 586);
INSERT INTO `groups_users` VALUES (90, 2, 587);
INSERT INTO `groups_users` VALUES (91, 2, 588);
INSERT INTO `groups_users` VALUES (92, 2, 589);
INSERT INTO `groups_users` VALUES (93, 2, 590);
INSERT INTO `groups_users` VALUES (94, 2, 591);
INSERT INTO `groups_users` VALUES (95, 2, 592);
INSERT INTO `groups_users` VALUES (96, 2, 593);
INSERT INTO `groups_users` VALUES (97, 2, 594);
INSERT INTO `groups_users` VALUES (98, 2, 595);
INSERT INTO `groups_users` VALUES (99, 2, 596);
INSERT INTO `groups_users` VALUES (100, 2, 597);
INSERT INTO `groups_users` VALUES (101, 2, 598);
INSERT INTO `groups_users` VALUES (102, 2, 599);
INSERT INTO `groups_users` VALUES (103, 2, 600);
INSERT INTO `groups_users` VALUES (104, 2, 601);
INSERT INTO `groups_users` VALUES (105, 2, 602);
INSERT INTO `groups_users` VALUES (106, 2, 603);
INSERT INTO `groups_users` VALUES (107, 2, 604);
INSERT INTO `groups_users` VALUES (108, 2, 605);
INSERT INTO `groups_users` VALUES (109, 2, 606);
INSERT INTO `groups_users` VALUES (110, 2, 607);
INSERT INTO `groups_users` VALUES (111, 2, 608);
INSERT INTO `groups_users` VALUES (112, 2, 609);
INSERT INTO `groups_users` VALUES (113, 2, 610);
INSERT INTO `groups_users` VALUES (114, 2, 611);
INSERT INTO `groups_users` VALUES (115, 2, 612);
INSERT INTO `groups_users` VALUES (116, 2, 613);
INSERT INTO `groups_users` VALUES (117, 2, 614);
INSERT INTO `groups_users` VALUES (118, 2, 615);
INSERT INTO `groups_users` VALUES (119, 2, 616);
INSERT INTO `groups_users` VALUES (120, 2, 617);
INSERT INTO `groups_users` VALUES (121, 2, 618);
INSERT INTO `groups_users` VALUES (122, 2, 619);
INSERT INTO `groups_users` VALUES (123, 2, 620);
INSERT INTO `groups_users` VALUES (124, 2, 621);
INSERT INTO `groups_users` VALUES (125, 2, 622);
INSERT INTO `groups_users` VALUES (126, 2, 623);
INSERT INTO `groups_users` VALUES (127, 2, 624);
INSERT INTO `groups_users` VALUES (128, 2, 625);
INSERT INTO `groups_users` VALUES (129, 2, 626);
INSERT INTO `groups_users` VALUES (130, 2, 627);
INSERT INTO `groups_users` VALUES (131, 2, 628);
INSERT INTO `groups_users` VALUES (132, 2, 629);
INSERT INTO `groups_users` VALUES (133, 2, 630);
INSERT INTO `groups_users` VALUES (134, 2, 631);
INSERT INTO `groups_users` VALUES (135, 2, 632);
INSERT INTO `groups_users` VALUES (136, 2, 633);
INSERT INTO `groups_users` VALUES (137, 2, 634);
INSERT INTO `groups_users` VALUES (138, 2, 635);
INSERT INTO `groups_users` VALUES (139, 2, 636);
INSERT INTO `groups_users` VALUES (140, 2, 637);
INSERT INTO `groups_users` VALUES (141, 2, 638);
INSERT INTO `groups_users` VALUES (142, 2, 639);
INSERT INTO `groups_users` VALUES (143, 2, 640);
INSERT INTO `groups_users` VALUES (144, 2, 641);
INSERT INTO `groups_users` VALUES (145, 2, 642);
INSERT INTO `groups_users` VALUES (146, 2, 643);
INSERT INTO `groups_users` VALUES (147, 2, 644);
INSERT INTO `groups_users` VALUES (148, 2, 645);
INSERT INTO `groups_users` VALUES (150, 2, 647);
INSERT INTO `groups_users` VALUES (164, 2, 660);
INSERT INTO `groups_users` VALUES (180, 2, 662);
INSERT INTO `groups_users` VALUES (173, 2, 670);
INSERT INTO `groups_users` VALUES (7, 3, 5);
INSERT INTO `groups_users` VALUES (9, 3, 7);
INSERT INTO `groups_users` VALUES (149, 3, 646);
INSERT INTO `groups_users` VALUES (151, 3, 648);
INSERT INTO `groups_users` VALUES (152, 3, 649);
INSERT INTO `groups_users` VALUES (153, 3, 650);
INSERT INTO `groups_users` VALUES (154, 3, 651);
INSERT INTO `groups_users` VALUES (155, 3, 652);
INSERT INTO `groups_users` VALUES (174, 3, 653);
INSERT INTO `groups_users` VALUES (157, 3, 654);
INSERT INTO `groups_users` VALUES (158, 3, 655);
INSERT INTO `groups_users` VALUES (159, 3, 656);
INSERT INTO `groups_users` VALUES (160, 3, 657);
INSERT INTO `groups_users` VALUES (161, 3, 658);
INSERT INTO `groups_users` VALUES (162, 3, 659);
INSERT INTO `groups_users` VALUES (177, 3, 661);
INSERT INTO `groups_users` VALUES (168, 3, 665);
INSERT INTO `groups_users` VALUES (170, 3, 667);
INSERT INTO `groups_users` VALUES (179, 3, 672);
INSERT INTO `groups_users` VALUES (187, 3, 1014);
INSERT INTO `groups_users` VALUES (8, 4, 6);
INSERT INTO `groups_users` VALUES (181, 5, 662);
INSERT INTO `groups_users` VALUES (167, 5, 664);
INSERT INTO `groups_users` VALUES (171, 6, 668);
INSERT INTO `groups_users` VALUES (175, 7, 653);
INSERT INTO `groups_users` VALUES (176, 8, 671);
INSERT INTO `groups_users` VALUES (182, 11, 1);
INSERT INTO `groups_users` VALUES (183, 11, 1008);
INSERT INTO `groups_users` VALUES (623, 12, 770);

SET FOREIGN_KEY_CHECKS = 1;
