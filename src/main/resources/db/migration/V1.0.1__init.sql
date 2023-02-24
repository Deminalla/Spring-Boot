use demodb;

DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS `order`;
-- i do `...` because order is an sql keyword, so this is done to avoid errors
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS product_in_order;

CREATE TABLE client (
id_number bigint(20) NOT NULL,
name varchar(45) NOT NULL,
surname varchar(45) NOT NULL,
email varchar(50) NOT NULL,
age int NOT NULL,
PRIMARY KEY (id_number),
CONSTRAINT appropriate_age CHECK ( age > 16 )
);

CREATE TABLE company (
company_number bigint(20) NOT NULL AUTO_INCREMENT,
name varchar(45) NOT NULL,
city varchar(45) NOT NULL,
PRIMARY KEY (company_number)
);

CREATE TABLE product (
name varchar(45) NOT NULL,
category varchar(45) NOT NULL,
amount bigint(20) DEFAULT 1,
city varchar(45) NOT NULL,
PRIMARY KEY (name),
CONSTRAINT amount_positive CHECK ( amount >= 0 )
);


CREATE TABLE `order` (
order_number bigint(20) NOT NULL AUTO_INCREMENT,
client_id_number bigint(20) NOT NULL,
created_at datetime NOT NULL,
price decimal (20,2) NOT NULL,
company_number bigint(20),
status varchar(30) NOT NULL,
paid smallint NOT NULL,
PRIMARY KEY (order_number),
FOREIGN KEY (client_id_number) REFERENCES client(id_number),
FOREIGN KEY (company_number) REFERENCES company(company_number)
);

CREATE TABLE product_in_order (
order_number bigint(20) NOT NULL,
product_name varchar(45) NOT NULL,
amount bigint(20),
PRIMARY KEY (order_number, product_name),
FOREIGN KEY (order_number) REFERENCES `order`(order_number),
FOREIGN KEY (product_name) REFERENCES product(name)
);