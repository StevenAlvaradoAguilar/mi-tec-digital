package tec.bd.app.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tec.bd.app.dao.set.CursoSetDAOImpl;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.domain.Entity;
import tec.bd.app.domain.Curso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class CursoSetDAOImplTest {

    private CursoSetDAOImpl cursoDAO;

    @BeforeEach
    public void setUp() throws Exception {

        var basesDeDatosId = new RowAttribute("id", 1);
        var basesDeDatosNombre = new RowAttribute("nombre", "Bases de Datos");
        var basesDeDatosDepartamento = new RowAttribute("departamento", "Informatica");
        var basesDeDatosCreditos = new RowAttribute("creditos", 4);
        var basesDeDatos = new Row(new RowAttribute[]{ basesDeDatosId, basesDeDatosNombre, basesDeDatosDepartamento, basesDeDatosCreditos });

        var geneticaId = new RowAttribute("id", 10);
        var geneticaNombre = new RowAttribute("nombre", "Genetica");
        var geneticaDepartamento = new RowAttribute("departamento", "Biologia");
        var geneticaCreditos = new RowAttribute("creditos", 4);
        var genetica = new Row(new RowAttribute[]{ geneticaId, geneticaNombre, geneticaDepartamento, geneticaCreditos });

        var introBioId = new RowAttribute("id", 20);
        var introBioNombre = new RowAttribute("nombre", "Intro Biologia");
        var introBioDepartamento = new RowAttribute("departamento", "Biologia");
        var introBioCreditos = new RowAttribute("creditos", 4);
        var introBio = new Row(new RowAttribute[]{ introBioId, introBioNombre, introBioDepartamento, introBioCreditos });

        var tables = new HashMap<Class<? extends Entity>, Set<Row>>();
        tables.put(Curso.class, new HashSet<>() {{
            add(basesDeDatos);
            add(genetica);
            add(introBio);
        }});
        var setDB = new SetDB(tables);
        this.cursoDAO = new CursoSetDAOImpl(setDB);
    }

    @Test
    public void findByDepartment() throws Exception {

        var actual = this.cursoDAO.findByDepartment("Biologia");

        assertThat(actual).hasSize(2);

    }


    @Test
    public void findByDepartment_whenNoDepartmentExists() throws Exception {

        var actual = this.cursoDAO.findByDepartment("Agronomia");

        assertThat(actual).isEmpty();

    }



}

