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
    ciudad varchar(30)
    -- sueldo int check (sueldo > 0),
    -- departamento varchar(30)
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

insert into profesor(id, nombre, apellido, ciudad) values(1, 'Martin', 'Flores', 'San Carlos');
insert into profesor(id, nombre, apellido, ciudad) values(2, 'Allan', 'Cascante', 'San Jose');
insert into profesor(id, nombre, apellido, ciudad) values(3, 'Albert', 'Einstein', 'Cartago');
insert into profesor(id, nombre, apellido, ciudad) values(4, 'Marco', 'Calvo', 'Alajuela');
insert into profesor(id, nombre, apellido, ciudad) values(5, 'Jose', 'Herrera', 'San Carlos');
insert into profesor(id, nombre, apellido, ciudad) values(6, 'Carolina', 'Lizano', 'Cartago');
insert into profesor(id, nombre, apellido, ciudad) values(7, 'Raquel', 'Rodriguez', 'San Carlos');

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

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Proyecto #2 Bases de Datos
-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Creacion del nuevo usuario 
CREATE USER 'universidad_user'@'localhost' IDENTIFIED BY 'universidad_pass';
GRANT INSERT ON universidad.* TO 'universidad_user'@'localhost';
GRANT DROP ON universidad.* TO 'universidad_user'@'localhost';
GRANT DELETE ON universidad.* TO 'universidad_user'@'localhost';
GRANT UPDATE ON universidad.* TO 'universidad_user'@'localhost';
GRANT SELECT ON universidad.* TO 'universidad_user'@'localhost';
GRANT EXECUTE ON universidad.* TO 'universidad_user'@'localhost';
GRANT EXECUTE ON universidad.* TO 'universidad_user'@'localhost';
GRANT EXECUTE ON universidad.add_teacher TO 'universidad_user'@'localhost';
SHOW GRANTS FOR 'universidad_user'@'localhost';

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Procedimientos almacenados
-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Tabla Curso
-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Buscar curso por id
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists find_by_Id_Course;
delimiter $$
create procedure find_by_Id_Course(in course_id int)
begin
	select * from curso where id = course_id;
end
$$
delimiter ;

call find_by_Id_Course(8);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Buscar por apartamento
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists find_By_Departament;
delimiter $$
create procedure find_By_Departament(in course_departament varchar(20))
begin
	select * from curso where departamento = course_departament;
end
$$
delimiter ;

call find_By_Departament('Biologia');

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Muestra todos los cursos
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists all_courses;
delimiter $$
create procedure all_courses()
begin
	select * from curso;
end
$$
delimiter ;

call all_courses();

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Agregar un curso
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists add_course;
delimiter $$
create procedure add_course(in course_id int, in course_name varchar(50), in course_dept varchar(50), in course_credits int)
begin
    declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	insert into curso(id, nombre, departamento, creditos) values (course_id, course_name, course_dept, course_credits);
	commit;
end
$$
delimiter ;

call add_course(8, 'Estructuras discretas', 'Computacion', 4);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Actualizar un curso
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists update_course;
delimiter $$
create procedure update_course(in course_id int, in course_name varchar(50), in course_dept varchar(50), in course_credits int)
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	update curso set id = course_id, nombre = course_name, departamento = course_dept, creditos = course_credits where id = course_id;
	commit;
end
$$
delimiter ;

call update_course(8, 'Estructuras_discretas', 'Computacion', 3);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Borrar un curso
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists delete_course;
delimiter $$
create procedure delete_course(in course_id int)
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	delete from curso where course_id = id;
    commit;
end
$$
delimiter ;

call delete_course(9);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Tabla Estudiante                 
-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Muestra todos los estudiantes
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists all_students;
delimiter $$
create procedure all_students()
begin
	select * from estudiante;
end
$$
delimiter ;

call all_students();

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Buscar el estudiante por apellido
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists find_By_LastName;
delimiter $$
create procedure find_By_LastName(in LastName varchar(20))
begin
	select * from estudiante where apellido = LastName;
end
$$
delimiter ;

call find_By_LastName('Biarreta');

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Buscar todos los estudiantes por apellido
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists findAllSortByLastName;
delimiter $$
create procedure findAllSortByLastName(in LastName varchar(20))
begin
	select * from estudiante where apellido = LastName;
end
$$
delimiter ;

call findAllSortByLastName('Biarreta');
-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Buscar estudiante por id
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists find_by_Id_Student;
delimiter $$
create procedure find_by_Id_Student(in student_id int)
begin
	select * from estudiante where id = student_id;
end
$$
delimiter ;

call find_by_Id_Student(8);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Agregar un estudiante   
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists add_student;
delimiter $$
create procedure add_student(in student_id int, in student_name varchar(50), in student_lastname varchar(50), in student_Date_of_birth date, in student_credits int)
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values (student_id, student_name, student_lastname, student_Date_of_birth, student_credits);
    commit;
end
$$
delimiter ;

call add_student(12, 'Keneth', 'Alfonso', '1998-04-12', 14);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Actualizar un estudiante
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists update_student;
delimiter $$
create procedure update_student(in student_id int, in student_name varchar(50), in student_lastname varchar(50), in student_Date_of_birth date, in student_credits int)
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	update estudiante set id = student_id, nombre = student_name, apellido = student_lastname, fecha_nacimiento = student_Date_of_birth, total_creditos = student_credits where id = student_id;
    commit;
end
$$
delimiter ;

call update_student(12, 'Keneth', 'Alfonso', '1998-04-12', 13);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Borrar un estudiante
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists delete_student;
delimiter $$
create procedure delete_student(in student_id int)
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	delete from estudiante where student_id = id;
    commit;
end
$$
delimiter ;

call delete_student(11);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Tabla Profesores                 
-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Muestra todos los estudiantes
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists all_teachers;
delimiter $$
create procedure all_teachers()
begin
	select * from profesor;
end
$$
delimiter ;

call all_teachers();

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Buscar todos los profesores por apellido
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists find_By_City;
delimiter $$
create procedure find_By_City(in city varchar(20))
begin
	select * from profesor where ciudad = city;
end
$$
delimiter ;

call find_By_City('Cartago');

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Buscar estudiante por id
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists find_by_Id_teacher;
delimiter $$
create procedure find_by_Id_teacher(in teacher_id int)
begin
	select * from profesor where id = teacher_id;
end
$$
delimiter ;

call find_by_Id_teacher(8);

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Agregar un profesor    id, nombre, apellido, ciudad
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists add_teacher;
delimiter $$
create procedure add_teacher(in teacher_id int, in teacher_name varchar(50), in teacher_lastname varchar(50), in teacher_city varchar(50))
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	insert into profesor(id, nombre, apellido, ciudad) values (teacher_id, teacher_name, teacher_lastname, teacher_city);
    commit;
end
$$
delimiter ;

call add_teacher(8, 'Keneth', 'Solis', 'Alajuela');

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Actualizar un profesor
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists update_teacher;
delimiter $$
create procedure update_teacher(in teacher_id int, in teacher_name varchar(50), in teacher_lastname varchar(50), in teacher_city varchar(50))
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	update profesor set id = teacher_id, nombre = teacher_name, apellido = teacher_lastname, ciudad = teacher_city where id = teacher_id;
	commit;	
    end
$$
delimiter ;

call update_teacher(8, 'Keneth', 'Alfonso', 'San Carlos');

-- ------------------------------------------------------------------------------------------------------------------------------------------
-- Borrar un profesor
-- ------------------------------------------------------------------------------------------------------------------------------------------
drop procedure if exists delete_teacher;
delimiter $$
create procedure delete_teacher(in teacher_id int)
begin
	declare exit handler for sqlexception
    begin
    rollback;
    end;
    DECLARE exit handler for sqlwarning
    begin
    rollback;
    end;
	start transaction;
	delete from profesor where teacher_id = id;
    commit;
end
$$
delimiter ;

call delete_teacher(12);

