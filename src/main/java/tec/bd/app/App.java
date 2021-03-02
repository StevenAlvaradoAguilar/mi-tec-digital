package tec.bd.app;

import org.apache.commons.cli.*;
import tec.bd.app.bd.SetDB;
import tec.bd.app.domain.Estudiante;
import tec.bd.app.service.EstudianteService;

import java.util.HashSet;

public class App  {


    public static EstudianteService init() {
        var juan = new Estudiante(1L, "Juan", "Perez", 20);
        var maria = new Estudiante(2L, "Maria", "Rojas", 21);
        var pedro = new Estudiante(3L, "Pedro", "Infante", 23);

        var database = new SetDB(new HashSet<>() {{
            add(juan);
            add(maria);
            add(pedro);
        }});

        return  new EstudianteService(database);
    }


    public static void main(String[] args) {

        var estudianteService = init();

        Options options = new Options();
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Ayuda: ver argumentos soportados")
                .required(false)
                .build());

        options.addOption(Option.builder("c")
                .longOpt("nuevo")
                .hasArg(true)
                .numberOfArgs(4)
                .desc("Agregar Estudiante: carne, nombre y apellido son requeridos. --nuevo <carne> <nombre> <apellido>")
                .required(false)
                .build());

        options.addOption(Option.builder("r")
                .longOpt("ver")
                .desc("Ver Estudiantes: ver todos los estudiantes. --ver")
                .required(false)
                .build());

        options.addOption(Option.builder("eid")
                .longOpt("carne")
                .hasArg()
                .desc("Ver Estudiante por carne: ver un estudiante por carne. --carne <carne>")
                .required(false)
                .build());

        options.addOption(Option.builder("u")
                .longOpt("actualizar")
                .numberOfArgs(4)
                .desc("carne, nombre y apellido son requeridos")
                .required(false)
                .build());

        options.addOption(Option.builder("d")
                .longOpt("borrar")
                .hasArg(true)
                .desc("el carne es requerido")
                .required(false)
                .build());

        CommandLineParser parser = new DefaultParser();

        try {
            var cmd = parser.parse(options, args);

            if(cmd.hasOption("r")) {
                // Mostrar todos los estudiantes
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("eid")) {
                // Mostrar un estudiante por carne
                var carne = cmd.getOptionValue("eid");
                showStudentInfo(estudianteService, Long.parseLong(carne));
            } else if(cmd.hasOption("c")) {
                // Crear/Agregar un nuevo estudiante
                var newStudentValues = cmd.getOptionValues("c");
                addNewStudent(estudianteService,
                        Long.parseLong(newStudentValues[0]),
                        newStudentValues[1],
                        newStudentValues[2],
                        Integer.parseInt(newStudentValues[3]));
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("d")) {
                // Borrar/remover un  estudiante
                var carne = cmd.getOptionValue("d");
                deleteStudent(estudianteService, Long.parseLong(carne));
                showAllStudents(estudianteService);
            } else if(cmd.hasOption("u")) {
                // Actualizar datos de un estudiante
                var newStudentValues = cmd.getOptionValues("u");
                updateStudent(estudianteService,
                        Long.parseLong(newStudentValues[0]),
                        newStudentValues[1],
                        newStudentValues[2],
                        Integer.parseInt(newStudentValues[3]));
                showAllStudents(estudianteService);
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
            formatter.printHelp( "Log messages to sequence diagrams converter", options );
            System.exit(1);
        }
    }

    public static void showAllStudents(EstudianteService estudianteService) {

        System.out.println("\n\n");
        System.out.println("Lista de Estudiantes");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Carne\t\tNombre\t\tApellido\tEdad");
        System.out.println("-----------------------------------------------------------------------");
        for(Estudiante estudiante : estudianteService.getAll()) {
            System.out.println(estudiante.getCarne() + "\t\t" + estudiante.getNombre() + "\t\t" +estudiante.getApellido() + "\t\t"+ estudiante.getEdad());
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("\n\n");
    }

    public static void showStudentInfo(EstudianteService estudianteService, Long carne) {
        Estudiante estudiante = estudianteService.getById(carne);
        if(null != estudiante) {
            System.out.println("Estudiante: " + estudiante.getNombre() + " " + estudiante.getApellido());
            System.out.println("Carne: " + estudiante.getCarne());
        } else {
            System.out.println("Estudiante con carne: " + carne + " no existe");
        }
    }

    public static void addNewStudent(EstudianteService estudianteService, Long carne, String nombre, String apellido, int edad) {
        var nuevoEstudiante = new Estudiante(carne,nombre, apellido, edad);
        estudianteService.addNew(nuevoEstudiante);
    }

    public static void deleteStudent(EstudianteService estudianteService, Long carne) {
        estudianteService.deleteStudent(carne);
    }

    public static void updateStudent(EstudianteService estudianteService, Long carne, String nombre, String apellido, int edad) {
        var nuevoEstudiante = new Estudiante(carne,nombre, apellido, edad);
        estudianteService.updateStudent(nuevoEstudiante);
    }
}
