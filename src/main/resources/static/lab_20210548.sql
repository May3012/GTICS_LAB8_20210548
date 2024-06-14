-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- Schema movies
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema movies
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `movies` DEFAULT CHARACTER SET utf8mb3 ;


USE `movies` ;

-- -----------------------------------------------------
-- Table `movies`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movies`.`user` (
  `id` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `movies`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `movies`.`movie` (
  `id_movie` INT NOT NULL,
  `id_user` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `overview` VARCHAR(45) NOT NULL,
  `popularidad` VARCHAR(45) NOT NULL,
  `fecha_lanzamiento` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_movie`),
  INDEX `fk_movie_user_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `fk_movie_user`
    FOREIGN KEY (`id_user`)
    REFERENCES `movies`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
