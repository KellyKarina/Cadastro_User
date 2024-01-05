CREATE DATABASE IF NOT EXISTS cadastrousuario;

USE cadastrousuario;

CREATE TABLE USERS (
    id bigint not null AUTO_INCREMENT primary key,
    name varchar(50) not null,
    age decimal(3, 0) not null,
    endereco varchar(50) not null,
    telefone varchar(15) not null
);

SELECT * FROM USERS;

CREATE TABLE CONTAS (
    id bigint not null auto_increment primary key,
    tipoConta enum('CONTA_CORRENTE', 'CONTA_POUPANCA'),
    saldo decimal(10, 2),
    userId bigint not null,
    numConta int not null,
    foreign key (userId) references USERS(id)
);

SELECT * FROM CONTAS;