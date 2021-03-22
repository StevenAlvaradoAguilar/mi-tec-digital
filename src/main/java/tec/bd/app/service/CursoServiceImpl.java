package tec.bd.app.service;

import tec.bd.app.domain.Curso;
import tec.bd.app.dao.CursoDAO;

import java.util.List;
import java.util.Optional;

public class CursoServiceImpl implements CursoService{


    private CursoDAO cursoDAO;

    public CursoServiceImpl(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    @Override
    public List<Curso> getAll() {
        return this.cursoDAO.findAll();
    }

    @Override
    public Optional<Curso> getById(int id) {
        //TODO: validar el carne > 0. Si no cumple con eso se devuelve Optional.empty()
        var cursos = this.getAll();
        if (!(cursos == null)){
            return this.cursoDAO.findById(id);
        }
        return Optional.empty();
    }

    public void addNew(Curso c) {
        Optional<Curso> curso = this.cursoDAO.findById(c.getId());
        if(!curso.isPresent()) {
            this.cursoDAO.save(c);
        }
    }

    public Optional<Curso> updateCourse(Curso c) {
        //TODO: validar que el carne exista en la BD. Si existe se actualiza
        Optional<Curso> curso = this.cursoDAO.findById(c.getId());
        if (curso.isPresent()) {
            return this.cursoDAO.update(c);
        }
        return Optional.empty();
    }

    public void deleteCourse(int id) {
        //TODO: validar que el carne exista en la BD. Si existe se borra
        Optional<Curso> curso = this.cursoDAO.findById(id);
        if (curso.isPresent()){
            this.cursoDAO.delete(id);
        }
    }

    @Override
    public List<Curso> findByDepartment(String department) {
        //Validar si department es nulo o vacio. Si no es nulo o vacio se va a poder llamar al DAO.
        //sino, se retorna una lista vacia
        if (!department.isEmpty()){
            return this.cursoDAO.findByDepartment(department);
        }
        return this.cursoDAO.findByDepartment(null);
    }

    @Override
    public List<Curso> getCourseByDepartment(String department) {
        if (!department.isEmpty()) {
            return this.cursoDAO.findByDepartment(department);
        }
        return null;
    }

}
