package tec.bd.app.dao.set;

import tec.bd.app.dao.EstudianteDAO;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Estudiante;

import java.util.List;
import java.util.stream.Collectors;

public class EstudianteDAOImpl extends GenericSetDAOImpl<Estudiante, Integer> implements EstudianteDAO {

    public EstudianteDAOImpl(SetDB setDB, Class<Estudiante> clazz) {
        super(setDB, clazz);
    }

    @Override
    public List<Estudiante> findByLastName(String lastName) {
        return this.findAll().stream().filter(e -> e.getApellido().equals(lastName)).collect(Collectors.toList());
    }

    @Override
    public List<Estudiante> findAllSortByLastName() {
        return this.findAll().stream().filter(e -> e.getApellido().equals(Estudiante.class)).collect(Collectors.toList());
    }

    @Override
    protected Estudiante rowToEntity(Row row) {
        // conversiones de Row a Estudiante
        var carne = row.intAttributeValue("id");
        var nombre = row.stringAttributeValue("nombre");
        var apellido = row.stringAttributeValue("apellido");
        var edad = row.intAttributeValue("edad");
        return new Estudiante(carne, nombre, apellido, edad);
    }

    @Override
    protected Row entityToRow(Estudiante e) {
        // conversiones de Estudiante a Row
        return new Row(new RowAttribute[] {
                new RowAttribute("id", e.getCarne()),
                new RowAttribute("nombre", e.getNombre()),
                new RowAttribute("apellido", e.getApellido()),
                new RowAttribute("edad", e.getEdad())
        });
    }

}
