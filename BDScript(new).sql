-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema crm
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema crm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `crm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `crm` ;

-- -----------------------------------------------------
-- Table `crm`.`personal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`personal` (
  `personal_id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `nameSername` VARCHAR(200) NULL DEFAULT NULL,
  `contacts` VARCHAR(100) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `role` VARCHAR(100) NULL DEFAULT NULL,
  `subrole` VARCHAR(100) NULL DEFAULT NULL,
  `status` VARCHAR(100) NULL DEFAULT NULL,
  `description` VARCHAR(1000) NULL DEFAULT NULL,
  `regDate` DATE NULL DEFAULT NULL,
  `imageName` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`personal_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`clients` (
  `clients_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `responsable_id` INT NULL DEFAULT NULL,
  `phone` VARCHAR(100) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `adress` VARCHAR(100) NULL DEFAULT NULL,
  `description` VARCHAR(400) NULL DEFAULT NULL,
  `type` VARCHAR(100) NULL DEFAULT NULL,
  `work_type` VARCHAR(100) NULL DEFAULT NULL,
  `reg_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`clients_id`),
  INDEX `fk_personal_clients` (`responsable_id` ASC) VISIBLE,
  CONSTRAINT `fk_personal_clients`
    FOREIGN KEY (`responsable_id`)
    REFERENCES `crm`.`personal` (`personal_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`projects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`projects` (
  `projects_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `description` VARCHAR(200) NULL DEFAULT NULL,
  `responsable_id` INT NULL DEFAULT NULL,
  `deadline` DATE NULL DEFAULT NULL,
  `status` VARCHAR(100) NULL DEFAULT NULL,
  `creation_date` DATE NULL DEFAULT NULL,
  `trudozatraty` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `start_control` VARCHAR(100) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `end_control` VARCHAR(100) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `checker_id` INT NULL DEFAULT NULL,
  `plan_control` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `izm` VARCHAR(30) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  PRIMARY KEY (`projects_id`),
  INDEX `fk_responsable_projects` (`responsable_id` ASC) VISIBLE,
  INDEX `fk_checker_project` (`checker_id` ASC) VISIBLE,
  CONSTRAINT `fk_checker_project`
    FOREIGN KEY (`checker_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_responsable_projects`
    FOREIGN KEY (`responsable_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`processes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`processes` (
  `processes_id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(100) NULL DEFAULT NULL,
  `client_id` INT NULL DEFAULT NULL,
  `responsable_id` INT NULL DEFAULT NULL,
  `checker_id` INT NULL DEFAULT NULL,
  `payment` VARCHAR(100) NULL DEFAULT NULL,
  `description` VARCHAR(100) NULL DEFAULT NULL,
  `project_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`processes_id`),
  INDEX `fk_checker_process` (`checker_id` ASC) VISIBLE,
  INDEX `fk_client_process` (`client_id` ASC) VISIBLE,
  INDEX `fk_project_process` (`project_id` ASC) VISIBLE,
  INDEX `fk_responsable_process` (`responsable_id` ASC) VISIBLE,
  CONSTRAINT `fk_checker_process`
    FOREIGN KEY (`checker_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_client_process`
    FOREIGN KEY (`client_id`)
    REFERENCES `crm`.`clients` (`clients_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_project_process`
    FOREIGN KEY (`project_id`)
    REFERENCES `crm`.`projects` (`projects_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_responsable_process`
    FOREIGN KEY (`responsable_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`tasks` (
  `tasks_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `responsable_id` INT NULL DEFAULT NULL,
  `description` VARCHAR(300) NULL DEFAULT NULL,
  `checker_id` INT NULL DEFAULT NULL,
  `deadline` DATE NULL DEFAULT NULL,
  `project_id` INT NULL DEFAULT NULL,
  `process_id` INT NULL DEFAULT NULL,
  `status` VARCHAR(100) NULL DEFAULT NULL,
  `client_id` INT NULL DEFAULT NULL,
  `creation_date` DATETIME NULL DEFAULT NULL,
  `priority` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  PRIMARY KEY (`tasks_id`),
  INDEX `fk_checker_tasks` (`checker_id` ASC) VISIBLE,
  INDEX `fk_process_tasks` (`process_id` ASC) VISIBLE,
  INDEX `fk_project_tasks` (`project_id` ASC) VISIBLE,
  INDEX `fk_responsable_tasks` (`responsable_id` ASC) VISIBLE,
  INDEX `fk_client_tasks` (`client_id` ASC) VISIBLE,
  CONSTRAINT `fk_checker_tasks`
    FOREIGN KEY (`checker_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_client_tasks`
    FOREIGN KEY (`client_id`)
    REFERENCES `crm`.`clients` (`clients_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_process_tasks`
    FOREIGN KEY (`process_id`)
    REFERENCES `crm`.`processes` (`processes_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_project_tasks`
    FOREIGN KEY (`project_id`)
    REFERENCES `crm`.`projects` (`projects_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_responsable_tasks`
    FOREIGN KEY (`responsable_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`business`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`business` (
  `business_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `description` VARCHAR(100) NULL DEFAULT NULL,
  `client_id` INT NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  `place` VARCHAR(100) NULL DEFAULT NULL,
  `responsable_id` INT NULL DEFAULT NULL,
  `process_id` INT NULL DEFAULT NULL,
  `status` VARCHAR(100) NULL DEFAULT NULL,
  `task_id` INT NULL DEFAULT NULL,
  `project_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`business_id`),
  INDEX `fk_client_business` (`client_id` ASC) VISIBLE,
  INDEX `fk_process_business` (`process_id` ASC) VISIBLE,
  INDEX `fk_responsable_business` (`responsable_id` ASC) VISIBLE,
  INDEX `fk_tasks_business` (`task_id` ASC) VISIBLE,
  INDEX `fk_project_business` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_client_business`
    FOREIGN KEY (`client_id`)
    REFERENCES `crm`.`clients` (`clients_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_process_business`
    FOREIGN KEY (`process_id`)
    REFERENCES `crm`.`processes` (`processes_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_project_business`
    FOREIGN KEY (`project_id`)
    REFERENCES `crm`.`projects` (`projects_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_responsable_business`
    FOREIGN KEY (`responsable_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_tasks_business`
    FOREIGN KEY (`task_id`)
    REFERENCES `crm`.`tasks` (`tasks_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`chats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`chats` (
  `chats_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(100) NULL DEFAULT NULL,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `imageName` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`chats_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`chat_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`chat_members` (
  `chat_members_id` INT NOT NULL AUTO_INCREMENT,
  `personal_id` INT NULL DEFAULT NULL,
  `chat_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`chat_members_id`),
  INDEX `fk_chat_members` (`chat_id` ASC) VISIBLE,
  INDEX `fk_members_chat` (`personal_id` ASC) VISIBLE,
  CONSTRAINT `fk_chat_members`
    FOREIGN KEY (`chat_id`)
    REFERENCES `crm`.`chats` (`chats_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_members_chat`
    FOREIGN KEY (`personal_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`journals`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`journals` (
  `journals_id` INT NOT NULL AUTO_INCREMENT,
  `process_id` INT NULL DEFAULT NULL,
  `task_id` INT NULL DEFAULT NULL,
  `project_id` INT NULL DEFAULT NULL,
  `client_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`journals_id`),
  INDEX `fk_client_journal` (`client_id` ASC) VISIBLE,
  INDEX `fk_process_journal` (`process_id` ASC) VISIBLE,
  INDEX `fk_project_journal` (`project_id` ASC) VISIBLE,
  INDEX `fk_task_journal` (`task_id` ASC) VISIBLE,
  CONSTRAINT `fk_client_journal`
    FOREIGN KEY (`client_id`)
    REFERENCES `crm`.`clients` (`clients_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_process_journal`
    FOREIGN KEY (`process_id`)
    REFERENCES `crm`.`processes` (`processes_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_project_journal`
    FOREIGN KEY (`project_id`)
    REFERENCES `crm`.`projects` (`projects_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_task_journal`
    FOREIGN KEY (`task_id`)
    REFERENCES `crm`.`tasks` (`tasks_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`comments` (
  `comments_id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(300) NULL DEFAULT NULL,
  `date` DATETIME NULL DEFAULT NULL,
  `sender_id` INT NULL DEFAULT NULL,
  `journal_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`comments_id`),
  INDEX `fk_journal_comment` (`journal_id` ASC) VISIBLE,
  INDEX `fk_sender_comment` (`sender_id` ASC) VISIBLE,
  CONSTRAINT `fk_journal_comment`
    FOREIGN KEY (`journal_id`)
    REFERENCES `crm`.`journals` (`journals_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_sender_comment`
    FOREIGN KEY (`sender_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`desks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`desks` (
  `desk_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `personal_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`desk_id`),
  INDEX `fk_desks_personal` (`personal_id` ASC) VISIBLE,
  CONSTRAINT `fk_desks_personal`
    FOREIGN KEY (`personal_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `articul` VARCHAR(100) NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `taxes` INT NULL DEFAULT NULL,
  `measurement` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`messages` (
  `messages_id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(200) NULL DEFAULT NULL,
  `date` DATE NULL DEFAULT NULL,
  `sender_id` INT NULL DEFAULT NULL,
  `chat_id` INT NULL DEFAULT NULL,
  `time` TIME NULL DEFAULT NULL,
  PRIMARY KEY (`messages_id`),
  INDEX `fk_chat_message` (`chat_id` ASC) VISIBLE,
  INDEX `fk_sender_message` (`sender_id` ASC) VISIBLE,
  CONSTRAINT `fk_chat_message`
    FOREIGN KEY (`chat_id`)
    REFERENCES `crm`.`chats` (`chats_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_sender_message`
    FOREIGN KEY (`sender_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`message_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`message_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `personal_id` INT NULL DEFAULT NULL,
  `message_id` INT NULL DEFAULT NULL,
  `status` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `chat_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_messages` (`message_id` ASC) VISIBLE,
  INDEX `fk_personal` (`personal_id` ASC) VISIBLE,
  INDEX `fk_chat_id` (`chat_id` ASC) VISIBLE,
  CONSTRAINT `fk_chat_id`
    FOREIGN KEY (`chat_id`)
    REFERENCES `crm`.`chats` (`chats_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_messages`
    FOREIGN KEY (`message_id`)
    REFERENCES `crm`.`messages` (`messages_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_personal`
    FOREIGN KEY (`personal_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`payments` (
  `payment_id` INT NOT NULL AUTO_INCREMENT,
  `creation_date` DATE NULL DEFAULT NULL,
  `deadline` DATE NULL DEFAULT NULL,
  `sub_info` VARCHAR(100) NULL DEFAULT NULL,
  `paymenter_id` INT NULL DEFAULT NULL,
  `reciever_id` INT NULL DEFAULT NULL,
  `item_id` INT NULL DEFAULT NULL,
  `amount` INT NULL DEFAULT NULL,
  `final_price` INT NULL DEFAULT NULL,
  `status` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  INDEX `fk_client_paymenter_payments` (`paymenter_id` ASC) VISIBLE,
  INDEX `fk_client_reciever_payments` (`reciever_id` ASC) VISIBLE,
  INDEX `fk_items_payments` (`item_id` ASC) VISIBLE,
  CONSTRAINT `fk_client_paymenter_payments`
    FOREIGN KEY (`paymenter_id`)
    REFERENCES `crm`.`clients` (`clients_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_client_reciever_payments`
    FOREIGN KEY (`reciever_id`)
    REFERENCES `crm`.`clients` (`clients_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_items_payments`
    FOREIGN KEY (`item_id`)
    REFERENCES `crm`.`items` (`item_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`process_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`process_members` (
  `process_members_id` INT NOT NULL AUTO_INCREMENT,
  `process_id` INT NULL DEFAULT NULL,
  `personal_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`process_members_id`),
  INDEX `fk_members_process` (`personal_id` ASC) VISIBLE,
  INDEX `fk_process_members` (`process_id` ASC) VISIBLE,
  CONSTRAINT `fk_members_process`
    FOREIGN KEY (`personal_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_process_members`
    FOREIGN KEY (`process_id`)
    REFERENCES `crm`.`processes` (`processes_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `crm`.`project_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `crm`.`project_members` (
  `project_members_id` INT NOT NULL AUTO_INCREMENT,
  `project_id` INT NULL DEFAULT NULL,
  `personal_id` INT NULL DEFAULT NULL,
  `team_name` VARCHAR(100) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  PRIMARY KEY (`project_members_id`),
  INDEX `fk_members_project` (`personal_id` ASC) VISIBLE,
  INDEX `fk_project_members` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_members_project`
    FOREIGN KEY (`personal_id`)
    REFERENCES `crm`.`personal` (`personal_id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_project_members`
    FOREIGN KEY (`project_id`)
    REFERENCES `crm`.`projects` (`projects_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
