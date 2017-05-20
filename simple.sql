-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2017 at 10:44 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `simple`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` varchar(200) NOT NULL,
  `token` longtext NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `gender` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `token`, `name`, `email`, `gender`) VALUES
('1112603555535642', 'reset', 'Madan Limbu', 'madan_limbu@hotmail.co.uk', 'male'),
('109592976245390', 'reset', 'Tatsuya Wolfiestin', 'k1429795@kingston.ac.uk', 'male'),
('1388784841187924', 'reset', 'Shaun Tamang', 'NoEmail', 'male'),
('614110368786666', 'reset', 'Shin Bantawa', 'shin.bantawa@gmail.com', 'male'),
('104235053493201', 'reset', 'Dimtest Maktest', 'ku32195@kingston.ac.uk', 'male');

-- --------------------------------------------------------

--
-- Table structure for table `conversation`
--

CREATE TABLE `conversation` (
  `convId` int(200) NOT NULL,
  `user_one_id` varchar(200) NOT NULL,
  `user_two_id` varchar(200) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `conversation`
--

INSERT INTO `conversation` (`convId`, `user_one_id`, `user_two_id`, `time`) VALUES
(8, '109592976245390', '1112603555535642', '2017-05-16 23:13:08'),
(9, '109592976245390', '1388784841187924', '2017-05-17 09:17:30'),
(10, '109592976245390', '614110368786666', '2017-05-17 11:30:48'),
(11, '104235053493201', '109592976245390', '2017-05-17 12:28:49');

-- --------------------------------------------------------

--
-- Table structure for table `crossed`
--

CREATE TABLE `crossed` (
  `crossed_id` int(200) NOT NULL,
  `user_one_id` varchar(200) NOT NULL,
  `user_two_id` varchar(200) NOT NULL,
  `lat` varchar(111) NOT NULL,
  `log` varchar(111) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `crossed`
--

INSERT INTO `crossed` (`crossed_id`, `user_one_id`, `user_two_id`, `lat`, `log`, `time`) VALUES
(18, '109592976245390', '1112603555535642', '51.4104574', '-0.2950217', '2017-05-16 23:12:12'),
(19, '109592976245390', '1388784841187924', '51.4106679', '-0.2951342', '2017-04-29 07:51:15'),
(20, '109592976245390', '614110368786666', '51.4104532', '-0.295023', '2017-05-15 22:25:52'),
(21, '104235053493201', '109592976245390', '51.4020445', '-0.3024029', '2017-05-17 12:34:21');

-- --------------------------------------------------------

--
-- Table structure for table `livedata`
--

CREATE TABLE `livedata` (
  `user_id` varchar(200) NOT NULL,
  `lat` varchar(111) NOT NULL,
  `log` varchar(111) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `livedata`
--

INSERT INTO `livedata` (`user_id`, `lat`, `log`, `time`) VALUES
('1', '51.4105352', '-0.2950238', '2017-02-26 22:25:19'),
('4', '51.4105148', '-0.2950006', '2017-03-21 08:37:56'),
('2', '51.4105352', '-0.2950238', '2017-02-20 23:00:55'),
('1112603555535642', '51.4104574', '-0.2950217', '2017-05-16 23:12:02'),
('109592976245390', '51.4021472', '-0.3021992', '2017-05-17 12:34:45'),
('1388784841187924', '51.4106697', '-0.2950885', '2017-04-29 08:02:04'),
('614110368786666', '51.4104532', '-0.295023', '2017-05-15 22:27:14'),
('104235053493201', '51.4020445', '-0.3024029', '2017-05-17 12:34:19');

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
  `crossed_id_fk` int(222) NOT NULL,
  `lat` varchar(222) NOT NULL,
  `log` varchar(222) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `location`
--

INSERT INTO `location` (`crossed_id_fk`, `lat`, `log`, `time`) VALUES
(14, '51.4105352', '-0.2950238', '2017-02-26 22:26:19');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `message_Id` int(11) NOT NULL,
  `conv_Id` varchar(200) NOT NULL,
  `user_id` varchar(200) NOT NULL,
  `recever_id` varchar(200) NOT NULL,
  `message` text NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`message_Id`, `conv_Id`, `user_id`, `recever_id`, `message`, `time`) VALUES
(27, '8', '109592976245390', '1112603555535642', 'Hello', '2017-05-16 23:13:08'),
(28, '8', '109592976245390', '1112603555535642', 'Refresh', '2017-05-17 08:13:50'),
(29, '8', '109592976245390', '1112603555535642', 'Test 2', '2017-05-17 08:17:16'),
(30, '8', '109592976245390', '1112603555535642', 'T3', '2017-05-17 08:18:13'),
(31, '8', '109592976245390', '1112603555535642', 'T4', '2017-05-17 08:19:53'),
(32, '8', '109592976245390', '1112603555535642', 'P', '2017-05-17 08:21:40'),
(33, '8', '109592976245390', '1112603555535642', 'Another', '2017-05-17 08:25:24'),
(34, '8', '109592976245390', '1112603555535642', 'T4r', '2017-05-17 08:26:02'),
(35, '9', '109592976245390', '1388784841187924', 'Knew mesage', '2017-05-17 09:17:30'),
(36, '8', '109592976245390', '1112603555535642', '', '2017-05-17 09:45:30'),
(37, '9', '109592976245390', '1388784841187924', 'Another', '2017-05-17 09:55:14'),
(38, '9', '109592976245390', '1388784841187924', '2', '2017-05-17 09:55:48'),
(41, '8', '109592976245390', '1112603555535642', 'U', '2017-05-17 10:01:24'),
(43, '8', '1112603555535642', '109592976245390', 'fix bug', '2017-05-17 10:26:55'),
(44, '8', '109592976245390', '1112603555535642', 'Yes', '2017-05-17 10:27:32'),
(45, '9', '109592976245390', '1388784841187924', 'Good', '2017-05-17 10:27:43'),
(46, '8', '109592976245390', '1112603555535642', 'Another', '2017-05-17 10:31:27'),
(47, '8', '109592976245390', '1112603555535642', 'New refresh test', '2017-05-17 10:36:25'),
(48, '9', '109592976245390', '1388784841187924', 'Looks good', '2017-05-17 10:36:36'),
(49, '8', '109592976245390', '1112603555535642', '3rd try', '2017-05-17 10:36:56'),
(50, '9', '109592976245390', '1388784841187924', 'Next try', '2017-05-17 10:39:31'),
(51, '9', '109592976245390', '1388784841187924', 'Works', '2017-05-17 10:39:46'),
(52, '8', '1112603555535642', '109592976245390', 'LAST', '2017-05-17 10:46:36'),
(53, '10', '109592976245390', '614110368786666', 'Test 3', '2017-05-17 11:30:48'),
(54, '10', '109592976245390', '614110368786666', 'Second', '2017-05-17 11:31:01'),
(55, '11', '104235053493201', '109592976245390', 'Hello world!', '2017-05-17 12:28:49'),
(56, '11', '104235053493201', '109592976245390', 'Hello world!', '2017-05-17 12:28:55'),
(57, '11', '109592976245390', '104235053493201', 'H', '2017-05-17 12:29:40');

-- --------------------------------------------------------

--
-- Table structure for table `relationship`
--

CREATE TABLE `relationship` (
  `user_one_id` varchar(200) NOT NULL,
  `user_two_id` varchar(200) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `action_user_id` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `relationship`
--

INSERT INTO `relationship` (`user_one_id`, `user_two_id`, `status`, `action_user_id`) VALUES
('109592976245390', '1388784841187924', 1, '109592976245390'),
('109592976245390', '614110368786666', 1, '109592976245390'),
('109592976245390', '1112603555535642', 1, '1112603555535642'),
('104235053493201', '109592976245390', 1, '109592976245390');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `pass` varchar(200) NOT NULL,
  `lat` varchar(200) NOT NULL,
  `log` varchar(200) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `pass`, `lat`, `log`, `time`) VALUES
(1, 'madan', 'limbu', '51.4105352', '-0.2950238', '2017-01-23 01:47:09'),
(2, 'layfon', 'layfon', '51.4103755', '-0.2950803', '2017-01-23 02:21:16'),
(3, 'someone', 'someone', '323', '33', '2017-01-23 02:20:05'),
(4, 'megumi', 'megumi', '51.4105352', '-0.2950238', '2017-02-12 04:27:02');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `conversation`
--
ALTER TABLE `conversation`
  ADD PRIMARY KEY (`convId`);

--
-- Indexes for table `crossed`
--
ALTER TABLE `crossed`
  ADD PRIMARY KEY (`crossed_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_Id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `conversation`
--
ALTER TABLE `conversation`
  MODIFY `convId` int(200) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `crossed`
--
ALTER TABLE `crossed`
  MODIFY `crossed_id` int(200) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `message_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
