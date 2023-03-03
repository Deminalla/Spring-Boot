use applicationdb;

DROP TABLE IF EXISTS product_in_order;
DROP TABLE IF EXISTS `order`;
-- i do `...` because order is an sql keyword, so this is done to avoid errors
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS product;

CREATE TABLE `user` (
user_id bigint(20) NOT NULL AUTO_INCREMENT,
username varchar(45) NOT NULL,
email varchar(50) NOT NULL,
password varchar(50) NOT NULL,
balance decimal(20,2) NOT NULL,
PRIMARY KEY (user_id)
);

CREATE TABLE company (
company_id bigint(20) NOT NULL AUTO_INCREMENT,
name varchar(45) NOT NULL,
city varchar(45) NOT NULL,
PRIMARY KEY (company_id)
);

CREATE TABLE product (
name varchar(45) NOT NULL,
category varchar(45) NOT NULL,
amount bigint(20) DEFAULT 1,
PRIMARY KEY (name),
CONSTRAINT amount_positive CHECK ( amount >= 0 )
);


CREATE TABLE `order` (
order_id bigint(20) NOT NULL AUTO_INCREMENT,
user_id bigint(20) NOT NULL,
created_at datetime NOT NULL,
price decimal (20,2) NOT NULL,
company_id bigint(20),
status varchar(30) NOT NULL,
PRIMARY KEY (order_id),
FOREIGN KEY (user_id) REFERENCES `user`(user_id),
FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE TABLE product_in_order (
order_id bigint(20) NOT NULL,
product_name varchar(45) NOT NULL,
amount bigint(20),
PRIMARY KEY (order_id, product_name),
FOREIGN KEY (order_id) REFERENCES `order`(order_id),
FOREIGN KEY (product_name) REFERENCES product(name)
);