package tec.bd.app;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tec.bd.app.domain.Curso;
import tec.bd.app.domain.Estudiante;
import tec.bd.app.domain.Profesor;
import tec.bd.app.service.CursoService;
import tec.bd.app.service.EstudianteService;
import tec.bd.app.service.ProfesorService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class App  {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public static void main(String[] args) {

        ApplicationContext applicationContext = ApplicationContext.init();
        var estudianteService = applicationContext.getEstudianteService();
        var cursoService = applicationContext.getCursoService();
        var profesorService = applicationContext.getProfesorService();

        Options options = new Options();

        //------------------------------------------------------------------------
        // Opciones para estudiante

        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Ayuda: ver argumentos soportados")
                .required(false)
                .build());

        options.addOption(Option.builder("ec")
                .longOpt("estudiante-nuevo")
                .hasArg(true)
                .numberOfArgs(5)
                .desc("Agregar Estudiante: carne, nombre, apellido, fecha de nacimiento y total creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("er")
                .longOpt("estudiate-ver-todos")
                .desc("Ver todos los estudiantes")
                .required(false)
                .build());

        options.addOption(Option.builder("eid")
                .longOpt("estudiante-por-carne")
                .hasArg()
                .desc("Ver Estudiante por carne: ver un estudiante por carne")
                .required(false)
                .build());

        options.addOption(Option.builder("eu")
                .longOpt("estudiante-actualizar")
                .numberOfArgs(5)
                .desc("Actualizar estudiante: carne, nombre, apellido, fecha de nacimiento y total creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("ed")
                .longOpt("estudiante-borrar")
                .hasArg(true)
                .desc("Borrar estudiante: el carne es requerido")
                .required(false)
                .build());

        options.addOption(Option.builder("erln")
                .longOpt("estudiante-ordenar-por-apellido")
                .desc("Ver los estudiantes ordenados por apellido")
                .required(false)
                .build());

        options.addOption(Option.builder("eln")
                .longOpt("estudiante-buscar-por-apellido")
                .hasArg(true)
                .desc("Buscar los estudiantes por apellido")
                .required(false)
                .build());

        //------------------------------------------------------------------------
        // Opciones para curso

        options.addOption(Option.builder("cc")
                .longOpt("curso-nuevo")
                .hasArg(true)
                .numberOfArgs(4)
                .desc("Agregar Curso: id, nombre, departamento, creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("cr")
                .longOpt("cursos-ver-todos")
                .desc("Ver todos los cursos.")
                .required(false)
                .build());

        options.addOption(Option.builder("cid")
                .longOpt("curso-por-id")
                .hasArg()
                .desc("Ver curso por id")
                .required(false)
                .build());

        options.addOption(Option.builder("cu")
                .longOpt("curso-actualizar")
                .numberOfArgs(4)
                .desc("Actualizar curso: id, nombre, departamento, creditos son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("cd")
                .longOpt("curso-borrar")
                .hasArg(true)
                .desc("Borrar curso: el id es requerido")
                .required(false)
                .build());

        options.addOption(Option.builder("crd")
                .longOpt("curso-ver-por-departamento")
                .hasArg(true)
                .desc("Ver curso por departamento")
                .required(false)
                .build());

        //------------------------------------------------------------------------
        // Opciones para profesor

        options.addOption(Option.builder("pc")
                .longOpt("profesor-nuevo")
                .hasArg(true)
                .numberOfArgs(4)
                .desc("Agregar Profesor: id, nombre, apellido, ciudad son requeridos.")
                .required(false)
                .build());

        options.addOption(Option.builder("pr")
                .longOpt("profesor-ver-todos")
                .desc("Ver todos los profesores.")
                .required(false)
                .build());

        options.addOption(Option.builder("pid")
                .longOpt("profesor-por-id")
                .hasArg()
                .desc("Ver profesor por id")
                .required(false)
                .build());

        options.addOption(Option.builder("pu")
                .longOpt("profesor-actualizar")
                .numberOfArgs(4)
                .desc("Actualizar profesor: id, nombre, apellido, ciudad son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("pd")
                .longOpt("profesor-borrar")
                .hasArg(true)
                .desc("Borrar profesor: el id es requerido")
                .required(false)
                .build());

        options.addOption(Option.builder("prc")
                .longOpt("profesor-por-ciudad")
                .hasArg(true)
                .desc("Ver profesores por ciudad")
                .required(false)
                .build());

        CommandLineParser parser = new DefaultParser();

        try {
            var cmd = parser.parse(options, args);

            //------------------------------------------------------------------------
            // Opciones para estudiante

            if(cmd.hasOption("er")) {
                // Mostrar todos los estudiantes
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("eid")) {
                // Mostrar un estudiante por carne
                var carne = cmd.getOptionValue("eid");
                showStudentInfo(estudianteService, Integer.parseInt(carne));
            } else if(cmd.hasOption("ec")) {
                // Crear/Agregar un nuevo estudiante
                var newStudentValues = cmd.getOptionValues("ec");
                addNewStudent(estudianteService,
                        Integer.parseInt(newStudentValues[0]),
                        newStudentValues[1],
                        newStudentValues[2],
                        newStudentValues[3],
                        Integer.parseInt(newStudentValues[4]));
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("ed")) {
                // Borrar/remover un estudiante
                var carne = cmd.getOptionValue("ed");
                deleteStudent(estudianteService, Integer.parseInt(carne));
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("eu")) {
                // Actualizar datos de un estudiante
                var newStudentValues = cmd.getOptionValues("eu");
                updateStudent(estudianteService,
                        Integer.parseInt(newStudentValues[0]),
                        newStudentValues[1],
                        newStudentValues[2],
                        newStudentValues[3],
                        Integer.parseInt(newStudentValues[4]));
                showAllStudents(estudianteService);

            } else if(cmd.hasOption("erln")) {
                // Ver todos los estudiantes ordenados por apellido
                System.out.println("IMPLEMENTAR: Ver todos los estudiantes ordenados por apellido");
                showAllStudentsSortByLastName(estudianteService);

            } else if(cmd.hasOption("eln")) {
                // Ejemplo: -eln Rojas
                // Ver todos los estudiantes con un apellido en particular
                System.out.println("IMPLEMENTAR: Ver todos los estudiantes con un apellido en particular");
                var lastName = cmd.getOptionValues("eln");
                showAllStudentsfindByLastName(estudianteService, lastName[0]);
            //------------------------------------------------------------------------
            // Opciones para curso

            } else if(cmd.hasOption("cr")) {
                // Mostrar todos los cursos
                System.out.println("IMPLEMENTAR: Mostrar todos los cursos");
                showAllCourses(cursoService);

            } else if(cmd.hasOption("cid")) {
                // Mostrar un curso por id
                System.out.println("IMPLEMENTAR: Mostrar curso por id");
                var id = cmd.getOptionValue("cid");
                showCourseInfo(cursoService, Integer.parseInt(id));

            } else if(cmd.hasOption("cc")) {
                // Crear/Agregar un nuevo curso
                System.out.println("IMPLEMENTAR: Crear/Agregar un nuevo curso");
                var newCourseValues = cmd.getOptionValues("cc");
                addNewCourse(cursoService,
                        Integer.parseInt(newCourseValues[0]),
                        newCourseValues[1],
                        newCourseValues[2],
                        Integer.parseInt(newCourseValues[3]));
                showAllCourses(cursoService);


            } else if(cmd.hasOption("cd")) {
                // Borrar/remover un curso
                System.out.println("IMPLEMENTAR: Borrar/remover un curso");
                var id = cmd.getOptionValue("cd");
                deleteCourse(cursoService, Integer.parseInt(id));
                showAllCourses(cursoService);

            } else if(cmd.hasOption("cu")) {
                // Actualizar datos de un curso
                var newCourseValues = cmd.getOptionValues("cu");
                updateCourse(cursoService,
                        Integer.parseInt(newCourseValues[0]),
                        newCourseValues[1],
                        newCourseValues[2],
                        Integer.parseInt(newCourseValues[3]));
                showAllCourses(cursoService);

            } else if(cmd.hasOption("crd")) {
                // Ver cursos por departamento
                System.out.println("IMPLEMENTAR: ver cursos por departamento");
                var department = cmd.getOptionValues("crd");
                showAllCoursesSortByDepartment(cursoService, department[0]);

            //------------------------------------------------------------------------
            // Opciones para profesor

            } else if(cmd.hasOption("pr")) {
                // Mostrar todos los profesores
                System.out.println("IMPLEMENTAR: Mostrar todos los profesores");
                showAllTeachers(profesorService);

            } else if(cmd.hasOption("pid")) {
                // Mostrar un profesor por id
                System.out.println("IMPLEMENTAR: Mostrar profesor por id");
                var id = cmd.getOptionValue("pid");
                showTeacherInfo(profesorService, Integer.parseInt(id));

            } else if(cmd.hasOption("pc")) {
                // Crear/Agregar un nuevo profesor
                System.out.println("IMPLEMENTAR: Crear/Agregar un nuevo profesor");
                var newTeacherValues = cmd.getOptionValues("pc");
                addNewTeacher(profesorService,
                        Integer.parseInt(newTeacherValues[0]),
                        newTeacherValues[1],
                        newTeacherValues[2],
                        newTeacherValues[3]);
                showAllTeachers(profesorService);

            } else if(cmd.hasOption("pd")) {
                // Borrar/remover un profesor
                System.out.println("IMPLEMENTAR: Borrar/remover un profesor");
                var id = cmd.getOptionValue("pd");
                deleteTeacher(profesorService, Integer.parseInt(id));
                showAllTeachers(profesorService);

            } else if(cmd.hasOption("pu")) {
                // Actualizar datos de un profesor
                System.out.println("IMPLEMENTAR: Actualizar datos de un profesor");
                var newTeacherValues = cmd.getOptionValues("pu");
                updateTeacher(profesorService,
                        Integer.parseInt(newTeacherValues[0]),
                        newTeacherValues[1],
                        newTeacherValues[2],
                        newTeacherValues[3]);
                showAllTeachers(profesorService);

            } else if(cmd.hasOption("prc")) {
                // Ver profesores por ciudad
                System.out.println("IMPLEMENTAR: ver profesores por ciudad");
                var city = cmd.getOptionValues("prc");
                showAllTeacherSortByCity(profesorService, city[0]);
                //------------------------------------------------------------------------


            //------------------------------------------------------------------------


            } else if(cmd.hasOption("h")) {
                var formatter = new HelpFormatter();
                formatter.printHelp( "Estos son los commandos soportados", options );
            } else {
                var formatter = new HelpFormatter();
                formatter.printHelp( "Estos son los comandos soportados", options );
            }
        } catch (ParseException pe) {
            System.out.println("Error al parsear los argumentos de linea de comandos!");
            System.out.println("Por favor, seguir las siguientes instrucciones:");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "Los siguientes son los comandos soportados", options );
            System.exit(1);
        }
    }

    //Estudiantes
    public static void showAllStudents(EstudianteService estudianteService) {

        System.out.println("\n\n");
        System.out.println("Lista de Estudiantes");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Carne\t\tNombre\t\tApellido\tFecha Nacimiento\tCreditos");
        System.out.println("-----------------------------------------------------------------------");
        for(Estudiante estudiante : estudianteService.getAll()) {
            System.out.println(estudiante.getCarne() + "\t\t" + estudiante.getNombre() + "\t\t" +estudiante.getApellido() + "\t\t"+ estudiante.getFechaNacimiento() + "\t\t" + estudiante.getTotalCreditos());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showStudentInfo(EstudianteService estudianteService, int carne) {
        Optional<Estudiante> estudiante = estudianteService.getById(carne);
        if(estudiante.isPresent()) {
            System.out.println("Estudiante: " + estudiante.get().getNombre() + " " + estudiante.get().getApellido());
            System.out.println("Carne: " + estudiante.get().getCarne());
        } else {
            System.out.println("Estudiante con carne: " + carne + " no existe");
        }
    }

    private static void showAllStudentsSortByLastName(EstudianteService estudianteService) {
        System.out.println("\n\n");
        System.out.println("Lista de Estudiantes ordenados por apellido");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Carne\t\tNombre\t\tApellido\tFecha de nacimiento\tTotal de creditos");
        System.out.println("-----------------------------------------------------------------------");
        for(Estudiante estudiante : estudianteService.getStudentsSortedByLastName()) {
            System.out.println(estudiante.getCarne() + "\t\t" + estudiante.getNombre() + "\t\t" +estudiante.getApellido() + "\t\t"+ estudiante.getFechaNacimiento() + "\t\t" +estudiante.getTotalCreditos());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showAllStudentsfindByLastName(EstudianteService estudianteService, String lastName){
        System.out.println("\n\n");
        System.out.println("Lista de Estudiantes ordenados por apellido");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Carne\t\tNombre\t\tApellido\tFecha de nacimiento\tTotal de creditos");
        System.out.println("-----------------------------------------------------------------------");
        for(Estudiante estudiante : estudianteService.getStudentsByLastName(lastName)) {
            System.out.println(estudiante.getCarne() + "\t\t" + estudiante.getNombre() + "\t\t" +estudiante.getApellido() + "\t\t"+ estudiante.getFechaNacimiento() + "\t\t" +estudiante.getTotalCreditos());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void addNewStudent(EstudianteService estudianteService, int carne, String nombre, String apellido,
                                     String fechaNacimientoString, int totalCreditos) {
        var fechaNacimiento = dateFromString(fechaNacimientoString);
        var nuevoEstudiante = new Estudiante(carne,nombre, apellido, fechaNacimiento, totalCreditos);
        estudianteService.addNew(nuevoEstudiante);
    }

    public static void deleteStudent(EstudianteService estudianteService, int carne) {
        estudianteService.deleteStudent(carne);
    }

    public static void updateStudent(EstudianteService estudianteService, int carne, String nombre, String apellido,
                                     String fechaNacimientoString, int totalCreditos) {
        var fechaNacimiento = dateFromString(fechaNacimientoString);
        var nuevoEstudiante = new Estudiante(carne,nombre, apellido, fechaNacimiento, totalCreditos);
        estudianteService.updateStudent(nuevoEstudiante);
    }

    private static Date dateFromString(String fecha) {
        Objects.requireNonNull(fecha, "Debe de proporcionar una fecha");
        try {
            return DATE_FORMAT.parse(fecha);
        } catch (java.text.ParseException e) {
            throw new RuntimeException("La fecha proporcionada es invalida", e);
        }
    }

    //Cursos
    public static void showAllCourses(CursoService cursoService){
        System.out.println("\n\n");
        System.out.println("Lista de Cursos");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Id\t\tNombre\t\tDepartamento\tCreditos");
        System.out.println("-----------------------------------------------------------------------");
        for(Curso curso : cursoService.getAll()) {
            System.out.println(curso.getId() + "\t\t" + curso.getNombre() + "\t\t" + curso.getDepartamento() + "\t\t"+ curso.getCreditos());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showCourseInfo(CursoService cursoService, int id) {
        Optional<Curso> curso = cursoService.getById(id);
        if(curso.isPresent()) {
            System.out.println("Curso: " + curso.get().getNombre() + " " + curso.get().getCreditos());
            System.out.println("Id: " + curso.get().getId());
        } else {
            System.out.println("Curso con id: " + id + " no existe");
        }
    }

    private static void showAllCoursesSortByDepartment(CursoService cursoService, String department) {
        System.out.println("\n\n");
        System.out.println("Lista de cursos ordenados por departamento");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Id\t\tNombre\t\tDepartamento\tCreditos");
        System.out.println("-----------------------------------------------------------------------");
        for(Curso curso : cursoService.findByDepartment(department)) {
            System.out.println(curso.getId() + "\t\t" + curso.getNombre() + "\t\t" + curso.getDepartamento() + "\t\t" + curso.getCreditos());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showAllCoursesSortByName(CursoService cursoService, String nombre){
        System.out.println("\n\n");
        System.out.println("Lista de cursos ordenados por nombre");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Id\t\tNombre\t\tDepartamento\tCreditos");
        System.out.println("-----------------------------------------------------------------------");
        for(Curso curso : cursoService.getCourseByDepartment(nombre)) {
            System.out.println(curso.getId() + "\t\t" + curso.getNombre() + "\t\t"+ curso.getDepartamento() + "\t\t" +curso.getCreditos());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void addNewCourse(CursoService cursoService, int id, String nombre, String departamento, int creditos) {
        var nuevoCurso = new Curso(id,nombre, departamento, creditos);
        cursoService.addNew(nuevoCurso);
    }

    public static void deleteCourse(CursoService cursoService, int id) {
        cursoService.deleteCourse(id);
    }

    public static void updateCourse(CursoService cursoService, int id, String nombre, String departamento, int creditos) {
        var nuevoCurso = new Curso(id,nombre, departamento, creditos);
        cursoService.updateCourse(nuevoCurso);
    }

    //Profesores
    public static void showAllTeachers(ProfesorService profesorService) {
        System.out.println("\n\n");
        System.out.println("Lista de Profesores");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Id\t\tNombre\t\tApellido\tCiudad");
        System.out.println("-----------------------------------------------------------------------");
        for(Profesor profesor : profesorService.getAll()) {
            System.out.println(profesor.getId() + "\t\t" + profesor.getNombre() + "\t\t" +profesor.getApellido() + "\t\t"+ profesor.getCiudad());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showTeacherInfo(ProfesorService profesorService, int id) {
        Optional<Profesor> profesor = profesorService.getById(id);
        if(profesor.isPresent()) {
            System.out.println("Profesor: " + profesor.get().getNombre() + " " + profesor.get().getApellido());
            System.out.println("Id: " + profesor.get().getId());
        } else {
            System.out.println("Profesor con id: " + id + " no existe");
        }
    }

    public static void showAllTeacherSortByLastName(ProfesorService profesorService, String lastName){
        System.out.println("\n\n");
        System.out.println("Lista de Profesores ordenados por apellido");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Id\t\tNombre\t\tApellido\tCiudad");
        System.out.println("-----------------------------------------------------------------------");
        for(Profesor profesor : profesorService.getTeachersByLastName(lastName)) {
            System.out.println(profesor.getId() + "\t\t" + profesor.getNombre() + "\t\t" +profesor.getApellido() + "\t\t"+ profesor.getCiudad());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showAllTeacherSortByCity(ProfesorService profesorService, String city){
        System.out.println("\n\n");
        System.out.println("Lista de Profesores ordenados por ciudad");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Id\t\tNombre\t\tApellido\tCiudad");
        System.out.println("-----------------------------------------------------------------------");
        for(Profesor profesor : profesorService.getTeachersByCity(city)) {
            System.out.println(profesor.getId() + "\t\t" + profesor.getNombre() + "\t\t" +profesor.getApellido() + "\t\t"+ profesor.getCiudad());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void addNewTeacher(ProfesorService profesorService, int id, String nombre, String apellido, String ciudad) {
        var nuevoProfesor = new Profesor(id,nombre, apellido, ciudad);
        profesorService.addNew(nuevoProfesor);
    }

    public static void deleteTeacher(ProfesorService profesorService, int id) {
        profesorService.deleteTeacher(id);
    }

    public static void updateTeacher(ProfesorService profesorService, int id, String nombre, String apellido, String ciudad) {
        var nuevoProfesor = new Profesor(id,nombre, apellido, ciudad);
        profesorService.updateTeacher(nuevoProfesor);
    }
}
