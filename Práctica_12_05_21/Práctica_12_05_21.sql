-- crear base de datos
show databases;

-- bases de datos
create database Movies;
use Movies;
show tables;

-- Crear tablas

create table MOVIE(
	id int primary key,
    title varchar(200),
    release_Date DATETIME,
    category_Id int
);

create table CATEGORY(
	id int primary key,
    name varchar(100)
);

create table RATING(
	movie_Id int not null,
    score int not null,
    review varchar(255),
    user_Id int
);

create table USERS(
	id int,
    username varchar(50),
	firstname varchar(50),
	lastname varchar(50),
    user_Id int
);

ALTER TABLE USERS ADD FULLTEXT(firstname);
ALTER TABLE USERS ADD FULLTEXT(lastname);
create fulltext index firstname_idx on users(firstname);