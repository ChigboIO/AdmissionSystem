-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 22, 2014 at 12:40 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `admission_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `admitted`
--

CREATE TABLE IF NOT EXISTS `admitted` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `department` varchar(100) NOT NULL,
  `regno` varchar(11) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `othernames` varchar(100) NOT NULL,
  `sex` enum('male','female') NOT NULL,
  `dob` varchar(30) NOT NULL,
  `state` varchar(30) NOT NULL,
  `lga` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regno` (`regno`),
  KEY `department` (`department`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `admitted`
--

INSERT INTO `admitted` (`id`, `department`, `regno`, `surname`, `othernames`, `sex`, `dob`, `state`, `lga`) VALUES
(5, 'Computer Science', '92321145AB', 'Chigbo', 'Emmanuel C', 'male', '23/11/90', 'Enugu', 'Ezeagu'),
(6, 'Mathematics', '456876fd', 'Obi', 'Julieth', 'female', '11/11/1993', 'Enugu', 'Nsukka');

-- --------------------------------------------------------

--
-- Table structure for table `alloweddepts`
--

CREATE TABLE IF NOT EXISTS `alloweddepts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department` varchar(100) NOT NULL,
  `sourceDepartment` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sourceDepartment` (`sourceDepartment`),
  KEY `department` (`department`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `alloweddepts`
--

INSERT INTO `alloweddepts` (`id`, `department`, `sourceDepartment`) VALUES
(6, 'Computer Science', 'Computer Science'),
(7, 'Computer Science', 'Mathematics'),
(8, 'Computer Science', 'Statistics');

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE IF NOT EXISTS `departments` (
  `departmentId` int(4) NOT NULL AUTO_INCREMENT,
  `faculty` varchar(100) NOT NULL DEFAULT 'Physical Sciences',
  `departmentName` varchar(100) NOT NULL,
  `utme_cut_off` int(3) NOT NULL DEFAULT '0',
  `putme_cut_off` int(3) NOT NULL DEFAULT '0',
  `PG_GP` float NOT NULL DEFAULT '0',
  `PGScreeningCut` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`departmentId`),
  UNIQUE KEY `departmentName` (`departmentName`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`departmentId`, `faculty`, `departmentName`, `utme_cut_off`, `putme_cut_off`, `PG_GP`, `PGScreeningCut`) VALUES
(1, 'Physical Sciences', 'Computer Science', 200, 230, 3.5, 200),
(2, 'Physical Sciences', 'Mathematics', 200, 220, 0, 0),
(3, 'Physical Sciences', 'Statistics', 0, 0, 0, 0),
(4, 'Physical Sciences', 'Physics and Astronomy', 0, 0, 0, 0),
(5, 'Physical Sciences', 'Pure and Industrial Chemistry', 0, 0, 0, 0),
(6, 'Physical Sciences', 'Geology', 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `optional_courses`
--

CREATE TABLE IF NOT EXISTS `optional_courses` (
  `requiredId` int(5) NOT NULL AUTO_INCREMENT,
  `department` varchar(100) NOT NULL,
  `subject` varchar(100) NOT NULL,
  PRIMARY KEY (`requiredId`),
  KEY `department` (`department`),
  KEY `subject` (`subject`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=47 ;

--
-- Dumping data for table `optional_courses`
--

INSERT INTO `optional_courses` (`requiredId`, `department`, `subject`) VALUES
(8, 'Mathematics', 'Chemistry'),
(9, 'Mathematics', 'Agricultural Science'),
(10, 'Mathematics', 'Geography'),
(43, 'Computer Science', 'Biology'),
(44, 'Computer Science', 'Agricultural Science'),
(45, 'Computer Science', 'Economics'),
(46, 'Computer Science', 'Geography');

-- --------------------------------------------------------

--
-- Table structure for table `pgadmitted`
--

CREATE TABLE IF NOT EXISTS `pgadmitted` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `department` varchar(100) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `othernames` varchar(100) NOT NULL,
  `sex` enum('male','female') NOT NULL,
  `dob` varchar(30) NOT NULL,
  `state` varchar(30) NOT NULL,
  `lga` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `department` (`department`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `pgadmitted`
--

INSERT INTO `pgadmitted` (`id`, `department`, `surname`, `othernames`, `sex`, `dob`, `state`, `lga`) VALUES
(1, 'Computer Science', 'Chigbo', 'Emmanuel C', 'male', '23 nov', 'Enugu', 'Ezeagu');

-- --------------------------------------------------------

--
-- Table structure for table `required_courses`
--

CREATE TABLE IF NOT EXISTS `required_courses` (
  `requiredId` int(5) NOT NULL AUTO_INCREMENT,
  `department` varchar(100) NOT NULL,
  `subject` varchar(100) NOT NULL,
  PRIMARY KEY (`requiredId`),
  KEY `department` (`department`),
  KEY `subject` (`subject`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=59 ;

--
-- Dumping data for table `required_courses`
--

INSERT INTO `required_courses` (`requiredId`, `department`, `subject`) VALUES
(10, 'Mathematics', 'English Language'),
(11, 'Mathematics', 'Mathematics'),
(12, 'Mathematics', 'Further Mathematics'),
(13, 'Mathematics', 'Physics'),
(55, 'Computer Science', 'English Language'),
(56, 'Computer Science', 'Mathematics'),
(57, 'Computer Science', 'Physics'),
(58, 'Computer Science', 'Chemistry');

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE IF NOT EXISTS `subjects` (
  `subjectId` int(3) NOT NULL AUTO_INCREMENT,
  `subjectName` varchar(100) NOT NULL,
  PRIMARY KEY (`subjectId`),
  UNIQUE KEY `subjectName` (`subjectName`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`subjectId`, `subjectName`) VALUES
(6, 'Agricultural Science'),
(3, 'Biology'),
(4, 'Chemistry'),
(7, 'Economics'),
(1, 'English Language'),
(10, 'Further Mathematics'),
(8, 'Geography'),
(12, 'Hausa Language'),
(9, 'Health Science'),
(11, 'Igbo Language'),
(2, 'Mathematics'),
(5, 'Physics'),
(13, 'Yoruba Language');

-- --------------------------------------------------------

--
-- Table structure for table `utme_optional`
--

CREATE TABLE IF NOT EXISTS `utme_optional` (
  `requiredId` int(5) NOT NULL AUTO_INCREMENT,
  `department` varchar(100) NOT NULL,
  `subject` varchar(100) NOT NULL,
  PRIMARY KEY (`requiredId`),
  KEY `department` (`department`),
  KEY `subject` (`subject`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

--
-- Dumping data for table `utme_optional`
--

INSERT INTO `utme_optional` (`requiredId`, `department`, `subject`) VALUES
(49, 'Computer Science', 'Biology'),
(50, 'Computer Science', 'Chemistry'),
(51, 'Computer Science', 'Economics'),
(52, 'Computer Science', 'Geography'),
(53, 'Mathematics', 'Chemistry'),
(54, 'Mathematics', 'Further Mathematics');

-- --------------------------------------------------------

--
-- Table structure for table `utme_required`
--

CREATE TABLE IF NOT EXISTS `utme_required` (
  `requiredId` int(5) NOT NULL AUTO_INCREMENT,
  `department` varchar(100) NOT NULL,
  `subject` varchar(100) NOT NULL,
  PRIMARY KEY (`requiredId`),
  KEY `department` (`department`),
  KEY `subject` (`subject`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=44 ;

--
-- Dumping data for table `utme_required`
--

INSERT INTO `utme_required` (`requiredId`, `department`, `subject`) VALUES
(39, 'Computer Science', 'English Language'),
(40, 'Computer Science', 'Mathematics'),
(41, 'Computer Science', 'Physics'),
(42, 'Mathematics', 'English Language'),
(43, 'Mathematics', 'Mathematics');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alloweddepts`
--
ALTER TABLE `alloweddepts`
  ADD CONSTRAINT `alloweddepts_ibfk_1` FOREIGN KEY (`department`) REFERENCES `departments` (`departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `alloweddepts_ibfk_2` FOREIGN KEY (`sourceDepartment`) REFERENCES `departments` (`departmentName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `optional_courses`
--
ALTER TABLE `optional_courses`
  ADD CONSTRAINT `optional_courses_ibfk_1` FOREIGN KEY (`department`) REFERENCES `departments` (`departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `optional_courses_ibfk_2` FOREIGN KEY (`subject`) REFERENCES `subjects` (`subjectName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `required_courses`
--
ALTER TABLE `required_courses`
  ADD CONSTRAINT `required_courses_ibfk_1` FOREIGN KEY (`department`) REFERENCES `departments` (`departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `required_courses_ibfk_2` FOREIGN KEY (`subject`) REFERENCES `subjects` (`subjectName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `utme_optional`
--
ALTER TABLE `utme_optional`
  ADD CONSTRAINT `utme_optional_ibfk_1` FOREIGN KEY (`department`) REFERENCES `departments` (`departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utme_optional_ibfk_2` FOREIGN KEY (`subject`) REFERENCES `subjects` (`subjectName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `utme_required`
--
ALTER TABLE `utme_required`
  ADD CONSTRAINT `utme_required_ibfk_1` FOREIGN KEY (`department`) REFERENCES `departments` (`departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utme_required_ibfk_2` FOREIGN KEY (`subject`) REFERENCES `subjects` (`subjectName`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
