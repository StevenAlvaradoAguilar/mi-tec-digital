package tec.bd.app.dao;

import tec.bd.app.domain.Estudiante;
import tec.bd.app.domain.Profesor;

import java.util.List;

public interface ProfesorDAO extends GenericDAO<Profesor, Integer>{

    /**
     * Obtener todos los cursos por departamentos
     * @param lastName
     * return
     */
    List<Profesor> findByLastName(String lastName);

}
