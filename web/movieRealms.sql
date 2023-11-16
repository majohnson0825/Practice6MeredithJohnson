-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 29, 2020 at 09:46 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `movierealms`
--
DROP DATABASE IF EXISTS `movierealms`;
CREATE DATABASE IF NOT EXISTS `movierealms` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `movierealms`;

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `movieID` int(11) NOT NULL,
  `title` varchar(128) NOT NULL,
  `director` varchar(128) NOT NULL,
  `year` year(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`movieID`, `title`, `director`, `year`) VALUES
(1, 'Troll 2', 'Fragasso', 1990),
(2, 'The Last Jedi', 'Jonson', 2016),
(3, 'Robocop', 'Verhooven', 1987),
(4, 'Dune', 'Villeneuve', 2021),
(5, 'Dune', 'Lynch', 1984),
(6, 'Fateful Findings', 'Breen', 2013);

-- --------------------------------------------------------

--
-- Table structure for table `ratings`
--

CREATE TABLE `ratings` (
  `ratingID` int(11) NOT NULL,
  `movieID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `rating` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ratings`
--

INSERT INTO `ratings` (`ratingID`, `movieID`, `userID`, `rating`) VALUES
(1, 3, 1, 10),
(3, 4, 2, 9),
(4, 4, 3, 6),
(5, 4, 7, 10),
(6, 3, 7, 9),
(7, 2, 7, 2),
(8, 2, 4, 8),
(9, 3, 4, 1),
(10, 1, 4, 5),
(11, 4, 4, 6),
(12, 4, 7, 10),
(13, 5, 4, 3),
(14, 5, 7, 7);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `Username` varchar(128) NOT NULL,
  `Hash` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Email`, `FirstName`, `LastName`, `Username`, `Hash`) VALUES
(1, 'jsmith@gmail.com', 'John', 'Smith', 'john', '44a7d44e2e8506b959b0257bd5799b87$4096$9587d36651f2984ec37bd178c99e9371032db29aa3c9a95f884d71314a2b3e19'),
(2, 'andi@murach.com', 'Andrea', 'Steelman', 'andrea', '8cd9eded3e3402c96bb7212c1057a475$4096$f9a20ff21cad65ac95ccf48b14f2be2c3204c0f3846c37e27529f89f15d72ad1'),
(3, 'joelmurach@yahoo.com', 'Joel', 'Murach', 'joel', '44a7d44e2e8506b959b0257bd5799b87$4096$9587d36651f2984ec37bd178c99e9371032db29aa3c9a95f884d71314a2b3e19'),
(4, 'aa@aa.aa', 'Aaron', 'Aaronson', 'aaron', '44a7d44e2e8506b959b0257bd5799b87$4096$9587d36651f2984ec37bd178c99e9371032db29aa3c9a95f884d71314a2b3e19'),
(7, 'bb@bb.bb', 'Billy', 'Billerson', 'billy', '44a7d44e2e8506b959b0257bd5799b87$4096$9587d36651f2984ec37bd178c99e9371032db29aa3c9a95f884d71314a2b3e19');

-- --------------------------------------------------------

--
-- Table structure for table `userrole`
--

CREATE TABLE `userrole` (
  `Username` varchar(15) NOT NULL,
  `Rolename` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userrole`
--

INSERT INTO `userrole` (`Username`, `Rolename`) VALUES
('aaron', 'reviewer'),
('andrea', 'programmer'),
('andrea', 'admin'),
('billy', 'reviewer'),
('john', 'admin'),
('john', 'astronaut'),
('john', 'dude'),
('john', 'reviewer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`movieID`);

--
-- Indexes for table `ratings`
--
ALTER TABLE `ratings`
  ADD PRIMARY KEY (`ratingID`),
  ADD KEY `movID` (`movieID`),
  ADD KEY `usID` (`userID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Indexes for table `userrole`
--
ALTER TABLE `userrole`
  ADD PRIMARY KEY (`Username`,`Rolename`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `movieID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `ratings`
--
ALTER TABLE `ratings`
  MODIFY `ratingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ratings`
--
ALTER TABLE `ratings`
  ADD CONSTRAINT `movID` FOREIGN KEY (`movieID`) REFERENCES `movies` (`movieID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usID` FOREIGN KEY (`userID`) REFERENCES `user` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
