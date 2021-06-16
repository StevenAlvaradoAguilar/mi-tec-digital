package tec.bd.app.dao.mysql.routine;

import tec.bd.app.dao.EstudianteDAO;
import tec.bd.app.dao.mysql.GenericMySqlDAOImpl;
import tec.bd.app.domain.Estudiante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.database.mysql.DBProperties;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EstudianteMySqlDAOImpl extends GenericMySqlDAOImpl<Estudiante, Integer> implements EstudianteDAO {

    private static final Logger LOG = LoggerFactory.getLogger(EstudianteMySqlDAOImpl.class);

    private static final String FIND_BY_LASTNAME_PROCEDURE = "{call find_By_LastName(?)}";
    private static final String FIND_ALL_SORT_BY_LASTNAME = "{call findAllSortByLastName(?)}";
    private static final String ALL_STUDENTS_PROCEDURE = "{call all_students()}";
    private static final String FIND_BY_ID_PROCEDURE = "{call find_by_Id_Student(?)}";

    private static final String ADD_STUDENTS_PROCEDURE = "{call add_student(?, ?, ?, ?, ?)}";
    private static final String UPDATE_STUDENTS_PROCEDURE = "{call update_student(?, ?, ?, ?, ?)}";
    private static final String DELETE_STUDENTS_PROCEDURE = "{call delete_student(?)}";

    private DBProperties dbProperties;
    public EstudianteMySqlDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Estudiante> findByLastName(String lastName) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement value = connection.prepareCall(FIND_BY_LASTNAME_PROCEDURE)) {

            value.setString(1, lastName);
            ResultSet res = value.executeQuery();
            return this.resultSetToList(res);

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Estudiante> findAllSortByLastName() {

        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(FIND_ALL_SORT_BY_LASTNAME)) {

            try (ResultSet res = stmt.executeQuery(FIND_ALL_SORT_BY_LASTNAME)) {
                return this.resultSetToList(res);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Estudiante> findAll() {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(ALL_STUDENTS_PROCEDURE)) {

            try (ResultSet res = stmt.executeQuery(ALL_STUDENTS_PROCEDURE)) {
                return this.resultSetToList(res);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Estudiante> findById(Integer id) {
        ResultSet resultSet;
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement value = connection.prepareCall(FIND_BY_ID_PROCEDURE)) {

            value.setInt(1, id);
            resultSet = value.executeQuery();
            return this.resultSetToList(resultSet).stream().findFirst();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void save(Estudiante estudiante) {
        try {
            try (Connection connection = this.dbProperties.getConnection();
                 CallableStatement stmt = connection.prepareCall(ADD_STUDENTS_PROCEDURE)) {

                SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
                String sfecha = fecha.format(estudiante.getFechaNacimiento());
                java.sql.Date sqlfecha = java.sql.Date.valueOf(sfecha);

                stmt.setInt(1, estudiante.getId());
                stmt.setString(2, estudiante.getNombre());
                stmt.setString(3, estudiante.getApellido());
                stmt.setDate(4,sqlfecha);
                stmt.setInt(5, estudiante.getTotalCreditos());
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            LOG.error("Error when running {}", ADD_STUDENTS_PROCEDURE, ex);
        }
    }

    @Override
    public Optional<Estudiante> update(Estudiante estudiante) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(UPDATE_STUDENTS_PROCEDURE)) {

            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            String sfecha = fecha.format(estudiante.getFechaNacimiento());
            java.sql.Date sqlfecha = java.sql.Date.valueOf(sfecha);

            connection.setAutoCommit(false);

            stmt.setInt(1, estudiante.getId());
            stmt.setString(2, estudiante.getNombre());
            stmt.setString(3, estudiante.getApellido());
            stmt.setDate(4, sqlfecha);
            stmt.setInt(5, estudiante.getTotalCreditos());
            var result = stmt.executeUpdate();
            System.out.println("Resultado " + result);

            connection.commit();
            if(connection!=null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    connection.rollback();
                }
            }
        } catch (SQLException ex) {
            LOG.error("Error when running {}", UPDATE_STUDENTS_PROCEDURE, ex);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(DELETE_STUDENTS_PROCEDURE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            LOG.error("Error when running {}", DELETE_STUDENTS_PROCEDURE, ex);
        }
    }

    @Override
    protected Estudiante resultSetToEntity(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var nombre = resultSet.getString("nombre");
        var apellido = resultSet.getString("apellido");
        var fechaNacimiento = resultSet.getDate("fecha_nacimiento");
        var totalCreditos = resultSet.getInt("total_creditos");
        return new Estudiante(id, nombre, apellido, fechaNacimiento, totalCreditos);
    }

    @Override
    protected List<Estudiante> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        while(resultSet.next()) {
            estudiantes.add(this.resultSetToEntity(resultSet));
        }
        return estudiantes;
    }
}
