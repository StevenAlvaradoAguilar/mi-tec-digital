-- crear base de datos

show databases;

-- bases de datos

drop database if exists universidad;
create database universidad;
use universidad;


drop table if exists departamento;
drop table if exists profesor;
drop table if exists estudiante;
drop table if exists tutor;

-- tablas

create table departamento(
	nombre varchar(20),
    edificio varchar(20),
    presupuesto int check (presupuesto > 0),
    primary key (nombre)
);

create table profesor(
	id int primary key,
    nombre varchar(30),
    apellido varchar(30),
    ciudad varchar(30),
    -- sueldo int check (sueldo > 0),
    departamento varchar(30)
    -- foreign key (departamento) references departamento(nombre)
);


create table estudiante(
	id int primary key,
    nombre varchar(30),
    apellido varchar(30),
    fecha_nacimiento date,
    total_creditos int
    -- departamento varchar(20),
    -- foreign key (departamento) references departamento(nombre)
);

create table curso(
	id int primary key,
    nombre varchar(30),
	departamento varchar(30),
	creditos int
);

create table tutor(
	estudiante_id int,
    profesor_id int, 
	foreign key(estudiante_id) references estudiante(id),
    foreign key(profesor_id) references profesor(id)
);


-- indices
create index estudianteApellidoIndex on estudiante(apellido);
create index profesorApellidoIndex on profesor(apellido);

-- alter table
alter table estudiante add column fecha_nacimiento timestamp not null after total_creditos;


-- ingresar datos

insert into departamento(nombre, edificio, presupuesto) values ('Computacion', 'Exactas', 120000);
insert into departamento(nombre, edificio, presupuesto) values ('Biologia', 'Ciencias', 100000);
insert into departamento(nombre, edificio, presupuesto) values ('Quimica', 'Exactas', 110000);
insert into departamento(nombre, edificio, presupuesto) values ('Ing Civil', 'Ingenieria', 120000);
insert into departamento(nombre, edificio, presupuesto) values ('Filosofia', 'Sociales', 0);
insert into departamento(nombre, edificio, presupuesto) values ('Fisica', 'Exactas', 0);

update departamento set presupuesto = 90 where nombre = 'Filosofia';


-- ingresar profesores

insert into profesor(id, nombre, apellido, ciudad, departamento) values(1, 'Martin', 'Flores', 'San Carlos', 'Computacion');
insert into profesor(id, nombre, apellido, ciudad, departamento) values(2, 'Allan', 'Cascante', 'San Jose', 'Computacion');
insert into profesor(id, nombre, apellido, ciudad, departamento) values(3, 'Albert', 'Einstein', 'Cartago', 'Fisica');
insert into profesor(id, nombre, apellido, ciudad, departamento) values(4, 'Marco', 'Calvo', 'Alajuela', 'Quimica');
insert into profesor(id, nombre, apellido, ciudad, departamento) values(5, 'Jose', 'Herrera', 'San Carlos', 'Filosofia');
insert into profesor(id, nombre, apellido, ciudad, departamento) values(6, 'Carolina', 'Lizano', 'Cartago', 'Biologia');
insert into profesor(id, nombre, apellido, ciudad, departamento) values(7, 'Raquel', 'Rodriguez', 'San Carlos', 'Biologia');

-- ingresa estudiantes
describe estudiante;
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(1, 'Steven', 'Alvarado', '1999-12-02', 8);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(2, 'Lermith', 'Biarreta', '2000-05-07', 8);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(3, 'Maria', 'Biarreta', '2000-04-06', 8);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(4, 'Valeria', 'Calderon', '2000-09-08', 12);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(5, 'Sebastian', 'Campos', '2000-07-05', 4);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(6, 'Josue', 'Castro', '2000-08-11', 11);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(7, 'Susana', 'Cen', '2000-09-08', 16);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(8, 'Johan', 'Echeverria', '2000-11-09', 8);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(9, 'Junior', 'Herrera', '2000-08-12', 8);
insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(10, 'Alejandro', 'Loaiza', '2000-08-04', 4);

-- Curso
insert into curso(id, nombre, departamento, creditos) values(1, 'Bases de Datos', 'Computacion', 4);
insert into curso(id, nombre, departamento, creditos) values(2, 'Genetica', 'Biologia', 3);
insert into curso(id, nombre, departamento, creditos) values(3, 'Intro a la cuantica', 'Quimica', 4);
insert into curso(id, nombre, departamento, creditos) values(4, 'Estudio de las SIG', 'Ing Civil', 2);
insert into curso(id, nombre, departamento, creditos) values(5, 'Sem Estudios Costarricenses', 'Filosofia', 2);
insert into curso(id, nombre, departamento, creditos) values(6, 'Bases de Fisica Nuclear', 'Fisica', 4);

-- tutor

insert into tutor(estudiante_id, profesor_id) values(1, 2);
insert into tutor(estudiante_id, profesor_id) values(8, 2);
insert into tutor(estudiante_id, profesor_id) values(3, 3);



select * from departamento;
select * from profesor;
select * from estudiante;
select * from curso;
select * from tutor;

-- updates

update estudiante set fecha_nacimiento = '2000-08-08' where id in (2,3,4,5,6,7,8,9, 10);


select nombre, apellido from estudiante where apellido like 'Biarreta';
select nombre, apellido from profesor where departamento like 'Biologia';

select 
concat(e.nombre, ' ', e.apellido) as `estudiante`,
concat(p.nombre, ' ', p.apellido) as `profesor`
from 
estudiante as e 
join tutor as t on t.estudiante_id = e.id 
join profesor as p on profesor_id = p.id;


select e.nombre, e.apellido
from 
estudiante e 
join tutor t on e.id = t.estudiante_id
join profesor p on p.id = t.profesor_id
where
p.nombre = 'Allan'
;

select *
from 
estudiante e 
join tutor t on e.id = t.estudiante_id
;


select 
count(*), departamento 
from 
estudiante
group by departamento; 


delete from estudiante where id = 1;
delete from tutor where estudiante_id = 1;

select current_date;

