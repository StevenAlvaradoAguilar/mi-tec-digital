package tec.bd.app;

import tec.bd.app.dao.*;
import tec.bd.app.dao.mysql.EstudianteMySqlDAOImpl;
import tec.bd.app.dao.set.CursoSetDAOImpl;
import tec.bd.app.dao.set.EstudianteSetDAOImpl;
import tec.bd.app.dao.set.ProfesorSetDAOImpl;
import tec.bd.app.database.mysql.DBProperties;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Curso;
import tec.bd.app.domain.Entity;
import tec.bd.app.domain.Estudiante;
import tec.bd.app.service.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ApplicationContext {

    private SetDB setDB;
    private EstudianteDAO estudianteDAO;
    private EstudianteService estudianteService;

    private CursoDAO cursoDAO;
    private CursoService cursoService;

    private ProfesorDAO profesorDAO;
    private ProfesorService profesorService;




//    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/universidad";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "rootroot";
//    private static final DBProperties DB_PROPERTIES = new DBProperties(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD);

    private static final String DATABASE_PROPERTIES_FILE = "/database.properties";
    private static final String CONNECTION_STRING_PROP = "database.url";
    private static final String DB_USERNAME_PROP = "database.username";
    private static final String DB_PASSWORD_PROP = "database.password";


    private ApplicationContext() {

    }

    public static ApplicationContext init() {
        ApplicationContext applicationContext = new ApplicationContext();
//        Objetos que usan SetDB
        applicationContext.setDB = initSetDB();
//        applicationContext.estudianteDAO = initEstudianteSetDAO(applicationContext.setDB);
        applicationContext.cursoDAO = initCursoSetDAO(applicationContext.setDB);
        applicationContext.profesorDAO = initProfesorSetDAO(applicationContext.setDB);

//        Objetos que se conectan a MySQL
        String dbPropertiesFilePath = applicationContext.getClass().getResource(DATABASE_PROPERTIES_FILE).getFile();
        DBProperties databaseProperties = initDBProperties(dbPropertiesFilePath);
        applicationContext.estudianteDAO = initEstudianteMysqlDAO(databaseProperties);
//        applicationContext.cursoDAO = initCursoMysqlDAO(databaseProperties);
//        applicationContext.profesorDAO = initProfesorMysqlDAO(databaseProperties);


        applicationContext.estudianteService = initEstudianteService(applicationContext.estudianteDAO);
        applicationContext.cursoService = initCursoService(applicationContext.cursoDAO);
        applicationContext.profesorService = initProfesorService(applicationContext.profesorDAO);
        return applicationContext;
    }

    private static SetDB initSetDB() {
        // Registros de la tabla estudiante
        var juanId = new RowAttribute("id", 1);
        var juanNombre = new RowAttribute("nombre", "Juan");
        var juanApellido = new RowAttribute("apellido", "Perez");
        var juanEdad = new RowAttribute("edad", 20);
        var juanRow = new Row(new RowAttribute[]{ juanId, juanNombre, juanApellido, juanEdad });

        var mariaId = new RowAttribute("id", 3);
        var mariaNombre = new RowAttribute("nombre", "Maria");
        var mariaApellido = new RowAttribute("apellido", "Rojas");
        var mariaEdad = new RowAttribute("edad", 21);
        var mariaRow = new Row(new RowAttribute[]{ mariaId, mariaNombre, mariaApellido, mariaEdad });

        var pedroId = new RowAttribute("id", 2);
        var pedroNombre = new RowAttribute("nombre", "Pedro");
        var pedroApellido = new RowAttribute("apellido", "Infante");
        var pedroEdad = new RowAttribute("edad", 23);
        var pedroRow = new Row(new RowAttribute[]{ pedroId, pedroNombre, pedroApellido, pedroEdad });

        var raquelId = new RowAttribute("id", 10);
        var raquelNombre = new RowAttribute("nombre", "Raquel");
        var raquelApellido = new RowAttribute("apellido", "Rojas");
        var raquelEdad = new RowAttribute("edad", 25);
        var raquelRow = new Row(new RowAttribute[]{ raquelId, raquelNombre, raquelApellido, raquelEdad });

        // ---------------------------------------------------------------
        // Registros de la tabla curso
        // ---------------------------------------------------------------
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


        // ---------------------------------------------------------------
        // Registros de la tabla profesor
        // ---------------------------------------------------------------


        // Agregando las "tablas" a SetDB
        var tables = new HashMap<Class<? extends Entity>, Set<Row>>();
        tables.put(Estudiante.class, new HashSet<>() {{
            add(juanRow);
            add(mariaRow);
            add(pedroRow);
            add(raquelRow);
        }});

        // Agregar las filas de curso y estudiante a tables
        tables.put(Curso.class, new HashSet<>() {{
            add(basesDeDatos);
            add(genetica);
            add(introBio);
        }});
        // tables.put(Profesor.class, new HashSet<>() {{ ... }}

        return new SetDB(tables);
    }

    private static EstudianteDAO initEstudianteSetDAO(SetDB setDB) {
        return new EstudianteSetDAOImpl(setDB);
    }
    private static CursoDAO initCursoSetDAO(SetDB setDB) {
        return new CursoSetDAOImpl(setDB);
    }
    private static ProfesorDAO initProfesorSetDAO(SetDB setDB) { return new ProfesorSetDAOImpl(setDB); }




    private static DBProperties initDBProperties(String dbPropertiesFilePath) {
        try (InputStream propFileStream = new FileInputStream(dbPropertiesFilePath)) {
            Properties properties = new Properties();
            properties.load(propFileStream);
            return new DBProperties(
                    properties.getProperty(CONNECTION_STRING_PROP),
                    properties.getProperty(DB_USERNAME_PROP),
                    properties.getProperty(DB_PASSWORD_PROP)
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("No se puede cargar el archivo de propiedades de la base de datos");
        }
    }

    private static EstudianteDAO initEstudianteMysqlDAO(DBProperties dbProperties) {
        return new EstudianteMySqlDAOImpl(dbProperties);
    }

    private static CursoDAO initCursoMysqlDAO(DBProperties dbProperties) {
//        return new CursoMySqlDAOImpl(dbProperties);
        return null;
    }

    private static ProfesorDAO initProfesorMysqlDAO(DBProperties dbProperties) {
//        return new EstudianteMySqlDAOImpl(dbProperties);
        return null;
    }


//    Servicios

    private static EstudianteService initEstudianteService(EstudianteDAO estudianteDAO) {
        return new EstudianteServiceImpl(estudianteDAO);
    }

    private static CursoService initCursoService(CursoDAO cursoDAO) {
        return new CursoServiceImpl(cursoDAO);
    }

    private static ProfesorService initProfesorService(ProfesorDAO profesorDAO) {
        return new ProfesorServiceImpl(profesorDAO);
    }


    public SetDB getSetDB() {
        return this.setDB;
    }

    public EstudianteDAO getEstudianteDAO() {
        return this.estudianteDAO;
    }

    public EstudianteService getEstudianteService() {
        return this.estudianteService;
    }

    public CursoDAO getCursoSetDAO() {
        return this.cursoDAO;
    }

    public CursoService getCursoService() {
        return this.cursoService;
    }

    public ProfesorDAO getProfesorDAO() {
        return profesorDAO;
    }

    public ProfesorService getProfesorService() {
        return profesorService;
    }


}
