package tec.bd.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.bd.app.dao.set.ProfesorSetDAOImpl;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Entity;
import tec.bd.app.domain.Profesor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class ProfesorSetDAOImplTest {

    private ProfesorSetDAOImpl profesorDAO;

    @BeforeEach
    public void setUp() throws Exception {

        var jaimeId = new RowAttribute("id", 1);
        var jaimeNombre = new RowAttribute("nombre", "Jaime");
        var jaimeApellido = new RowAttribute("apellido", "Solis");
        var jaimeCiudad = new RowAttribute("ciudad", "Alajuela");
        var jaimeDepartamento = new RowAttribute("departamento", "Computacion");
        var jaimeRow = new Row(new RowAttribute[]{ jaimeId, jaimeNombre, jaimeApellido, jaimeCiudad, jaimeDepartamento});

        var lorenaId = new RowAttribute("id", 2);
        var lorenaNombre = new RowAttribute("nombre", "Lorena");
        var lorenaApellido = new RowAttribute("apellido", "Rojas");
        var lorenaCiudad = new RowAttribute("ciudad", "San Jose");
        var lorenaDepartamento = new RowAttribute("departamento", "Computacion");
        var lorenaRow = new Row(new RowAttribute[]{ lorenaId, lorenaNombre, lorenaApellido, lorenaCiudad, lorenaDepartamento});

        var kenethId = new RowAttribute("id", 4);
        var kenethNombre = new RowAttribute("nombre", "Keneth");
        var kenethApellido = new RowAttribute("apellido", "Hernandez");
        var kenethCiudad = new RowAttribute("ciudad", "Alajuela");
        var kenethDepartamento = new RowAttribute("departamento", "Ing Civil");
        var kenethRow = new Row(new RowAttribute[]{ kenethId, kenethNombre, kenethApellido, kenethCiudad });


        var tables = new HashMap<Class<? extends Entity>, Set<Row>>();
        tables.put(Profesor.class, new HashSet<>() {{
            add(jaimeRow);
            add(lorenaRow);
            add(kenethRow);
        }});
        var setDB = new SetDB(tables);
        this.profesorDAO = new ProfesorSetDAOImpl(setDB);
    }

    @Test
    public void findAll() throws Exception {
        var actual = this.profesorDAO.findAll();
        assertThat(actual).hasSize(3);
    }

    @Test
    public void findById() throws Exception {
        var teacher = this.profesorDAO.findById(2);
        assertThat(teacher.get().getId()).isEqualTo(2);
        assertThat(teacher.get().getNombre()).isEqualTo("Lorena");
        assertThat(teacher.get().getApellido()).isEqualTo("Rojas");
        assertThat(teacher.get().getCiudad()).isEqualTo("San Jose");
        assertThat(teacher.get().getDepartamento()).isEqualTo("Computacion");
    }

    @Test
    public void save() throws Exception {
        this.profesorDAO.save(new Profesor(5, "Luis", "Chacon", "Cartago", "Ing Civil"));
        var teacher = this.profesorDAO.findById(5);
        assertThat(this.profesorDAO.findAll()).hasSize(4);
        assertThat(teacher.isPresent()).isTrue();
        assertThat(teacher.get().getId()).isEqualTo(5);
        assertThat(teacher.get().getNombre()).isEqualTo("Luis");
        assertThat(teacher.get().getApellido()).isEqualTo("Chacon");
        assertThat(teacher.get().getCiudad()).isEqualTo("Cartago");
        assertThat(teacher.get().getDepartamento()).isEqualTo("Ing Civil");
    }

    @Test
    public void update() throws Exception {
        var current = this.profesorDAO.findById(2);
        current.get().setApellido("Rodriguez");
        current.get().setCiudad("San Carlos");
        var actual = this.profesorDAO.update(current.get());
        assertThat(this.profesorDAO.findAll()).hasSize(3);
        assertThat(actual.get().getId()).isEqualTo(2);
        assertThat(actual.get().getNombre()).isEqualTo("Lorena");
        assertThat(actual.get().getApellido()).isEqualTo("Rodriguez");
        assertThat(actual.get().getCiudad()).isEqualTo("San Carlos");
        assertThat(actual.get().getCiudad()).isEqualTo("Computacion");
    }

    @Test
    public void delete() throws Exception {
        this.profesorDAO.delete(2);
        assertThat(this.profesorDAO.findAll()).hasSize(2);
    }

    @Test
    public void findByLastName() throws Exception {
        //TODO: hay que implementarlo
        var actual = this.profesorDAO.findByLastName("Solis");

        assertThat(actual).hasSize(1);

        var profesor = actual.get(0);

        assertThat(profesor.getNombre()).isEqualTo("Jaime");
    }

    @Test
    public void findByCity() throws Exception {
        //TODO: hay que implementarlo
        var actual = this.profesorDAO.findByCity("San Jose");

        assertThat(actual).hasSize(1);

        var profesor = actual.get(0);

        assertThat(profesor.getCiudad()).isEqualTo("San Jose");
    }


}


