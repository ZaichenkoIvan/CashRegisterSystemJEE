CREATE SCHEMA IF NOT EXISTS `cashregister` DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `cashregister`.`Invoices`
(
    `Invoice_id`           INT           NOT NULL AUTO_INCREMENT,
    `Invoice_project_name` VARCHAR(45)   NOT NULL,
    `Invoice_description`  VARCHAR(1000) NULL,
    PRIMARY KEY (`Invoice_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `cashregister`.`s`
(
    `_id`    INT         NOT NULL AUTO_INCREMENT,
    `_name`  VARCHAR(45) NOT NULL,
    `Invoice_id` INT         NOT NULL,
    PRIMARY KEY (`_id`),
    INDEX `fk_s_Invoices1_idx` (`Invoice_id` ASC),
    CONSTRAINT `fk_s_Invoices1`
        FOREIGN KEY (`Invoice_id`)
            REFERENCES `cashregister`.`Invoices` (`Invoice_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `cashregister`.`users`
(
    `user_id`       INT         NOT NULL AUTO_INCREMENT,
    `user_name`     VARCHAR(45) NOT NULL,
    `user_surname`  VARCHAR(45) NOT NULL,
    `user_email`    VARCHAR(60) NOT NULL,
    `user_password` VARCHAR(60) NOT NULL,
    `user_role`     VARCHAR(25) NOT NULL,
    `Invoice_id`    INT         NULL,
    PRIMARY KEY (`user_id`),
    INDEX `fk_users_Invoices1_idx` (`Invoice_id` ASC),
    CONSTRAINT `fk_users_Invoices1`
        FOREIGN KEY (`Invoice_id`)
            REFERENCES `cashregister`.`Invoices` (`Invoice_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `cashregister`.`Products`
(
    `Product_id`          INT           NOT NULL AUTO_INCREMENT,
    `Product_name`        VARCHAR(45)   NOT NULL,
    `Product_start`       DATE          NOT NULL,
    `Product_end`         DATE          NOT NULL,
    `Product_description` VARCHAR(1000) NULL,
    PRIMARY KEY (`Product_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `cashregister`.`payment`
(
    `Payment_id`          INT           NOT NULL AUTO_INCREMENT,
    `Payment_name`        VARCHAR(30)   NOT NULL,
    `Payment_spent_time`  TIME(0)       NOT NULL,
    `Payment_description` VARCHAR(1000) NULL,
    `Payment_status`      VARCHAR(20)   NOT NULL,
    `_id`           INT           NOT NULL,
    `user_id`           INT           NULL,
    `Product_id`         INT           NULL,
    PRIMARY KEY (`Payment_id`),
    INDEX `fk_payment_s1_idx` (`_id` ASC),
    INDEX `fk_payment_users1_idx` (`user_id` ASC),
    INDEX `fk_payment_Products1_idx` (`Product_id` ASC),
    CONSTRAINT `fk_payment_s1`
        FOREIGN KEY (`_id`)
            REFERENCES `cashregister`.`s` (`_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_payment_users1`
        FOREIGN KEY (`user_id`)
            REFERENCES `cashregister`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_payment_Products1`
        FOREIGN KEY (`Product_id`)
            REFERENCES `cashregister`.`Products` (`Product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
