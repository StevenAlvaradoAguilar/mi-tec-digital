package tec.bd.app.service;

import tec.bd.app.dao.ProfesorDAO;
import tec.bd.app.domain.Profesor;

import java.util.List;
import java.util.Optional;

public class ProfesorServiceImpl implements ProfesorService {

    private ProfesorDAO profesorDAO;

    public ProfesorServiceImpl(ProfesorDAO profesorDAO) {
        this.profesorDAO = profesorDAO;
    }

    @Override
    public List<Profesor> getAll() {
        return this.profesorDAO.findAll();
    }

    @Override
    public Optional<Profesor> getById(int carne) {
        //TODO: validar el carne > 0. Si no cumple con eso se devuelve Optional.empty()
        var cursos = this.getAll();
        if (!(cursos == null)){
            return this.profesorDAO.findById(carne);
        }
        return Optional.empty();
    }

    @Override
    public void addNew(Profesor p) {
        Optional<Profesor> profesor = this.profesorDAO.findById(p.getId());
        if(!profesor.isPresent()) {
            this.profesorDAO.save(p);
        }
    }

    @Override
    public Optional<Profesor> updateTeacher(Profesor p) {
        //TODO: validar que el carne exista en la BD. Si existe se actualiza
        Optional<Profesor> profesor = this.profesorDAO.findById(p.getId());
        if(profesor.isPresent()) {
            return this.profesorDAO.update(p);
        }
        return null;
    }

    @Override
    public void deleteTeacher(int id) {
        //TODO: validar que el carne exista en la BD. Si existe se borra
        Optional<Profesor> profesor = this.profesorDAO.findById(id);
        if (profesor.isPresent()){
            this.profesorDAO.delete(id);
        }
    }

    @Override
    public List<Profesor> getTeachersByLastName(String lastName) {
        //TODO: implementarlo
        //validar que el lastName no sea nulo
        if (!lastName.isEmpty()){
            return this.profesorDAO.findByLastName(lastName);
        }
        return null;
    }

    @Override
    public List<Profesor> getTeachersByCity(String city) {
        //TODO: implementarlo
        //validar que el lastName no sea nulo
        if (!city.isEmpty()){
            return this.profesorDAO.findByCity(city);
        }
        return null;
    }

}
