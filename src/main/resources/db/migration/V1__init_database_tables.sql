CREATE TABLE `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`full_name` VARCHAR(30) NOT NULL COLLATE 'utf8_general_ci',
	`username` VARCHAR(30) NOT NULL COLLATE 'utf8_general_ci',
	`email` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`password` VARCHAR(60) NOT NULL COLLATE 'utf8_general_ci',
	`mobile_number` VARCHAR(10) NOT NULL COLLATE 'utf8_general_ci',
	`locked` BIT(1) NOT NULL DEFAULT b'1',
	`first_login` BIT(1) NULL DEFAULT b'1',
	`enabled` BIT(1) NOT NULL DEFAULT b'1',
	`expired` BIT(1) NOT NULL DEFAULT b'1',
	`registered_date` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`ward_no` INT(11) NOT NULL DEFAULT '1',
	`signature` varchar(200) DEFAULT NULL,
	`stamp` varchar(200) DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `username` (`username`) USING BTREE,
	UNIQUE INDEX `mobile_number` (`mobile_number`) USING BTREE,
	UNIQUE INDEX `email` (`email`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE IF NOT EXISTS `activation_key` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`validation_key` VARCHAR(500) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `role` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`role` VARCHAR(15) NOT NULL,
	`role_nepali` VARCHAR(15) NOT NULL,
	PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `user_role` (
	`user_id` INT(11) NOT NULL,
	`role_id` INT(11) NOT NULL,
	PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE IF NOT EXISTS `form` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `form_id` VARCHAR(50) NOT NULL DEFAULT '0',
  `form_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `form_id` (`form_id`)
);

CREATE TABLE IF NOT EXISTS lg_districts (
  district_id int NOT NULL AUTO_INCREMENT,
  district_name varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  province_id int NOT NULL,
  disabled bit(1) NOT NULL DEFAULT b'0',
  district_name_nep varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (district_id)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='List of Districts';

-- Dumping structure for table ipalika.lg_municipality
CREATE TABLE IF NOT EXISTS lg_municipality (
  municipality_id int NOT NULL AUTO_INCREMENT,
  municipality_name varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  municipality_name_nep varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  province_id int NOT NULL,
  district_id int NOT NULL,
  disabled bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (municipality_id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='List of Municipality';


-- Dumping structure for table ipalika.lg_province
CREATE TABLE IF NOT EXISTS lg_province (
  province_id int NOT NULL AUTO_INCREMENT,
  province_name varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  disabled bit(1) NOT NULL DEFAULT b'0',
   province_name_nep varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (province_id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='Provinces of Nepal';


-- Dumping structure for table ipalika.lg_ward
CREATE TABLE IF NOT EXISTS `lg_ward` (
  `ward_id` int NOT NULL AUTO_INCREMENT,
  `ward_description` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `municipality_id` int NOT NULL DEFAULT '0',
  `disabled` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ward_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `form` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`form_id` VARCHAR(50) NOT NULL DEFAULT '0',
	`form_name` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `form_id` (`form_id`)
);


CREATE TABLE IF NOT EXISTS `question` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`indx` INT(11) NOT NULL,
	`question_id` VARCHAR(50) NOT NULL,
	`description` TEXT NOT NULL,
	`group` VARCHAR(50) NOT NULL,
	`required` INT(11) NOT NULL DEFAULT 0,
	`type_id` INT(11) NOT NULL DEFAULT 1,
	`form_id` INT(11) NOT NULL,
	`reportable` BIT(1) NOT NULL DEFAULT b'0',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `question_id` (`question_id`),
	UNIQUE INDEX `indx` (`indx`)
);


CREATE TABLE IF NOT EXISTS `question_type` (
	`type_id` INT(11) NOT NULL AUTO_INCREMENT,
	`type_name` VARCHAR(20) NOT NULL DEFAULT '0',
	PRIMARY KEY (`type_id`)
);


CREATE TABLE IF NOT EXISTS `options` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`option_id` INT NOT NULL,
	`option_text` VARCHAR(200) NOT NULL,
	`question_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `favourite_place` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`fav_place_id` VARCHAR(100) NOT NULL,
	`fav_place_name` TEXT,
	`fav_place_type` TEXT,
	`fav_place_desc` TEXT,
	`fav_place_photo` TEXT,
	`fav_place_location` TEXT,
	`fav_place_ward` TEXT,
	`added_by` INT(11) NULL DEFAULT 0,
	`deleted` bit(1) NOT NULL DEFAULT b'0',
	PRIMARY KEY (`id`),
	UNIQUE KEY `fav_place_id_UNIQUE` (`fav_place_id`)
);


CREATE TABLE IF NOT EXISTS `family_member` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`family_id` varchar(50) NOT NULL,
	`full_name` varchar(50) NOT NULL,
	`relation_id` int(11) NOT NULL,
	`age` int(11) NOT NULL,
	`gender_id` int(11) NOT NULL,
	`marital_status` int(11) NOT NULL,
	`qualification_id` int(11) NOT NULL,
	`occupation` int(11) NOT NULL DEFAULT 0,
	`has_voter_id` bit(1) NOT NULL DEFAULT b'0',
	`health_status` int(11) NOT NULL DEFAULT 0,
	`educational_institute` int(11) DEFAULT NULL,
	`disability` int(11) NOT NULL DEFAULT 0,
	`member_id` varchar(50) NOT NULL DEFAULT '0',
	`is_dead` bit(1) NOT NULL DEFAULT b'0',
	`dob_ad` varchar(45) NOT NULL,
	`dob_bs` varchar(45) NOT NULL,
	`deleted` bit(1) NOT NULL DEFAULT b'0',
	PRIMARY KEY (`id`),
	UNIQUE KEY `member_id_UNIQUE` (`member_id`)
);


CREATE TABLE IF NOT EXISTS `family_relation` (
	`relation_id` INT(11) NOT NULL AUTO_INCREMENT,
	`relation_nepali` VARCHAR(50) NOT NULL DEFAULT '0',
	`relation_english` VARCHAR(50) NOT NULL DEFAULT '0',
	PRIMARY KEY (`relation_id`)
);

CREATE TABLE IF NOT EXISTS `gender` (
	`gender_id` INT(11) NOT NULL AUTO_INCREMENT,
	`gender_nepali` VARCHAR(45) DEFAULT NULL,
	`gender_english` VARCHAR(45) DEFAULT NULL,
	PRIMARY KEY (`gender_id`)
);

CREATE TABLE IF NOT EXISTS `academic_qualification` (
  `qualification_id` INT(11) NOT NULL AUTO_INCREMENT,
  `qualification_nep` VARCHAR(50) DEFAULT NULL,
  `qualification_eng` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`qualification_id`)
);

CREATE TABLE `population_report` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`based_on` VARCHAR(50) NOT NULL DEFAULT '0',
	`option_1` DOUBLE NOT NULL DEFAULT 0,
	`option_2` DOUBLE NOT NULL DEFAULT 0,
	`option_3` DOUBLE NOT NULL DEFAULT 0,
	`option_4` DOUBLE NOT NULL DEFAULT 0,
	`option_5` DOUBLE NOT NULL DEFAULT 0,
	`option_6` DOUBLE NOT NULL DEFAULT 0,
	`option_7` DOUBLE NOT NULL DEFAULT 0,
	`option_8` DOUBLE NOT NULL DEFAULT 0,
	`option_9` DOUBLE NOT NULL DEFAULT 0,
	`option_10` DOUBLE NOT NULL DEFAULT 0,
	`option_11` DOUBLE NOT NULL DEFAULT 0,
	`option_12` DOUBLE NOT NULL DEFAULT 0,
	`option_13` DOUBLE NOT NULL DEFAULT 0,
	`option_14` DOUBLE NOT NULL DEFAULT 0,
	`option_15` DOUBLE NOT NULL DEFAULT 0,
	`total` DOUBLE NOT NULL DEFAULT 0,
	`ward` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `based_on` (`based_on`)
);


CREATE TABLE `question_report` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`question_id` INT(11) NOT NULL DEFAULT 0,
	`option_1` DOUBLE NOT NULL DEFAULT 0,
	`option_2` DOUBLE NOT NULL DEFAULT 0,
	`option_3` DOUBLE NOT NULL DEFAULT 0,
	`option_4` DOUBLE NOT NULL DEFAULT 0,
	`option_5` DOUBLE NOT NULL DEFAULT 0,
	`option_6` DOUBLE NOT NULL DEFAULT 0,
	`option_7` DOUBLE NOT NULL DEFAULT 0,
	`option_8` DOUBLE NOT NULL DEFAULT 0,
	`option_9` DOUBLE NOT NULL DEFAULT 0,
	`option_10` DOUBLE NOT NULL DEFAULT 0,
	`option_11` DOUBLE NOT NULL DEFAULT 0,
	`option_12` DOUBLE NOT NULL DEFAULT 0,
	`option_13` DOUBLE NOT NULL DEFAULT 0,
	`option_14` DOUBLE NOT NULL DEFAULT 0,
	`option_15` DOUBLE NOT NULL DEFAULT 0,
	`total` DOUBLE NOT NULL DEFAULT 0,
	`ward` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `question_id` (`question_id`)
);

CREATE TABLE IF NOT EXISTS `districts` (
  `district_id` INT(11) NOT NULL AUTO_INCREMENT,
  `district_name_nep` VARCHAR(50) NOT NULL,
  `district_name_eng` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`district_id`)
);

CREATE TABLE IF NOT EXISTS `favourite_place_type` (
  `type_id` INT(11) NOT NULL AUTO_INCREMENT,
  `place_type_nep` VARCHAR(50) NOT NULL,
  `place_type_eng` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`type_id`)
);

CREATE TABLE IF NOT EXISTS `marital_status` (
  `marital_status_id` INT(11) NOT NULL AUTO_INCREMENT,
  `marital_status_nep` VARCHAR(50) NOT NULL,
  `marital_status_eng` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`marital_status_id`)
);

CREATE TABLE IF NOT EXISTS `ward` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`ward_number` INT(11) NOT NULL,
	`location` VARCHAR(100) NOT NULL DEFAULT '0',
	`name` VARCHAR(100) NOT NULL DEFAULT '0',
	`ward_description` TEXT,
	`main_person` VARCHAR(5000) NOT NULL DEFAULT '0',
	`contact_no` VARCHAR(50) NOT NULL DEFAULT '0',
	`building_image` VARCHAR(300) DEFAULT '0',
	PRIMARY KEY (`id`),
	UNIQUE KEY `ward_number_UNIQUE` (`ward_number`)
);


CREATE TABLE `death_record` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`registration_number` VARCHAR(150) NOT NULL COLLATE 'utf8_general_ci',
	`member_id` VARCHAR(15) NOT NULL COLLATE 'utf8_general_ci',
	`death_cause` VARCHAR(150) NOT NULL COLLATE 'utf8_general_ci',
	`demise_date` DATETIME NOT NULL,
	`place` VARCHAR(150) NOT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `registration_number` (`registration_number`) USING BTREE,
	UNIQUE INDEX `member_id` (`member_id`) USING BTREE
);


CREATE TABLE `extra_report` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`report_name` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`data` INT(11) NOT NULL DEFAULT '0',
	`ward` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `report_name` (`report_name`) USING BTREE
);

CREATE TABLE `favourite_place_report` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`place_type` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`data` INT(11) NOT NULL DEFAULT '0',
	`ward` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `place_type` (`place_type`) USING BTREE
);

CREATE TABLE IF NOT EXISTS `differently_abled` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `differently_abled_nep` varchar(200) DEFAULT NULL,
  `differently_abled_eng` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `educational_institute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `educational_institute_nep` varchar(200) DEFAULT NULL,
  `educational_institute_eng` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `health_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `health_status_nep` varchar(200) NOT NULL,
  `health_status_eng` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `occupation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `occupation_nep` varchar(200) DEFAULT NULL,
  `occupation_eng` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS status_level (
    type_id INT(11) NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(20) NOT NULL DEFAULT '0',
    PRIMARY KEY (type_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='verification status level';

CREATE TABLE IF NOT EXISTS `standard_log`(
`form_id` int NOT NULL,
`token_id` VARCHAR(50)  NOT NULL,
`status` INT(11)  NOT NULL DEFAULT 1,
`status_1_by` VARCHAR(50) ,
`status_1_in` DATETIME ,
`status_2_by` VARCHAR(50) ,
`status_2_in` DATETIME ,
`registration_number` VARCHAR(50),
`status_3_by` VARCHAR(50),
`status_3_in` DATETIME ,
`status_4_by` VARCHAR(50) ,
`status_4_in` DATETIME ,
`status_5_by` VARCHAR(50),
`status_5_in` DATETIME ,
`status_6_by` VARCHAR(50),
`status_6_in` DATETIME
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='stander form id ';


CREATE TABLE IF NOT EXISTS forms_table(
`form_id` INT(11) NOT NULL AUTO_INCREMENT,
`form_name` VARCHAR(50) NOT NULL,
`form_label` VARCHAR(50) NOT NULL,
`form_database_table` VARCHAR(100) NOT NULL,
PRIMARY KEY (`form_id`),
UNIQUE INDEX `form_name` (`form_name`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='form table that hold unique id of form and table name ';


CREATE TABLE IF NOT EXISTS forms_details(
`id` INT(11) NOT NULL AUTO_INCREMENT,
`form_id` INT(11) NOT NULL,
`element_id` INT(11) NOT NULL,
`element_name` VARCHAR(100) NOT NULL,
`element_type` INT(11) NOT NULL,
`fillable_by_system` bit(1) NOT NULL DEFAULT b'0',
`is_required` bit(1) NOT NULL DEFAULT b'0',
 PRIMARY KEY (`id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='form details hold garcha';


CREATE TABLE `answer` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`form_id` INT(11) NOT NULL DEFAULT 0,
	`entry_date` DATETIME NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`duration` VARCHAR(50) NOT NULL DEFAULT '0',
	`filled_id` VARCHAR(50) NOT NULL DEFAULT '0',
	`added_by` INT(11) NOT NULL DEFAULT 0,
	`verified_by` INT(11) NOT NULL DEFAULT 0,
	`modified_by` INT(11) NOT NULL DEFAULT 0,
	`answer_1` TEXT NULL,
	`answer_2` TEXT NULL,
	`answer_3` TEXT NULL,
	`answer_4` TEXT NULL,
	`answer_5` TEXT NULL,
	`answer_6` TEXT NULL,
	`answer_7` TEXT NULL,
	`answer_8` TEXT NULL,
	`answer_9` TEXT NULL,
	`answer_10` TEXT NULL,
	`answer_11` TEXT NULL,
	`answer_12` TEXT NULL,
	`answer_13` TEXT NULL,
	`answer_14` TEXT NULL,
	`answer_15` TEXT NULL,
	`answer_16` TEXT NULL,
	`answer_17` TEXT NULL,
	`answer_18` TEXT NULL,
	`answer_19` TEXT NULL,
	`answer_20` TEXT NULL,
	`answer_21` TEXT NULL,
	`answer_22` TEXT NULL,
	`answer_23` TEXT NULL,
	`answer_24` TEXT NULL,
	`answer_25` TEXT NULL,
	`answer_26` TEXT NULL,
	`answer_27` TEXT NULL,
	`answer_28` TEXT NULL,
	`answer_29` TEXT NULL,
	`answer_30` TEXT NULL,
	`answer_31` TEXT NULL,
	`answer_32` TEXT NULL,
	`answer_33` TEXT NULL,
	`answer_34` TEXT NULL,
	`answer_35` TEXT NULL,
	`answer_36` TEXT NULL,
	`answer_37` TEXT NULL,
	`answer_38` TEXT NULL,
	`answer_39` TEXT NULL,
	`answer_40` TEXT NULL,
	`answer_41` TEXT NULL,
	`answer_42` TEXT NULL,
	`answer_43` TEXT NULL,
	`answer_44` TEXT NULL,
	`answer_45` TEXT NULL,
	`answer_46` TEXT NULL,
	`answer_47` TEXT NULL,
	`answer_48` TEXT NULL,
	`answer_49` TEXT NULL,
	`answer_50` TEXT NULL,
	`answer_51` TEXT NULL,
	`answer_52` TEXT NULL,
	`answer_53` TEXT NULL,
	`answer_54` TEXT NULL,
	`answer_55` TEXT NULL,
	`answer_56` TEXT NULL,
	`answer_57` TEXT NULL,
	`answer_58` TEXT NULL,
	`answer_59` TEXT NULL,
	`answer_60` TEXT NULL,
	`answer_61` TEXT NULL,
	`answer_62` TEXT NULL,
	`answer_63` TEXT NULL,
	`answer_64` TEXT NULL,
	`answer_65` TEXT NULL,
	`answer_66` TEXT NULL,
	`answer_67` TEXT NULL,
	`answer_68` TEXT NULL,
	`answer_69` TEXT NULL,
	`answer_70` TEXT NULL,
	`answer_71` TEXT NULL,
	`answer_72` TEXT NULL,
	`answer_73` TEXT NULL,
	`answer_74` TEXT NULL,
	`answer_75` TEXT NULL,
	`answer_76` TEXT NULL,
	`answer_77` TEXT NULL,
	`answer_78` TEXT NULL,
	`answer_79` TEXT NULL,
	`answer_80` TEXT NULL,
	`answer_81` TEXT NULL,
	`answer_82` TEXT NULL,
	`answer_83` TEXT NULL,
	`answer_84` TEXT NULL,
	`answer_85` TEXT NULL,
	`answer_86` TEXT NULL,
	`answer_87` TEXT NULL,
	`answer_88` TEXT NULL,
	`answer_89` TEXT NULL,
	`answer_90` TEXT NULL,
	`answer_91` TEXT NULL,
	`answer_92` TEXT NULL,
	`answer_93` TEXT NULL,
	`answer_94` TEXT NULL,
	`answer_95` TEXT NULL,
	`answer_96` TEXT NULL,
	`answer_97` TEXT NULL,
	`answer_98` TEXT NULL,
	`answer_99` TEXT NULL,
	`answer_100` TEXT NULL,
	`deleted` bit(1) NOT NULL DEFAULT b'0',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `filled_id` (`filled_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `sifaris_report` (
	 `id` INT(11) NOT NULL AUTO_INCREMENT,
	 `form_id` INT(11) NOT NULL,
	 `form_title` VARCHAR(150) NOT NULL,
	 `unverified` INT(11) NOT NULL,
	 `stamped` INT(11) NOT NULL,
	 `registered` INT(11) NOT NULL,
	 `processed` INT(11) NOT NULL,
	 `verified` INT(11) NOT NULL,
	 `total` INT(11) NOT NULL,

	 PRIMARY KEY (`id`),
	 UNIQUE KEY `form_id` (`form_id`)
);

CREATE TABLE IF NOT EXISTS sifaris_form_fields_mapper(
    form_id  INT (11) NOT NULL,
    element_id INT (11) NOT NULL,
    element_placeholder VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS egovernance_electricity_connection(
form_id INT NOT NULL ,
ipalika_username VARCHAR(100) NOT NULL DEFAULT "",
token_id varchar(100) NOT NULL,
answer_1 VARCHAR(200) ,
answer_2 VARCHAR(200) ,
answer_3 VARCHAR(200) ,
answer_4 VARCHAR(200) ,
answer_5 VARCHAR(200) ,
answer_6 VARCHAR(200) ,
answer_7 VARCHAR(200) ,
answer_8 VARCHAR(200) ,
answer_9 VARCHAR(200) ,
answer_10 VARCHAR(200) ,
answer_11 VARCHAR(200) ,
answer_12 VARCHAR(200) ,
answer_13 VARCHAR(200) ,
answer_14 VARCHAR(200) ,
answer_15 VARCHAR(200) ,
answer_16 VARCHAR(200) ,
answer_17 VARCHAR(200) ,
answer_18 VARCHAR(200) ,
answer_19 VARCHAR(200) ,
answer_20 VARCHAR(200) ,
answer_21 VARCHAR(200) ,
answer_22 VARCHAR(200) ,
answer_23 VARCHAR(200) ,
answer_24 VARCHAR(200) ,
answer_25 VARCHAR(200) ,
answer_26 VARCHAR(200) ,
answer_27 VARCHAR(200) ,
answer_28 VARCHAR(200) ,
answer_29 VARCHAR(200) ,
answer_30 VARCHAR(200),
answer_101 VARCHAR(200),
answer_102 VARCHAR(200),
answer_103 VARCHAR(200),
answer_104 VARCHAR(200),
answer_105 VARCHAR(200)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='egovernance_electricity_connection';




CREATE TABLE IF NOT EXISTS egovernance_water_connection(
form_id INT NOT NULL ,
ipalika_username VARCHAR(100) NOT NULL DEFAULT "",
token_id varchar(100) NOT NULL,
answer_1 VARCHAR(200) ,
answer_2 VARCHAR(200) ,
answer_3 VARCHAR(200) ,
answer_4 VARCHAR(200) ,
answer_5 VARCHAR(200) ,
answer_6 VARCHAR(200) ,
answer_7 VARCHAR(200) ,
answer_8 VARCHAR(200) ,
answer_9 VARCHAR(200) ,
answer_10 VARCHAR(200) ,
answer_11 VARCHAR(200) ,
answer_12 VARCHAR(200) ,
answer_13 VARCHAR(200) ,
answer_14 VARCHAR(200) ,
answer_15 VARCHAR(200) ,
answer_16 VARCHAR(200) ,
answer_17 VARCHAR(200) ,
answer_18 VARCHAR(200) ,
answer_19 VARCHAR(200) ,
answer_20 VARCHAR(200) ,
answer_21 VARCHAR(200) ,
answer_22 VARCHAR(200) ,
answer_23 VARCHAR(200) ,
answer_24 VARCHAR(200) ,
answer_25 VARCHAR(200) ,
answer_26 VARCHAR(200) ,
answer_27 VARCHAR(200) ,
answer_28 VARCHAR(200) ,
answer_29 VARCHAR(200) ,
answer_30 VARCHAR(200),
answer_101 VARCHAR(200),
answer_102 VARCHAR(200),
answer_103 VARCHAR(200),
answer_104 VARCHAR(200),
answer_105 VARCHAR(200)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='egovernance_water_connection';




CREATE TABLE IF NOT EXISTS egovernance_ghar_patal(
form_id INT NOT NULL ,
ipalika_username VARCHAR(100) NOT NULL DEFAULT "",
token_id varchar(100) NOT NULL,
answer_1 VARCHAR(200) ,
answer_2 VARCHAR(200) ,
answer_3 VARCHAR(200) ,
answer_4 VARCHAR(200) ,
answer_5 VARCHAR(200) ,
answer_6 VARCHAR(200) ,
answer_7 VARCHAR(200) ,
answer_8 VARCHAR(200) ,
answer_9 VARCHAR(200) ,
answer_10 VARCHAR(200) ,
answer_11 VARCHAR(200) ,
answer_12 VARCHAR(200) ,
answer_13 VARCHAR(200) ,
answer_14 VARCHAR(200) ,
answer_15 VARCHAR(200) ,
answer_16 VARCHAR(200) ,
answer_17 VARCHAR(200) ,
answer_18 VARCHAR(200) ,
answer_19 VARCHAR(200) ,
answer_20 VARCHAR(200) ,
answer_21 VARCHAR(200) ,
answer_22 VARCHAR(200) ,
answer_23 VARCHAR(200) ,
answer_24 VARCHAR(200) ,
answer_25 VARCHAR(200) ,
answer_26 VARCHAR(200) ,
answer_27 VARCHAR(200) ,
answer_28 VARCHAR(200) ,
answer_29 VARCHAR(200) ,
answer_30 VARCHAR(200),
answer_101 VARCHAR(200),
answer_102 VARCHAR(200),
answer_103 VARCHAR(200),
answer_104 VARCHAR(200),
answer_105 VARCHAR(200)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='egovernance_ghar_patal';