-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 20, 2015 at 01:01 AM
-- Server version: 5.5.41-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `demoapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `demo_task`
--

CREATE TABLE IF NOT EXISTS `demo_task` (
  `task_id` int(5) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(50) NOT NULL,
  `task_description` varchar(300) NOT NULL,
  `assignee` int(5) DEFAULT NULL,
  `assign_by` int(5) DEFAULT NULL,
  `finish_date` date DEFAULT NULL,
  `last_updated_by` int(5) NOT NULL,
  `last_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` int(11) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `demo_task`
--

INSERT INTO `demo_task` (`task_id`, `task_name`, `task_description`, `assignee`, `assign_by`, `finish_date`, `last_updated_by`, `last_update_date`, `created_by`, `creation_date`) VALUES
(1, 'Task01', 'This is new taskkkkk', NULL, NULL, NULL, 1, '2015-07-19 17:03:10', 1, '2015-07-19 16:39:29'),
(4, 'Task02', 'New Task', 2, 3, '2015-07-20', 2, '2015-07-19 17:59:02', 3, '2015-07-19 17:58:15');

-- --------------------------------------------------------

--
-- Table structure for table `demo_user`
--

CREATE TABLE IF NOT EXISTS `demo_user` (
  `user_id` int(5) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(15) NOT NULL,
  `password` varchar(50) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  `last_updated_by` int(5) NOT NULL,
  `last_update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` int(11) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `demo_user`
--

INSERT INTO `demo_user` (`user_id`, `user_name`, `password`, `is_active`, `is_admin`, `last_updated_by`, `last_update_date`, `created_by`, `creation_date`) VALUES
(1, 'admin01', 'password', 1, 1, -1, '2015-07-18 11:50:02', -1, '2015-07-17 17:00:00'),
(2, 'user01', '', 1, 0, 1, '2015-07-19 16:20:46', 1, '2015-07-18 15:22:20'),
(3, 'admin02', 'password2', 1, 1, 3, '2015-07-19 17:52:33', 1, '2015-07-19 17:46:11'),
(4, 'user02', 'password', 0, 0, 3, '2015-07-19 17:47:26', 3, '2015-07-19 17:46:50');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
