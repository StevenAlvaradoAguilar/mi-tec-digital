package tec.bd.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MySQLDemo {

    private static final Logger LOG = LoggerFactory.getLogger(MySQLDemo.class);
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/universidad";
    private static final String DB_USERNAME = "universidad_user";
    private static final String DB_PASSWORD = "universidad_pass";


    private static final String SQL_SELECT_ESTUDIANTES = "select id,nombre,apellido,fecha_nacimiento, DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(), fecha_nacimiento)), \"%Y\")+0 as edad, total_creditos from estudiante order by nombre asc";
    private static final String SQL_SELECT_ESTUDIANTE_ID = "select * from estudiante where id = "; // concatenacion del 1
    private static final String SQL_INSERT_ESTUDIANTE = "insert into estudiante(id, nombre, apellido, fecha_nacimiento, total_creditos) values(45, 'Lucas', 'Cascante', '1999-01-01 00:00:00', 8)";
    private static final String ALL_COURSES_PROCEDURE = "{call all_courses(?)}";
    private static final String ADD_COURSE_PROCEDURE = "{call add_course(?, ?, ?, ?)}";

    public static void main(String... args) {

        try {
            try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD)) {
                LOG.info(SQL_SELECT_ESTUDIANTES);
                try (Statement stmt = connection.createStatement()) {
                    //execute query -- consultas de seleccion
                    try (ResultSet resultSet = stmt.executeQuery(SQL_SELECT_ESTUDIANTES)) {
                        System.out.println("ID\t\t Nombre\t\t Apellido\t\t Fecha Nacimiento\t\t Edad\t\t Creditos");
                        System.out.println("-------------------------------------------------------------------------");
                        while (resultSet.next()) {
                            System.out.println(
                                    resultSet.getInt("id") + "\t\t" +
                                            resultSet.getString("nombre") + "\t\t" +
                                            resultSet.getString("apellido") + "\t\t" +
                                            resultSet.getDate("fecha_nacimiento") + "\t\t" +
                                            resultSet.getInt("edad") + "\t\t" +
                                            resultSet.getInt("total_creditos")
                            );
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when running {}", SQL_SELECT_ESTUDIANTES, e);
        }


        // ---------------------------------------------------------------------------------------


//        try {
//            try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD)) {
//                LOG.info(SQL_INSERT_ESTUDIANTE);
//                try (Statement stmt = connection.createStatement()) {
//                    //execute query
//                    int rowCount = stmt.executeUpdate(SQL_INSERT_ESTUDIANTE);
//                    LOG.info("Row count: {}", rowCount);
//                }
//            }
//        } catch (SQLException e) {
//            LOG.error("Error when running {}", SQL_SELECT_ESTUDIANTES, e);
//        }

        System.out.println("\n\n Store procedure -------------------");
        allCourses(1);
        addCourse(8, "Seminario 1", "Computacion", 4);
    }

        public static void allCourses(int courseId) {

            ResultSet rs;

            try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD);
                    CallableStatement stmt = connection.prepareCall(ALL_COURSES_PROCEDURE)) {
                //stmt.setObject(1, null);
                //stmt.setInt(1, courseId);
                stmt.setInt(1, 1);
                rs = stmt.executeQuery();

                while (rs.next()){
                    System.out.println(rs.getInt("id") + "\t" + rs.getString("nombre") + "\t" + rs.getString("departamento") + "\t" + rs.getInt("creditos"));
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        public static void addCourse(int courseId, String courseName, String courseDepartment, int courseCredits){
            try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD);
                 CallableStatement stmt = connection.prepareCall(ADD_COURSE_PROCEDURE)) {

                    stmt.setInt(1, courseId);
                    stmt.setString(2, courseName);
                    stmt.setString(3, courseDepartment);
                    stmt.setInt(4, courseCredits);


                    stmt.executeUpdate();
        }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
    }
}
