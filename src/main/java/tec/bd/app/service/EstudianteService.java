package tec.bd.app.service;

import tec.bd.app.bd.SetDB;
import tec.bd.app.domain.Estudiante;

import java.util.List;
import java.util.stream.Collectors;

public class EstudianteService {

    private SetDB database;

    public EstudianteService(SetDB database) {
        this.database = database;
    }

    public List<Estudiante> getAll() {
        return this.database.getEstudianteTable()
                .stream()
                .collect(Collectors.toList());
    }

    public Estudiante getById(long carne) {
        return this.database.getEstudianteTable().stream().filter(e -> e.getCarne() == carne).findFirst().get();
    }

    public void addNew(Estudiante e) {

        /*Creo una función de bool para determinar cuando debo de detenerme
        * Guardo en una varieble los datos que poseo de la base de datos de cada estudiante*/

        boolean condicion = false;
        var estudianteActual = this.database.getEstudianteTable();

         /*Debo de recorrer cada estudiante y compararlo para que no me guarde duplicados
         * si encuentra duplicados el ciclo se detiene debido a que encontró un carnet igual al
         * dato ingresado.*/

        for (Estudiante estudianteInsert : estudianteActual){

            if (estudianteInsert.getCarne() == e.getCarne()){
                condicion = true;
                System.out.println("El estudiante ya existe");
                break;
            }
        }

        /*Sí la condición es distinta lo que hace es agregar el dato que se va ingresar en la base de datos*/
        if (!condicion){
            this.database.getEstudianteTable().add(e);
        }
    }

    public void updateStudent(Estudiante e)  {

        /*Creo una función de bool para determinar cuando debo de detenerme
         * Sí el carné búscado es igual a uno que exista en la base de datos este los reemplaza
         * cabe resaltar que sólo cambia nombre, apellido y edad*/

        boolean condicion = false;
        var estudianteActual = this.database.getEstudianteTable();

        /*El ciclo itera en toda la base de datos hasta encontrar el dato y sí no es así
        * Envía un mensaje en pantalla */

        for (Estudiante estudianteUpdate : estudianteActual){

            if (estudianteUpdate.getCarne() == e.getCarne()){

                estudianteUpdate.setNombre(e.getNombre());
                estudianteUpdate.setApellido(e.getApellido());
                estudianteUpdate.setEdad(e.getEdad());
                condicion = true;
                break;

            }else{
                System.out.println("El estudiante con ese carnet ya está actualizado = #carne " + estudianteUpdate.getCarne());
            }

        }

    }

    public void deleteStudent(long carne) {

        /*Creo una función de bool para determinar cuando debo de detenerme
        * se procede hacer el ciclo hasta encontrar con el carné búscado y una vez
        * encontrado se procede a borrarlo de la base de datos, sí no encuentra el carné significa
        * que el carné no existe y este envía un mensaje en pantalla*/

        boolean condicion = false;
        var estudianteActual = this.database.getEstudianteTable();

        for (Estudiante estudianteDelete : estudianteActual){

            if (estudianteDelete.getCarne() == carne){
                this.database.getEstudianteTable().remove(estudianteDelete);
                condicion = true;
                break;
            }
            if (estudianteDelete.getCarne() != carne){
                System.out.println("El estudiante con ese carnet no existe" + carne);
            }

        }

    }

}

