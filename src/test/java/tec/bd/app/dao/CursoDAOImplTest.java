package tec.bd.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.bd.app.dao.set.CursoDAOImpl;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.domain.Entity;
import tec.bd.app.domain.Curso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class CursoDAOImplTest {

    private CursoDAOImpl cursoDAO;

    @BeforeEach
    public void setUp() throws Exception {

        var matematicaId = new RowAttribute("id", 1);
        var matematicaNombre = new RowAttribute("nombre", "Matematica");
        var matematicaCreditos = new RowAttribute("creditos", 4);
        var matematicaDepartamentos = new RowAttribute("departamento", "Edi-A");
        var matematicaRow = new Row(new RowAttribute[]{ matematicaId, matematicaNombre,
                matematicaCreditos, matematicaDepartamentos });

        var pooId = new RowAttribute("id", 2);
        var pooNombre = new RowAttribute("nombre", "POO");
        var pooCreditos = new RowAttribute("creditos", 3);
        var pooDepartamentos = new RowAttribute("departamento", "Edi-C");
        var pooRow = new Row(new RowAttribute[]{ pooId, pooNombre,
                pooCreditos, pooDepartamentos });

        var db_2Id = new RowAttribute("id", 3);
        var db_2Nombre = new RowAttribute("nombre", "DB_2");
        var db_2Creditos = new RowAttribute("creditos", 5);
        var db_2Departamentos = new RowAttribute("departamento", "Edi-D");
        var db_2Row = new Row(new RowAttribute[]{ db_2Id, db_2Nombre,
                db_2Creditos, db_2Departamentos });

        var tables = new HashMap<Class<? extends Entity>, Set<Row>>();
        tables.put(Curso.class, new HashSet<>() {{
            add(matematicaRow);
            add(pooRow);
            add(db_2Row);
        }});
        var setDB = new SetDB(tables);
        this.cursoDAO = new CursoDAOImpl(setDB, Curso.class);
    }

    @Test
    public void findAll() throws Exception {
        var actual = this.cursoDAO.findAll();
        assertThat(actual).hasSize(3);
    }

    @Test
    public void findById() throws Exception {
        var curse = this.cursoDAO.findById(1);
        assertThat(curse.get().getId()).isEqualTo(1);
        assertThat(curse.get().getNombre()).isEqualTo("Matematica");
        assertThat(curse.get().getCreditos()).isEqualTo(4);
        assertThat(curse.get().getDepartamento()).isEqualTo("Edi-A");
    }

    @Test
    public void save() throws Exception {
        this.cursoDAO.save(new Curso(4, "Ingles", 2, "Edi-I"));
        var curso = this.cursoDAO.findById(4);
        assertThat(this.cursoDAO.findAll()).hasSize(4);
        assertThat(curso.isPresent()).isTrue();
        assertThat(curso.get().getId()).isEqualTo(4);
        assertThat(curso.get().getNombre()).isEqualTo("Ingles");
        assertThat(curso.get().getCreditos()).isEqualTo(2);
        assertThat(curso.get().getDepartamento()).isEqualTo("Edi-I");
    }

    @Test
    public void update() throws Exception {
        var current = this.cursoDAO.findById(3);
        current.get().setCreditos(4);
        current.get().setDepartamento("Edi-C");
        var actual = this.cursoDAO.update(current.get());
        assertThat(this.cursoDAO.findAll()).hasSize(3);
        assertThat(actual.get().getId()).isEqualTo(3);
        assertThat(actual.get().getNombre()).isEqualTo("DB_2");
        assertThat(actual.get().getCreditos()).isEqualTo(4);
        assertThat(actual.get().getDepartamento()).isEqualTo("Edi-C");
    }

    @Test
    public void delete() throws Exception {
        this.cursoDAO.delete(2);
        assertThat(this.cursoDAO.findAll()).hasSize(2);
    }

    @Test
    public void findByDepartment() throws Exception {
        //TODO: hay que implementarlo

        var actual = this.cursoDAO.findByDepartment("Edi-C");

        assertThat(actual).hasSize(1);

        var curso = actual.get(0);

        assertThat(curso.getNombre()).isEqualTo("POO");

    }



}

