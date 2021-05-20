-- crear base de datos
-- ------------------------------------------------------------------------------
-- Schema Movies
-- ------------------------------------------------------------------------------
drop schema if exists movies;

-- ------------------------------------------------------------------------------
-- Schema Movies
-- ------------------------------------------------------------------------------ 
-- bases de datos
show databases;
create schema if not exists movies;
use movies;
show tables;

-- Crear tablas

-- ------------------------------------------------------------------------------
-- Tabla CATEGORY
-- ------------------------------------------------------------------------------
drop table if exists CATEGORY;

create table if not exists CATEGORY(
	id int not null auto_increment,
    category_Name varchar(100) not null,
    primary key(id)
);

create unique index category_name_idx on category (category_name asc);

-- ------------------------------------------------------------------------------
-- Tabla MOVIE
-- ------------------------------------------------------------------------------
drop table if exists MOVIE;

create table if not exists MOVIE(
	id int not null auto_increment,
    title varchar(200) not null,
    release_Date datetime,
    category_Id int not null,
	primary key(id),
    constraint category_fk foreign key (category_Id) references CATEGORY(id)
);

create unique index title_idx on movie (title asc);

-- ------------------------------------------------------------------------------
-- Tabla USERS
-- ------------------------------------------------------------------------------
drop table if exists USERS;

create table if not exists USERS(
	id int not null auto_increment,
    username varchar(50) not null,
	firstname varchar(50),
	lastname varchar(50),
    primary key(id)
);

ALTER TABLE USERS ADD FULLTEXT(firstname);
ALTER TABLE USERS ADD FULLTEXT(lastname);
create fulltext index firstname_idx on users(firstname);

-- ------------------------------------------------------------------------------
-- Tabla RATING
-- ------------------------------------------------------------------------------
drop table if exists RATING;

create table if not exists RATING(
	movie_Id int not null,
    score int not null check(score >= 0 and score <= 5),
    review varchar(200),
    user_Id int not null,
    constraint movie_idx foreign key(movie_Id) references movie(id),
    constraint user_idx foreign key(user_Id) references users(id)
);

create index movie_idx on rating(movie_Id ASC);
create index user_idx on rating(user_Id ASC);

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
insert into USERS(username, firstname, lastname) values ('Mike', 'Michael', 'Barrantes');
insert into USERS(username, firstname, lastname) values ('NickJ', 'Nick', 'Jonas');
insert into USERS(username, firstname, lastname) values ('Moiso', 'Moises', 'Navarro');

-- select * from USERS order by id;

insert into MOVIE(title, release_Date, category_Id) values ('Star Wars', '1980-01-01', 3);
insert into MOVIE(title, release_Date, category_Id) values ('Jurasic Park', '1993-08-15', 3);
insert into MOVIE(title, release_Date, category_Id) values ('Jonh Wick', '2014-09-11', 5);
insert into MOVIE(title, release_Date, category_Id) values ('Ace Ventura', '1994-04-15', 1);
insert into MOVIE(title, release_Date, category_Id) values ('El Camino: una película de Breaking Bad', '2019-09-7', 2);

-- select * from MOVIE order by id;

insert into RATING(movie_Id, user_Id, score, review) values (1, 1, 3, 'it is king of boring');
insert into RATING(movie_Id, user_Id, score, review) values (2, 1, 4, 'this one is more fun');
insert into RATING(movie_Id, user_Id, score, review) values (3, 2, 5, 'superb movie');
insert into RATING(movie_Id, user_Id, score, review) values (4, 3, 5, 'this one is more fun');
insert into RATING(movie_Id, user_Id, score, review) values (5, 4, 5, 'it is more original and impressive than others');

-- select * from RATING order by id;

select * from MOVIE as m INNER JOIN category as c on m.category_id = c.id;

select * from MOVIE as m LEFT JOIN category as c on m.category_id = c.id;

select * 
from 
MOVIE as m RIGHT JOIN category as c on m.category_id = c.id
where
m.category_id is null
;

select c.id, c.category_Name 
from 
MOVIE as m RIGHT JOIN category as c on m.category_id = c.id
where
m.category_id is not null
;

select * from USERS as u INNER JOIN RATING as r on u.id = r.user_Id;

select * from RATING RIGHT JOIN USERS ON RATING.user_Id = user_Id where rating.user_Id is null;

select c.id, c.category_name 
from USERS as u inner join RATING as r on u.id = r.user_Id
inner join MOVIE as m on m.id = r.movie_Id
inner join CATEGORY as c on c.id = m.category_Id
;

-- Funciones 
drop function if exists movie_rating;

delimiter $$
create function movie_rating(movie_Id int, user_Id int) returns varchar(50) reads sql data
begin
	declare the_score int;
	select r.score into the_score from RATING as r where r.movie_Id = movie_Id and r.user_Id and r.user_Id = user_Id;
    case the_score
		when 1 then return 'mala';
        when 2 then return 'regular';
        when 3 then return 'buena';
        when 4 then return 'muy buena';
        when 5 then return 'excelente';
        else return 'no calificado';
	end case;
end
$$
delimiter ;

select * from RATING;

select movie_rating(2, 3);











