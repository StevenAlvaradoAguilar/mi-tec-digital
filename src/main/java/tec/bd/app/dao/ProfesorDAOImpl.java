package tec.bd.app.dao;

import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Estudiante;
import tec.bd.app.domain.Profesor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProfesorDAOImpl extends GenericSetDAOImpl<Profesor, Integer> implements ProfesorDAO {

    public ProfesorDAOImpl(SetDB setDB, Class<Profesor> clazz) {
        super(setDB, clazz);
    }

    @Override
    public List<Profesor> findByLastName(String lastName) {
        return this.findAll().stream().filter(p -> p.getApellido().equals(lastName)).collect(Collectors.toList());
    }

    @Override
    public List<Profesor> findByCity(String city) {
        return this.findAll().stream().filter(c -> c.getCiudad().equals(city)).collect(Collectors.toList());
    }

    @Override
    protected Profesor rowToEntity(Row row) {
        // conversiones de Row a Estudiante
        var id = row.intAttributeValue("id");
        var nombre = row.stringAttributeValue("nombre");
        var apellido = row.stringAttributeValue("apellido");
        var ciudad = row.stringAttributeValue("ciudad");
        return new Profesor(id, nombre, apellido, ciudad);
    }

    @Override
    protected Row entityToRow(Profesor p) {
        // conversiones de Estudiante a Row
        return new Row(new RowAttribute[] {
                new RowAttribute("id", p.getId()),
                new RowAttribute("nombre", p.getNombre()),
                new RowAttribute("apellido", p.getApellido()),
                new RowAttribute("ciudad", p.getCiudad())
        });
    }

}
