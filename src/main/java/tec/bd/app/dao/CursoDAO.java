package tec.bd.app.dao;

import tec.bd.app.domain.Curso;

import java.util.List;

public interface CursoDAO extends GenericDAO<Curso, Integer> {

    /**
     * Obtener todos los estudiantes por apellido
     * @param name
     * return
     */
    List<Curso> findByName(String name);

    /**
     * Obtener todos los cursos por departamentos
     * @param departamento
     * return
     */
    List<Curso> findByDepartment(String departamento);


}
