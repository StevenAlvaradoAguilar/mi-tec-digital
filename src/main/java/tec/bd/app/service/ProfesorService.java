package tec.bd.app.service;

import tec.bd.app.domain.Profesor;

import java.util.List;
import java.util.Optional;

public interface ProfesorService {

    List<Profesor> getAll();

    Optional<Profesor> getById(int id);

    void addNew(Profesor p);

    Optional<Profesor> updateTeacher(Profesor p);

    void deleteTeacher(int id);

    List<Profesor> getTeachersByLastName(String lastName);

    List<Profesor> getTeachersByCity(String city);

}
