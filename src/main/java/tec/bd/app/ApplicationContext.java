package tec.bd.app;

import tec.bd.app.dao.*;
import tec.bd.app.database.set.Row;
import tec.bd.app.database.set.RowAttribute;
import tec.bd.app.database.set.SetDB;
import tec.bd.app.domain.Entity;
import tec.bd.app.domain.Estudiante;
import tec.bd.app.domain.Curso;
import tec.bd.app.domain.Profesor;
import tec.bd.app.service.EstudianteService;
import tec.bd.app.service.EstudianteServiceImpl;
import tec.bd.app.service.CursoService;
import tec.bd.app.service.CursoServiceImpl;
import tec.bd.app.service.ProfesorService;
import tec.bd.app.service.ProfesorServiceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ApplicationContext {

    private SetDB setDB;
    private EstudianteDAO estudianteSetDAO;
    private EstudianteService estudianteServiceSet;

    private CursoDAO cursoSetDAO;
    private CursoService cursoServiceSet;

    private ProfesorDAO profesorSetDAO;
    private ProfesorService profesorServiceSet;

    private ApplicationContext() {

    }

    public static ApplicationContext init() {
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.setDB = initSetDB();
        applicationContext.estudianteSetDAO = initEstudianteSetDAO(applicationContext.setDB);
        applicationContext.estudianteServiceSet = initEstudianteSetService(applicationContext.estudianteSetDAO);
        applicationContext.cursoSetDAO = initCursoSetDAO(applicationContext.setDB);
        applicationContext.cursoServiceSet = initCursoService(applicationContext.cursoSetDAO);
        applicationContext.profesorSetDAO = initProfesorSetDAO(applicationContext.setDB);
        applicationContext.profesorServiceSet = initProfesorService(applicationContext.profesorSetDAO);

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
        var mateId = new RowAttribute("id", 101);
        var mateNombre = new RowAttribute("nombre", "Juan");
        var mateCreditos = new RowAttribute("creditos", "Perez");
        var mateDepartamento = new RowAttribute("departamento", 20);
        var mateRow = new Row(new RowAttribute[]{ mateId, mateNombre, mateCreditos, mateDepartamento });

        var pooId = new RowAttribute("id", 121);
        var pooNombre = new RowAttribute("nombre", "Maria");
        var pooCreditos = new RowAttribute("creditos", "Rojas");
        var pooDepartamento = new RowAttribute("departamento", 21);
        var pooRow = new Row(new RowAttribute[]{ pooId, pooNombre, pooCreditos, pooDepartamento });

        var dbId = new RowAttribute("id", 103);
        var dbNombre = new RowAttribute("nombre", "Pedro");
        var dbCreditos = new RowAttribute("creditos", "Infante");
        var dbDepartamento = new RowAttribute("departamento", 23);
        var dbRow = new Row(new RowAttribute[]{ dbId, dbNombre, dbCreditos, dbDepartamento });

        var inglesId = new RowAttribute("id", 120);
        var inglesNombre = new RowAttribute("nombre", "Raquel");
        var inglesCreditos = new RowAttribute("creditos", "Rojas");
        var inglesDepartamento = new RowAttribute("departamento", 25);
        var inglesRow = new Row(new RowAttribute[]{ inglesId, inglesNombre, inglesCreditos, inglesDepartamento });


        // ---------------------------------------------------------------
        // Registros de la tabla profesor
        // ---------------------------------------------------------------
        var jaimeId = new RowAttribute("id", 1);
        var jaimeNombre = new RowAttribute("nombre", "Jaime");
        var jaimeApellido = new RowAttribute("apellido", "Solis");
        var jaimeCiudad = new RowAttribute("ciudad", "Alajuela");
        var jaimeRow = new Row(new RowAttribute[]{ jaimeId, jaimeNombre, jaimeApellido, jaimeCiudad });

        var lorenaId = new RowAttribute("id", 2);
        var lorenaNombre = new RowAttribute("nombre", "Maria");
        var lorenaApellido = new RowAttribute("apellido", "Rojas");
        var lorenaCiudad = new RowAttribute("ciudad", "San Jose");
        var lorenaRow = new Row(new RowAttribute[]{ lorenaId, lorenaNombre, lorenaApellido, lorenaCiudad });

        var kenethId = new RowAttribute("id", 4);
        var kenethNombre = new RowAttribute("nombre", "Keneth");
        var kenethApellido = new RowAttribute("apellido", "Hernandez");
        var kenethCiudad = new RowAttribute("ciudad", "Alajuela");
        var kenethRow = new Row(new RowAttribute[]{ kenethId, kenethNombre, kenethApellido, kenethCiudad });

        var joseId = new RowAttribute("id", 10);
        var joseNombre = new RowAttribute("nombre", "Raquel");
        var joseApellido = new RowAttribute("apellido", "Rojas");
        var joseCiudad = new RowAttribute("ciudad", "Alajuela");
        var joseRow = new Row(new RowAttribute[]{ joseId, joseNombre, joseApellido, joseCiudad });


        var tables = new HashMap<Class<? extends Entity>, Set<Row>>();
        // Agregar las filas de profesores a tables
        tables.put(Estudiante.class, new HashSet<>() {{
            add(juanRow);
            add(mariaRow);
            add(pedroRow);
            add(raquelRow);
        }});

        // Agregar las filas de curso a tables
        tables.put(Curso.class, new HashSet<>() {{
            add(mateRow);
            add(pooRow);
            add(dbRow);
            add(inglesRow);
        }});

        tables.put(Profesor.class, new HashSet<>() {{
            add(jaimeRow);
            add(lorenaRow);
            add(kenethRow);
            add(joseRow);
        }});

        return new SetDB(tables);
    }

    //Estudiante
    private static EstudianteDAO initEstudianteSetDAO(SetDB setDB) {
        return new EstudianteDAOImpl(setDB, Estudiante.class);
    }

    private static EstudianteService initEstudianteSetService(EstudianteDAO estudianteDAO) {
        return new EstudianteServiceImpl(estudianteDAO);
    }

    public SetDB getSetDB() {
        return this.setDB;
    }

    public EstudianteDAO getEstudianteSetDAO() {
        return this.estudianteSetDAO;
    }

    public EstudianteService getEstudianteServiceSet() {
        return this.estudianteServiceSet;
    }

    //Curso

     private static CursoDAO initCursoSetDAO(SetDB setDB){
        return new CursoDAOImpl(setDB, Curso.class);
     }

     private static CursoService initCursoService(CursoDAO cursoDAO){
        return new CursoServiceImpl(cursoDAO);
     }

    public CursoDAO getCursoSetDAO() {
        return this.cursoSetDAO;
    }

    public CursoService getCursoServiceSet() {
        return this.cursoServiceSet;
    }


    //Profesor
    private static ProfesorDAO initProfesorSetDAO(SetDB setDB){
        return new ProfesorDAOImpl(setDB, Profesor.class);
    }

    private static ProfesorService initProfesorService(ProfesorDAO profesorDAO){
        return new ProfesorServiceImpl(profesorDAO);
    }

    public ProfesorDAO getProfesorSetDAO() {
        return this.profesorSetDAO;
    }

    public ProfesorService getProfesorServiceSet() {
        return this.profesorServiceSet;
    }

}
