package tec.bd.app.dao.mysql.routine;

import tec.bd.app.dao.CursoDAO;
import tec.bd.app.dao.mysql.GenericMySqlDAOImpl;
import tec.bd.app.domain.Curso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.database.mysql.DBProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CursoMySqlDAOImpl extends GenericMySqlDAOImpl<Curso, Integer> implements CursoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CursoMySqlDAOImpl.class);

    private static final String FIND_BY_DEPARTAMENT_PROCEDURE = "{call find_By_Departament(?)}";
    private static final String ALL_COURSES_PROCEDURE = "{call all_courses()}";
    private static final String FIND_BY_ID_PROCEDURE = "{call find_by_Id_Course(?)}";
    private static final String ADD_COURSE_PROCEDURE = "{call add_course(?, ?, ?, ?)}";
    private static final String UPDATE_COURSE_PROCEDURE = "{call update_course(?, ?, ?, ?)}";
    private static final String DELETE_COURSE_PROCEDURE = "{call delete_course(?)}";

    private DBProperties dbProperties;
    public CursoMySqlDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<Curso> findByDepartment(String departament) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement value = connection.prepareCall(FIND_BY_DEPARTAMENT_PROCEDURE)) {

            value.setString(1, departament);
            ResultSet res = value.executeQuery();
            return this.resultSetToList(res);

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Curso> findAll() {

        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(ALL_COURSES_PROCEDURE)) {

            try (ResultSet res = stmt.executeQuery(ALL_COURSES_PROCEDURE)) {
                return this.resultSetToList(res);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Curso> findById(Integer id) {
        ResultSet resultSet;
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement value = connection.prepareCall(FIND_BY_ID_PROCEDURE)) {

            value.setInt(1, id);
            resultSet = value.executeQuery();
            return this.resultSetToList(resultSet).stream().findFirst();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void save(Curso curso) {
        try (Connection connection = this.dbProperties.getConnection();
                CallableStatement stmt = connection.prepareCall(ADD_COURSE_PROCEDURE)) {

                stmt.setInt(1, curso.getId());
                stmt.setString(2, curso.getNombre());
                stmt.setString(3, curso.getDepartamento());
                stmt.setInt(4, curso.getCreditos());
                stmt.executeUpdate();

        } catch (SQLException ex) {
            LOG.error("Error when running {}", ADD_COURSE_PROCEDURE, ex);
        }
    }

    @Override
    public Optional<Curso> update(Curso curso) {
        try (Connection connection = this.dbProperties.getConnection();
                 CallableStatement stmt = connection.prepareCall(UPDATE_COURSE_PROCEDURE)) {

            stmt.setInt(1, curso.getId());
            stmt.setString(2, curso.getNombre());
            stmt.setString(3, curso.getDepartamento());
            stmt.setInt(4, curso.getCreditos());
            var result = stmt.executeUpdate();
            System.out.println("Resultado " + result);

        } catch (SQLException ex) {
            LOG.error("Error when running {}", UPDATE_COURSE_PROCEDURE, ex);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = this.dbProperties.getConnection();
             CallableStatement stmt = connection.prepareCall(DELETE_COURSE_PROCEDURE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            LOG.error("Error when running {}", DELETE_COURSE_PROCEDURE, ex);
        }
    }

    @Override
    protected Curso resultSetToEntity(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var nombre = resultSet.getString("nombre");
        var departamento = resultSet.getString("departamento");
        var creditos = resultSet.getInt("creditos");
        return new Curso(id, nombre, departamento, creditos);
    }

    @Override
    protected List<Curso> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        while(resultSet.next()) {
            cursos.add(this.resultSetToEntity(resultSet));
        }
        return cursos;
    }
}
