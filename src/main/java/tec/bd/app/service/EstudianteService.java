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

        //

        boolean condicion = false;
        var estudianteActual = this.database.getEstudianteTable();
         //
        for (Estudiante estudianteInsert : estudianteActual){

            //
            if (estudianteInsert.getCarne() == e.getCarne()){
                condicion = true;
                break;
            }
        }
        // jhf
        if (!condicion){
            this.database.getEstudianteTable().add(e);
        }
        else {
            System.out.println("El estudiante ya existe");
        }

    }

    public void updateStudent(Estudiante e)  {
        // Implementar codigo de actualizacion
        // Traer el estudiante con carne X
        // modificar el estudiante con los datos del estudiante e

        //
        boolean condicion = false;
        var estudianteActual = this.database.getEstudianteTable();

        //
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
        // implementar codigo de borrado

        //
        boolean condicion = false;
        var estudianteActual = this.database.getEstudianteTable();

        for (Estudiante estudianteDelete : estudianteActual){

            //
            if (estudianteDelete.getCarne() == carne){
                this.database.getEstudianteTable().remove(estudianteDelete);
                condicion = true;
                break;
            }
            //
            if (estudianteDelete.getCarne() != carne){
                System.out.println("El estudiante con ese carnet no existe" + carne);
            }

        }

    }

}

