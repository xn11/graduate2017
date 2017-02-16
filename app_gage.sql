-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-02-17 01:05:26
-- 服务器版本： 5.6.21
-- PHP Version: 5.6.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `app_gage`
--

-- --------------------------------------------------------

--
-- 表的结构 `ex_company`
--

CREATE TABLE IF NOT EXISTS `ex_company` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `contact` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) NOT NULL,
  `score` double NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ex_contract`
--

CREATE TABLE IF NOT EXISTS `ex_contract` (
  `id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `account_manager_id` int(11) NOT NULL,
  `co_manager_id` int(11) NOT NULL,
  `from_date` timestamp NOT NULL,
  `to_date` timestamp NOT NULL,
  `loan` double NOT NULL,
  `status` int(11) NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ex_staff`
--

CREATE TABLE IF NOT EXISTS `ex_staff` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `id_card_num` varchar(100) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `hire_date` timestamp NOT NULL,
  `level` int(11) NOT NULL,
  `post` varchar(50) NOT NULL,
  `department_id` int(11) NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `gage`
--

CREATE TABLE IF NOT EXISTS `gage` (
`id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` int(11) NOT NULL,
  `max_pledge_rate` double NOT NULL,
  `warning_line` double NOT NULL,
  `dispose_line` double NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `mail`
--

CREATE TABLE IF NOT EXISTS `mail` (
`id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text,
  `from_uid` int(11) NOT NULL,
  `to_uid` varchar(200) DEFAULT NULL,
  `send_time` timestamp NOT NULL,
  `urgency` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `note` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `regulators`
--

CREATE TABLE IF NOT EXISTS `regulators` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `contact` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `score` double NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `regulators_company`
--

CREATE TABLE IF NOT EXISTS `regulators_company` (
  `regulator_uid` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `regulators_id` int(11) NOT NULL,
  `from_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `to_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fee` double NOT NULL,
  `status` int(11) NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `st_department`
--

CREATE TABLE IF NOT EXISTS `st_department` (
`id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `organization_id` int(11) NOT NULL,
  `manager_id` int(11) NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `st_organization`
--

CREATE TABLE IF NOT EXISTS `st_organization` (
`id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `parent_id` int(11) NOT NULL,
  `manager_id` int(11) NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL,
  `uname` varchar(50) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `role` int(11) NOT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL COMMENT '登录状态',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_change_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user_authority`
--

CREATE TABLE IF NOT EXISTS `user_authority` (
  `uid` int(11) NOT NULL,
  `authority_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `_authority`
--

CREATE TABLE IF NOT EXISTS `_authority` (
`id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `table` varchar(50) NOT NULL,
  `range` int(11) NOT NULL,
  `operation` int(11) NOT NULL,
  `note` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `_config`
--

CREATE TABLE IF NOT EXISTS `_config` (
  `key` varchar(50) NOT NULL,
  `value` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `_role`
--

CREATE TABLE IF NOT EXISTS `_role` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `view` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ex_company`
--
ALTER TABLE `ex_company`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ex_contract`
--
ALTER TABLE `ex_contract`
 ADD PRIMARY KEY (`id`), ADD KEY `company_id` (`company_id`), ADD KEY `account_manager_id` (`account_manager_id`), ADD KEY `co_manager_id` (`co_manager_id`);

--
-- Indexes for table `ex_staff`
--
ALTER TABLE `ex_staff`
 ADD PRIMARY KEY (`id`), ADD KEY `department_id` (`department_id`);

--
-- Indexes for table `gage`
--
ALTER TABLE `gage`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mail`
--
ALTER TABLE `mail`
 ADD PRIMARY KEY (`id`), ADD KEY `from_id` (`from_uid`);

--
-- Indexes for table `regulators`
--
ALTER TABLE `regulators`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `regulators_company`
--
ALTER TABLE `regulators_company`
 ADD PRIMARY KEY (`company_id`,`regulators_id`), ADD KEY `regulators_id` (`regulators_id`);

--
-- Indexes for table `st_department`
--
ALTER TABLE `st_department`
 ADD PRIMARY KEY (`id`), ADD KEY `manager_id` (`manager_id`), ADD KEY `organization_id` (`organization_id`), ADD KEY `manager_id_2` (`manager_id`);

--
-- Indexes for table `st_organization`
--
ALTER TABLE `st_organization`
 ADD PRIMARY KEY (`id`), ADD KEY `parent_id` (`parent_id`), ADD KEY `manager_id` (`manager_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`uid`);

--
-- Indexes for table `user_authority`
--
ALTER TABLE `user_authority`
 ADD KEY `username` (`uid`), ADD KEY `authority_id` (`authority_id`);

--
-- Indexes for table `_authority`
--
ALTER TABLE `_authority`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `gage`
--
ALTER TABLE `gage`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `mail`
--
ALTER TABLE `mail`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `st_department`
--
ALTER TABLE `st_department`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `st_organization`
--
ALTER TABLE `st_organization`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `_authority`
--
ALTER TABLE `_authority`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 限制导出的表
--

--
-- 限制表 `ex_contract`
--
ALTER TABLE `ex_contract`
ADD CONSTRAINT `ex_contract_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `ex_company` (`id`),
ADD CONSTRAINT `ex_contract_ibfk_2` FOREIGN KEY (`account_manager_id`) REFERENCES `ex_staff` (`id`),
ADD CONSTRAINT `ex_contract_ibfk_3` FOREIGN KEY (`co_manager_id`) REFERENCES `ex_staff` (`id`);

--
-- 限制表 `ex_staff`
--
ALTER TABLE `ex_staff`
ADD CONSTRAINT `ex_staff_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `st_department` (`id`);

--
-- 限制表 `regulators_company`
--
ALTER TABLE `regulators_company`
ADD CONSTRAINT `regulators_company_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `ex_company` (`id`),
ADD CONSTRAINT `regulators_company_ibfk_2` FOREIGN KEY (`regulators_id`) REFERENCES `regulators` (`id`);

--
-- 限制表 `st_department`
--
ALTER TABLE `st_department`
ADD CONSTRAINT `st_department_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `st_organization` (`id`),
ADD CONSTRAINT `st_department_ibfk_2` FOREIGN KEY (`manager_id`) REFERENCES `ex_staff` (`id`);

--
-- 限制表 `st_organization`
--
ALTER TABLE `st_organization`
ADD CONSTRAINT `st_organization_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `st_organization` (`id`),
ADD CONSTRAINT `st_organization_ibfk_2` FOREIGN KEY (`manager_id`) REFERENCES `ex_staff` (`id`);

--
-- 限制表 `user_authority`
--
ALTER TABLE `user_authority`
ADD CONSTRAINT `user_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `_authority` (`id`),
ADD CONSTRAINT `user_authority_ibfk_3` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
