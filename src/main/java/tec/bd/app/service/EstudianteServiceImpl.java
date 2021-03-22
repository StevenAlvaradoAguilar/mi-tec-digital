package tec.bd.app.service;

import tec.bd.app.dao.EstudianteDAO;
import tec.bd.app.domain.Estudiante;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EstudianteServiceImpl implements EstudianteService {

    private EstudianteDAO estudianteDAO;

    public EstudianteServiceImpl(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    @Override
    public List<Estudiante> getStudentsByLastName(String lastName) {
        //TODO: implementarlo
        //validar que el lastName no sea nulo
        if (!lastName.isEmpty()){
            return this.estudianteDAO.findByLastName(lastName);
        }
        return null;
    }

    public List<Estudiante> getStudentsSortedByLastName() {

        var estudiantes = this.estudianteDAO.findAll();
        Comparator<Estudiante> comparator = (e1, e2) -> e1.getApellido().compareTo(e2.getApellido());

        estudiantes.stream().forEach(e -> System.out.println(e.getNombre() +" "+e.getApellido()));

        estudiantes.sort(comparator);

        System.out.println("-----------------------------------------");

        estudiantes.stream().forEach(e -> System.out.println(e.getNombre() +" "+e.getApellido()));

        return estudiantes;

    }

    @Override
    public List<Estudiante> getAll() {
        return this.estudianteDAO.findAll();
    }

    @Override
    public Optional<Estudiante> getById(int carne) {

        //TODO: validar el carne > 0. Si no cumple con eso se devuelve Optional.empty()
        var cursos = this.getAll();
        if (!(cursos == null)){
            return this.estudianteDAO.findById(carne);
        }
        return Optional.empty();
    }

    public void addNew(Estudiante e) {
        Optional<Estudiante> estudiante = this.estudianteDAO.findById(e.getCarne());
        if(!estudiante.isPresent()) {
            this.estudianteDAO.save(e);
        }
    }

    public Optional<Estudiante> updateStudent(Estudiante e) {
        //TODO: validar que el carne exista en la BD. Si existe se actualiza
        Optional<Estudiante> estudiante = this.estudianteDAO.findById(e.getCarne());
        if(estudiante.isPresent()) {
            return this.estudianteDAO.update(e);
        }
        return this.estudianteDAO.update(e);
    }

    public void deleteStudent(int carne) {
        //TODO: validar que el carne exista en la BD. Si existe se borra
        Optional<Estudiante> estudiante = this.estudianteDAO.findById(carne);
        if (estudiante.isPresent()){
            this.estudianteDAO.delete(carne);
        }
    }

}