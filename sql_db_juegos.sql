create database juegos;
use juegos;

create table tarjetas(
id int primary key auto_increment,
nombre varchar(45),
apellido varchar(45),
email varchar(45),
telefono bigint,
saldo double
);