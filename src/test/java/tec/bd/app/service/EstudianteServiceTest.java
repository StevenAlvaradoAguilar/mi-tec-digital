package tec.bd.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.bd.app.bd.SetDB;
import tec.bd.app.domain.Estudiante;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class EstudianteServiceTest {

    private EstudianteService estudianteService;


    @BeforeEach
    public void setUp() throws Exception {
        Set<Estudiante> estudiantesTable = new TreeSet<>(){{
            add(new Estudiante(1, "Juan", "Perez", 20));
            add(new Estudiante(32, "Maria", "Rojas", 21));
            add(new Estudiante(22, "Roberto", "Brenes", 22));
        }};
        SetDB database = new SetDB(estudiantesTable);

        this.estudianteService = new EstudianteService(database);
    }

    @Test
    public void whenNoDataInDB_thenNoResult() throws Exception {
        SetDB database = new SetDB(Collections.emptySet());

        EstudianteService localEstudianteService = new EstudianteService(database);
        var estudiantes = localEstudianteService.getAll();

        assertThat(estudiantes).hasSize(0);
    }

    @Test
    public void getAllTest() throws Exception {

        var estudiantes = this.estudianteService.getAll();

        assertThat(estudiantes).hasSize(3);

    }


    @Test
    public void addNewStudent() throws Exception {

        var karol = new Estudiante(2, "Karol", "Jimenez", 21);
        var melani = new Estudiante(5, "melani", "Alfaro", 18);
        var maria = new Estudiante(32, "Maria", "Rojas", 21);
        estudianteService.addNew(karol);
        estudianteService.addNew(melani);
        estudianteService.addNew(maria);

        var estudiantes = this.estudianteService.getAll();

        assertThat(estudiantes).hasSize(5);
    }

    @Test
    public void deleteStudent() throws Exception {

        var estudianteBorrado = estudianteService.getById(1);
        estudianteService.deleteStudent(estudianteBorrado.getCarne());

        var estudianteBorrado1 = estudianteService.getById(32);
        estudianteService.deleteStudent(estudianteBorrado1.getCarne());

        var estudiantes = this.estudianteService.getAll();

        assertThat(estudiantes).hasSize(1);

    }

    @Test
    public void updateStudent() throws Exception {

        var estudianteActualizado = estudianteService.getById(1);
        estudianteActualizado.setNombre(estudianteActualizado.getNombre());
        estudianteActualizado.setApellido(estudianteActualizado.getApellido());
        estudianteActualizado.setEdad(estudianteActualizado.getEdad());

        var estudiantes = this.estudianteService.getAll();

        assertThat(estudiantes).hasSize(3);

    }

}
