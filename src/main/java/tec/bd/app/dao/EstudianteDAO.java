package tec.bd.app.dao;

import tec.bd.app.domain.Estudiante;

import java.util.List;

public interface EstudianteDAO extends GenericDAO<Estudiante, Integer>{

    /**
     * Obtener todos los estudiantes por apellido
     * @param lastName
     * return
     */
    List<Estudiante> findByLastName(String lastName);

    /**
     * Listar todos los nombres de los estudiantes ordenados por apellido
     * @return
     */
    List<Estudiante> findAllSortByLastName();

}
