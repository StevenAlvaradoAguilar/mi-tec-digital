Mi-TEC-Digital
==============
Steven Gerardo Alvarado Aguilar
2019044923

Aplicación Demo para gestionar Estudiantes, Profesores y Cursos.

## Revisión de Tarea 1
Nota: 9
El proyecto se ve bien y todo corre. 

#### Observaciones

1. No está listando los estudiantes por numero de carné de forma ascendente
2. La lógica de `EstudianteServiceTest` pudo haber sido un poco más simple: en lugar de obtener siempre toda la tabla y recorrerla intentando buscar duplicados, pudo haberse ahorrado todo ese trabajo llamando a `this.getById()` y luego validar si el resultado de la ejecución de ese método era nulo o no.
3. En `EstudianteServiceTest` para las pruebas unitarias intente tomar un enfoque de: "lo que pasó antes" y "lo que va a pasar". Es que por ejemplo probar que ud hizo una actualización y luego verificar que la cantidad de estudiantes sigue siendo 3 es poco útil. Hubiera podido obtener de la "base de datos" actual por medio del carné (2), luego hacer la actualización (2) y, como método de verificación comparar que del paso (1) con el del paso (2). Intente un enfoque similar para las pruebas de borrado.

## Revisión Tarea 2

Nota: 5

Noté que había cosas incompletas relativas a los cursos y profesores 


## Revisión Tarea 4

Nota: 8.5

No hizo sentencias SQL para los casos de encontrar por ID o por nombre. Filtró los resultados del `select` general.

## Revisión proyecto 2

Nota: 8

Hubo una interpretación errónea de los requirimientos(creo). Hubo procedimientos almacenados de inserción, actualización y borrado que no estaban dentro de bloques transaccionales.

