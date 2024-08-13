drop database if exists base_tecnical_test;
drop TABLE if exists bank_transaction cascade ;
DROP TABLE if exists account cascade ;
DROP TABLE if exists customer cascade ;
DROP TABLE if exists person cascade ;
DROP TABLE if exists "user" cascade ;

create database base_tecnical_test;

CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    identification VARCHAR(100) UNIQUE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(50),
    status varchar(1) not null
);

create table "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    person_id INT null null,
    status varchar(1) not null
);

CREATE TABLE customer (
    customer_id SERIAL PRIMARY KEY,
    person_id INT REFERENCES person(id),
    password VARCHAR(255) NOT NULL,
    status varchar(1) not null
);

CREATE TABLE account (
id Serial primary key,
    account_number int not null,
    account_type VARCHAR(50) NOT NULL,
    initial_balance DECIMAL(15, 2) NOT NULL,
    status varchar(1)not null,
    customer_id INT REFERENCES customer(customer_id)
);

CREATE TABLE bank_transaction (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    account_id INT REFERENCES account(id),
    status varchar(1) not null
);

ALTER TABLE "user"
ADD CONSTRAINT fk_person
FOREIGN KEY (person_id) 
REFERENCES person(id);

---------insert
INSERT INTO public."user"
(username, person_id, status)
VALUES('admin', NULL, '1');

INSERT INTO public.person
("name", gender, age, identification, address, phone, status)
VALUES('Jose Lema', 'Masculino', 50, '12323212321', 'Otavalo sn y principal', '098254785', '1');

INSERT INTO public.person
("name", gender, age, identification, address, phone, status)
VALUES('Marianela Montalvo', 'Femenino', 50, '11111111111111', 'Amazonas y NNUU', '097548965', '1');

INSERT INTO public.person
("name", gender, age, identification, address, phone, status)
VALUES('Juan Osorio', 'Maculino', 50, '2222222222222222', '13 junio y Equinoccial', '098874587', '1');