-- CREATE DATABASE
DROP DATABASE IF EXISTS `hikky-db`;
CREATE DATABASE `hikky-db`;

-- map指定
USE `hikky-db`;

DROP TABLE IF EXISTS `players`;
CREATE TABLE `players` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    -- 1: プレイ中
    -- 2: 捕まった
    -- 3: 勝利
    -- 4: 鬼
    `state` int NOT NULL,
    PRIMARY KEY (`id`)
);

-- mock data
INSERT INTO `players` VALUES
(
    1, 1
),
(
    2, 1
),
(
    3, 4
);


DROP TABLE IF EXISTS `spacetimes`;
CREATE TABLE `spacetimes` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `time` time,
    `latitudea` float NOT NULL,
    `longtitude` float NOT NULL,
    `altitude` float NOT NULL,
    `locator` int,
    PRIMARY KEY (`id`)
);
