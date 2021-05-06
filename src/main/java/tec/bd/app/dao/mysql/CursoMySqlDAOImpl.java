package tec.bd.app.dao.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.dao.CursoDAO;
import tec.bd.app.database.mysql.DBProperties;
import tec.bd.app.domain.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CursoMySqlDAOImpl extends GenericMySqlDAOImpl<Curso, Integer> implements CursoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CursoMySqlDAOImpl.class);

    private final DBProperties dbProperties;

    private static final String SQL_SELECT_CURSOS = "select id, nombre, departamento, creditos from curso;";
    private static final String SQL_SELECT_CURSO_ID = "select id, nombre, departamento, creditos from curso where id = %d";
    private static final String SQL_INSERT_CURSO = "insert into curso(id, nombre, departamento, creditos) values(%d, '%s', '%s', %d)";
    private static final String SQL_UPDATE_CURSO = "update curso set nombre = '%s', departamento = '%s', creditos = %d where id = %d";
    private static final String SQL_DELETE_CURSO = "delete from curso where id = %d";

    public CursoMySqlDAOImpl(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
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
    public List<Curso> findAll() {
        try {
            try (Connection connection = this.dbProperties.getConnection()) {
                LOG.info(SQL_SELECT_CURSOS);
                try (Statement stmt = connection.createStatement()) {
                    //execute query
                    try (ResultSet rs = stmt.executeQuery(SQL_SELECT_CURSOS)) {
                        return this.resultSetToList(rs);
                    }

                }
            }
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_CURSOS, e);
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<Curso> findById(Integer id) {
        try {
            try (Connection connection = this.dbProperties.getConnection()) {
                try (Statement stmt = connection.createStatement()) {
                    //execute query
                    var sql = String.format(SQL_SELECT_CURSO_ID, id);
                    LOG.info(sql);
                    try (ResultSet rs = stmt.executeQuery(sql)) {
                        return this.resultSetToList(rs).stream().findFirst();
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_CURSOS, e);
        }

        return Optional.empty();
    }

    @Override
    public void save(Curso curso) {
        try {
            try (Connection connection = this.dbProperties.getConnection()) {
                try (Statement stmt = connection.createStatement()) {
                    //execute query
                    var sql = String.format(SQL_INSERT_CURSO,
                            curso.getId(),
                            curso.getNombre(),
                            curso.getDepartamento(),
                            curso.getCreditos()
                    );
                    LOG.info(sql);
                    int rowCount = stmt.executeUpdate(sql);
                    LOG.debug("{} fila agregada", rowCount);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_CURSOS, e);
        }
    }

    @Override
    public Optional<Curso> update(Curso curso) {

        try {
            try (Connection connection = this.dbProperties.getConnection()) {
                try (Statement stmt = connection.createStatement()) {
                    //execute query
                    var sql = String.format(SQL_UPDATE_CURSO,
                            curso.getNombre(),
                            curso.getDepartamento(),
                            curso.getCreditos(),
                            curso.getId()
                    );
                    LOG.info(sql);
                    int rowCount = stmt.executeUpdate(sql);
                    LOG.debug("{} fila actualizada", rowCount);
                    if(rowCount == 1) {
                        return Optional.of(curso);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_CURSOS, e);
        }

        return Optional.empty();
    }


    @Override
    public void delete(Integer id) {
        try {
            try (Connection connection = this.dbProperties.getConnection()) {
                try (Statement stmt = connection.createStatement()) {
                    //execute query
                    var sql = String.format(SQL_DELETE_CURSO, id);
                    LOG.info(sql);
                    int rowCount = stmt.executeUpdate(sql);
                    LOG.debug("{} fila borrada", rowCount);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_CURSOS, e);
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
