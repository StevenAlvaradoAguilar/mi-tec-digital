package tec.bd.app.dao.set;

import tec.bd.app.dao.CursoDAO;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Curso;

import java.util.List;
import java.util.stream.Collectors;

public class CursoSetDAOImpl extends GenericSetDAOImpl<Curso, Integer> implements CursoDAO {

    public CursoSetDAOImpl(SetDB setDB) {
        super(setDB, Curso.class);
    }

    @Override
    public List<Curso> findByDepartment(String department) {
        return this.findAll().stream().filter(c -> c.getDepartamento().equals(department)).collect(Collectors.toList());
    }

    @Override
    protected Curso rowToEntity(Row row) {
        // conversiones de Row a Curso
        Curso curso = new Curso();
        curso.setId(row.intAttributeValue("id"));
        curso.setNombre(row.stringAttributeValue("nombre"));
        curso.setDepartamento(row.stringAttributeValue("departamento"));
        curso.setCreditos(row.intAttributeValue("creditos"));
        return curso;
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

