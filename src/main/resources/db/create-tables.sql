-- Drop the e-commerce database if it exists
DROP DATABASE IF EXISTS `e-commerce`;

-- Create the database
CREATE DATABASE IF NOT EXISTS `e-commerce`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci
    DEFAULT ENCRYPTION = 'N';

-- Switch to the e-commerce database
USE `e-commerce`;

-- Drop the tables if they exist to avoid errors during creation
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `cart_items`;
DROP TABLE IF EXISTS `carts`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `users`;

-- Create the users table first since other tables reference it
CREATE TABLE IF NOT EXISTS `users`
(
    `user_id`       bigint unsigned NOT NULL AUTO_INCREMENT,
    `username`      varchar(50)     NOT NULL,
    `password_hash` varchar(255)    NOT NULL,
    `email`         varchar(100)    NOT NULL,
    `first_name`    varchar(50)     NOT NULL,
    `last_name`     varchar(50)     NOT NULL,
    `created_at`    timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_id` (`user_id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Create the categories table
CREATE TABLE IF NOT EXISTS `categories`
(
    `category_id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(100)    NOT NULL,
    `created_at`  timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`category_id`),
    UNIQUE KEY `category_id` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Create the products table
CREATE TABLE IF NOT EXISTS `products`
(
    `product_id`     bigint unsigned NOT NULL AUTO_INCREMENT,
    `name`           varchar(100)    NOT NULL,
    `description`    text,
    `price`          decimal(10, 2)  NOT NULL,
    `stock_quantity` int             NOT NULL,
    `category_id`    bigint unsigned      DEFAULT NULL,
    `created_at`     timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`product_id`),
    UNIQUE KEY `product_id` (`product_id`),
    KEY `category_id` (`category_id`),
    CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Create the carts table
CREATE TABLE IF NOT EXISTS `carts`
(
    `cart_id`    bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id`    bigint unsigned NOT NULL,
    `created_at` timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp       NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`cart_id`),
    UNIQUE KEY `cart_id` (`cart_id`),
    UNIQUE KEY `user_id` (`user_id`),
    CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Create the cart_items table
CREATE TABLE IF NOT EXISTS `cart_items`
(
    `cart_item_id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `cart_id`      bigint unsigned NOT NULL,
    `product_id`   bigint unsigned NOT NULL,
    `quantity`     int             NOT NULL DEFAULT '1',
    `added_at`     timestamp       NULL     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`cart_item_id`),
    UNIQUE KEY `cart_item_id` (`cart_item_id`),
    KEY `cart_id` (`cart_id`),
    KEY `product_id` (`product_id`),
    CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`) ON DELETE CASCADE,
    CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Create the orders table
CREATE TABLE IF NOT EXISTS `orders`
(
    `order_id`     bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id`      bigint unsigned NOT NULL,
    `total_amount` decimal(10, 2)  NOT NULL,
    `status`       varchar(50)     NOT NULL,
    `created_at`   timestamp       NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `order_id` (`order_id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- Create the order_items table
CREATE TABLE IF NOT EXISTS `order_items`
(
    `order_item_id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `order_id`      bigint unsigned NOT NULL,
    `product_id`    bigint unsigned NOT NULL,
    `quantity`      int             NOT NULL,
    `price`         decimal(10, 2)  NOT NULL,
    PRIMARY KEY (`order_item_id`),
    UNIQUE KEY `order_item_id` (`order_item_id`),
    KEY `order_id` (`order_id`),
    KEY `product_id` (`product_id`),
    CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
    CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
