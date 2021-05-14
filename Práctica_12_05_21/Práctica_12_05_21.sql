-- crear base de datos
show databases;

-- bases de datos
create database Movies;
use Movies;
show tables;

-- Crear tablas

create table CATEGORY(
	id int not null auto_increment,
    category_Name varchar(100) not null,
    primary key(id)
);

create unique index category_Name_idx on category (category_name asc);

create table MOVIE(
	id int not null auto_increment,
    title varchar(200) not null,
    release_Date DATETIME,
    category_Id int not null,
	primary key(id),
    constraint category_fk foreign key (category_Id) references CATEGORY(id)
);

create unique index title_idx on movie (title asc);

create table USERS(
	id int,
    username varchar(50),
	firstname varchar(50),
	lastname varchar(50)
);

create table RATING(
	movie_Id int not null,
    score int not null,
    review varchar(255),
    user_Id int
);

ALTER TABLE USERS ADD FULLTEXT(firstname);
ALTER TABLE USERS ADD FULLTEXT(lastname);
create fulltext index firstname_idx on users(firstname);

-- Agregar datos a las tablas

insert into CATEGORY(category_Name) values ('Comedy');
insert into CATEGORY(category_Name) values ('Drama');
insert into CATEGORY(category_Name) values ('Science Fiction');
insert into CATEGORY(category_Name) values ('Documental');
insert into CATEGORY(category_Name) values ('Action');
insert into CATEGORY(category_Name) values ('Romance');

-- select * from category order by id asc;

insert into USERS(username, firstname, lastname) values ('Sergi', 'Sergio', 'Lopez');
insert into USERS(username, firstname, lastname) values ('Eduar12', 'Eduardo', 'Gomez');
insert into USERS(username, firstname, lastname) values ('Steve6', 'Steve', 'Solis');

insert into MOVIE(title, release_Date, category_Id) values ('Star Wars', '1980-01-01', 3);
insert into MOVIE(title, release_Date, category_Id) values ('Jurasic Park', '1993-08-15', 3);
insert into MOVIE(title, release_Date, category_Id) values ('Jonh Wick', '2014-09-11', 5);

insert into RATING(movie_Id, user_Id, score, review) values (1, 1, 3, 'it is king of boring');
insert into RATING(movie_Id, user_Id, score, review) values (2, 1, 4, 'this one is more fun');
insert into RATING(movie_Id, user_Id, score, review) values (3, 30, 5, 'superb movie');