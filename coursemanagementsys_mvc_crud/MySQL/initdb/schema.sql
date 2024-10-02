DROP SCHEMA IF EXISTS `course_management_v2`;
CREATE SCHEMA `course_management_v2`;
USE `course_management_v2`;

SET FOREIGN_KEY_CHECKS = 0;

-- Base User table
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) UNIQUE NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Admin table extends User
CREATE TABLE `admin` (
  `id` int NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Tutor table extends User
CREATE TABLE `tutor` (
  `id` int NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Course table
CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `tutor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TITLE_UNIQUE` (`title`),  
  KEY `FK_TUTOR_idx` (`tutor_id`),
  CONSTRAINT `FK_TUTOR` 
  FOREIGN KEY (`tutor_id`) 
  REFERENCES `tutor` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Student table extends User
CREATE TABLE `student` (
  `id` int NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),  
  FOREIGN KEY (`id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Junction table for course-student relationship
CREATE TABLE `course_student` (
  `course_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`course_id`, `student_id`),
  KEY `FK_STUDENT_idx` (`student_id`),
  CONSTRAINT `FK_COURSE` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO user (username, password, role)
VALUES ('admin', '$2a$12$XJMot2y6uMM7vJSYl/It7OklGwen0bFRHirRP2UkvBbCpJLpMjAbG','ADMIN');

SET @admin_id = LAST_INSERT_ID();

INSERT INTO admin (id, first_name, last_name, email)
VALUES (@admin_id,'admin', 'admin', 'admin@admin.com');

INSERT INTO course (title)
VALUES
    ('Algorithms & Data Structure 1'),
    ('Algorithms & Data Structure 2'),
    ('Databases'),
    ('Computer System'),
    ('Web Information Processing'),
    ('Software Testing'),
    ('Mobile Application Development'),
    ('Object Oriented Programming');

