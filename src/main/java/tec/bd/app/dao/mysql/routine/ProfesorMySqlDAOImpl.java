package tec.bd.app.dao.mysql.routine;

import tec.bd.app.dao.ProfesorDAO;
import tec.bd.app.dao.mysql.GenericMySqlDAOImpl;
import tec.bd.app.domain.Profesor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.database.mysql.DBProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProfesorMySqlDAOImpl extends GenericMySqlDAOImpl<Profesor, Integer> implements ProfesorDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ProfesorMySqlDAOImpl.class);

    private static final String FIND_BY_CITY_PROCEDURE = "{call find_By_City(?)}";
    private static final String ALL_TEACHERS_PROCEDURE = "{call all_teachers()}";
    private static final String FIND_BY_ID_PROCEDURE = "{call find_by_Id_teacher(?)}";

    private static final String ADD_TEACHERS_PROCEDURE = "{call add_teacher(?, ?, ?, ?)}";
    private static final String UPDATE_TEACHERS_PROCEDURE = "{call update_teacher(?, ?, ?, ?)}";
    private static final String DELETE_TEACHERS_PROCEDURE = "{call delete_teacher(?)}";

    private DBProperties dbProperties;
    public ProfesorMySqlDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Profesor> findAll() {

        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(ALL_TEACHERS_PROCEDURE)) {

            try (ResultSet res = stmt.executeQuery(ALL_TEACHERS_PROCEDURE)) {
                return this.resultSetToList(res);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Profesor> findById(Integer id) {
        ResultSet resultSet;
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement value = connection.prepareCall(FIND_BY_ID_PROCEDURE)) {

            value.setInt(1, id);
            resultSet = value.executeQuery();
            return this.resultSetToList(resultSet).stream().findFirst();

        }catch (SQLException ex){
            LOG.error("Error when running {}", FIND_BY_ID_PROCEDURE, ex);
        }
        return Optional.empty();
    }

    @Override
    public void save(Profesor profesor) {
        try (Connection connection = this.dbProperties.getConnection();
                 CallableStatement stmt = connection.prepareCall(ADD_TEACHERS_PROCEDURE)) {

            stmt.setInt(1, profesor.getId());
            stmt.setString(2, profesor.getNombre());
            stmt.setString(3, profesor.getApellido());
            stmt.setString(4, profesor.getCiudad());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            LOG.error("Error when running {}", ADD_TEACHERS_PROCEDURE, ex);
        }
    }

    @Override
    public Optional<Profesor> update(Profesor profesor) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(UPDATE_TEACHERS_PROCEDURE)) {

            stmt.setInt(1, profesor.getId());
            stmt.setString(2, profesor.getNombre());
            stmt.setString(3, profesor.getApellido());
            stmt.setString(4, profesor.getCiudad());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            LOG.error("Error when running {}", UPDATE_TEACHERS_PROCEDURE, ex);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(DELETE_TEACHERS_PROCEDURE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            LOG.error("Error when running {}", DELETE_TEACHERS_PROCEDURE, ex);
        }
    }


    @Override
    public List<Profesor> findByCity(String city) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement value = connection.prepareCall(FIND_BY_CITY_PROCEDURE)) {

            value.setString(1, city);
            ResultSet res = value.executeQuery();
            return this.resultSetToList(res);

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    protected Profesor resultSetToEntity(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var nombre = resultSet.getString("nombre");
        var apellido = resultSet.getString("apellido");
        var ciudad = resultSet.getString("ciudad");
        return new Profesor(id, nombre, apellido, ciudad);
    }

    @Override
    protected List<Profesor> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Profesor> profesores = new ArrayList<>();
        while(resultSet.next()) {
            profesores.add(this.resultSetToEntity(resultSet));
        }
        return profesores;
    }
}
