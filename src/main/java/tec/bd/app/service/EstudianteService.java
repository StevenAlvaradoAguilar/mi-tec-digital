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
        // Verificar si el estudiante que viene por parametro ya existe en la BD
        this.database.getEstudianteTable().add(e);
    }

    public void updateStudent(Estudiante e) {
        // Implementar codigo de actualizacion
        // Traer el estudiante con carne X
        // modificar el estudiante con los datos del estudiante e
    }

    public void deleteStudent(long carne) {
        // implementar codigo de borrado
        // HashSet.remove(e);
    }

}
