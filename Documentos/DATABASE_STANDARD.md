 ###
                                    Instituto Tecnológico de Costa Rica

									Centro Académico de Alajuela

									Escuela de Ingeniería en Computación

									Bases de datos I (IC4301)

									Realizado por:
									Alvarado Aguilar Steven           2019044923

									Profesor:
									Carlos Martin Flores González 

									Grupo 20


									Marzo, 2021
**Contenidos**

[TOCM]

[TOC]

# Introducción
# Justificación del esquema seleccionado
# Collation and Character Sets
# Conclusión
# Bibliografía


Introducción
=============
El estándar de nombres para la definición de esquemas de entidad relación (diccionario de datos) en MySQL es de 
suma importancia su buen uso y la aplicación de esta es una escritura que todas las personas varían, pero debido 
a que muchas veces debemos cambiar y adaptarnos a un estándar que sea correcto y estándar en donde la sintaxis 
del código sea correcta y que sea entendible para todos los futuros programadores puedan darle mantenimiento y 
mejorarlo de la mejor forma.
Es importante la distinción entre las entidades y los atributos deben de ser distintos, ya que su significado es 
distinto tenemos distintos tipos de atributos los cuales varían en su uso dentro del código.
Es importante repasar las llaves primarias y llaves secundarias y ver la diferencia de sus usos y las distintas 
formas de uso que se pueden aplicar sobre ellas.
Se dará la explicación de cada una de ellas y la forma en que son tratadas dentro de un programa y que estos pueden 
variar por la persona que la utiliza y es importante saber y destacar la importancia de saber los distintos usos 
y aplicaciones.

# Justificación del esquema seleccionado
Definición del estándar para entidades:

a.	Descripción del estándar

Use nombres de palabras en singular si es posible, solo cambie a nombres de palabras compuestas si es absolutamente 
necesario. Idealmente, los nombres deberían ser palabras simples. Utilice letra mayúscula al escribir la entidad, 
utilizar el guion bajo o underscore para separar las palabras o si este no se utiliza escribir la segunda palabra 
en mayúscula de igual forma al utilizar el guion bajo si se va a utilizar palabras compuestas.
b.	Ejemplos
Como "Usuario", "Transacción", "Ferrocarril" o "Sistema" y excluir nombres compuestos como "Sistema_Ferroviario" 
“SistemaFerroviario” hasta que sea necesario para evitar confusiones o colisiones de nombres de lo contrario utilice 
buenas combinaciones que sean fáciles de entender su significado a simple vista.

CREATE TABLE Usuario (

    usuario_Id int AUTO_INCREMENT PRIMARY KEY,

    nombre_usuario varchar (40),

    contraseña varchar (255),

    email varchar (255)

);

c.	Excepciones
Muchas veces no se podrá elegir nombres de entidades de forma singular si este es el caso se debe de elegir un nombre 
en plural que no provoque problemas al ser usado en objetos.

Definición del estándar para atributos:

a.	Descripción del estándar

Usar minúscula al inicio de la palabra para distinguir que es un atributo y no una entidad puede ser singular o plural 
según sea el caso, también si es una palabra compuesta la otra palabra debe iniciar con mayúscula para que su 
significado sea autoexplicativo, esto ayudará a escribir rápidamente, evitará errores. Los nombres no deben tener más 
de 64 caracteres. Evite usar el prefijo.

b.	Ejemplos

Nombres válidos y comprensibles como: james, matemática (la tilde no se utiliza por problemas de sintaxis evitarla en 
el código), estudios_sociales o estudiosSociales, db, poo, inglés, nombre_país, código_país, nombre_cliente.
CREATE TABLE Usuario (

    usuario_Id int AUTO_INCREMENT PRIMARY KEY,

    nombre_usuario varchar (40),

    contraseña varchar (255),

    email varchar (255)

);

c.	Excepciones

No es un problema si dos columnas, en diferentes tablas de la base de datos, tienen el mismo nombre. Aun así, 
tener nombres únicos para cada columna está bien porque reducimos la posibilidad de mezclar posteriormente estas dos 
columnas mientras escribimos consultas.

Definición del estándar para tipos de datos a usar para atributos:

a.	Descripción del estándar

Numéricos enteros
Comencemos por conocer las opciones que tenemos para almacenar datos que sean números enteros (edades, cantidades, 
magnitudes sin decimales); poseemos una variedad de opciones:

![](http://imgfz.com/i/LBHJy3p.png)

Números con decimales
Valores numéricos con decimales.
Estos tipos de datos son necesarios para almacenar precios, salarios, importes de cuentas bancarias, etc. que no 
son enteros.
La estructura con la que podemos declarar un campo FLOAT, DOUBLE y
DECIMAL.
Datos alfanuméricos
CHAR, VARCHAR, BINARY, VARBINARY, TINYBLOB, TINYTEXT, BLOB, TEXT, MEDIUMBLOB, MEDIUMTEXT, LONGBLOB, LONGTEX, ENUM, SET.
Todos ellos sirven para una cantidad de datos alfanuméricos distinta.

b.	Ejemplos

Nombres como start_date y end_date son bastante descriptivos. Si lo desea, puede describirlos de manera aún más precisa,
utilizando nombres como call_start_date y call_end_date para tipo de datos de fecha.
CREATE TABLE user (

    user_Id int AUTO_INCREMENT PRIMARY KEY,

    username varchar (40),

    password varchar (255),

    email varchar (255)

);

c. Excepciones

Definición del estándar para llaves primarias:

a.	Descripción del estándar

Los nombres de campo deben ser comprensibles, por ejemplo: precio, nombre de la empresa, apellido, ciudad. El nombre 
de la columna principal: la clave principal puede ser id o el nombre de la tabla
Evite los nombres de claves primarias con significado semántico. Un error de diseño clásico es crear una tabla con 
clave principal que tenga un significado real como "nombre" como clave principal. En este caso, si alguien cambia 
su nombre, la relación con otra tabla se verá afectada y el nombre puede ser repetitivo (no único). Utilizar los 
atributos reservados todo en mayúsculo o minúscula.

b.	Ejemplos

Algunos ejemplos pueden ser: estudiante_id, profesor_id, curso_id, etc.
CREATE TABLE user (

    user_Id int AUTO_INCREMENT PRIMARY KEY,

    username varchar (40),

    password varchar (255),

    email varchar (255)

);

c.	Excepciones
Evite utilizar una palabra de reserva como nombre de campo: orden, fecha en vez de eso utilice puede agregar un prefijo 
a estos nombres para que sea comprensible como nombre_de_usuario, fecha_de_suscripción, hora_inicio, día_fina etc.

Definición del estándar para llaves foráneas e índices:

a.	Descripción del estándar

Los nombres de campo deben ser comprensibles, Evite los nombres abreviados, concatenados o basados en acrónimos. 
Usar minúscula al inicio de la palabra. La columna de clave externa debe tener un nombre de tabla con su clave principal.
Dado que almacenan valores del rango de la clave principal de la tabla a la que se hace referencia, debe usar ese 
nombre de tabla e "id_nombreTabla", Esto nos dirá que esta es una columna de clave externa y también apuntará a la 
tabla referenciada. Utilizar los atributos reservados todo en mayúsculo o minúscula.

b.	Ejemplos

Por ejemplo: blog_id representa la identificación de clave externa del blog de la tabla, customer_id o id_customer, 
employee_id o id_employee, id_precio, nombre_de_la_empresa, apellido, ciudad.
CREATE TABLE Usuario (

    usuario_Id int AUTO_INCREMENT PRIMARY KEY,

    nombre_usuario varchar (40),

    contraseña varchar (255),

    email varchar (255),

    PRIMARY KEY (usuario_Id),

    FOREIGN KEY (usuario_Id) REFERENCES Persona(persona_Id)

);

c.	Excepciones

# Collation and Character Sets
Debido a que no todo el mundo quiere almacenar cadenas en inglés, es importante que un servidor 
de base de datos pueda administrar caracteres que no estén en inglés y diferentes formas de clasificar 
caracteres. Cuando compara u ordena cadenas, la forma en que MySQL evalúa el resultado depende del 
conjunto de caracteres y la intercalación utilizados. Los conjuntos de caracteres definen qué caracteres 
se pueden almacenar; por ejemplo, es posible que deba almacenar caracteres que no estén en inglés, como ü. 
Una intercalación define cómo se ordenan las cadenas, y hay distintas intercalaciones para diferentes idiomas: 
por ejemplo, la posición del carácter ü en el alfabeto es diferente en dos ordenamientos alemanes, y diferente 
nuevamente en sueco y finlandés.
En nuestros ejemplos anteriores de comparación de cadenas, ignoramos el problema de la intercalación y el juego 
de caracteres, y simplemente permitimos que MySQL use sus valores predeterminados; en las versiones de MySQL 
anteriores a la 8.0, el juego de caracteres predeterminado es latin1 y la intercalación predeterminada es 
latin1_swedish_ci. MySQL 8.0 cambió los valores predeterminados, y ahora el juego de caracteres predeterminado 
es utf8mb4, y la intercalación predeterminada es utf8mb4_0900_ai_ci. MySQL se puede configurar para utilizar 
diferentes conjuntos de caracteres y órdenes de clasificación en los niveles de conexión, base de datos, 
tabla y columna. Los resultados a continuación provienen de MySQL 8.0.

![](http://imgfz.com/i/bcXTPwi.png)

# Conclusión
En base con la realización de esta tarea-3 es importante destacar la importancia del uso del 
estándar para realizar mejor los proyectos de la mejor manera así cómo desarrollar 
mejores prácticas en el uso de MySQL así cómo en el manejo de los datos que se puedan utilizar 
de mejor manera y de la forma adecuada para que no se pierdan datos, información y espacio debido 
a que no se debe de usar más memoria de la necesaria y la importancia de la utilización de un 
estándar específico.

# Bibliografía
Delgado, Hugo. (2017). Tipos de datos en MySQL para una base de datos SQL. 
Recuperado 28 de marzo, 2021, de [Link](https://disenowebakus.net/tipos-de-datos-mysql.php)

Drkusic, E. (2019). Learn SQL: Primary Key. SQL Shack - Articles about Database Auditing, Server Performance, 
Data Recovery, and More. [Link](https://www.sqlshack.com/learn-sql-primary-key/)

Drkusic, E. (2020). Learn SQL: Foreign Key. SQL Shack - Articles about Database Auditing, Server Performance, 
Data Recovery, and More. [Link](https://www.sqlshack.com/learn-sql-foreign-key/)

Drkusic, E. (2020b). Learn SQL: Naming Conventions. SQL Shack - Articles about Database Auditing, Server Performance, 
Data Recovery, and More. [Link](https://www.sqlshack.com/learn-sql-naming-conventions/)

Hall, A. (2020). Pragmatic Database Schema Naming Conventions, Practices, and Patterns. Medium. 
[Link](https://adron.medium.com/pragmatic-database-schema-naming-conventions-practices-and-patterns-e483b0617bd)

Ing. Hugo Delgado. (2015). Tipos de datos en MySQL para una base de datos SQL. Diseño Web akus.net. 
[Link](https://disenowebakus.net/tipos-de-datos-mysql.php)

Pandey, A. R. (2015). MySQL naming / coding conventions: tips on mySQL database. How To Tutorials. 
[Link](https://anandarajpandey.com/2015/05/10/mysql-naming-coding-conventions-tips-on-mysql-database/)

