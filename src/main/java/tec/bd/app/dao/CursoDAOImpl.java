package tec.bd.app.dao;

import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Curso;

import java.util.List;
import java.util.stream.Collectors;

public class CursoDAOImpl extends GenericSetDAOImpl<Curso, Integer> implements CursoDAO {

    public CursoDAOImpl(SetDB setDB, Class<Curso> clazz) {
        super(setDB, clazz);
    }

    @Override
    public List<Curso> findByName(String name) {
        return this.findAll().stream().filter(c -> c.getNombre().equals(name)).collect(Collectors.toList());
    }

    @Override
    public List<Curso> findByDepartment(String department) {
        return this.findAll().stream().filter(c -> c.getDepartamento().equals(department)).collect(Collectors.toList());
    }

    @Override
    protected Curso rowToEntity(Row row) {
        // conversiones de Row a Curso
        var id = row.intAttributeValue("id");
        var nombre = row.stringAttributeValue("nombre");
        var creditos = row.intAttributeValue("creditos");
        var departamento = row.stringAttributeValue("departamento");
        return new Curso(id, nombre, creditos, departamento);
    }

    @Override
    protected Row entityToRow(Curso c) {
        // conversiones de Curso a Row
        return new Row(new RowAttribute[]{
                new RowAttribute("id", c.getId()),
                new RowAttribute("nombre", c.getNombre()),
                new RowAttribute("creditos", c.getCreditos()),
                new RowAttribute("departamento", c.getDepartamento())
        });
    }

}

